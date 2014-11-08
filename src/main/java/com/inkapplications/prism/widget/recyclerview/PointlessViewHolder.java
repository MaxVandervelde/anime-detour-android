/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Pointless shell of a viewholder.
 *
 * For when you don't want to rely on the viewholder pattern.
 * This is A completely pointless viewholder class that only exists to make
 * using the recyclerview possible.
 * This is just boilerplate.
 * It does nothing.
 * Android is dumb.
 *
 * @param <VIEW> The View that we're holding onto for no good reason.
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class PointlessViewHolder<VIEW extends View> extends RecyclerView.ViewHolder
{
    /**
     * Don't you wish we could natively store this view instead of making a
     * pointless class to wrap it every time? Me too. I I guess for now we'll
     * just keep it here for now.
     */
    final private VIEW view;

    /**
     * @param itemView View to hold onto, just to be spat out moments later.
     */
    public PointlessViewHolder(VIEW itemView)
    {
        super(itemView);
        this.view = itemView;
    }

    /**
     * Gets the view.
     *
     * @return Gets the view we're holding onto for no reason.
     */
    public VIEW getView()
    {
        return this.view;
    }
}
