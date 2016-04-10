package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.services.ServiceErrors;

import rx.Subscriber;

/**
 * BLLJob
 * <p>
 */
abstract class BLLJob {
    <T> void handleError(Throwable e, Subscriber<T> subscriber) {
        if (e instanceof ServiceErrors.NoConnection) {
            subscriber.onError(new BLLErrors.NoConnection());
        } else if (e instanceof ServiceErrors.ServerError) {
            subscriber.onError(new BLLErrors.ServiceError());
        } else {
            subscriber.onError(e);
        }
    }
}
