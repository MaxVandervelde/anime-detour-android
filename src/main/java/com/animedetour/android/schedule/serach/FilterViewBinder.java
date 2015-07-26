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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.inkapplications.android.widget.recyclerview.ItemBoundClickListener;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;
import com.inkapplications.android.widget.recyclerview.ViewClickListener;

/**
 * Creates and binds filter item views to the available filter data set.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FilterViewBinder implements ItemViewBinder<FilterItemView, String>
{
    final private Context context;

    public FilterViewBinder(Context context)
    {
        this.context = context;
    }

    @Override
    public FilterItemView createView(ViewGroup viewGroup, int i)
    {
        return new FilterItemView(this.context);
    }

    @Override
    public void bindView(String o, FilterItemView view)
    {
        view.setTitle(o);
        view.setOnClickListener(new ItemBoundClickListener<>(o,
            new ViewClickListener<View, String>() {
                @Override public void onViewClicked(String s, View view) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            }
        ));
    }
}
