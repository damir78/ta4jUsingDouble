/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Utility class for {@code Double} tests.
 */
public class TATestsUtils {

    /**
     * Offset for double equality checking
     */
    public static final double TA_OFFSET = 0.0001;

    /**
     * Verifies that the actual {@code Double} value is equal to the given {@code String} representation.
     *
     * @param actual   the actual {@code Double} value
     * @param expected the given {@code String} representation to compare the actual value to
     * @throws AssertionError if the actual value is not equal to the given {@code String} representation
     */
    public static void assertDoubleEquals(Double actual, String expected) {
        assertEquals(Double.valueOf(expected), actual, MathUtils.DELTA);
    }

    /**
     * Verifies that the actual {@code Double} value is equal to the given {@code Integer} representation.
     *
     * @param actual   the actual {@code Double} value
     * @param expected the given {@code Integer} representation to compare the actual value to
     * @throws AssertionError if the actual value is not equal to the given {@code Integer} representation
     */
    public static void assertDoubleEquals(Double actual, int expected) {
        assertEquals(expected, actual, MathUtils.DELTA);
    }

    /**
     * Verifies that the actual {@code Double} value is equal (within a positive offset) to the given {@code Double} representation.
     *
     * @param actual   the actual {@code Double} value
     * @param expected the given {@code Double} representation to compare the actual value to
     * @throws AssertionError if the actual value is not equal to the given {@code Double} representation
     */
    public static void assertDoubleEquals(Double actual, double expected) {
        assertEquals(expected, actual, TA_OFFSET);
    }

    /**
     * Verifies that the actual {@code Double} value is not equal to the given {@code Integer} representation.
     *
     * @param actual     the actual {@code Double} value
     * @param unexpected the given {@code Integer} representation to compare the actual value to
     * @throws AssertionError if the actual value is equal to the given {@code Integer} representation
     */
    public static void assertDoubleNotEquals(Double actual, int unexpected) {
        assertNotEquals(unexpected, actual, MathUtils.DELTA);
    }
}
