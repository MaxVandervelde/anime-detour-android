/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

import com.animedetour.android.model.Event;
import com.j256.ormlite.dao.Dao;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Creates or updates every event in a list of events.
 *
 * This is used as a batch operation in ORMLite which requires a callable
 * to be run while it has auto-commit disabled.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
public class BatchEventSave implements Callable<Void> {

    final private Dao<Event, String> localAccess;
    final private List<Event> saveList;

    public BatchEventSave(Dao<Event, String> localAccess, List<Event> saveList)
    {
        this.localAccess = localAccess;
        this.saveList = saveList;
    }

    @Override
    public Void call() throws Exception
    {
        for (Event event : this.saveList) {
            this.localAccess.createOrUpdate(event);
        }

        return null;
    }
}
