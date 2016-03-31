package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.AppRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;

/**
 * MainUIContainer
 * <p>
 */
public class MainUIContainer extends UiContainer {
    @Inject
    AppRouter appRouter;

    public MainUIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        WandererApplication.getApplicationComponent().inject(this);
        appRouter.observe(this::goTo);
    }
}
