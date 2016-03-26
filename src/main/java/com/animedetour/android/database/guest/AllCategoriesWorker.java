/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015-2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.guest;

import com.animedetour.android.model.MetaData;
import com.animedetour.api.guest.GuestEndpoint;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.groundcontrol.SyncWorker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import monolog.Monolog;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up all guests and returns them grouped by category.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class AllCategoriesWorker extends SyncWorker<List<Category>>
{
    final private Dao<Category, String> localCategoryAccess;
    final private Dao<Guest, String> localGuestAccess;
    final private Dao<MetaData, Integer> metaData;
    final private GuestEndpoint remoteAccess;
    final private Monolog logger;

    public AllCategoriesWorker(
        Dao<Category, String> localCategoryAccess,
        Dao<Guest, String> localGuestAccess,
        Dao<MetaData, Integer> metaData,
        GuestEndpoint remoteAccess,
        Monolog logger
    ) {
        this.localCategoryAccess = localCategoryAccess;
        this.localGuestAccess = localGuestAccess;
        this.metaData = metaData;
        this.remoteAccess = remoteAccess;
        this.logger = logger;
    }

    @Override
    public List<Category> lookupRemote() throws Exception
    {
        return this.remoteAccess.getAll();
    }

    @Override
    public List<Category> lookupLocal() throws SQLException
    {
        QueryBuilder<Category, String> builder = this.localCategoryAccess.queryBuilder();

        PreparedQuery<Category> query = builder.prepare();
        List<Category> result = this.localCategoryAccess.query(query);

        return result;
    }

    @Override
    public boolean dataIsStale() throws SQLException
    {
        MetaData metaData = this.metaData.queryForId(MetaData.SINGLETON);

        if (null == metaData || null == metaData.getGuestsFetched()) {
            return true;
        }

        DateTime cutoff = new DateTime().minusHours(8);
        if (metaData.getGuestsFetched().isBefore(cutoff)) {
            return true;
        }

        return false;
    }

    @Override
    public void saveLocal(List<Category> categories) throws SQLException
    {
        try {
            BatchCategorySave batch = new BatchCategorySave(this.localCategoryAccess, this.localGuestAccess, categories);
            this.localCategoryAccess.callBatchTasks(batch);
        } catch (Exception e) {
            this.logger.error("Error saving Guest Categories", e);
        }

        MetaData metaData = this.metaData.queryForId(MetaData.SINGLETON);
        metaData = null == metaData ? new MetaData() : metaData;
        metaData = metaData.withGuestsFetched(new DateTime());
        this.metaData.createOrUpdate(metaData);
    }
}
