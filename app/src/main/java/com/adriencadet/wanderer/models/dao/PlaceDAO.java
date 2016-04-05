package com.adriencadet.wanderer.models.dao;

import android.content.Context;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.dao.dto.PlaceDAODTO;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * PlaceDAO
 * <p>
 */
class PlaceDAO extends BaseDAO implements IPlaceDAO {
    private ApplicationConfiguration configuration;

    PlaceDAO(Context context, ApplicationConfiguration configuration) {
        super(context);

        this.configuration = configuration;
    }

    private PlaceDAODTO find(Realm realm, int id) {
        return realm.where(PlaceDAODTO.class).equalTo("id", id).findFirst();
    }

    @Override
    public List<PlaceDAODTO> listPlacesByVisitDateDescJob() {
        return getRealm().where(PlaceDAODTO.class).findAllSorted("visitDate", Sort.DESCENDING);
    }

    @Override
    public void save(List<PlaceDAODTO> places) {
        Realm realm = getRealm();
        List<PlaceDAODTO> sortedToSavePlaces, sortedSavedPlaces;
        int i1, i2, s1, s2;

        // TODO : factorize with picture
        realm.beginTransaction();

        sortedToSavePlaces = Stream.of(places).sortBy(PlaceDAODTO::getId).collect(Collectors.toList());
        sortedSavedPlaces = realm.allObjectsSorted(PlaceDAODTO.class, "id", Sort.ASCENDING);

        i1 = 0;
        i2 = 0;
        s1 = sortedToSavePlaces.size();
        s2 = sortedSavedPlaces.size();

        while (i1 < s1 && i2 < s2) {
            PlaceDAODTO p1 = sortedToSavePlaces.get(i1), p2 = sortedSavedPlaces.get(i2);

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
                if (new DateTime(p2.getUpdatedAt()).plusMinutes(configuration.PLACE_MAX_CACHING_DURATION_MINS).isBeforeNow()) {
                    p2.removeFromRealm();
                }
                i2++;
            }
        }


        while (i1 < s1) {
            PlaceDAODTO p = sortedToSavePlaces.get(i1);
            p.setUpdatedAt(DateTime.now().toDate());
            realm.copyToRealm(p);
            i1++;
        }

        while (i2 < s2) {
            PlaceDAODTO p = sortedSavedPlaces.get(i2);
            if (new DateTime(p.getUpdatedAt()).plusMinutes(configuration.PLACE_MAX_CACHING_DURATION_MINS).isBeforeNow()) {
                p.removeFromRealm();
            }
            i2++;
        }

        realm.commitTransaction();
    }

    @Override
    public PlaceDAODTO toggleLike(int placeID) {
        Realm realm = getRealm();
        PlaceDAODTO place = find(realm, placeID);

        realm.beginTransaction();
        place.setLikes(place.isLiking() ? place.getLikes() - 1 : place.getLikes() + 1);
        place.setLiking(!place.isLiking());
        place.setUpdatedAt(DateTime.now().toDate());
        realm.commitTransaction();

        return place;
    }

    @Override
    public boolean hasEntries() {
        return getRealm().allObjects(PlaceDAODTO.class).size() > 0;
    }

    @Override
    public PlaceDAODTO randomEntry() {
        List<PlaceDAODTO> source = getRealm().allObjects(PlaceDAODTO.class);

        return source.get((int) Math.round(Math.random() * (source.size() - 1)));
    }
}
