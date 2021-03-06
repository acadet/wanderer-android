package com.adriencadet.wanderer.ui.controllers.body;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.bll.BLLErrors;
import com.adriencadet.wanderer.ui.adapters.PlaceListAdapter;
import com.adriencadet.wanderer.ui.controllers.ApplicationController;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * PlaceListController
 * <p>
 */
public class PlaceListController extends ApplicationController {
    private Subscription listPlacesSubscription;
    private List<Place>  placeListBuffer;

    @Bind(R.id.place_list_menu_listview)
    ListView listView;

    @Bind(R.id.place_list_menu_no_content_label)
    TextView noContentLabelView;

    private void fadeIn(View view) {
        view.setVisibility(View.VISIBLE);
        YoYo
            .with(Techniques.FadeIn)
            .duration(300)
            .playOn(view);
    }

    @Override
    protected int layoutId() {
        return R.layout.place_list;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        WandererApplication.getApplicationComponent().inject(this);

        showSpinner();
        listPlacesSubscription = dataReadingBLL
            .listPlacesByVisitDateDesc()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSubscriber<List<Place>>() {
                @Override
                public void onCompleted() {
                    if (placeListBuffer.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        fadeIn(noContentLabelView);
                    } else {
                        listView.setAdapter(new PlaceListAdapter(context, placeListBuffer, appRouter));
                        noContentLabelView.setVisibility(View.GONE);
                        fadeIn(listView);
                    }
                    hideSpinner();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);

                    if (placeListBuffer.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        fadeIn(noContentLabelView);
                    } else {
                        boolean mustLoadPictures = !(e instanceof BLLErrors.NoConnection);

                        listView.setAdapter(new PlaceListAdapter(context, placeListBuffer, appRouter, mustLoadPictures));
                        noContentLabelView.setVisibility(View.GONE);
                        fadeIn(listView);
                    }

                    hideSpinner();
                }

                @Override
                public void onNext(List<Place> places) {
                    placeListBuffer = places;
                }
            });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (listPlacesSubscription != null) {
            listPlacesSubscription.unsubscribe();
        }
    }
}
