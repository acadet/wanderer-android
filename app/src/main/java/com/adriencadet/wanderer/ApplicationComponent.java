package com.adriencadet.wanderer;

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
    WandererServiceFactory.class
})
public interface ApplicationComponent {

}
