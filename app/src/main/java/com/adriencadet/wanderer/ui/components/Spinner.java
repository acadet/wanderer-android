package com.adriencadet.wanderer.ui.components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.adriencadet.wanderer.R;
import com.eftimoff.androipathview.PathView;

import java.util.Arrays;
import java.util.List;

/**
 * Spinner
 * <p>
 */
public class Spinner {
    private static class Item {
        int color;
        int resourceID;

        public Item(String hexColor, int resourceID) {
            this.color = Color.parseColor(hexColor);
            this.resourceID = resourceID;
        }
    }

    private List<Item>     items;
    private ProgressDialog progressDialog;
    private Handler        delayedShowingHandler;
    private boolean        delayedShowingHasBeenCancelled;
    private Handler        timeoutHandler;
    private boolean        timeoutHasBeenCancelled;
    private boolean        isShowing;
    private boolean        isAnimatingForTheFirstTime;

    public Spinner(Activity activity) {
        progressDialog = new ProgressDialog(activity, R.style.ProgressBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        this.items = Arrays.asList(
            new Item("#F06868", R.raw.plane),
            new Item("#FAB57A", R.raw.backpack),
            new Item("#8DED8E", R.raw.camera),
            new Item("#80D6FF", R.raw.map)
        );
    }

    private void runAnimation(PathView pathView) {
        int delay = (isAnimatingForTheFirstTime) ? 200 : 1000;

        isAnimatingForTheFirstTime = false;

        pathView
            .getPathAnimator()
            .delay(delay)
            .duration(3000)
            .listenerEnd(() -> runAnimation(pathView))
            .interpolator(new AccelerateDecelerateInterpolator())
            .start();
    }

    public void show(boolean withDelay) {
        Runnable runnable;

        if (isShowing) {
            return;
        }

        runnable = () -> {
            int index;
            Item item;
            View spinner;
            PathView pathView;

            if (delayedShowingHasBeenCancelled) {
                return;
            }

            progressDialog.show();
            progressDialog.setContentView(R.layout.spinner);

            index = (int) Math.round(Math.random() * (items.size() - 1));
            item = items.get(index);

            spinner = progressDialog.findViewById(R.id.spinner);
            pathView = (PathView) progressDialog.findViewById(R.id.spinner_content);
            spinner.setBackgroundColor(item.color);
            pathView.setSvgResource(item.resourceID);
            isAnimatingForTheFirstTime = true;
            runAnimation(pathView);
        };

        delayedShowingHasBeenCancelled = false;
        timeoutHasBeenCancelled = false;
        isShowing = true;

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
        isShowing = false;

        if (timeoutHandler != null) {
            timeoutHandler.removeCallbacks(null);
            timeoutHasBeenCancelled = true;
            timeoutHandler = null;
        }
    }
}
