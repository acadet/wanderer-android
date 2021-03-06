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
    @Named("pageBody")
    @Singleton
    public IRouter providePageBodyRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("pageFooter")
    @Singleton
    public IRouter providePageFooterRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("fullscreenBody")
    @Singleton
    public IRouter provideFullscreenBodyRouter() {
        return new BaseRouter(new ScreenScooper());
    }

    @Provides
    @Named("fullscreen")
    @Singleton
    public IRouter provideFullScreenRouter() {
        return new FullscreenRouter(new ScreenScooper());
    }

    @Provides
    @Named("page")
    @Singleton
    public IRouter providePageRouter() {
        return new PageRouter(new ScreenScooper());
    }

    @Provides
    @Singleton
    @Named("main")
    public IRouter provideMainRouter() {
        return new MainRouter(new ScreenScooper());
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
