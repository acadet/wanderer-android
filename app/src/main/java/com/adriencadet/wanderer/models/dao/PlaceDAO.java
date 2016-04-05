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
    private CachingModule            cachingModule;

    PlaceDAO(Context context, ApplicationConfiguration configuration, CachingModule cachingModule) {
        super(context);

        this.configuration = configuration;
        this.cachingModule = cachingModule;
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

        cachingModule.merge(
            realm,
            (List<PlaceDAODTO>) Stream.of(places).sortBy(PlaceDAODTO::getId).collect(Collectors.toList()),
            realm.allObjectsSorted(PlaceDAODTO.class, "id", Sort.ASCENDING),
            (e) -> e,
            configuration.PLACE_MAX_CACHING_DURATION_MINS
        );
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
