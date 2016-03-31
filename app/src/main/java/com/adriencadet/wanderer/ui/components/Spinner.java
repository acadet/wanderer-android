package com.adriencadet.wanderer.ui.components;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.adriencadet.wanderer.R;

/**
 * Spinner
 * <p>
 */
public class Spinner {
    private ProgressDialog progressDialog;
    private Handler        delayedShowingHandler;
    private boolean        delayedShowingHasBeenCancelled;
    private Handler        timeoutHandler;
    private boolean        timeoutHasBeenCancelled;

    public Spinner(Context context) {
        progressDialog = new ProgressDialog(context, R.style.ProgressBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    public void show(boolean withDelay) {
        Runnable runnable = () -> {
            if (delayedShowingHasBeenCancelled) {
                return;
            }

            progressDialog.show();
            progressDialog.setContentView(R.layout.spinner);
        };

        delayedShowingHasBeenCancelled = false;
        timeoutHasBeenCancelled = false;

        if (withDelay) {
            delayedShowingHandler = new Handler(Looper.getMainLooper());
            delayedShowingHandler.postDelayed(runnable, 500);
        } else {
            new Handler().post(() -> runnable.run());
        }

        timeoutHandler = new Handler(Looper.getMainLooper());
        timeoutHandler.postDelayed(
            () -> {
                if (timeoutHasBeenCancelled) {
                    return;
                } else {
                    progressDialog.dismiss();
                }
            },
            10 * 1000
        );
    }

    public void show() {
        show(true);
    }

    public void hide() {
        if (delayedShowingHandler != null) {
            delayedShowingHandler.removeCallbacks(null);
            delayedShowingHasBeenCancelled = true;
            delayedShowingHandler = null;
        }

        progressDialog.dismiss();

        if (timeoutHandler != null) {
            timeoutHandler.removeCallbacks(null);
            timeoutHasBeenCancelled = true;
            timeoutHandler = null;
        }
    }
}
