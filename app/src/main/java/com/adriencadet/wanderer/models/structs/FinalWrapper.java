package com.adriencadet.wanderer.models.structs;

/**
 * FinalWrapper
 * <p>
 */
public class FinalWrapper<T> {
    private T value;

    public FinalWrapper() {

    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
