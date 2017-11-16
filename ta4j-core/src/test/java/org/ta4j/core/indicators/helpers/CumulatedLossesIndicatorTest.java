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
import org.ta4j.core.TimeSeries;
import org.ta4j.core.mocks.MockTimeSeries;

import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class CumulatedLossesIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {
        data = new MockTimeSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2);
    }

    @Test
    public void averageGainUsingTimeFrame5UsingClosePrice() {
        CumulatedLossesIndicator losses = new CumulatedLossesIndicator(new ClosePriceIndicator(data), 5);

        assertDoubleEquals(losses.getValue(0), 0);
        assertDoubleEquals(losses.getValue(1), 0);
        assertDoubleEquals(losses.getValue(2), 0);
        assertDoubleEquals(losses.getValue(3), 0);
        assertDoubleEquals(losses.getValue(4), 1);
        assertDoubleEquals(losses.getValue(5), 1);
        assertDoubleEquals(losses.getValue(6), 1);
        assertDoubleEquals(losses.getValue(7), 2);
        assertDoubleEquals(losses.getValue(8), 3);
        assertDoubleEquals(losses.getValue(9), 2);
        assertDoubleEquals(losses.getValue(10), 2);
        assertDoubleEquals(losses.getValue(11), 3);
        assertDoubleEquals(losses.getValue(12), 3);
    }
}
