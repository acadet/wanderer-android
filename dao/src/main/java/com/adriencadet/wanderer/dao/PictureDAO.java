package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Picture;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

/**
 * PictureDAO
 * <p>
 */
class PictureDAO extends BaseDAO implements IPictureDAO {
    private Configuration      configuration;
    private CachingModule      cachingModule;
    private IPictureSerializer pictureSerializer;

    PictureDAO(RealmConfiguration realmConfiguration, Configuration configuration, CachingModule cachingModule, IPictureSerializer pictureSerializer) {
        super(realmConfiguration);

        this.configuration = configuration;
        this.cachingModule = cachingModule;
        this.pictureSerializer = pictureSerializer;
    }

    private PictureDTO find(Realm realm, int id) {
        return realm.where(PictureDTO.class).equalTo("id", id).findFirst();
    }

    @Override
    public Picture find(int id) {
        Realm realm = getRealm();
        PictureDTO picture = find(realm, id);
        Picture outcome = pictureSerializer.fromDAO(picture);

        realm.close();

        return outcome;
    }

    @Override
    public List<Picture> listForPlace(int placeID) {
        Realm realm = getRealm();
        List<Picture> outcome;

        outcome = pictureSerializer.fromDAO(realm.where(PictureDTO.class).equalTo("placeID", placeID).findAllSorted("id"));

        realm.close();

        return outcome;
    }

    @Override
    public void save(Picture picture) {
        Realm realm = getRealm();
        PictureDTO existingEntry = find(realm, picture.getId());
        PictureDTO newEntry = pictureSerializer.toDAO(picture);

        realm.beginTransaction();
        if (existingEntry != null) {
            existingEntry.removeFromRealm();
        }
        newEntry.setUpdatedAt(DateTime.now().toDate());
        realm.copyToRealm(newEntry);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void save(List<Picture> pictures) {
        Realm realm = getRealm();

        cachingModule.merge(
            realm,
            (List<PictureDTO>) Stream.of(pictureSerializer.toDAO(pictures)).sortBy(PictureDTO::getId).collect(Collectors.toList()),
            realm.allObjectsSorted(PictureDTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PICTURE_MAX_CACHING_DURATION_MINS
        );

        realm.close();
    }
}
