/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.schedule;

import android.content.Context;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.prism.widget.recyclerview.ItemBoundClickListener;
import com.inkapplications.prism.widget.recyclerview.ItemViewBinder;
import com.inkapplications.prism.widget.recyclerview.ViewClickListener;

/**
 * Binds Events to the displayed Panel View
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
class EventViewBinder implements ItemViewBinder<PanelView, Event>
{
    /**
     * Context to be used when creating new panel views.
     */
    private Context context;

    /**
     * Listener to invoke when a panel view is clicked.
     */
    private ViewClickListener<PanelView, Event> clickListener;

    /**
     * @param context Context to be used when creating new panel views
     * @param clickListener Listener to invoke when a panel view is clicked
     */
    public EventViewBinder(Context context, ViewClickListener<PanelView, Event> clickListener)
    {
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public PanelView createView()
    {
        return new PanelView(this.context);
    }

    @Override
    public void bindView(final Event event, final PanelView view)
    {
        view.bind(event);
        view.setOnClickListener(new ItemBoundClickListener<>(event, this.clickListener));
    }
}
