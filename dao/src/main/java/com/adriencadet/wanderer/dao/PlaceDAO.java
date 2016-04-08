package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Place;
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
    private Configuration    configuration;
    private CachingModule    cachingModule;
    private IPlaceSerializer placeSerializer;

    PlaceDAO(RealmConfiguration realmConfiguration, Configuration configuration, CachingModule cachingModule, IPlaceSerializer placeSerializer) {
        super(realmConfiguration);

        this.configuration = configuration;
        this.cachingModule = cachingModule;
        this.placeSerializer = placeSerializer;
    }

    private PlaceDTO find(Realm realm, int id) {
        return realm.where(PlaceDTO.class).equalTo("id", id).findFirst();
    }

    @Override
    public List<Place> listPlacesByVisitDateDescJob() {
        Realm realm = getRealm();
        List<Place> outcome = placeSerializer.fromDAO(realm.where(PlaceDTO.class).findAllSorted("visitDate", Sort.DESCENDING));

        realm.close();

        return outcome;
    }

    @Override
    public void save(List<Place> places) {
        Realm realm = getRealm();

        cachingModule.merge(
            realm,
            (List<PlaceDTO>) Stream.of(placeSerializer.toDAO(places)).sortBy(PlaceDTO::getId).collect(Collectors.toList()),
            realm.allObjectsSorted(PlaceDTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PLACE_MAX_CACHING_DURATION_MINS
        );

        realm.close();
    }

    @Override
    public Place toggleLike(int placeID) {
        Realm realm = getRealm();
        PlaceDTO place = find(realm, placeID);

        realm.beginTransaction();
        place.setLikes(place.isLiking() ? place.getLikes() - 1 : place.getLikes() + 1);
        place.setLiking(!place.isLiking());
        place.setUpdatedAt(DateTime.now().toDate());
        realm.commitTransaction();

        realm.close();

        return placeSerializer.fromDAO(place);
    }

    @Override
    public boolean hasEntries() {
        Realm realm = getRealm();
        boolean outcome = realm.allObjects(PlaceDTO.class).size() > 0;
        realm.close();
        return outcome;
    }

    @Override
    public Place randomEntry() {
        Realm realm = getRealm();
        List<PlaceDTO> source = realm.allObjects(PlaceDTO.class);
        Place outcome = placeSerializer.fromDAO(source.get((int) Math.round(Math.random() * (source.size() - 1))));

        realm.close();
        return outcome;
    }
}
