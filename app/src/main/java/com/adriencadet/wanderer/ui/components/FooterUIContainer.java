package com.adriencadet.wanderer.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.lyft.scoop.UiContainer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * FooterUIContainer
 * <p>
 */
public class FooterUIContainer extends UiContainer {
    @Inject
    @Named("footer")
    IRouter footerRouter;

    public FooterUIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        WandererApplication.getApplicationComponent().inject(this);
        footerRouter.observe(this::goTo);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        footerRouter.unobserve(this::goTo);
    }
}
