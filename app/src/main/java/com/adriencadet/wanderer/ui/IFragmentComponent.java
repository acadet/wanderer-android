package com.adriencadet.wanderer.ui;

import com.adriencadet.wanderer.ui.controllers.PlaceMapController;

import dagger.Subcomponent;

/**
 * IFragmentComponent
 * <p>
 */
@Subcomponent(modules = {
    FragmentModule.class
})
public interface IFragmentComponent {

    void inject(PlaceMapController placeMapController);
}
