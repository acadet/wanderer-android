package com.adriencadet.wanderer.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PictureSliderAdapter
 * <p>
 */
public class PictureSliderAdapter extends PagerAdapter {
    private Context             context;
    private List<PictureBLLDTO> items;
    private Map<Integer, View>  inflatedViews;

    public PictureSliderAdapter(Context context, List<PictureBLLDTO> items) {
        this.context = context;
        this.items = items;
        this.inflatedViews = new HashMap<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        ImageView picture;
        PictureBLLDTO item = items.get(position);

        if (inflatedViews.containsKey(position)) {
            view = inflatedViews.get(position);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_picture_slider, container, false);
            inflatedViews.put(position, view);
        }

        picture = (ImageView) view.findViewById(R.id.adapter_picture_slider_embedded);
        Picasso
            .with(context)
            .load(item.getUrl())
            .into(picture);

        container.addView(view);

        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (inflatedViews.containsKey(position)) {
            inflatedViews.remove(position);
        }
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        int index = items.indexOf(object);

        if (index >= 0) {
            if (inflatedViews.containsKey(index)) {
                return view.equals(inflatedViews.get(index));
            }
        }

        return false;
    }
}
