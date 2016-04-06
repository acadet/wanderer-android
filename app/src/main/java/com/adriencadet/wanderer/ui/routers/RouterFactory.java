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
    @Named("body")
    @Singleton
    public IRouter provideBodyRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("footer")
    @Singleton
    public IRouter provideFooterRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("app")
    @Singleton
    public IRouter provideAppRouter(@Named("body") IRouter bodyRouter, @Named("footer") IRouter footerRouter) {
        return new AppRouter(bodyRouter, footerRouter);
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
