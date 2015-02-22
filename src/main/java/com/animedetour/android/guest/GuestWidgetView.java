/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.animedetour.android.R;
import com.animedetour.api.guest.model.Guest;

/**
 * A small widget view to represent a guest.
 *
 * This just displays the guest's name and an icon for a simple glance-view.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class GuestWidgetView extends FrameLayout
{
    /**
     * The avatar for the guest.
     */
    final private ImageView image;

    /**
     * The displayed name of the guest.
     */
    final private TextView name;

    private Guest displayedGuest;

    public GuestWidgetView(Context context)
    {
        this(context, null, 0);
    }

    public GuestWidgetView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GuestWidgetView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.guest_widget_view, this);
        this.image = (ImageView) this.findViewById(R.id.guest_widget_image);
        this.name = (TextView) this.findViewById(R.id.guest_widget_name);
    }

    /**
     * Attach a guest to the view for later reference and bind it's properties
     * to the display.
     *
     * @param guest The guest to display in the view.
     */
    public void bindGuest(Guest guest)
    {
        this.setName(guest.getFirstName());
        this.displayedGuest = guest;
    }

    /**
     * @return The guest that is currently displayed in the view.
     */
    public Guest getDisplayedGuest()
    {
        return displayedGuest;
    }

    /**
     * @param name Name of the guest to be displayed.
     */
    public void setName(String name)
    {
        this.name.setText(name);
    }

    /**
     * @param image Avatar of the guest.
     */
    public void setImage(Bitmap image)
    {
        this.image.setImageBitmap(image);
    }

    /**
     * Reset the guest's avatar to a default icon.
     *
     * This could be called on error, or to reset the view to a default state.
     */
    public void showDefaultImage()
    {
        this.image.setImageResource(R.drawable.account_96dp);
    }
}
