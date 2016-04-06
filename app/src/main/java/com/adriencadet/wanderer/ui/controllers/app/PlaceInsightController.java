package com.adriencadet.wanderer.ui.controllers.app;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.adapters.PictureSliderAdapter;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.helpers.DateFormatterHelper;
import com.adriencadet.wanderer.ui.helpers.IntFormatterHelper;
import com.adriencadet.wanderer.ui.screens.app.PlaceInsightScreen;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lyft.scoop.Screen;
import com.viewpagerindicator.PageIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * PlaceInsightController
 * <p>
 */
public class PlaceInsightController extends BaseAppController {
    private PlaceBLLDTO  currentPlace;
    private Subscription listPicturesForPlaceSubscription;
    private Subscription randomPlaceSubscription;
    private Subscription toggleLikeSubscription;

    @Inject
    @Named("segue")
    EventBus segueBus;

    @Bind(R.id.place_insight_slider)
    ViewPager sliderView;

    @Bind(R.id.place_insight_slider_indicators)
    PageIndicator pageIndicatorView;

    @Bind(R.id.place_insight_name)
    TextView nameView;

    @Bind(R.id.place_insight_country)
    TextView countryView;

    @Bind(R.id.place_insight_date)
    TextView dateView;

    @Bind(R.id.place_insight_description)
    TextView descriptionView;

    @Bind(R.id.place_insight_random_icon)
    View randomIconView;

    @Bind(R.id.place_insight_like_icon)
    ImageView likeIconView;

    @BindDrawable(R.drawable.ic_liked)
    Drawable likedIcon;

    @BindDrawable(R.drawable.ic_unliked)
    Drawable unlikedIcon;

    @Bind(R.id.place_insight_like_label)
    TextView likeLabelView;

    private void toggleLikeButton() {
        if (currentPlace.isLiking()) {
            likeIconView.setImageDrawable(likedIcon);
        } else {
            likeIconView.setImageDrawable(unlikedIcon);
        }
        likeLabelView.setText(IntFormatterHelper.userFriendly(currentPlace.getLikes()));
    }

    private void setContent(PlaceBLLDTO place, boolean mustHideSpinner) {
        currentPlace = place;

        nameView.setText(place.getName());
        countryView.setText(place.getCountry());
        dateView.setText(DateFormatterHelper.userFriendy(place));
        descriptionView.setText(place.getDescription());
        toggleLikeButton();

        if (listPicturesForPlaceSubscription != null) {
            listPicturesForPlaceSubscription.unsubscribe();
        }

        listPicturesForPlaceSubscription = dataReadingBLL
            .listPicturesForPlace(place)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSubscriber<List<PictureBLLDTO>>() {
                @Override
                public void onCompleted() {
                    if (mustHideSpinner) {
                        hideSpinner();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (mustHideSpinner) {
                        hideSpinner();
                    }
                }

                @Override
                public void onNext(List<PictureBLLDTO> pictureBLLDTOs) {
                    sliderView.setAdapter(new PictureSliderAdapter(context, pictureBLLDTOs));
                    pageIndicatorView.setViewPager(sliderView);
                }
            });
    }

    private void setRandomContent(boolean mustPlayAnimation) {
        showSpinner();

        if (randomPlaceSubscription != null) {
            randomPlaceSubscription.unsubscribe();
        }

        randomPlaceSubscription = dataReadingBLL
            .randomPlace()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSubscriber<PlaceBLLDTO>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    hideSpinner();
                }

                @Override
                public void onNext(PlaceBLLDTO placeBLLDTO) {
                    setContent(placeBLLDTO, true);

                    if (mustPlayAnimation) {
                        YoYo
                            .with(Techniques.Shake)
                            .duration(500)
                            .playOn(randomIconView);
                    }
                }
            });
    }

    @Override
    protected int layoutId() {
        return R.layout.place_insight;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        WandererApplication.getApplicationComponent().inject(this);
        PlaceInsightScreen screen = Screen.fromController(this);

        if (screen.hasPlace()) {
            showSpinner();
            setContent(screen.place, true);
        } else {
            randomIconView.setVisibility(View.VISIBLE);
            setRandomContent(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (listPicturesForPlaceSubscription != null) {
            listPicturesForPlaceSubscription.unsubscribe();
        }

        if (randomPlaceSubscription != null) {
            randomPlaceSubscription.unsubscribe();
        }

        if (toggleLikeSubscription != null) {
            toggleLikeSubscription.unsubscribe();
        }

        segueBus.post(new SegueEvents.Exit.PlaceInsight());
    }

    @OnClick(R.id.place_insight_close_icon)
    public void onClose() {
        appRouter.goBack();
    }

    @OnClick(R.id.place_insight_random_icon)
    public void onRandom() {
        setRandomContent(true);
    }

    @OnClick(R.id.place_insight_like_wrapper)
    public void onLikeToggle() {
        if (toggleLikeSubscription != null) {
            toggleLikeSubscription.unsubscribe();
        }

        toggleLikeSubscription =
            dataWritingBLL
                .toggleLike(currentPlace)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PlaceBLLDTO>() {
                    @Override
                    public void onCompleted() {
                        toggleLikeButton();
                    }

                    @Override
                    public void onNext(PlaceBLLDTO placeBLLDTO) {
                        currentPlace = placeBLLDTO;
                    }
                });
    }
}
