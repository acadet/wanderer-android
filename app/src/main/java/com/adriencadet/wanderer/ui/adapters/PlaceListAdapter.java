package com.adriencadet.wanderer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.ui.controllers.body.IPlaceUpdateObserver;
import com.adriencadet.wanderer.ui.helpers.DateFormatterHelper;
import com.adriencadet.wanderer.ui.routers.IRouter;
import com.adriencadet.wanderer.ui.screens.PlaceInsightScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * PlaceListAdapter
 * <p>
 */
public class PlaceListAdapter extends BaseAdapter<Place> implements IPlaceUpdateObserver {
    static class ViewHolder {
        @Bind(R.id.adapter_place_list_background)
        ImageView background;

        @Bind(R.id.adapter_place_list_name)
        TextView name;

        @Bind(R.id.adapter_place_list_country)
        TextView country;

        @Bind(R.id.adapter_place_list_like)
        View like;

        @Bind(R.id.adapter_place_list_date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private IRouter appRouter;
    private boolean mustLoadPictures;

    public PlaceListAdapter(Context context, List<Place> items, IRouter appRouter) {
        this(context, items, appRouter, true);
    }

    public PlaceListAdapter(Context context, List<Place> items, IRouter appRouter, boolean mustLoadPictures) {
        super(context, items);

        this.appRouter = appRouter;
        this.mustLoadPictures = mustLoadPictures;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        Place item = itemAt(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_place_list, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
            Picasso.with(getContext()).cancelRequest(holder.background);
        }

        if (mustLoadPictures) {
            holder.background.setAlpha(0.5f);
            Picasso
                .with(getContext())
                .load(item.getMainPicture().getUrl())
                .into(holder.background);
        } else {
            holder.background.setAlpha(1f);
            holder.background.setImageDrawable(null);
        }

        holder.name.setText(item.getName());
        holder.country.setText(item.getCountry());
        holder.date.setText(DateFormatterHelper.userFriendy(item));

        if (item.isLiking()) {
            holder.like.setVisibility(View.VISIBLE);
        } else {
            holder.like.setVisibility(View.GONE);
        }

        view.setOnClickListener((v) -> {
            appRouter.goTo(new PlaceInsightScreen(item, this));
        });

        return view;
    }

    @Override
    public void onUpdate(Place updatedPlace) {
        swap(updatedPlace, (a, b) -> a.getId() == b.getId());
    }
}
