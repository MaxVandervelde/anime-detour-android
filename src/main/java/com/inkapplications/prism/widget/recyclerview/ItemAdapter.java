/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Item-based Adapter for RecyclerViews
 *
 * A generic Adapter class that stores a collection of items and delegates the
 * view creation to an ItemBinder. Used to remove some of the boilerplate for
 * RecyclerViews when you only need to store a simple collection.
 *
 * If you need more custom control of the items than this class
 * provides, you're probably using the wrong abstraction. Implement your own
 * custom Adapter.
 *
 * @param <VIEW> The type of the view that is being displayed for each item
 * @param <ITEM> The item to represent the data to be displayed in the view
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ItemAdapter<VIEW extends View, ITEM> extends RecyclerView.Adapter<PointlessViewHolder<VIEW>>
{
    /**
     * Collection of items to be displayed in the list.
     */
    private List<ITEM> items;

    /**
     * Binder used to update and create views.
     */
    private ItemViewBinder<VIEW, ITEM> itemBinder;

    /**
     * @param items Collection of items to be displayed in the list
     * @param binder Binder used to update and create views
     */
    public ItemAdapter(List<ITEM> items, ItemViewBinder<VIEW, ITEM> binder)
    {
        this.items = items;
        this.itemBinder = binder;
    }

    @Override
    public PointlessViewHolder<VIEW> onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new PointlessViewHolder<VIEW>(this.itemBinder.createView());
    }

    @Override
    public void onBindViewHolder(PointlessViewHolder<VIEW> viewHolder, int i)
    {
        ITEM item = this.items.get(i);
        VIEW view = viewHolder.getView();
        this.itemBinder.bindView(item, view);

    }

    @Override
    public int getItemCount()
    {
        return this.items.size();
    }

    /**
     * Add single item to be displayed in the list.
     *
     * Adds in a single item at the end of the current collection and triggers
     * a notification of the item inserted so that animations will be triggered.
     *
     * @param newItem The item to be added to the list
     */
    public void addItem(ITEM newItem)
    {
        this.items.add(newItem);
        this.notifyItemInserted(this.getItemCount() - 1);
    }

    /**
     * Add Items to the displayed list.
     *
     * Adds in the items specified at the end of the current collection and
     * triggers a notification of the range inserted so that animations
     * will be triggered.
     *
     * @param newItems The items to add to the list
     */
    public void addItems(List<ITEM> newItems)
    {
        if (null == newItems|| 0 == newItems.size()) {
            return;
        }

        int previousCount = this.items.size();
        this.items.addAll(newItems);
        this.notifyItemRangeInserted(previousCount, newItems.size());
    }

    /**
     * Replace the displayed items.
     *
     * Sets the collection to the specified list of items and triggers a
     * notification that the entire range has changed. If the new list is null,
     * it will clear the list.
     *
     * @param items The new list of items to display or null to empty the list
     */
    public void setItems(List<ITEM> items)
    {
        if (null == items) {
            this.items = new ArrayList<ITEM>();
        } else {
            this.items = items;
        }

        this.notifyDataSetChanged();
    }

    /**
     * Remove a single item from the list.
     *
     * Removes an item from the collection and triggers a notification of the
     * removal so that animations can be triggered.
     * If the object does not exist in the list, no further action is taken.
     *
     * @param item The item to be removed
     */
    public void removeItem(ITEM item)
    {
        int position = this.items.indexOf(item);

        if (-1 == position) {
            return;
        }

        this.items.remove(item);
        this.notifyItemRemoved(position);
    }

    /**
     * Remove a set of items from the list.
     *
     * Removes multiple items from the collection and triggers a notification of
     * the removal so that animations can be triggered.
     * If any object does not exist in the list, no further action is taken for
     * that object.
     *
     * @param items The item to be removed
     */
    public void removeItems(List<ITEM> items)
    {
        for (ITEM item : items) {
            this.removeItem(item);
        }
    }
}
