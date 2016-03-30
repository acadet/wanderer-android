package com.adriencadet.wanderer;

import com.adriencadet.wanderer.models.bll.jobs.BLLJobFactory;
import com.adriencadet.wanderer.models.serializers.SerializerFactory;
import com.adriencadet.wanderer.models.services.wanderer.WandererServiceFactory;
import com.adriencadet.wanderer.models.services.wanderer.api.WandererAPIFactory;
import com.adriencadet.wanderer.models.services.wanderer.jobs.WandererServerJobFactory;

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
    BLLJobFactory.class
})
public interface ApplicationComponent {

}
