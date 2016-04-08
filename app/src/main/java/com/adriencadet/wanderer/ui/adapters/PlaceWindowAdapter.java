package com.adriencadet.wanderer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adriencadet.wanderer.beans.beans.Place;
import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.helpers.DateFormatterHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * PlaceWindowAdapter
 * <p>
 */
public class PlaceWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private Place   item;

    static class ViewHolder {
        @Bind(R.id.adapter_place_window_name)
        TextView name;

        @Bind(R.id.adapter_place_window_date)
        TextView date;
    }

    public PlaceWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null; // On purpose
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view;
        ViewHolder viewHolder = new ViewHolder();

        view = LayoutInflater.from(context).inflate(R.layout.adapter_place_window, null, false);
        ButterKnife.bind(viewHolder, view);

        viewHolder.name.setText(item.getName());
        viewHolder.date.setText(DateFormatterHelper.userFriendy(item));

        return view;
    }

    public void setItem(Place item) {
        this.item = item;
    }
}
