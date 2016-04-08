package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * PageBodyUIContainer
 * <p>
 */
public class PageBodyUIContainer extends UiContainer {
    @Inject
    @Named("pageBody")
    IRouter router;

    public PageBodyUIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        WandererApplication.getApplicationComponent().inject(this);
        router.observe(this::goTo);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        router.stopObserving(this::goTo);
    }
}
