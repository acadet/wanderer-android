package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.RouteChange;
import com.lyft.scoop.Router;
import com.lyft.scoop.ScreenScooper;

/**
 * AppRouter
 * <p>
 */
public class AppRouter extends Router {
    private IRouterObserver observer;

    public AppRouter(ScreenScooper screenScooper) {
        super(screenScooper);
    }

    @Override
    protected void onScoopChanged(RouteChange routeChange) {
        if (observer != null) {
            observer.onScoopChanged(routeChange);
        }
    }

    public void observe(IRouterObserver observer) {
        this.observer = observer;
    }
}
