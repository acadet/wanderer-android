package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.IDataReadingBLL;
import com.adriencadet.wanderer.ui.components.Footer;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.screens.app.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapScreen;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerImmediatelyScreen;
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
    private Scoop appScoop;
    private Scoop popupScoop;
    private Scoop spinnerScoop;

    private Footer       footer;
    private Subscription canUseRandomPlaceSubscription;

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
        setContentView(R.layout.activity_main);

        appScoop = new Scoop.Builder("app").build();
        appScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.main_layout), true);

        popupScoop = new Scoop.Builder("popup").build();
        popupScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.popup_ui_container), true);

        spinnerScoop = new Scoop.Builder("spinner").build();
        spinnerScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.spinner_ui_container), true);

        spinnerRouter.goTo(new ShowSpinnerImmediatelyScreen());

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
    protected void onDestroy() {
        super.onDestroy();

        appScoop.destroy();
        popupScoop.destroy();
        spinnerScoop.destroy();
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
                        popupRouter.goTo(new AlertScreen(getString(R.string.cannot_use_random)));
                    }
                }
            });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitPlaceInsight(SegueEvents.Exit.PlaceInsight e) {
        footer.show();
    }
}
