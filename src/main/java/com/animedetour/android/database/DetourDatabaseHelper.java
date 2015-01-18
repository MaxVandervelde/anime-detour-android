/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.animedetour.api.sched.api.model.Event;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * ORMLite Database Helper for Events, Guests and any other convention specific
 * data to be stored.
 *
 * This is used by ORMlite for creating the initial database connections.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class DetourDatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "detour.db";
    private static final int DATABASE_VERSION = 3;

    public DetourDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        try {
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Guest.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try {
            TableUtils.dropTable(connectionSource, Event.class, true);
            TableUtils.dropTable(connectionSource, Guest.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
