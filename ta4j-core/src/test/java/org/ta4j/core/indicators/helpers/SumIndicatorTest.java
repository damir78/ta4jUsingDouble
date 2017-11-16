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
package org.ta4j.core.indicators.helpers;

import org.junit.Before;
import org.junit.Test;

import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class SumIndicatorTest {

    private ConstantIndicator<Double> constantIndicator;

    private FixedIndicator<Double> mockIndicator;

    private FixedIndicator<Double> mockIndicator2;

    private SumIndicator sumIndicator;

    @Before
    public void setUp() {
        constantIndicator = new ConstantIndicator<Double>(Double.valueOf(6));
        mockIndicator = new FixedIndicator<Double>(
                Double.valueOf("-2.0"),
                Double.valueOf("0.00"),
                Double.valueOf("1.00"),
                Double.valueOf("2.53"),
                Double.valueOf("5.87"),
                Double.valueOf("6.00"),
                Double.valueOf("10.0")
        );
        mockIndicator2 = new FixedIndicator<Double>(
                0d,
                1d,
                2d,
                3d,
                10d,
                Double.valueOf("-42"),
                Double.valueOf("-1337")
        );
        sumIndicator = new SumIndicator(constantIndicator, mockIndicator, mockIndicator2);
    }

    @Test
    public void getValue() {
        assertDoubleEquals(sumIndicator.getValue(0), "4");
        assertDoubleEquals(sumIndicator.getValue(1), "7");
        assertDoubleEquals(sumIndicator.getValue(2), "9");
        assertDoubleEquals(sumIndicator.getValue(3), "11.53");
        assertDoubleEquals(sumIndicator.getValue(4), "21.87");
        assertDoubleEquals(sumIndicator.getValue(5), "-30");
        assertDoubleEquals(sumIndicator.getValue(6), "-1321");
    }
}
