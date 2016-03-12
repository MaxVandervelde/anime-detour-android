/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.support.annotation.Nullable;
import android.view.View;

import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import monolog.Monolog;
import rx.Observer;

/**
 * Listens for updates to the Guest List.
 *
 * When an update is received, this will update the adapter on the recyclerview
 * with the new contents.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 * @author Kenton Watson (kenton@imsofucking.moe)
 */
class CategoryUpdateObserver implements Observer<List<Category>>
{
    final private Monolog log;
    final private SimpleRecyclerView<GuestWidgetView, Guest> categoryList;
    final private View emptyView;

    /**
     * @param log Used when an error occurs fetching the guest list.
     * @param categoryList View which will be updated on guest-list updates.
     * @param emptyView Optional view to show if there are no guests.
     */
    public CategoryUpdateObserver(
            Monolog log,
            SimpleRecyclerView<GuestWidgetView, Guest> categoryList,
            @Nullable View emptyView
    ) {
        this.log = log;
        this.categoryList = categoryList;
        this.emptyView = emptyView;
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
        setEmptyViewIfNecessary();
    }

    /**
     * Hide the list, and shows the empty view
     *
     * Will only hide list if
     *  - There are no list items
     *  - There is an empty view.
     *
     *  If there are items, then it will show the list, and hide any empty view.
     */
    private void setEmptyViewIfNecessary()
    {
        if (isEmpty() && this.emptyView != null) {
            this.categoryList.setVisibility(View.GONE);
            this.emptyView.setVisibility(View.VISIBLE);
        } else if (!isEmpty()) {
            this.categoryList.setVisibility(View.VISIBLE);

            if (this.emptyView != null) {
                this.emptyView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Wrapper method for determining if the guest list is empty.
     *
     * @return True if empty.
     */
    private boolean isEmpty() {
        return this.categoryList.getItemAdapter().getItemCount() == 0;
    }
}
