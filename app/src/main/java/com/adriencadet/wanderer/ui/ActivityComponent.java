package com.adriencadet.wanderer.ui;

import com.adriencadet.wanderer.ui.controllers.app.PlaceMapController;
import com.adriencadet.wanderer.ui.controllers.popup.PopupController;
import com.adriencadet.wanderer.ui.controllers.spinner.SpinnerController;

import dagger.Subcomponent;

/**
 * ActivityComponent
 * <p>
 */
@Subcomponent(modules = {
    ActivityModule.class
})
public interface ActivityComponent {
    void inject(PopupController popupController);

    void inject(SpinnerController spinnerController);

    void inject(PlaceMapController placeMapController);
}
