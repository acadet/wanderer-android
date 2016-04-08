package com.adriencadet.wanderer.dao;

import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.functions.Func1;

/**
 * CachingModule
 * <p>
 */
class CachingModule {
    <T extends ICachingDTO> void merge(Realm realm, List<T> freshData, List<T> oldData, Func1<T, RealmObject> converter, int cachingDurationMins) {
        int i1, i2, s1, s2;
        List<T> toAdd = new ArrayList<>(), toDelete = new ArrayList<>();

        realm.beginTransaction();

        i1 = 0;
        i2 = 0;
        s1 = freshData.size();
        s2 = oldData.size();

        while (i1 < s1 && i2 < s2) {
            T freshObject = freshData.get(i1), oldObject = oldData.get(i2);

            if (freshObject.getId() == oldObject.getId()) { // Update
                toDelete.add(oldObject);
                toAdd.add(freshObject);
                i1++;
                i2++;
            } else if (freshObject.getId() < oldObject.getId()) { // Addition
                toAdd.add(freshObject);
                i1++;
            } else { // Deletion
                if (new DateTime(oldObject.getUpdatedAt()).plusMinutes(cachingDurationMins).isBeforeNow()) {
                    toDelete.add(oldObject);
                }
                i2++;
            }
        }

        while (i1 < s1) {
            T o = freshData.get(i1);
            toAdd.add(o);
            i1++;
        }

        while (i2 < s2) {
            T o = oldData.get(i2);
            if (new DateTime(o.getUpdatedAt()).plusMinutes(cachingDurationMins).isBeforeNow()) {
                toDelete.add(o);
            }
            i2++;
        }

        Stream.of(toDelete).forEach((e) -> e.removeFromRealm());
        Stream.of(toAdd).forEach((e) -> {
            e.setUpdatedAt(DateTime.now().toDate());
            realm.copyToRealm(converter.call(e));
        });

        realm.commitTransaction();
    }
}
