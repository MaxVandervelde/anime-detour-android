/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.favorite;

import com.animedetour.android.schedule.favorite.Favorite;
import com.inkapplications.groundcontrol.SingleYieldWorker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up all events that the user has favorited locally.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class GetAllFavoritesWorker extends SingleYieldWorker<List<Favorite>>
{
    /** Local favorite storage. */
    final private Dao<Favorite, Integer> localAccess;

    /**
     * @param localAccess Local favorite storage.
     */
    public GetAllFavoritesWorker(Dao<Favorite, Integer> localAccess)
    {
        this.localAccess = localAccess;
    }

    /**
     * @return All of the events that the user has marked as a favorite.
     */
    public List<Favorite> lookupLocal() throws SQLException
    {
        QueryBuilder<Favorite, Integer> builder = this.localAccess.queryBuilder();

        PreparedQuery<Favorite> query = builder.prepare();
        List<Favorite> result = this.localAccess.query(query);

        return result;
    }
}
