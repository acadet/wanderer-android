package com.adriencadet.wanderer.ui.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.events.SegueEvents;
import com.adriencadet.wanderer.ui.routers.IRouterGoBackObserver;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Footer
 * <p>
 */
public class Footer implements IRouterGoBackObserver {
    private enum Section {
        MAP, LIST
    }

    private class Item {
        public Section type;
        public int     layoutID;
        public int     primarySrcID;
        public int     whiteSrcID;

        Item(Section type, int layoutID, int primarySrcID, int whiteSrcID) {
            this.type = type;
            this.layoutID = layoutID;
            this.primarySrcID = primarySrcID;
            this.whiteSrcID = whiteSrcID;
        }
    }

    private boolean        isActive;
    private EventBus       segueBus;
    private Stack<Section> sectionHistory;
    private View           motherView;
    private List<Item>     groups;

    @BindColor(R.color.blue)
    int primaryColor;

    @BindColor(R.color.white)
    int whiteColor;

    public Footer(View view, EventBus segueBus) {
        this.isActive = true;
        this.motherView = view;
        this.segueBus = segueBus;
        this.groups = new ArrayList<>();
        this.sectionHistory = new Stack<>();

        groups.add(new Item(Section.MAP, R.id.footer_map, R.drawable.ic_globe_primary, R.drawable.ic_globe_white));
        groups.add(new Item(Section.LIST, R.id.footer_list, R.drawable.ic_list_primary, R.drawable.ic_list_white));

        ButterKnife.bind(this, view);
        toggleSelection(Section.MAP);
    }

    public void toggleSelection(Section selection) {
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

        sectionHistory.add(selection);
    }

    public void show() {
        isActive = true;
        motherView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        isActive = false;
        motherView.setVisibility(View.GONE);
    }

    @OnClick(R.id.footer_map)
    public void onMap() {
        if (sectionHistory.peek() != Section.MAP) {
            toggleSelection(Section.MAP);
            segueBus.post(new SegueEvents.Show.PlaceMap());
        }
    }

    @OnClick(R.id.footer_list)
    public void onList() {
        if (sectionHistory.peek() != Section.LIST) {
            toggleSelection(Section.LIST);
            segueBus.post(new SegueEvents.Show.PlaceList());
        }
    }

    @OnClick(R.id.footer_random)
    public void onRandom() {
        segueBus.post(new SegueEvents.Show.RandomPlace());
    }

    @Override
    public void onGoingBack() {
        if (!isActive) {
            // Ignore events because footer is hidden so no new screen was stacked
            return;
        }

        sectionHistory.pop();
        if (!sectionHistory.isEmpty()) {
            toggleSelection(sectionHistory.pop());
        }
    }
}
