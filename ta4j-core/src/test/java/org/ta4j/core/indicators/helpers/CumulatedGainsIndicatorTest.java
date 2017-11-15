/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
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

public class CumulatedGainsIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {
        data = new MockTimeSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2);
    }

    @Test
    public void averageGainUsingTimeFrame5UsingClosePrice() {
        CumulatedGainsIndicator gains = new CumulatedGainsIndicator(new ClosePriceIndicator(data), 5);

        assertDoubleEquals(gains.getValue(0), 0);
        assertDoubleEquals(gains.getValue(1), 1);
        assertDoubleEquals(gains.getValue(2), 2);
        assertDoubleEquals(gains.getValue(3), 3);
        assertDoubleEquals(gains.getValue(4), 3);
        assertDoubleEquals(gains.getValue(5), 4);
        assertDoubleEquals(gains.getValue(6), 4);
        assertDoubleEquals(gains.getValue(7), 3);
        assertDoubleEquals(gains.getValue(8), 2);
        assertDoubleEquals(gains.getValue(9), 2);
        assertDoubleEquals(gains.getValue(10), 2);
        assertDoubleEquals(gains.getValue(11), 1);
        assertDoubleEquals(gains.getValue(12), 1);
    }
}
