/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.animedetour.android.view.schedule.PanelView;
import com.animedetour.sched.api.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel List Adapter
 *
 *  Adapts a list of panel objects into view objects.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class PanelListAdapter extends BaseAdapter
{
    private Context context;
    private List<Event> events;

    public PanelListAdapter(Context context)
    {
        this.context = context;
        this.events = new ArrayList<Event>();
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return this.events.size();
    }

    @Override
    public Event getItem(int i)
    {
        return this.events.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View recycledView, ViewGroup viewGroup)
    {
        PanelView view;

        if (null == recycledView) {
            view = new PanelView(this.context);
        } else {
            view = (PanelView) recycledView;
        }

        Event event = this.getItem(i);
        view.bind(event);

        return view;
    }
}
