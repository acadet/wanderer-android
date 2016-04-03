package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.components.MainUIContainer;
import com.adriencadet.wanderer.ui.screens.PlaceMapScreen;
import com.lyft.scoop.Scoop;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_ui_container)
    MainUIContainer container;

    private Scoop rootScoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSpinnerImmediately();

        setContentView(R.layout.activity_main);

        rootScoop = new Scoop.Builder("root").build();
        rootScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.main_layout), true);
        ButterKnife.bind(this);

        appRouter.goTo(new PlaceMapScreen());
    }

    @Override
    public void onBackPressed() {
        if (!appRouter.goBack()) {
            finish();
        }
    }
}
