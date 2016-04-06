package com.adriencadet.wanderer.ui.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.controllers.app.FooterController;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerImmediatelyScreen;
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

        spinnerRouter.goTo(new ShowSpinnerImmediatelyScreen());
        appRouter.goTo(new PlaceMapScreen());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        rootScoop.destroy();
    }

    @Override
    public void onBackPressed() {
        if (!appRouter.goBack()) {
            finish();
        }
    }
}
