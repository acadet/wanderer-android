package com.adriencadet.wanderer.models.dao;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
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
    private ApplicationConfiguration configuration;
    private CachingModule            cachingModule;

    PictureDAO(RealmConfiguration realmConfiguration, ApplicationConfiguration configuration, CachingModule cachingModule) {
        super(realmConfiguration);

        this.configuration = configuration;
        this.cachingModule = cachingModule;
    }

    private PictureDAODTO find(Realm realm, int id, boolean mustClose) {
        PictureDAODTO outcome = realm.where(PictureDAODTO.class).equalTo("id", id).findFirst();

        if (mustClose) {
            realm.close();
        }

        return outcome;
    }

    @Override
    public PictureDAODTO find(int id) {
        return find(getRealm(), id, true);
    }

    @Override
    public List<PictureDAODTO> listForPlace(int placeID) {
        Realm realm = getRealm();
        List<PictureDAODTO> outcome;

        outcome = realm.where(PictureDAODTO.class).equalTo("placeID", placeID).findAllSorted("id");
        realm.close();

        return outcome;
    }

    @Override
    public void save(PictureDAODTO picture) {
        Realm realm = getRealm();
        PictureDAODTO existingEntry = find(realm, picture.getId(), false);

        realm.beginTransaction();
        if (existingEntry != null) {
            existingEntry.removeFromRealm();
        }
        picture.setUpdatedAt(DateTime.now().toDate());
        realm.copyToRealm(picture);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void save(List<PictureDAODTO> pictures) {
        Realm realm = getRealm();

        cachingModule.merge(
            realm,
            (List<PictureDAODTO>) Stream.of(pictures).sortBy(PictureDAODTO::getId).collect(Collectors.toList()),
            realm.allObjectsSorted(PictureDAODTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PICTURE_MAX_CACHING_DURATION_MINS
        );

        realm.close();
    }
}
