package com.adriencadet.wanderer.ui.components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.adriencadet.wanderer.R;

import io.saeid.fabloading.LoadingView;

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

    public Spinner(Activity activity) {
        progressDialog = new ProgressDialog(activity, R.style.ProgressBar);
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

            LoadingView spinner = (LoadingView) progressDialog.findViewById(R.id.spinner);
            spinner.addAnimation(Color.parseColor("#F06868"), R.drawable.loading1, LoadingView.FROM_LEFT);
            spinner.addAnimation(Color.parseColor("#FAB57A"), R.drawable.loading2, LoadingView.FROM_TOP);
            spinner.addAnimation(Color.parseColor("#8DED8E"), R.drawable.loading3, LoadingView.FROM_RIGHT);
            spinner.addAnimation(Color.parseColor("#80D6FF"), R.drawable.loading4, LoadingView.FROM_BOTTOM);
            spinner.startAnimation();
            spinner.setVisibility(View.VISIBLE);
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
