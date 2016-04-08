package com.adriencadet.wanderer.ui.controllers.popup;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.controllers.BaseController;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.adriencadet.wanderer.ui.screens.popup.ConfirmScreen;
import com.adriencadet.wanderer.ui.screens.popup.InfoScreen;
import com.adriencadet.wanderer.ui.screens.popup.WarnScreen;
import com.adriencadet.wanderer.ui.utils.Point;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lyft.scoop.Screen;
import com.nineoldandroids.animation.Animator;

import butterknife.Bind;
import butterknife.OnTouch;
import timber.log.Timber;

/**
 * PopupController
 * <p>
 */
public class PopupController extends BaseController {

    private enum Style {
        INFO, CONFIRM, ALERT, WARN
    }

    private Handler currentHandler;
    private View    currentNotification;
    private float   currentTrail;
    private boolean isTouchMovementOver;
    private Point   previousPoint;

    @Bind(R.id.popup)
    ViewGroup wrapperView;

    private void hideNotification() {
        wrapperView.removeView(currentNotification);
    }

    private void showPopup(String message, Style style) {
        TextView label;
        int colorId = 0;

        currentNotification = LayoutInflater.from(context).inflate(R.layout.notification, wrapperView, true);

        label = (TextView) currentNotification.findViewById(R.id.notification_content);
        label.setText(message);

        switch (style) {
            case INFO:
                colorId = R.color.inform;
                break;
            case CONFIRM:
                colorId = R.color.confirm;
                break;
            case ALERT:
                colorId = R.color.alert;
                break;
            case WARN:
                colorId = R.color.warn;
                break;
        }

        label.setBackgroundResource(colorId);

        YoYo
            .with(Techniques.SlideInDown)
            .duration(500)
            .playOn(currentNotification);

        currentHandler = new Handler(Looper.getMainLooper());
        currentHandler.postDelayed(() -> {
            YoYo
                .with(Techniques.SlideOutUp)
                .duration(500)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideNotification();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(currentNotification);
        }, 3 * 1000);
    }

    @Override
    protected int layoutId() {
        return R.layout.popup;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        Screen notification = Screen.fromController(this);

        if (notification instanceof AlertScreen) {
            showPopup(((AlertScreen) notification).message, Style.ALERT);
        } else if (notification instanceof ConfirmScreen) {
            showPopup(((ConfirmScreen) notification).message, Style.CONFIRM);
        } else if (notification instanceof InfoScreen) {
            showPopup(((InfoScreen) notification).message, Style.INFO);
        } else if (notification instanceof WarnScreen) {
            showPopup(((WarnScreen) notification).message, Style.WARN);
        } else {
            Timber.e("Unexpected screen");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (currentHandler != null) {
            currentHandler.removeCallbacksAndMessages(null);
        }
    }

    @OnTouch(R.id.popup)
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentTrail = 0;
            previousPoint = new Point(event.getX(), event.getY());
            isTouchMovementOver = false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && !isTouchMovementOver) {
            currentTrail += previousPoint.getY() - event.getY();
            previousPoint = new Point(event.getX(), event.getY());

            if (currentTrail >= 5) {
                isTouchMovementOver = true;
                hideNotification();
            }
        }

        return false;
    }
}
