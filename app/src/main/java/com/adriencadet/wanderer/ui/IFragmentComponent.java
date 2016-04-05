package com.adriencadet.wanderer.ui;

import com.adriencadet.wanderer.ui.controllers.PlaceMapController;
import com.adriencadet.wanderer.ui.events.EventBusFactory;

import dagger.Subcomponent;

/**
 * IFragmentComponent
 * <p>
 */
@Subcomponent(modules = {
    FragmentModule.class,
    EventBusFactory.class
})
public interface IFragmentComponent {

    void inject(PlaceMapController placeMapController);
}
