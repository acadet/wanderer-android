package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.PopupRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;

/**
 * PopupUIContainer
 * <p>
 */
public class PopupUIContainer extends UiContainer {
    @Inject
    PopupRouter popupRouter;

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
