/*
 * Copyright (c) 2016 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Math functions for merging data into pairs.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
final public class PairMerge
{
    /**
     * Combines a list of data with a single element to create a list of pairs.
     *
     * For example,
     * Merging `["foo", "bar"]` with `"baz"` should yield pairs of:
     * `[["foo", "baz"],["bar", "baz"]]`
     *
     * @param list The list of data to merge into
     *             (will be the first/left data in the pair)
     * @param merger The single data item to merge into each item in the left
     *               list. (will be the right/second data in the pair)
     * @param <A> First Type in the resulting Pair.
     * @param <B> Second Type in the resulting pair.
     * @return A pair mer
     */
    public static <A, B> List<Pair<A, B>> mergeRight(List<A> list, B merger)
    {
        ArrayList<Pair<A, B>> newList = new ArrayList<>(list.size());
        for (A item : list) {
            newList.add(new Pair<>(item, merger));
        }

        return newList;
    }
}
