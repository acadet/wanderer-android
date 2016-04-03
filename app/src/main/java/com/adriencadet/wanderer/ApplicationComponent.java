package com.adriencadet.wanderer;

import com.adriencadet.wanderer.models.bll.BLLFactory;
import com.adriencadet.wanderer.models.bll.jobs.BLLJobFactory;
import com.adriencadet.wanderer.models.serializers.SerializerFactory;
import com.adriencadet.wanderer.models.services.wanderer.WandererServiceFactory;
import com.adriencadet.wanderer.models.services.wanderer.api.WandererAPIFactory;
import com.adriencadet.wanderer.models.services.wanderer.jobs.WandererServerJobFactory;
import com.adriencadet.wanderer.ui.FragmentModule;
import com.adriencadet.wanderer.ui.IFragmentComponent;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.adriencadet.wanderer.ui.activities.MainActivity;
import com.adriencadet.wanderer.ui.adapters.PlaceListAdapter;
import com.adriencadet.wanderer.ui.components.MainUIContainer;
import com.adriencadet.wanderer.ui.controllers.BaseController;
import com.adriencadet.wanderer.ui.events.EventBusFactory;
import com.adriencadet.wanderer.ui.routers.RouterFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent
 * <p>
 */
@Singleton
@Component(modules = {
    ApplicationModule.class,
    WandererAPIFactory.class,
    WandererServerJobFactory.class,
    WandererServiceFactory.class,
    SerializerFactory.class,
    BLLJobFactory.class,
    BLLFactory.class,
    EventBusFactory.class,
    RouterFactory.class,
})
public interface ApplicationComponent {
    IFragmentComponent fragmentComponent(FragmentModule fragmentModule);

    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    void inject(BaseController baseController);

    void inject(MainUIContainer container);

    void inject(PlaceListAdapter adapter);
}
