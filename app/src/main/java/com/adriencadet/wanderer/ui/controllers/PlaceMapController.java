package com.adriencadet.wanderer.ui.controllers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

/**
 * PlaceMapController
 * <p>
 */
public class PlaceMapController extends BaseController implements OnMapReadyCallback {

    @Inject
    FragmentManager fragmentManager;

    @Override
    protected int layoutId() {
        return R.layout.place_map;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        BaseActivity.getFragmentComponent().inject(this);

        MapFragment fragment = MapFragment.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.place_map, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
