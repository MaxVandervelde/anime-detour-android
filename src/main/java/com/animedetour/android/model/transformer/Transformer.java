/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.model.transformer;

import java.util.List;

/**
 * A service that changes one data type into another.
 *
 * Implementations of this class should be bijective functions.
 *
 * @param <FROM> First data type that we're transforming "from".
 * @param <TO> Second Data type that we're transforming "to".
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
public interface Transformer<FROM, TO>
{
    /**
     * Transforms a value from the original representation to a transformed representation.
     *
     * @param data the original data representation
     * @return A transformed representation.
     */
    TO transform(FROM data);

    /**
     * Transforms a value from the transformed representation to its original representation.
     *
     * @param data the transformed representation.
     * @return The original representation.
     */
    FROM reverseTransform(TO data);

    /**
     * Transforms a list of data to its transformed representation.
     *
     * @param data A list of original representation objects.
     * @return A list of transformed objects.
     */
    List<TO> bulkTransform(List<FROM> data);

    /**
     * Transforms a list of data back to its original representation.
     *
     * @param data A list of transformed representation objects.
     * @return A list of the original representation objects.
     */
    List<FROM> bulkReverseTransform(List<TO> data);
}
