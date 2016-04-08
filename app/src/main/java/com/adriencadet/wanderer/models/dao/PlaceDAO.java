package com.adriencadet.wanderer.models.dao;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.dao.dto.PlaceDAODTO;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

/**
 * PlaceDAO
 * <p>
 */
class PlaceDAO extends BaseDAO implements IPlaceDAO {
    private ApplicationConfiguration configuration;
    private CachingModule            cachingModule;

    PlaceDAO(RealmConfiguration realmConfiguration, ApplicationConfiguration configuration, CachingModule cachingModule) {
        super(realmConfiguration);

        this.configuration = configuration;
        this.cachingModule = cachingModule;
    }

    private PlaceDAODTO find(Realm realm, int id, boolean mustClose) {
        PlaceDAODTO outcome = realm.where(PlaceDAODTO.class).equalTo("id", id).findFirst();
        if (mustClose) {
            realm.close();
        }
        return outcome;
    }

    @Override
    public List<PlaceDAODTO> listPlacesByVisitDateDescJob() {
        Realm realm = getRealm();
        List<PlaceDAODTO> outcome;

        outcome = realm.where(PlaceDAODTO.class).findAllSorted("visitDate", Sort.DESCENDING);
        realm.close();

        return outcome;
    }

    @Override
    public void save(List<PlaceDAODTO> places) {
        Realm realm = getRealm();

        cachingModule.merge(
            realm,
            (List<PlaceDAODTO>) Stream.of(places).sortBy(PlaceDAODTO::getId).collect(Collectors.toList()),
            realm.allObjectsSorted(PlaceDAODTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PLACE_MAX_CACHING_DURATION_MINS
        );

        realm.close();
    }

    @Override
    public PlaceDAODTO toggleLike(int placeID) {
        Realm realm = getRealm();
        PlaceDAODTO place = find(realm, placeID, false);

        realm.beginTransaction();
        place.setLikes(place.isLiking() ? place.getLikes() - 1 : place.getLikes() + 1);
        place.setLiking(!place.isLiking());
        place.setUpdatedAt(DateTime.now().toDate());
        realm.commitTransaction();

        realm.close();

        return place;
    }

    @Override
    public boolean hasEntries() {
        Realm realm = getRealm();
        boolean outcome = realm.allObjects(PlaceDAODTO.class).size() > 0;
        realm.close();
        return outcome;
    }

    @Override
    public PlaceDAODTO randomEntry() {
        Realm realm = getRealm();
        List<PlaceDAODTO> source = realm.allObjects(PlaceDAODTO.class);

        realm.close();
        return source.get((int) Math.round(Math.random() * (source.size() - 1)));
    }
}
