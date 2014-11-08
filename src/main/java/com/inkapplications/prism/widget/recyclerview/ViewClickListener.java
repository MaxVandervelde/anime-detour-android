/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.view.View;

/**
 * Callback to be invoked when an item in a list view is clicked
 *
 * @param <VIEW> Type of the View in the list
 * @param <ITEM> Data type bound to the view
 */
public interface ViewClickListener<VIEW extends View, ITEM>
{
    /**
     * Called when an item in the recycler list view is clicked.
     *
     * @param item The item bound to the position that was clicked
     * @param view The view that was clicked on
     */
    public void onViewClicked(ITEM item, VIEW view);
}
