package com.adriencadet.wanderer.ui.routers;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.screens.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.PlaceMapScreen;
import com.adriencadet.wanderer.ui.screens.footer.PlaceListFooterScreen;
import com.adriencadet.wanderer.ui.screens.footer.PlaceMapFooterScreen;
import com.adriencadet.wanderer.ui.screens.page.LandingPageScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ScreenScooper;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * PageRouter
 * <p>
 */
public class PageRouter extends BaseRouter {

    @Inject
    @Named("pageBody")
    IRouter bodyRouter;

    @Inject
    @Named("pageFooter")
    IRouter footerRouter;

    PageRouter(ScreenScooper screenScooper) {
        super(screenScooper);
        WandererApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void goTo(Screen screen) {
        super.goTo(new LandingPageScreen());

        if (screen instanceof PlaceMapScreen) {
            bodyRouter.goTo(screen);
            footerRouter.goTo(new PlaceMapFooterScreen());
        } else if (screen instanceof PlaceListScreen) {
            bodyRouter.goTo(screen);
            footerRouter.goTo(new PlaceListFooterScreen());
        } else {
            Timber.e("Invalid screen");
        }
    }

    @Override
    public boolean goBack() {
        footerRouter.goBack();
        if (bodyRouter.goBack()) {
            return true;
        }

        return super.goBack();
    }
}
