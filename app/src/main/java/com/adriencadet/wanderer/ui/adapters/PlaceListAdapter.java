package com.adriencadet.wanderer.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * PlaceListAdapter
 * <p>
 */
public class PlaceListAdapter extends BaseAdapter<PlaceBLLDTO> {

    @Inject
    @Named("segue")
    EventBus segueBus;

    static class ViewHolder {
        @Bind(R.id.adapter_place_list_background)
        ImageView background;

        @Bind(R.id.adapter_place_list_name)
        TextView name;

        @Bind(R.id.adapter_place_list_country)
        TextView country;

        @Bind(R.id.adapter_place_list_date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public PlaceListAdapter(Context context, List<PlaceBLLDTO> items) {
        super(context, items);

        WandererApplication.getApplicationComponent().inject(this);
    }

    private String userFriendlyVisitDate(PlaceBLLDTO item) {
        DateFormat format;

        format = new SimpleDateFormat("MMM yyyy");

        return format.format(item.getVisitDate().toDate());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        PlaceBLLDTO item = itemAt(position);

        view = recycle(R.layout.adapter_place_list, convertView, parent);
        holder = new ViewHolder(view);

        Picasso
            .with(getContext())
            .load(item.getMainPicture().getUrl())
            .into(holder.background);

        holder.name.setText(item.getName());
        holder.country.setText(item.getCountry());
        holder.date.setText(userFriendlyVisitDate(item));

        view.setOnClickListener((v) -> {
            segueBus.post(new SegueEvents.ShowPlaceInsight(item));
        });

        return view;
    }
}
