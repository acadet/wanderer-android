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
abstract class BaseRouter extends Router {
    private List<IRouterScoopChangedObserver> routerScoopChangedObservers;
    private List<IRouterGoBackObserver>       routerGoBackObservers;

    public BaseRouter(ScreenScooper screenScooper) {
        super(screenScooper);

        this.routerScoopChangedObservers = new ArrayList<>();
        this.routerGoBackObservers = new ArrayList<>();
    }

    @Override
    protected void onScoopChanged(RouteChange routeChange) {
        Stream.of(routerScoopChangedObservers).forEach((e) -> e.onScoopChanged(routeChange));
    }

    @Override
    public boolean goBack() {
        boolean hasElements = super.goBack();

        Stream.of(routerGoBackObservers).forEach((e) -> e.onGoingBack());

        return hasElements;
    }

    public void observe(IRouterScoopChangedObserver observer) {
        routerScoopChangedObservers.add(observer);
    }

    public void unobserve(IRouterScoopChangedObserver observer) {
        routerGoBackObservers.remove(observer);
    }

    public void observe(IRouterGoBackObserver observer) {
        routerGoBackObservers.add(observer);
    }

    public void unobserve(IRouterGoBackObserver observer) {
        routerGoBackObservers.remove(observer);
    }
}
