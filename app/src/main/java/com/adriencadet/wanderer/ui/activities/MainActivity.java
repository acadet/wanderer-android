package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.IDataReadingBLL;
import com.adriencadet.wanderer.ui.components.Footer;
import com.adriencadet.wanderer.ui.components.MainUIContainer;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.screens.app.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapScreen;
import com.lyft.scoop.Scoop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    private Scoop        rootScoop;
    private Footer       footer;
    private Subscription canUseRandomPlaceSubscription;

    @Bind(R.id.main_ui_container)
    MainUIContainer container;

    @Bind(R.id.footer)
    View footerView;

    @Inject
    @Named("segue")
    EventBus segueBus;

    @Inject
    IDataReadingBLL dataReadingBLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WandererApplication.getApplicationComponent().inject(this);
        showSpinnerImmediately();

        setContentView(R.layout.activity_main);

        rootScoop = new Scoop.Builder("root").build();
        rootScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.main_layout), true);
        ButterKnife.bind(this);

        footer = new Footer(footerView, segueBus);
        appRouter.observe(footer);
        footerView.bringToFront();

        appRouter.goTo(new PlaceMapScreen());
    }

    @Override
    protected void onResume() {
        super.onResume();

        segueBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (canUseRandomPlaceSubscription != null) {
            canUseRandomPlaceSubscription.unsubscribe();
        }

        segueBus.unregister(this);
        appRouter.unobserve(footer);
    }

    @Override
    public void onBackPressed() {
        if (!appRouter.goBack()) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlaceMap(SegueEvents.Show.PlaceMap e) {
        appRouter.goTo(new PlaceMapScreen());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlaceList(SegueEvents.Show.PlaceList e) {
        appRouter.goTo(new PlaceListScreen());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlaceInsight(SegueEvents.Show.PlaceInsight e) {
        footer.hide();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowRandomPlace(SegueEvents.Show.RandomPlace e) {
        if (canUseRandomPlaceSubscription != null) {
            canUseRandomPlaceSubscription.unsubscribe();
        }

        canUseRandomPlaceSubscription = dataReadingBLL
            .canUseRandomPlace()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean canUse) {
                    if (canUse) {
                        footer.hide();
                        appRouter.goTo(new PlaceInsightScreen());
                    } else {
                        alert(getString(R.string.cannot_use_random));
                    }
                }
            });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitPlaceInsight(SegueEvents.Exit.PlaceInsight e) {
        footer.show();
    }
}
