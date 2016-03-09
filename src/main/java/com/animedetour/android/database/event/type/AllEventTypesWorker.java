/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event.type;

import com.animedetour.android.model.Event;
import com.inkapplications.groundcontrol.SingleYieldWorker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Looks up a collection of the distinct event types.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class AllEventTypesWorker extends SingleYieldWorker<List<String>>
{
    final private Dao<Event, String> localAccess;

    public AllEventTypesWorker(Dao<Event, String> localAccess)
    {
        super();

        this.localAccess = localAccess;
    }

    @Override
    public List<String> lookupLocal() throws SQLException
    {
        QueryBuilder<Event, String> query = this.localAccess.queryBuilder();
        query.selectColumns("category");
        query.distinct();

        List<Event> events = query.query();
        List<String> result = new ArrayList<>();

        for (Event event : events) {
            result.add(event.getCategory());
        }

        return result;
    }
}
