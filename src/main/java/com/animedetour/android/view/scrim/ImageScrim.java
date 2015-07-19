/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.scrim;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LightingColorFilter;
import android.graphics.PointF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.animedetour.android.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Image with text Scrim
 *
 * This is a generic image with a scrim protection behind the title text
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ImageScrim extends FrameLayout
{
    /**
     * The main image displayed
     */
    final private SimpleDraweeView background;

    /**
     * The title text overlayed on the image
     */
    final private TextView title;

    /**
     * Height to set the view to when the image is set
     */
    private int imageHeight;

    public ImageScrim(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_image_scrim, this);

        this.background = (SimpleDraweeView) this.findViewById(R.id.banner);
        this.background.setColorFilter(new LightingColorFilter(0xffaaaaaa, 0x000000));
        this.background.getHierarchy().setActualImageFocusPoint(new PointF(.5f, .38f));

        this.title = (TextView) this.findViewById(R.id.title);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.ImageScrim,
            0,
            0
        );

        int src = attributes.getResourceId(R.styleable.ImageScrim_image_background, 0);
        String title = attributes.getString(R.styleable.ImageScrim_image_title);
        this.imageHeight = attributes.getDimensionPixelSize(R.styleable.ImageScrim_image_height, 0);
        int textPadding = attributes.getDimensionPixelSize(R.styleable.ImageScrim_image_textPaddingTop, 0);

        if (0 != src) {
            this.background.setImageResource(src);
            this.getLayoutParams().height = this.imageHeight;
        }


        this.title.setText(title);

        int leftPadding = this.title.getPaddingLeft();
        int topPadding = this.title.getPaddingTop() + textPadding;
        int rightPadding = this.title.getPaddingRight();
        int bottomPadding = this.title.getPaddingBottom();
        this.title.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }

    /**
     * Expand to the specified image height regardless of if image bitmap is set
     */
    public void expandImage()
    {
        this.background.getLayoutParams().height = this.imageHeight;
    }

    public void setImage(String uri)
    {
        if (null == uri) {
            this.background.setImageURI(null);
        } else {
            this.background.setImageURI(Uri.parse(uri));
        }
    }

    /**
     * Set the main image text
     *
     * @param title Text to overlay on the image
     */
    public void setTitle(String title)
    {
        this.title.setText(title);
    }
}
