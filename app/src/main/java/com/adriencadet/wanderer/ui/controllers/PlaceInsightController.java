package com.adriencadet.wanderer.ui.controllers;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.adapters.PictureSliderAdapter;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.lyft.scoop.Screen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * PlaceInsightController
 * <p>
 */
public class PlaceInsightController extends BaseController {

    @Bind(R.id.place_insight_slider)
    ViewPager sliderView;

    @Bind(R.id.place_insight_name)
    TextView nameView;

    @Bind(R.id.place_insight_country)
    TextView countryView;

    @Bind(R.id.place_insight_date)
    TextView dateView;

    @Bind(R.id.place_insight_description)
    TextView description;

    private String userFriendlyVisitDate(PlaceBLLDTO place) {
        DateFormat format;

        format = new SimpleDateFormat("MMM yyyy");

        return format.format(place.getVisitDate().toDate());
    }

    @Override
    protected int layoutId() {
        return R.layout.place_insight;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        PlaceInsightScreen screen = Screen.fromController(this);
        PlaceBLLDTO place = screen.place;

        nameView.setText(place.getName());
        countryView.setText(place.getCountry());
        dateView.setText(userFriendlyVisitDate(place));
        description.setText(place.getDescription());

        dataReadingBLL
            .listPicturesForPlace(place)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSubscriber<List<PictureBLLDTO>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onNext(List<PictureBLLDTO> pictureBLLDTOs) {
                    sliderView.setAdapter(new PictureSliderAdapter(context, pictureBLLDTOs));
                }
            });
    }

    @OnClick(R.id.place_insight_close_icon)
    public void onClose() {
        appRouter.goBack();
    }
}
