package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.ScreenScooper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * RouterFactory
 * <p>
 */
@Module
public class RouterFactory {

    @Provides
    @Named("app")
    @Singleton
    public IRouter provideAppRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("popup")
    @Singleton
    public IRouter providePopupRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("spinner")
    @Singleton
    public IRouter provideSpinnerRouter() {
        return new BaseRouter(new ScreenScooper());
    }
}
