package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;
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
        PictureDTO picture;
        Picture outcome = null;

        picture = find(realm, id);

        if (picture != null) {
            outcome = pictureSerializer.fromDAO(picture);
        }

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
    public void save(Place relatedPlace, Picture picture) {
        Realm realm = getRealm();
        PictureDTO existingEntry = find(realm, picture.getId());
        PictureDTO newEntry = pictureSerializer.toDAO(picture);

        realm.beginTransaction();
        if (existingEntry != null) {
            existingEntry.removeFromRealm();
        }
        newEntry.setPlaceID(relatedPlace.getId());
        newEntry.setUpdatedAt(DateTime.now().toDate());
        realm.copyToRealm(newEntry);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void save(Place relatedPlace, List<Picture> pictures) {
        Realm realm = getRealm();

        //TODO: refactor
        cachingModule.merge(
            realm,
            (List<PictureDTO>) Stream
                .of(pictureSerializer.toDAO(pictures))
                .map((e) -> {
                    e.setPlaceID(relatedPlace.getId());
                    return e;
                })
                .sortBy(PictureDTO::getId)
                .collect(Collectors.toList()),
            realm.allObjectsSorted(PictureDTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PICTURE_MAX_CACHING_DURATION_MINS
        );

        realm.close();
    }
}
