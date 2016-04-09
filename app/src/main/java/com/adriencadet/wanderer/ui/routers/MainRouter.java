package com.adriencadet.wanderer.ui.routers;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.screens.LandingScreen;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.PlaceMapScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ScreenScooper;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * MainRouter
 * <p>
 */
public class MainRouter extends BaseRouter {

    private boolean isInFullscreen;

    @Inject
    @Named("page")
    IRouter pageRouter;

    @Inject
    @Named("fullscreen")
    IRouter fullscreenRouter;

    MainRouter(ScreenScooper screenScooper) {
        super(screenScooper);

        WandererApplication.getApplicationComponent().inject(this);

        isInFullscreen = false;
    }

    @Override
    public void goTo(Screen screen) {
        if (screen instanceof LandingScreen) {
            super.goTo(screen);
            pageRouter.goTo(new PlaceMapScreen());
        } else if (screen instanceof PlaceMapScreen || screen instanceof PlaceListScreen) {
            pageRouter.goTo(screen);
        } else if (screen instanceof PlaceInsightScreen) {
            isInFullscreen = true;
            fullscreenRouter.goTo(screen);
            pageRouter.goTo(screen);
        } else {
            Timber.e("Invalid screen");
        }
    }

    @Override
    public boolean goBack() {
        if (isInFullscreen) {
            if (fullscreenRouter.goBack()) {
                return true;
            }

            isInFullscreen = false;

            if (pageRouter.hasActiveScreen()) {
                pageRouter.goBack();
                return true;
            }

            return super.goBack();
        } else {
            if (pageRouter.goBack()) {
                return true;
            }

            return super.goBack();
        }
    }
}
