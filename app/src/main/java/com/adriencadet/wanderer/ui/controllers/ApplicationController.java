package com.adriencadet.wanderer.ui.controllers;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.BLLErrors;
import com.adriencadet.wanderer.models.bll.IDataReadingBLL;
import com.adriencadet.wanderer.models.bll.IDataWritingBLL;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.adriencadet.wanderer.ui.screens.popup.ConfirmScreen;
import com.adriencadet.wanderer.ui.screens.popup.InfoScreen;
import com.adriencadet.wanderer.ui.screens.popup.WarnScreen;
import com.adriencadet.wanderer.ui.screens.spinner.HideSpinnerScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerImmediatelyScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerSreen;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;
import timber.log.Timber;

/**
 * ApplicationController
 * <p>
 */
public abstract class ApplicationController extends BaseController {
    public abstract class BaseSubscriber<T> extends Subscriber<T> {
        @Override
        public void onError(Throwable e) {
            if (e instanceof BLLErrors.NoConnection) {
                warn(context.getString(R.string.no_connection_error));
            } else if (e instanceof BLLErrors.ServiceError) {
                warn(context.getString(R.string.internal_server_error));
            } else {
                alert(e.getMessage());
                Timber.e(e, "Unhandled error");
            }
        }
    }

    @Inject
    @Named("main")
    public IRouter appRouter;

    @Inject
    @Named("popup")
    public IRouter popupRouter;

    @Inject
    @Named("spinner")
    public IRouter spinnerRouter;

    @Inject
    public IDataReadingBLL dataReadingBLL;

    @Inject
    public IDataWritingBLL dataWritingBLL;

    @Override
    public void onAttach() {
        super.onAttach();
        WandererApplication.getApplicationComponent().inject(this);
    }

    public void inform(String message) {
        popupRouter.goTo(new InfoScreen(message));
    }

    public void confirm(String message) {
        popupRouter.goTo(new ConfirmScreen(message));
    }

    public void warn(String message) {
        popupRouter.goTo(new WarnScreen(message));
    }

    public void alert(String message) {
        popupRouter.goTo(new AlertScreen(message));
    }

    public void showSpinner() {
        spinnerRouter.goTo(new ShowSpinnerSreen());
    }

    public void showSpinnerImmediately() {
        spinnerRouter.goTo(new ShowSpinnerImmediatelyScreen());
    }

    public void hideSpinner() {
        spinnerRouter.goTo(new HideSpinnerScreen());
    }
}
