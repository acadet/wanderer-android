package com.adriencadet.wanderer.bll;

/**
 * BLLErrors
 * <p>
 */
public class BLLErrors {
    private BLLErrors() {
    }

    public static class NoConnection extends Throwable {
    }

    public static class ServiceError extends Throwable {
    }
}
