package com.adriencadet.wanderer.models.dao;

import android.content.Context;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * PictureDAO
 * <p>
 */
class PictureDAO extends BaseDAO implements IPictureDAO {
    private ApplicationConfiguration configuration;

    PictureDAO(Context context, ApplicationConfiguration configuration) {
        super(context);
        this.configuration = configuration;
    }

    private PictureDAODTO find(Realm realm, int id) {
        return realm.where(PictureDAODTO.class).equalTo("id", id).findFirst();
    }

    @Override
    public PictureDAODTO find(int id) {
        return find(getRealm(), id);
    }

    @Override
    public List<PictureDAODTO> listForPlace(int placeID) {
        Realm realm = getRealm();

        return realm.where(PictureDAODTO.class).equalTo("placeID", placeID).findAll();
    }

    @Override
    public void save(PictureDAODTO picture) {
        Realm realm = getRealm();
        PictureDAODTO existingEntry = find(realm, picture.getId());

        realm.beginTransaction();
        if (existingEntry != null) {
            existingEntry.setUrl(picture.getUrl());
            existingEntry.setPlaceID(picture.getPlaceID());
            existingEntry.setUpdatedAt(DateTime.now().toDate());
        } else {
            realm.copyToRealm(picture);
        }
        realm.commitTransaction();
    }

    @Override
    public void save(List<PictureDAODTO> pictures) {
        Realm realm = getRealm();
        List<PictureDAODTO> sortedToSavePictures, sortedSavedPictures;
        int i1, i2, s1, s2;

        realm.beginTransaction();

        sortedToSavePictures = Stream.of(pictures).sortBy(PictureDAODTO::getId).collect(Collectors.toList());
        sortedSavedPictures = realm.allObjectsSorted(PictureDAODTO.class, "id", Sort.ASCENDING);

        i1 = 0;
        i2 = 0;
        s1 = sortedToSavePictures.size();
        s2 = sortedSavedPictures.size();

        while (i1 < s1 && i2 < s2) {
            PictureDAODTO p1 = sortedToSavePictures.get(i1), p2 = sortedSavedPictures.get(i2);

            if (p1.getId() == p2.getId()) { // Update
                p2.removeFromRealm();

                p1.setUpdatedAt(DateTime.now().toDate());
                realm.copyToRealm(p1);
                i1++;
                i2++;
            } else if (p1.getId() < p2.getId()) { // Addition
                p1.setUpdatedAt(DateTime.now().toDate());
                realm.copyToRealm(p1);
                i1++;
            } else {
                if (new DateTime(p2.getUpdatedAt()).plusMinutes(configuration.PICTURE_MAX_CACHING_DURATION_MINS).isBeforeNow()) {
                    p2.removeFromRealm();
                }
                i2++;
            }
        }

        while (i1 < s1) {
            PictureDAODTO p = sortedToSavePictures.get(i1);
            p.setUpdatedAt(DateTime.now().toDate());
            realm.copyToRealm(p);
            i1++;
        }

        while (i2 < s2) {
            PictureDAODTO p = sortedSavedPictures.get(i2);
            if (new DateTime(p.getUpdatedAt()).plusMinutes(configuration.PICTURE_MAX_CACHING_DURATION_MINS).isBeforeNow()) {
                p.removeFromRealm();
            }
            i2++;
        }

        realm.commitTransaction();
    }
}
