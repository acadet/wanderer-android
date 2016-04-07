package com.adriencadet.wanderer.ui.routers;

import com.adriencadet.wanderer.ui.screens.app.PlaceInsightFooterScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceListFooterScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapFooterScreen;
import com.adriencadet.wanderer.ui.screens.app.PlaceMapScreen;
import com.lyft.scoop.Screen;

import timber.log.Timber;

/**
 * AppRouter
 * <p>
 */
class AppRouter implements IRouter {
    private IRouter bodyRouter;
    private IRouter footerRouter;

    AppRouter(IRouter bodyRouter, IRouter footerRouter) {
        this.bodyRouter = bodyRouter;
        this.footerRouter = footerRouter;
    }

    @Override
    public void goTo(Screen screen) {
        Screen forFooter = null;

        if (screen instanceof PlaceInsightScreen) {
            forFooter = new PlaceInsightFooterScreen();
        } else if (screen instanceof PlaceListScreen) {
            forFooter = new PlaceListFooterScreen();
        } else if (screen instanceof PlaceMapScreen) {
            forFooter = new PlaceMapFooterScreen();
        } else {
            Timber.e("Unexpected screen");
        }

        footerRouter.goTo(forFooter);
        bodyRouter.goTo(screen);
    }

    @Override
    public boolean goBack() {
        footerRouter.goBack();
        return bodyRouter.goBack();
    }

    @Override
    public void observe(IRouterScoopChangedObserver observer) {

    }

    @Override
    public void unobserve(IRouterScoopChangedObserver observer) {

    }
}
