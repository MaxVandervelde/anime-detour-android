/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;
import org.apache.commons.logging.Log;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens for updates to the Guest List.
 *
 * When an update is received, this will update the adapter on the recyclerview
 * with the new contents.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
class CategoryUpdateObserver implements Observer<List<Category>>
{
    final private Log log;
    final private SimpleRecyclerView<GuestWidgetView, Guest> categoryList;

    /**
     * @param log Used when an error occurs fetching the guest list.
     * @param categoryList View which will be updated on guest-list updates.
     */
    public CategoryUpdateObserver(Log log, SimpleRecyclerView<GuestWidgetView, Guest> categoryList)
    {
        this.log = log;
        this.categoryList = categoryList;
    }

    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {
        log.error("Error looking up categories", e);
    }

    @Override public void onNext(List<Category> categories) {
        List<Guest> guests = new ArrayList<>();
        for (Category category : categories) {
            guests.addAll(category.getGuests());
        }
        this.categoryList.getItemAdapter().setItems(guests);
    }
}
