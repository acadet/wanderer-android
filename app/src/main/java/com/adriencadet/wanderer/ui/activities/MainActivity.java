package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.controllers.footer.FooterController;
import com.adriencadet.wanderer.ui.screens.LandingScreen;
import com.lyft.scoop.Scoop;

public class MainActivity extends BaseActivity {
    private Scoop rootScoop;

    private FooterController footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rootScoop = new Scoop.Builder("root").build();
        rootScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.main_layout), true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!appRouter.hasActiveScreen()) {
            appRouter.goTo(new LandingScreen());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        rootScoop.destroy();
    }

    @Override
    public void onBackPressed() {
        if (!appRouter.goBack()) {
            super.onBackPressed();
        }
    }
}
