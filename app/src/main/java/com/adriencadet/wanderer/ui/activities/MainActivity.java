package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.controllers.app.FooterController;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerImmediatelyScreen;
import com.lyft.scoop.Scoop;

public class MainActivity extends BaseActivity {
    private Scoop appScoop;
    private Scoop popupScoop;
    private Scoop spinnerScoop;

    private FooterController footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        appScoop = new Scoop.Builder("app").build();
        appScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.main_layout), true);

        popupScoop = new Scoop.Builder("popup").build();
        popupScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.popup_ui_container), true);

        spinnerScoop = new Scoop.Builder("spinner").build();
        spinnerScoop.inflate(R.layout.root_layout, (ViewGroup) findViewById(R.id.spinner_ui_container), true);

        spinnerRouter.goTo(new ShowSpinnerImmediatelyScreen());
        appRouter.goTo(new PlaceMapScreen());
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
}
