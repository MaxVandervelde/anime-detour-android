/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.event;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.android.R;
import com.animedetour.android.view.ImageScrim;
import com.animedetour.sched.api.model.Event;
import org.apache.commons.logging.Log;
import prism.framework.Layout;

import javax.inject.Inject;
import java.util.Date;

/**
 * Event Activity
 *
 * This is the screen that displays detailed information for a single event.
 * This is only ever displayed on top of other activities and should be stripped
 * off of the backstack when leaving.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Layout(R.layout.event)
public class EventActivity extends ActionBarActivity
{
    /**
     * Name of the intent-extra that the event is serialized into
     */
    final public static String EXTRA_EVENT = "event";

    /**
     * View for the top banner on the page that can display an image & title
     */
    @InjectView(R.id.event_banner)
    ImageScrim bannerView;

    /**
     * Main content section of the event
     */
    @InjectView(R.id.event_text)
    TextView descriptionView;

    @InjectView(R.id.event_details)
    TextView eventDetails;

    @InjectView(R.id.event_action_bar)
    Toolbar actionBar;

    /**
     * Service used for loading the banner image
     */
    @Inject
    ImageLoader loader;

    /**
     * Application error logger
     *
     * Used for logging issues with network issues on image lookup
     */
    @Inject
    Log logger;

    /**
     * The event we are currently displaying
     */
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.none);
        this.event = (Event) this.getIntent().getExtras().getSerializable(EXTRA_EVENT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        this.updateBannerImage();

        if (null != this.event.getDescription()) {
            this.descriptionView.setText(Html.fromHtml(this.event.getDescription()));
        } else {
            this.descriptionView.setText("");
        }
        this.bannerView.setTitle(this.event.getName());
        this.eventDetails.setText(this.getEventDetailsString());

        this.setupNavigation();
    }

    @Override
    public void finish()
    {
        super.finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    /**
     * Get details string for event
     *
     * The details for an event include the time and location for the event.
     *
     * @return The details string for the event
     */
    final protected String getEventDetailsString()
    {
        String format = this.getString(R.string.panel_details);
        Date start = this.event.getStartDateTime().toDate();
        Date end = this.event.getEndDateTime().toDate();
        String location = this.event.getVenue();

        return String.format(format, start, end, location);
    }

    /**
     * Updates the top banner based on the event data
     */
    final protected void updateBannerImage()
    {
        if (null == this.event.getMediaUrl()) {
            return;
        }

        this.bannerView.expandImage();
        this.loader.get(this.event.getMediaUrl(), new ImageLoader.ImageListener() {
            @Override public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                EventActivity.this.bannerView.setImage(imageContainer.getBitmap());
            }
            @Override public void onErrorResponse(VolleyError volleyError) {
                EventActivity.this.logger.error("Error loading banner image", volleyError);
            }
        });
    }

    /**
     * Set up the action-bar
     */
    protected void setupNavigation()
    {
        this.actionBar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        this.actionBar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                EventActivity.this.finish();
            }
        });
    }
}
