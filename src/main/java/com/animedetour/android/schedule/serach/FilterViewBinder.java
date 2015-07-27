/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.serach;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.ViewGroup;
import com.inkapplications.android.widget.recyclerview.ItemBoundClickListener;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;

/**
 * Creates and binds filter item views to the available filter data set.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FilterViewBinder implements ItemViewBinder<FilterItemView, String>
{
    final private Context context;
    final private FilterSelectionListener selectionListener;

    public FilterViewBinder(Context context, SearchView searchView)
    {
        this.context = context;
        this.selectionListener = new FilterSelectionListener(searchView);
    }

    @Override
    public FilterItemView createView(ViewGroup viewGroup, int i)
    {
        return new FilterItemView(this.context);
    }

    @Override
    public void bindView(String type, FilterItemView view)
    {
        view.setTitle(type);
        view.setOnClickListener(new ItemBoundClickListener<>(type, this.selectionListener));
    }
}
