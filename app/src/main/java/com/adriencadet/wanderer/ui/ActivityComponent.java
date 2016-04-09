package com.adriencadet.wanderer.ui;

import com.adriencadet.wanderer.ui.controllers.body.PlaceMapController;
import com.adriencadet.wanderer.ui.controllers.spinner.SpinnerController;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * ActivityComponent
 * <p>
 */
@Singleton
@Subcomponent(modules = {
    ActivityModule.class
})
public interface ActivityComponent {
    void inject(SpinnerController spinnerController);

    void inject(PlaceMapController placeMapController);
}
