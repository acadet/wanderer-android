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
                p2.setLatitude(p1.getLatitude());
                p2.setLongitude(p1.getLongitude());
                p2.setDescription(p1.getDescription());
                p2.setCountry(p1.getCountry());
                p2.setLikes(p1.getLikes());
                p2.setMainPictureID(p1.getMainPictureID());
                p2.setName(p1.getName());
                p2.setVisitDate(p1.getVisitDate());

                p2.setUpdatedAt(DateTime.now().toDate());
                i1++;
                i2++;
            } else if (p1.getId() < p2.getId()) { // Addition
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
            realm.copyToRealm(sortedToSavePlaces.get(i1));
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
    public PlaceDAODTO randomEntry() {
        List<PlaceDAODTO> source = getRealm().allObjects(PlaceDAODTO.class);

        return source.get((int) Math.round(Math.random() * (source.size() - 1)));
    }
}
