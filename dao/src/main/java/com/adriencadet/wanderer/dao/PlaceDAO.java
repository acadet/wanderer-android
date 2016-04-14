package com.adriencadet.wanderer.dao;

import android.content.SharedPreferences;

import com.adriencadet.wanderer.beans.Place;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

/**
 * PlaceDAO
 * <p>
 */
class PlaceDAO extends BaseDAO implements IPlaceDAO {
    private static final String PENDING_LIKES_KEY = "pending_likes";

    private Configuration     configuration;
    private SharedPreferences store;
    private CachingModule     cachingModule;
    private IPlaceSerializer  placeSerializer;

    PlaceDAO(RealmConfiguration realmConfiguration, Configuration configuration,
             SharedPreferences store, CachingModule cachingModule,
             IPlaceSerializer placeSerializer) {
        super(realmConfiguration);

        this.configuration = configuration;
        this.store = store;
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
    public Place toggleLike(Place place) {
        Realm realm = getRealm();
        PlaceDTO entry = placeSerializer.toDAO(place);
        Place outcome;

        realm.beginTransaction();

        entry.setLikes(place.isLiking() ? place.getLikes() - 1 : place.getLikes() + 1);
        entry.setLiking(!place.isLiking());
        entry.setUpdatedAt(DateTime.now().toDate());

        realm.commitTransaction();

        outcome = placeSerializer.fromDAO(entry);

        realm.close();

        return outcome;
    }

    @Override
    public void getPendingLikes(Collection<Integer> collectionToHydrate) {
        String serializedIntegers = store.getString(PENDING_LIKES_KEY, null);
        String[] ids;

        if (serializedIntegers == null) {
            return;
        }

        ids = serializedIntegers.split(":");

        for (int i = 0; i < ids.length; i++) {
            collectionToHydrate.add(Integer.getInteger(ids[i]));
        }
    }

    @Override
    public void savePendingLikes(Collection<Integer> placeIds) {
        if (placeIds.isEmpty()) {
            store.edit().remove(PENDING_LIKES_KEY).commit();
        } else {
            String serializedIntegers;
            serializedIntegers = Stream.of(placeIds).map((e) -> e.toString()).collect(Collectors.joining(":"));

            store.edit().putString(PENDING_LIKES_KEY, serializedIntegers).commit();
        }
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
