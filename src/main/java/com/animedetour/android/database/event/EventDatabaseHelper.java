/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.database.event;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.animedetour.sched.api.model.Event;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * ORMLite Database Helper for Events
 *
 * This is used by ORMlite for creating the initial database connections.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventDatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "detour_events.db";
    private static final int DATABASE_VERSION = 1;

    public EventDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        try {
            TableUtils.createTable(connectionSource, Event.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try {
            TableUtils.dropTable(connectionSource, Event.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
