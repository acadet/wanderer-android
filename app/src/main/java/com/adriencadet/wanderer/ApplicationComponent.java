package com.adriencadet.wanderer;

import com.adriencadet.wanderer.models.bll.jobs.BLLJobFactory;
import com.adriencadet.wanderer.models.serializers.SerializerFactory;
import com.adriencadet.wanderer.models.services.wanderer.WandererServiceFactory;
import com.adriencadet.wanderer.models.services.wanderer.api.WandererAPIFactory;
import com.adriencadet.wanderer.models.services.wanderer.jobs.WandererServerJobFactory;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
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
    EventBusFactory.class,
    RouterFactory.class
})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
}
