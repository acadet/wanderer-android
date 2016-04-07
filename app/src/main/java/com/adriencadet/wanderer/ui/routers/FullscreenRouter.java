package com.adriencadet.wanderer.ui.routers;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.fullscreen.LandingFullscreenScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ScreenScooper;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * FullscreenRouter
 * <p>
 */
public class FullscreenRouter extends BaseRouter {
    @Inject
    @Named("fullscreenBody")
    IRouter bodyRouter;

    FullscreenRouter(ScreenScooper screenScooper) {
        super(screenScooper);

        WandererApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void goTo(Screen screen) {
        super.goTo(new LandingFullscreenScreen());

        if (screen instanceof PlaceInsightScreen) {
            bodyRouter.goTo(new PlaceInsightScreen());
        } else {
            Timber.e("Invalid screen");
        }
    }

    @Override
    public boolean goBack() {
        return bodyRouter.goBack();
    }
}
