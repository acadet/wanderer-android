package com.adriencadet.wanderer.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;
import rx.Subscriber;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrofitJobTest {
    class FinalWrapper<T> {
        private T value;

        FinalWrapper() {
        }

        FinalWrapper(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }

        void setValue(T value) {
            this.value = value;
        }
    }

    class MockRetrofitJob extends RetrofitJob {

    }

    RetrofitJob job;

    @Before
    public void setup() {
        job = new MockRetrofitJob();
    }

    @After
    public void tearDown() {
        job = null;
    }

    @Test
    public void handleErrorNoConnectionNetworkKind() {
        RetrofitError raisedError = mock(RetrofitError.class);
        FinalWrapper<Throwable> expectedError = new FinalWrapper<>();
        Subscriber<Void> subscriber = new Subscriber<Void>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                expectedError.setValue(e);
            }

            @Override
            public void onNext(Void aVoid) {

            }
        };

        when(raisedError.getKind()).thenReturn(RetrofitError.Kind.NETWORK);
        when(raisedError.getResponse()).thenReturn(null);

        job.handleError(raisedError, subscriber);

        assertThat(expectedError.getValue()).isInstanceOf(ServiceErrors.NoConnection.class);
    }

    @Test
    public void handleErrorNoConnectionNetworkKindDoesNotTriggerOtherCallbacks() {
        RetrofitError raisedError = mock(RetrofitError.class);
        FinalWrapper<Boolean> hasCompleted = new FinalWrapper<>(false), onNext = new FinalWrapper<>(false);
        Subscriber<Void> subscriber = new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                hasCompleted.setValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                onNext.setValue(true);
            }
        };

        when(raisedError.getKind()).thenReturn(RetrofitError.Kind.NETWORK);
        when(raisedError.getResponse()).thenReturn(null);

        job.handleError(raisedError, subscriber);

        assertThat(hasCompleted.getValue()).isFalse();
        assertThat(onNext.getValue()).isFalse();
    }

    @Test
    public void handleErrorNoConnection502Response() {
        RetrofitError raisedError = mock(RetrofitError.class);
        Response response = new Response("", 502, "", new ArrayList<>(), mock(TypedInput.class));
        FinalWrapper<Throwable> expectedError = new FinalWrapper<>();
        Subscriber<Void> subscriber = new Subscriber<Void>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                expectedError.setValue(e);
            }

            @Override
            public void onNext(Void aVoid) {

            }
        };

        when(raisedError.getResponse()).thenReturn(response);

        job.handleError(raisedError, subscriber);

        assertThat(expectedError.getValue()).isInstanceOf(ServiceErrors.NoConnection.class);
    }

    @Test
    public void handleErrorNoConnection502DoesNotTriggerOtherCallbacks() {
        RetrofitError raisedError = mock(RetrofitError.class);
        Response response = new Response("", 502, "", new ArrayList<>(), mock(TypedInput.class));
        FinalWrapper<Boolean> hasCompleted = new FinalWrapper<>(false), onNext = new FinalWrapper<>(false);
        Subscriber<Void> subscriber = new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                hasCompleted.setValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                onNext.setValue(true);
            }
        };

        when(raisedError.getResponse()).thenReturn(response);

        job.handleError(raisedError, subscriber);

        assertThat(hasCompleted.getValue()).isFalse();
        assertThat(onNext.getValue()).isFalse();
    }

    @Test
    public void handleErrorServerError() {
        RetrofitError raisedError = mock(RetrofitError.class);
        Response response = new Response("", 300, "", new ArrayList<>(), mock(TypedInput.class));
        FinalWrapper<Throwable> expectedError = new FinalWrapper<>();
        Subscriber<Void> subscriber = new Subscriber<Void>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                expectedError.setValue(e);
            }

            @Override
            public void onNext(Void aVoid) {

            }
        };

        when(raisedError.getResponse()).thenReturn(response);
        when(raisedError.getKind()).thenReturn(RetrofitError.Kind.NETWORK);

        job.handleError(raisedError, subscriber);

        assertThat(expectedError.getValue()).isInstanceOf(ServiceErrors.ServerError.class);
    }

    @Test
    public void handleErrorServerErrorDoesNotTriggerOtherCallbacks() {
        RetrofitError raisedError = mock(RetrofitError.class);
        Response response = new Response("", 300, "", new ArrayList<>(), mock(TypedInput.class));
        FinalWrapper<Throwable> expectedError = new FinalWrapper<>();
        FinalWrapper<Boolean> hasCompleted = new FinalWrapper<>(false), onNext = new FinalWrapper<>(false);
        Subscriber<Void> subscriber = new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                hasCompleted.setValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                onNext.setValue(true);
            }
        };

        when(raisedError.getResponse()).thenReturn(response);
        when(raisedError.getKind()).thenReturn(RetrofitError.Kind.NETWORK);

        job.handleError(raisedError, subscriber);

        assertThat(hasCompleted.getValue()).isFalse();
        assertThat(onNext.getValue()).isFalse();
    }
}