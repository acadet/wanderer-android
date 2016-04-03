package com.adriencadet.wanderer.ui.controllers;

import android.content.Context;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.BLLErrors;
import com.adriencadet.wanderer.models.bll.IDataReadingBLL;
import com.adriencadet.wanderer.ui.events.PopupEvents;
import com.adriencadet.wanderer.ui.events.SpinnerEvents;
import com.adriencadet.wanderer.ui.routers.AppRouter;
import com.lyft.scoop.ViewController;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * BaseController
 * <p>
 */
public abstract class BaseController extends ViewController {
    public abstract class BaseSubscriber<T> extends Subscriber<T> {
        @Override
        public void onError(Throwable e) {
            if (e instanceof BLLErrors.NoConnection) {
                inform(context.getString(R.string.no_connection_error));
            } else if (e instanceof BLLErrors.ServiceError) {
                inform(context.getString(R.string.internal_server_error));
            } else {
                alert(e.getMessage());
            }
        }
    }

    @Inject
    Context context;

    @Inject
    AppRouter appRouter;

    @Inject
    @Named("popup")
    EventBus popupBus;

    @Inject
    @Named("spinner")
    EventBus spinnerBus;

    @Inject
    @Named("segue")
    EventBus segueBus;

    @Inject
    IDataReadingBLL dataReadingBLL;

    @Override
    public void onAttach() {
        super.onAttach();
        WandererApplication.getApplicationComponent().inject(this);
        ButterKnife.bind(this, getView());

        segueBus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        segueBus.unregister(this);
    }

    public void inform(String message) {
        popupBus.post(new PopupEvents.Info(message));
    }

    public void confirm(String message) {
        popupBus.post(new PopupEvents.Confirm(message));
    }

    public void alert(String message) {
        popupBus.post(new PopupEvents.Alert(message));
    }

    public void hideNotification() {
        popupBus.post(new PopupEvents.Hide());
    }

    public void showSpinner() {
        spinnerBus.post(new SpinnerEvents.Show());
    }

    public void hideSpinner() {
        spinnerBus.post(new SpinnerEvents.Hide());
    }
}
