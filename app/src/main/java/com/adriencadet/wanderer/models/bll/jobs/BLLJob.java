package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.bll.BLLErrors;
import com.adriencadet.wanderer.models.services.ServiceErrors;

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
