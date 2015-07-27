/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.serach;

import android.support.v7.widget.SearchView;
import com.inkapplications.android.widget.recyclerview.ViewClickListener;

/**
 * Updates the query field to match whenever a filter item is selected by the user.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FilterSelectionListener implements ViewClickListener<FilterItemView, String>
{
    final private SearchView searchView;

    public FilterSelectionListener(SearchView searchView)
    {
        this.searchView = searchView;
    }

    @Override
    public void onViewClicked(String filter, FilterItemView view)
    {
        this.searchView.setQuery(filter, true);
    }
}
