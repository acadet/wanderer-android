package com.adriencadet.wanderer.ui;

import com.adriencadet.wanderer.ui.controllers.PlaceMapController;
import com.adriencadet.wanderer.ui.controllers.PopupController;

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

    void inject(PlaceMapController placeMapController);
}
