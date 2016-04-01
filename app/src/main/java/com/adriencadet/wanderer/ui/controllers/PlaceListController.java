package com.adriencadet.wanderer.ui.controllers;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.adapters.PlaceListAdapter;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * PlaceListController
 * <p>
 */
public class PlaceListController extends BaseController {
    private Subscription listPlacesSubscription;

    @Inject
    @Named("segue")
    EventBus segueBus;

    @Bind(R.id.place_list_menu_listview)
    ListView listView;

    @Bind(R.id.place_list_menu_no_content_label)
    TextView noContentLabelView;

    @Override
    protected int layoutId() {
        return R.layout.place_list;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        WandererApplication.getApplicationComponent().inject(this);

        segueBus.register(this);

        showSpinner();
        listPlacesSubscription = dataReadingBLL
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
                    Action1<View> showView = (v) -> {
                        v.setVisibility(View.VISIBLE);
                        YoYo
                            .with(Techniques.FadeIn)
                            .duration(300)
                            .playOn(v);
                    };

                    if (placeBLLDTOs.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        showView.call(noContentLabelView);
                    } else {
                        listView.setAdapter(new PlaceListAdapter(context, placeBLLDTOs));
                        noContentLabelView.setVisibility(View.GONE);
                        showView.call(listView);
                    }
                }
            });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        segueBus.unregister(this);

        if (listPlacesSubscription != null) {
            listPlacesSubscription.unsubscribe();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlaceInsight(SegueEvents.ShowPlaceInsight event) {
        appRouter.goTo(new PlaceInsightScreen(event.place));
    }
}
