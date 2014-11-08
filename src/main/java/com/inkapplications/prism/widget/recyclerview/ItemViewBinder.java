/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.view.View;

/**
 * Item-View Binder.
 *
 * Describes the bare minimum functionality needed to implement a recycler view
 * adapter. These implementations need to create views and bind data to them.
 *
 * @param <VIEW> The type of the view that is being displayed for each item
 * @param <ITEM> The item to represent the data to be displayed in the view
 */
public interface ItemViewBinder<VIEW extends View, ITEM>
{
    /**
     * Create a new instance of the view to be used in the list view.
     *
     * This should the root view to be displayed for a single item in the list
     * and does not need to be bound to data yet.
     *
     * @return A new instance of the view objects displayed
     */
    public VIEW createView();

    /**
     * Bind data to view.
     *
     * Update the view with any needed information to be displayed, click
     * handlers, etc. that need to be attached before displaying in the list
     * based on a single item in our collection.
     * The View object provided may have been recycled and contain old data
     * bound to it that should be overwritten or discarded.
     *
     * @param item The item data to be displayed in the view
     * @param view The view object in the list to bind the data to
     */
    public void bindView(ITEM item, VIEW view);
}
