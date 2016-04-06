package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * PopupUIContainer
 * <p>
 */
public class PopupUIContainer extends UiContainer {
    @Inject
    @Named("popup")
    IRouter popupRouter;

    public PopupUIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        WandererApplication.getApplicationComponent().inject(this);
        popupRouter.observe(this::goTo);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        popupRouter.unobserve(this::goTo);
    }
}
