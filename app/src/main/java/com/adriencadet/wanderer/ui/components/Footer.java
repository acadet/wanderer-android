package com.adriencadet.wanderer.ui.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.events.SegueEvents;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Footer
 * <p>
 */
public class Footer {
    private enum ItemType {
        MAP, LIST
    }

    private class Item {
        public ItemType type;
        public int      layoutID;
        public int      primarySrcID;
        public int      whiteSrcID;

        Item(ItemType type, int layoutID, int primarySrcID, int whiteSrcID) {
            this.type = type;
            this.layoutID = layoutID;
            this.primarySrcID = primarySrcID;
            this.whiteSrcID = whiteSrcID;
        }
    }

    private EventBus   segueBus;
    private ItemType   currentItemType;
    private View       motherView;
    private List<Item> groups;

    @BindColor(R.color.blue)
    int primaryColor;

    @BindColor(R.color.white)
    int whiteColor;

    private void toggleSelection(ItemType selection) {
        for (Item item : groups) {
            ViewGroup group = (ViewGroup) motherView.findViewById(item.layoutID);
            ImageView img = (ImageView) group.getChildAt(0);

            if (item.type == selection) {
                img.setImageResource(item.whiteSrcID);
                group.setBackgroundColor(primaryColor);
            } else {
                img.setImageResource(item.primarySrcID);
                group.setBackgroundColor(whiteColor);
            }
        }

        currentItemType = selection;
    }

    public Footer(View view, EventBus segueBus) {
        this.motherView = view;
        this.segueBus = segueBus;
        this.groups = new ArrayList<>();

        groups.add(new Item(ItemType.MAP, R.id.footer_map, R.drawable.ic_globe_primary, R.drawable.ic_globe_white));
        groups.add(new Item(ItemType.LIST, R.id.footer_list, R.drawable.ic_list_primary, R.drawable.ic_list_white));

        ButterKnife.bind(this, view);
        toggleSelection(ItemType.MAP);
    }

    @OnClick(R.id.footer_map)
    public void onMap() {
        if (currentItemType != ItemType.MAP) {
            toggleSelection(ItemType.MAP);
            segueBus.post(new SegueEvents.ShowPlaceMap());
        }
    }

    @OnClick(R.id.footer_list)
    public void onList() {
        if (currentItemType != ItemType.LIST) {
            toggleSelection(ItemType.LIST);
            segueBus.post(new SegueEvents.ShowPlaceList());
        }
    }

    @OnClick(R.id.footer_random)
    public void onRandom() {
        segueBus.post(new SegueEvents.ShowRandomPlace());
    }
}
