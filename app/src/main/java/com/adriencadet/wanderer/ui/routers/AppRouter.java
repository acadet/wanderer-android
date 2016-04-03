package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.RouteChange;
import com.lyft.scoop.Router;
import com.lyft.scoop.ScreenScooper;

import java.util.ArrayList;
import java.util.List;

/**
 * AppRouter
 * <p>
 */
public class AppRouter extends Router {
    private List<IRouterObserver> observers;

    public AppRouter(ScreenScooper screenScooper) {
        super(screenScooper);
        observers = new ArrayList<>();
    }

    @Override
    protected void onScoopChanged(RouteChange routeChange) {
        for (IRouterObserver o : observers) {
            o.onScoopChanged(routeChange);
        }
    }

    public void observe(IRouterObserver observer) {
        observers.add(observer);
    }
}
