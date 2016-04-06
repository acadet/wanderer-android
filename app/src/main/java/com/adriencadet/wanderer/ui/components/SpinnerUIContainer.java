package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * SpinnerUIContainer
 * <p>
 */
public class SpinnerUIContainer extends UiContainer {
    @Inject
    @Named("spinner")
    IRouter spinnerRouter;

    public SpinnerUIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        WandererApplication.getApplicationComponent().inject(this);
        spinnerRouter.observe(this::goTo);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        spinnerRouter.unobserve(this::goTo);
    }
}
