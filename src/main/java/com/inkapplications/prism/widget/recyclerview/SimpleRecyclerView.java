/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Simple Item and View based recycler view.
 *
 * This is a simple implementation of the recyclerview intended to be used
 * when binding a simple collection of items to a single view type.
 *
 * This acts more like an old-fashioned ListView, with all the fun of a
 * RecyclerView but without all the horrifying boilerplate.
 *
 * If you need more custom control of the holders or views than this class
 * provides, you're probably using the wrong abstraction. Implement your own
 * custom RecyclerView.
 *
 * @param <VIEW> The View type to display for each item in the list
 * @param <TYPE> The Data Object type you're displaying views for
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class SimpleRecyclerView<VIEW extends View, TYPE> extends RecyclerView
{
    /**
     * Specific Item Adapter instance.
     *
     * This adapter will get set when initializations get run or it is done so
     * manually. It should be unset if a standard adapter is set in order to
     * maintain compatibility with standard adapter views.
     */
    private ItemAdapter<VIEW, TYPE> adapter;

    public SimpleRecyclerView(Context context)
    {
        super(context);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * Initialize the view with data and bindings.
     *
     * This will create the default adapter (ItemAdapter) if none is set as well
     * as a LinearLayoutManager as a default if none is set.
     *
     * @see com.inkapplications.prism.widget.recyclerview.ItemAdapter
     * @param items Items to be displayed in the view
     * @param binder For binding data to the views and creating new instances
     */
    public void init(List<TYPE> items, ItemViewBinder<VIEW, TYPE> binder)
    {
        if (null == this.getLayoutManager()) {
            this.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }
        if (null == this.getAdapter()) {
            ItemAdapter<VIEW, TYPE> adapter = new ItemAdapter<VIEW, TYPE>(items, binder);
            this.setItemAdapter(adapter);
        }
    }

    @Override
    public void setAdapter(Adapter adapter)
    {
        super.setAdapter(adapter);
        this.adapter = null;
    }

    /**
     * Set a custom item adapter.
     *
     * @param adapter Adapter to use in the view
     */
    public void setItemAdapter(ItemAdapter<VIEW, TYPE> adapter)
    {
        this.setAdapter(adapter);
        this.adapter = adapter;
    }

    /**
     * Get the current Item adapter, if available.
     *
     * If a standard adapter has been set on the recycler, this will return null.
     *
     * @return The item specific adapter instance, if available. Null otherwise
     */
    final public ItemAdapter<VIEW, TYPE> getItemAdapter()
    {
        return this.adapter;
    }
}
