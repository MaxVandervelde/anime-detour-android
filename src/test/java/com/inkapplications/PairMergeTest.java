/*
 * Copyright (c) 2016 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications;

import org.javatuples.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PairMergeTest
{
    /**
     * Ensure that given a list of objects, they are merged correctly with a
     * single instance.
     */
    @Test
    public void testMergeRight()
    {
        List<String> left = Arrays.asList("foo", "bar");
        String right = "baz";

        List<Pair<String, String>> result = PairMerge.mergeRight(left, right);

        assertEquals("foo", result.get(0).getValue0());
        assertEquals("bar", result.get(1).getValue0());
        assertEquals("baz", result.get(0).getValue1());
        assertEquals("baz", result.get(1).getValue1());
    }
}
