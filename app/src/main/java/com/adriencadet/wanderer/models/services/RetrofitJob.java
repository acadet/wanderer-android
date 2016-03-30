package com.adriencadet.wanderer.models.services;

import retrofit.RetrofitError;
import rx.Subscriber;
import timber.log.Timber;

/**
 * RetrofitJob
 * <p>
 */
public abstract class RetrofitJob {
    public <T> void handleError(RetrofitError e, Subscriber<T> subscriber) {
        if ((e.getKind() == RetrofitError.Kind.NETWORK && e.getResponse() == null)
            || (e.getResponse() != null) && e.getResponse().getStatus() == 502) {
            subscriber.onError(new ServiceErrors.NoConnection());
        } else {
            subscriber.onError(new ServiceErrors.ServerError());
        }

        Timber.e(e, "RetrofitError");
    }
}
