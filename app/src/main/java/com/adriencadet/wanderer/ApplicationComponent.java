package com.adriencadet.wanderer;

import com.adriencadet.wanderer.models.bll.BLLFactory;
import com.adriencadet.wanderer.models.bll.jobs.BLLJobFactory;
import com.adriencadet.wanderer.dao.DAOFactory;
import com.adriencadet.wanderer.models.serializers.SerializerFactory;
import com.adriencadet.wanderer.services.wanderer.WandererServiceFactory;
import com.adriencadet.wanderer.services.wanderer.WandererAPIFactory;
import com.adriencadet.wanderer.services.wanderer.WandererServerJobFactory;
import com.adriencadet.wanderer.ui.ActivityComponent;
import com.adriencadet.wanderer.ui.ActivityModule;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.adriencadet.wanderer.ui.components.FullscreenBodyUIContainer;
import com.adriencadet.wanderer.ui.components.FullscreenUIContainer;
import com.adriencadet.wanderer.ui.components.MainUIContainer;
import com.adriencadet.wanderer.ui.components.PageBodyUIContainer;
import com.adriencadet.wanderer.ui.components.PageFooterUIContainer;
import com.adriencadet.wanderer.ui.components.PageUIContainer;
import com.adriencadet.wanderer.ui.components.PopupUIContainer;
import com.adriencadet.wanderer.ui.components.SpinnerUIContainer;
import com.adriencadet.wanderer.ui.controllers.BaseController;
import com.adriencadet.wanderer.ui.controllers.ApplicationController;
import com.adriencadet.wanderer.ui.routers.FullscreenRouter;
import com.adriencadet.wanderer.ui.routers.MainRouter;
import com.adriencadet.wanderer.ui.routers.PageRouter;
import com.adriencadet.wanderer.ui.routers.RouterFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent
 * <p>
 */
@Singleton
@Component(modules = {
    ApplicationModule.class,
    WandererAPIFactory.class,
    WandererServerJobFactory.class,
    WandererServiceFactory.class,
    SerializerFactory.class,
    DAOFactory.class,
    BLLJobFactory.class,
    BLLFactory.class,
    RouterFactory.class
})
public interface ApplicationComponent {
    // Subcomponents
    ActivityComponent fragmentComponent(ActivityModule activityModule);

    // Activities
    void inject(BaseActivity baseActivity);

    // Controllers
    void inject(BaseController baseController);

    void inject(ApplicationController applicationController);

    // UIContainers
    void inject(MainUIContainer container);

    void inject(PageUIContainer container);

    void inject(FullscreenUIContainer container);

    void inject(PageBodyUIContainer container);

    void inject(PageFooterUIContainer container);

    void inject(FullscreenBodyUIContainer container);

    void inject(PopupUIContainer container);

    void inject(SpinnerUIContainer container);

    // Routers

    void inject(MainRouter mainRouter);

    void inject(PageRouter pageRouter);

    void inject(FullscreenRouter fullscreenRouter);
}
