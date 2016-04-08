package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * FullscreenUIContainer
 * <p>
 */
public class FullscreenUIContainer extends UiContainer {
    @Inject
    @Named("fullscreen")
    IRouter router;

    public FullscreenUIContainer(Context context, AttributeSet attrs) {
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
