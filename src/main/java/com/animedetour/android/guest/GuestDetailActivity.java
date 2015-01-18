/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.guest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.android.R;
import com.animedetour.android.view.ImageScrim;
import com.animedetour.api.guest.model.Guest;
import org.apache.commons.logging.Log;
import prism.framework.Layout;

import javax.inject.Inject;

/**
 * A detailed view of a particular guest.
 *
 * This activity REQUIRES the following arguments to start:
 *  * ARG_GUEST
 *
 * @see #ARG_GUEST
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Layout(R.layout.guest_detail)
final public class GuestDetailActivity extends ActionBarActivity
{
    @InjectView(R.id.guest_avatar)
    ImageScrim avatar;

    @InjectView(R.id.guest_bio)
    TextView bio;

    @InjectView(R.id.guest_category)
    TextView category;

    @InjectView(R.id.guest_action_bar)
    Toolbar actionBar;

    @Inject
    ImageLoader imageLoader;

    @Inject
    Log log;

    /**
     * Argument flag for the serialized guest object extra to be passed into the
     * intent starting this activity.
     */
    final public static String ARG_GUEST = "guest";

    /**
     * The guest object being displayed on the activity.
     */
    private Guest guest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.guest = (Guest) this.getIntent().getSerializableExtra(ARG_GUEST);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        this.actionBar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        this.actionBar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                GuestDetailActivity.this.finish();
            }
        });

        this.bio.setText(Html.fromHtml(this.guest.getBio()));
        this.bio.setMovementMethod(LinkMovementMethod.getInstance());
        this.category.setText(this.guest.getCategory().getName());
        this.avatar.setTitle(this.guest.getFullName());
        this.avatar.expandImage();

        GuestDetailsImageLoader loaderCallback = new GuestDetailsImageLoader(this.avatar, this.log);
        this.imageLoader.get(this.guest.getPhoto(), loaderCallback);
        if (null != this.guest.getFullPhoto()) {
            this.imageLoader.get(this.guest.getPhoto(), loaderCallback);
        }
    }
}
