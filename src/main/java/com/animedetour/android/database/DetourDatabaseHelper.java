/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.animedetour.android.model.Event;
import com.animedetour.android.schedule.favorite.Favorite;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * ORMLite Database Helper for Events, Guests and any other convention specific
 * data to be stored.
 *
 * This is used by ORMlite for creating the initial database connections.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
final class DetourDatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "detour.db";
    private static final int DATABASE_VERSION = 8;

    @Inject
    public DetourDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        try {
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Favorite.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Guest.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        if (oldVersion < 8) {
            this.reCreate();
        }
    }

    /**
     * Drop all information and re-create the tables.
     */
    private void reCreate()
    {
        try {
            TableUtils.dropTable(connectionSource, Favorite.class, true);
            TableUtils.dropTable(connectionSource, Event.class, true);
            TableUtils.dropTable(connectionSource, Guest.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Guest.class);
            TableUtils.createTable(connectionSource, Favorite.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
