package com.adriencadet.wanderer;

import com.adriencadet.wanderer.models.bll.BLLFactory;
import com.adriencadet.wanderer.models.bll.jobs.BLLJobFactory;
import com.adriencadet.wanderer.models.dao.DAOFactory;
import com.adriencadet.wanderer.models.serializers.SerializerFactory;
import com.adriencadet.wanderer.models.services.wanderer.WandererServiceFactory;
import com.adriencadet.wanderer.models.services.wanderer.api.WandererAPIFactory;
import com.adriencadet.wanderer.models.services.wanderer.jobs.WandererServerJobFactory;
import com.adriencadet.wanderer.ui.ActivityComponent;
import com.adriencadet.wanderer.ui.ActivityModule;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.adriencadet.wanderer.ui.activities.MainActivity;
import com.adriencadet.wanderer.ui.adapters.PlaceListAdapter;
import com.adriencadet.wanderer.ui.components.BodyUIContainer;
import com.adriencadet.wanderer.ui.components.FooterUIContainer;
import com.adriencadet.wanderer.ui.components.PopupUIContainer;
import com.adriencadet.wanderer.ui.components.SpinnerUIContainer;
import com.adriencadet.wanderer.ui.controllers.app.BaseAppController;
import com.adriencadet.wanderer.ui.controllers.app.PlaceInsightController;
import com.adriencadet.wanderer.ui.controllers.app.PlaceListController;
import com.adriencadet.wanderer.ui.events.EventBusFactory;
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
    EventBusFactory.class,
    RouterFactory.class
})
public interface ApplicationComponent {
    // Subcomponents
    ActivityComponent fragmentComponent(ActivityModule activityModule);

    // Activities
    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    // Controllers
    void inject(BaseAppController baseAppController);

    //TODO
    void inject(PlaceListController placeListController);

    //TODO
    void inject(PlaceInsightController placeInsightController);

    // UIContainers
    void inject(BodyUIContainer container);

    void inject(FooterUIContainer container);

    void inject(PopupUIContainer container);

    void inject(SpinnerUIContainer container);

    //TODO
    void inject(PlaceListAdapter adapter);
}
