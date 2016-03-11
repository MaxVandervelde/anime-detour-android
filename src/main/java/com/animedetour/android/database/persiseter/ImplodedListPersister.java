/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.persiseter;

import com.google.common.base.Joiner;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Persists a List of Strings to a comma separated string in the database.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
public class ImplodedListPersister extends StringType
{
    private static final ImplodedListPersister singleton = new ImplodedListPersister();

    public static ImplodedListPersister getSingleton()
    {
        return singleton;
    }

    public ImplodedListPersister()
    {
        this(SqlType.STRING, new Class[] { List.class });
    }

    protected ImplodedListPersister(SqlType sqlType, Class<?>[] classes)
    {
        super(sqlType, classes);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException
    {
        return Arrays.asList(sqlArg.toString().split(","));
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException
    {
        if (false == javaObject instanceof List) {
            throw new RuntimeException("Persistence requires that this field be a list");
        }

        List items = (List) javaObject;
        return Joiner.on(",").join(items);
    }
}
