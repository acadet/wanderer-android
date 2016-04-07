package com.adriencadet.wanderer.ui.controllers.footer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.controllers.BaseController;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.adriencadet.wanderer.ui.screens.footer.PlaceListFooterScreen;
import com.adriencadet.wanderer.ui.screens.PlaceListScreen;
import com.adriencadet.wanderer.ui.screens.footer.PlaceMapFooterScreen;
import com.adriencadet.wanderer.ui.screens.PlaceMapScreen;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.lyft.scoop.Screen;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * FooterController
 * <p>
 */
public class FooterController extends BaseController {
    private Subscription canUseRandomPlaceSubscription;

    @Bind(R.id.footer)
    ViewGroup root;

    @BindColor(R.color.blue)
    int primaryColor;

    @BindColor(R.color.white)
    int whiteColor;

    private void setSelection(int wrapperID, int primaryDrawable, int whiteDrawable) {
        ViewGroup group = (ViewGroup) root.findViewById(wrapperID);
        ImageView img = (ImageView) group.getChildAt(0);

        img.setImageResource(whiteDrawable);
        group.setBackgroundColor(primaryColor);
    }

    @Override
    protected int layoutId() {
        return R.layout.footer;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        Screen screen = Screen.fromController(this);
        if (screen instanceof PlaceMapFooterScreen) {
            setSelection(R.id.footer_map, R.drawable.ic_globe_primary, R.drawable.ic_globe_white);
        } else if (screen instanceof PlaceListFooterScreen) {
            setSelection(R.id.footer_list, R.drawable.ic_list_primary, R.drawable.ic_list_white);
        } else {
            root.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (canUseRandomPlaceSubscription != null) {
            canUseRandomPlaceSubscription.unsubscribe();
        }
    }

    @OnClick(R.id.footer_map)
    public void onMap() {
        appRouter.goTo(new PlaceMapScreen());
    }

    @OnClick(R.id.footer_list)
    public void onList() {
        appRouter.goTo(new PlaceListScreen());
    }

    @OnClick(R.id.footer_random)
    public void onRandom() {
        if (canUseRandomPlaceSubscription != null) {
            canUseRandomPlaceSubscription.unsubscribe();
        }

        canUseRandomPlaceSubscription = dataReadingBLL
            .canUseRandomPlace()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean canUse) {
                    if (canUse) {
                        appRouter.goTo(new PlaceInsightScreen());
                    } else {
                        popupRouter.goTo(new AlertScreen(context.getString(R.string.cannot_use_random)));
                    }
                }
            });
    }
}
