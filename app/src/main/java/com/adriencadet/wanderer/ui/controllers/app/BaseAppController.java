package com.adriencadet.wanderer.ui.controllers.app;

import android.content.Context;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.BLLErrors;
import com.adriencadet.wanderer.models.bll.IDataReadingBLL;
import com.adriencadet.wanderer.models.bll.IDataWritingBLL;
import com.adriencadet.wanderer.ui.events.SpinnerEvents;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.adriencadet.wanderer.ui.screens.popup.ConfirmScreen;
import com.adriencadet.wanderer.ui.screens.popup.InfoScreen;
import com.lyft.scoop.ViewController;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import rx.Subscriber;
import timber.log.Timber;

/**
 * BaseAppController
 * <p>
 */
public abstract class BaseAppController extends ViewController {
    public abstract class BaseSubscriber<T> extends Subscriber<T> {
        @Override
        public void onError(Throwable e) {
            if (e instanceof BLLErrors.NoConnection) {
                inform(context.getString(R.string.no_connection_error));
            } else if (e instanceof BLLErrors.ServiceError) {
                inform(context.getString(R.string.internal_server_error));
            } else {
                alert(e.getMessage());
                Timber.e(e, "Unhandled error");
            }
        }
    }

    @Inject
    Context context;

    @Inject
    @Named("app")
    IRouter appRouter;

    @Inject
    @Named("popup")
    IRouter popupRouter;

    @Inject
    @Named("spinner")
    EventBus spinnerBus;

    @Inject
    IDataReadingBLL dataReadingBLL;

    @Inject
    IDataWritingBLL dataWritingBLL;

    @Override
    public void onAttach() {
        super.onAttach();
        WandererApplication.getApplicationComponent().inject(this);
        ButterKnife.bind(this, getView());
    }

    public void inform(String message) {
        popupRouter.goTo(new InfoScreen(message));
    }

    public void confirm(String message) {
        popupRouter.goTo(new ConfirmScreen(message));
    }

    public void alert(String message) {
        popupRouter.goTo(new AlertScreen(message));
    }

    public void showSpinner() {
        spinnerBus.post(new SpinnerEvents.Show());
    }

    public void showSpinnerImmediately() {
        spinnerBus.post(new SpinnerEvents.ShowImmediately());
    }

    public void hideSpinner() {
        spinnerBus.post(new SpinnerEvents.Hide());
    }
}
