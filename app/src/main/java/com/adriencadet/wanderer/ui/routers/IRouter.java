package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.Screen;

/**
 * IRouter
 * <p>
 */
public interface IRouter {
    void goTo(Screen screen);

    boolean goBack();


    void observe(IRouterScoopChangedObserver observer);

    void unobserve(IRouterScoopChangedObserver observer);

    void observe(IRouterGoBackObserver observer);

    void unobserve(IRouterGoBackObserver observer);
}
