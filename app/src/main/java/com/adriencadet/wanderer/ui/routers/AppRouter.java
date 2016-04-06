package com.adriencadet.wanderer.ui.routers;

import com.lyft.scoop.Screen;

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
        bodyRouter.goTo(screen);
        footerRouter.goTo(screen);
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

    @Override
    public void observe(IRouterGoBackObserver observer) {

    }

    @Override
    public void unobserve(IRouterGoBackObserver observer) {

    }
}
