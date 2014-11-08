/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.view.View;

/**
 * Item based implementation of android's click listener.
 *
 * Intended to be bound to a list item in a recycler or listview, this
 * implementation of the android View `OnClickListener` invokes a provided
 * instance of a `ViewClickListener` with the item data that was clicked.
 * This will cast the clicked view to the specified view type.
 *
 * @see com.inkapplications.prism.widget.recyclerview.ViewClickListener
 * @param <VIEW> The type of the view that this listner is bound to
 * @param <ITEM> The data type represented in the view
 */
final public class ItemBoundClickListener<VIEW extends View, ITEM> implements View.OnClickListener
{
    /**
     * Data item to which this listener is bound, for informing the ViewClickListener.
     */
    final ITEM item;

    /**
     * Listener to forward notifications to when an item & view have been clicked.
     */
    final ViewClickListener<VIEW, ITEM> clickListener;

    /**
     * @param item Data item to which this listener is bound, for informing the ViewClickListener
     * @param clickListener Listener to forward notifications to when an item & view have been clicked
     */
    public ItemBoundClickListener(ITEM item, ViewClickListener<VIEW, ITEM> clickListener)
    {
        this.item = item;
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View view)
    {
        this.clickListener.onViewClicked(item, (VIEW) view);
    }
}
