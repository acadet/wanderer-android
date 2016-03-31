package com.adriencadet.wanderer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;

import java.util.List;

/**
 * BaseAdapter
 * <p>
 */
abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private Context context;
    private List<T> items;

    BaseAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    Context getContext() {
        return context;
    }

    T itemAt(int position) {
        return items.get(position);
    }

    <U> U recycle(int layoutID, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return (U) LayoutInflater.from(context).inflate(layoutID, parent, false);
        } else {
            return (U) convertView;
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return itemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<T> items) {
        items.clear();
        Stream.of(items).forEach(items::add);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        items.add(item);
    }
}
