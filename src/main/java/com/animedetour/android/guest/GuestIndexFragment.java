/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.android.R;
import com.animedetour.android.database.guest.GuestRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.groundcontrol.SubscriptionManager;
import com.inkapplications.prism.analytics.ScreenName;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;
import org.apache.commons.logging.Log;
import rx.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Displays a grid of the guests at Detour.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@ScreenName("Guests")
public class GuestIndexFragment extends Fragment
{
    @Inject
    GuestRepository repository;

    @InjectView(R.id.guest_category_index)
    SimpleRecyclerView<GuestWidgetView, Guest> categoryList;

    @Inject
    Log log;

    @Inject
    ImageLoader imageLoader;

    @Inject
    SubscriptionManager subscriptionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_category_index, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.categoryList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        this.categoryList.init(
            new ArrayList<Guest>(),
            new GuestIndexBinder(this.imageLoader, this.log, this.getActivity())
        );
    }

    @Override
    public void onStart()
    {
        super.onStart();

        CategoryUpdateSubscriber subscriber = new CategoryUpdateSubscriber(this.log, this.categoryList);
        Subscription subscription = this.repository.findAllCategories(subscriber);
        this.subscriptionManager.add(subscription);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.subscriptionManager.unsubscribeAll();
    }
}
