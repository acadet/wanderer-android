package com.adriencadet.wanderer.bll;

/**
 * FinalWrapper
 * <p>
 */
class FinalWrapper<T> {
    private T value;

    FinalWrapper() {

    }

    T get() {
        return value;
    }

    void set(T value) {
        this.value = value;
    }
}
