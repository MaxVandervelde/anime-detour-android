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
import android.graphics.PointF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.animedetour.android.R;
import com.animedetour.api.guest.model.Guest;
import com.facebook.drawee.view.SimpleDraweeView;

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
    final private SimpleDraweeView image;

    /**
     * The displayed name of the guest.
     */
    final private TextView name;

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
        this.image = (SimpleDraweeView) this.findViewById(R.id.guest_widget_image);
        this.image.getHierarchy().setActualImageFocusPoint(new PointF(.5f, .38f));
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
        this.setName(guest.getFullName());
        this.setImageUri(guest.getPhoto());
    }

    /**
     * @param name Name of the guest to be displayed.
     */
    public void setName(String name)
    {
        this.name.setText(name);
    }

    public void setImageUri(String uri)
    {
        this.image.setImageURI(Uri.parse(uri));
    }
}
