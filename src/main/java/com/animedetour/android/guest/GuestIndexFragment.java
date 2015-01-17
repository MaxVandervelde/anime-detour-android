/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
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
import com.animedetour.android.database.GuestRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.prism.widget.recyclerview.SimpleRecyclerView;
import org.apache.commons.logging.Log;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Displays a grid of the guests at Detour.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
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
        CategoryUpdateSubscriber categoryUpdateSubscriber = new CategoryUpdateSubscriber(
            this.log,
            this.categoryList
        );
        this.categoryList.init(
            new ArrayList<Guest>(),
            new GuestIndexBinder(this.imageLoader, this.log, this.getActivity())
        );
        this.repository.findAllCategories(categoryUpdateSubscriber);

    }
}
