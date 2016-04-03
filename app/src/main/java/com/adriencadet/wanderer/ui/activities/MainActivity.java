package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.components.Footer;
import com.adriencadet.wanderer.ui.components.MainUIContainer;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.screens.PlaceMapScreen;
import com.lyft.scoop.Scoop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private Scoop  rootScoop;
    private Footer footer;

    @Bind(R.id.main_ui_container)
    MainUIContainer container;

    @Bind(R.id.footer)
    View footerView;

    @Inject
    @Named("segue")
    EventBus segueBus;

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

        segueBus.unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (!appRouter.goBack()) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowRandomPlace(SegueEvents.Show.RandomPlace e) {
        footerView.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitPlaceInsight(SegueEvents.Exit.PlaceInsight e) {
        footerView.setVisibility(View.VISIBLE);
    }
}
