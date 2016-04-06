package com.adriencadet.wanderer.ui.routers;

import com.annimon.stream.Stream;
import com.lyft.scoop.RouteChange;
import com.lyft.scoop.Router;
import com.lyft.scoop.ScreenScooper;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRouter
 * <p>
 */
class BaseRouter extends Router implements IRouter {
    private List<IRouterScoopChangedObserver> routerScoopChangedObservers;

    public BaseRouter(ScreenScooper screenScooper) {
        super(screenScooper);

        this.routerScoopChangedObservers = new ArrayList<>();
    }

    @Override
    protected void onScoopChanged(RouteChange routeChange) {
        Stream.of(routerScoopChangedObservers).forEach((e) -> e.onScoopChanged(routeChange));
    }

    @Override
    public void observe(IRouterScoopChangedObserver observer) {
        routerScoopChangedObservers.add(observer);
    }

    @Override
    public void unobserve(IRouterScoopChangedObserver observer) {
        routerScoopChangedObservers.remove(observer);
    }
}
