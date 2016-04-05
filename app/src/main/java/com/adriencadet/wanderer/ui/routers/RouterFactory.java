package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.ScreenScooper;

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
    @Singleton
    public AppRouter provideAppRouter() {
        return new AppRouter(new ScreenScooper());
    }

    @Provides
    @Singleton
    public PopupRouter provideScopeRouter() {
        return new PopupRouter(new ScreenScooper());
    }
}
