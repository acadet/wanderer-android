package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.RouteChange;

/**
 * IRouterObserver
 * <p>
 */
public interface IRouterObserver {
    void onScoopChanged(RouteChange routeChange);
}
