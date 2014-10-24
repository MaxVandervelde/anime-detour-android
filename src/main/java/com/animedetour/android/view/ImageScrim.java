/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.animedetour.android.R;

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
    private ImageView background;

    /**
     * The title text overlayed on the image
     */
    private TextView title;

    /**
     * Text protection scrim
     */
    private View scrim;

    /**
     * Height to set the view to when the image is set
     */
    private int imageHeight;

    public ImageScrim(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.inflateView(context);

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
            this.scrim.setVisibility(VISIBLE);
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

    /**
     * Set the main image
     *
     * @param drawable The image to display
     */
    public void setImage(Bitmap drawable)
    {
        if (null == drawable) {
            this.background.setImageResource(0);
            this.scrim.setVisibility(INVISIBLE);
            return;
        }

        this.background.setImageBitmap(drawable);
        this.scrim.setVisibility(VISIBLE);
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

    /**
     * Inflate the main view layout and bind needed elements
     */
    private void inflateView(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.view_image_scrim, this);

        this.background = (ImageView) this.findViewById(R.id.banner);
        this.title = (TextView) this.findViewById(R.id.title);
        this.scrim = this.findViewById(R.id.scrim);
        this.scrim.setVisibility(INVISIBLE);
    }
}
