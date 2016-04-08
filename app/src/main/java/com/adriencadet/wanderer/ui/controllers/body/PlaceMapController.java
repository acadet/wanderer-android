package com.adriencadet.wanderer.ui.controllers.body;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.adriencadet.wanderer.ui.adapters.PlaceWindowAdapter;
import com.adriencadet.wanderer.ui.controllers.ApplicationController;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.annimon.stream.Stream;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * PlaceMapController
 * <p>
 */
public class PlaceMapController extends ApplicationController implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private Subscription listPlacesByVisitDateDescSubscription;

    private PlaceWindowAdapter       placeWindowAdapter;
    private Map<Marker, PlaceBLLDTO> markerPlaceHash;

    @Inject
    FragmentManager fragmentManager;

    @Override
    protected int layoutId() {
        return R.layout.place_map;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        BaseActivity.getActivityComponent().inject(this);

        showSpinner();
        GoogleMapOptions mapOptions = new GoogleMapOptions()
            .compassEnabled(false)
            .rotateGesturesEnabled(false);
        MapFragment fragment = MapFragment.newInstance(mapOptions);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.place_map, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (listPlacesByVisitDateDescSubscription != null) {
            listPlacesByVisitDateDescSubscription.unsubscribe();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        placeWindowAdapter = new PlaceWindowAdapter(context);

        googleMap.setInfoWindowAdapter(placeWindowAdapter);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        listPlacesByVisitDateDescSubscription = dataReadingBLL
            .listPlacesByVisitDateDesc()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSubscriber<List<PlaceBLLDTO>>() {
                @Override
                public void onCompleted() {
                    hideSpinner();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    hideSpinner();
                }

                @Override
                public void onNext(List<PlaceBLLDTO> placeBLLDTOs) {
                    markerPlaceHash = new HashMap<>();

                    Stream
                        .of(placeBLLDTOs)
                        .forEach((e) -> {
                            markerPlaceHash.put(
                                googleMap.addMarker(
                                    new MarkerOptions()
                                        .position(new LatLng(e.getLatitude(), e.getLongitude()))
                                ),
                                e
                            );
                        });
                }
            });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        placeWindowAdapter.setItem(markerPlaceHash.get(marker));
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        PlaceBLLDTO place = markerPlaceHash.get(marker);

        appRouter.goTo(new PlaceInsightScreen(place));
    }
}
