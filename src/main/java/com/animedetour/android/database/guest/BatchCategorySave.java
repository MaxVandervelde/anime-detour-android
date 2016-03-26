/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.guest;

import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Creates categories and guests in a list of categories.
 *
 * This is used as a batch operation in ORMLite which requires a callable
 * to be run while it has auto-commit disabled.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
public class BatchCategorySave implements Callable<Void>
{
    final private Dao<Category, String> localCategoryAccess;
    final private Dao<Guest, String> localGuestAccess;
    final private List<Category> categories;

    public BatchCategorySave(
        Dao<Category, String> localCategoryAccess,
        Dao<Guest, String> localGuestAccess,
        List<Category> categories
    ) {
        this.localCategoryAccess = localCategoryAccess;
        this.localGuestAccess = localGuestAccess;
        this.categories = categories;
    }

    @Override
    public Void call() throws Exception
    {
        for (Category category : categories) {
            this.saveLocal(category);
        }

        return null;
    }

    private void saveLocal(Category category) throws SQLException
    {
        this.localCategoryAccess.createOrUpdate(category);

        for (Guest guest : category.getGuests()) {
            this.localGuestAccess.createOrUpdate(guest);
        }
    }
}
