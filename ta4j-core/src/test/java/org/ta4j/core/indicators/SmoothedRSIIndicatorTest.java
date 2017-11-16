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
package org.ta4j.core.indicators;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.MathUtils;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockTimeSeries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class SmoothedRSIIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {
        data = new MockTimeSeries(
                50.45, 50.30, 50.20,
                50.15, 50.05, 50.06,
                50.10, 50.08, 50.03,
                50.07, 50.01, 50.14,
                50.22, 50.43, 50.50,
                50.56, 50.52, 50.70,
                50.55, 50.62, 50.90,
                50.82, 50.86, 51.20, 51.30, 51.10);
    }

    @Test
    public void rsiUsingTimeFrame14UsingClosePrice() {
        SmoothedRSIIndicator rsi = new SmoothedRSIIndicator(new ClosePriceIndicator(data), 2);

        assertDoubleEquals(rsi.getValue(2), 0.0);
        assertDoubleEquals(rsi.getValue(3), 0.0);
        assertDoubleEquals(rsi.getValue(4), 0.0);
        assertDoubleEquals(rsi.getValue(5), 9.638554217);
        assertDoubleEquals(rsi.getValue(6), 48.97959184);
        assertDoubleEquals(rsi.getValue(7), 34.12322275);
        assertDoubleEquals(rsi.getValue(8), 13.55932203);
        assertDoubleEquals(rsi.getValue(9), 55.99232982);
        assertDoubleEquals(rsi.getValue(10), 22.64443583);
        assertDoubleEquals(rsi.getValue(11), 78.39740119);
        assertDoubleEquals(rsi.getValue(12), 88.55224651);
    }

    @Test
    public void smoothedRsiFirstValueShouldBeZero() {
        SmoothedRSIIndicator rsi = new SmoothedRSIIndicator(new ClosePriceIndicator(data), 14);

        assertEquals(0d, rsi.getValue(0), MathUtils.DELTA);
    }

    @Test
    public void smoothedRsiIsNeverHundredIfLossPresentInDataSeries() {
        SmoothedRSIIndicator rsi = new SmoothedRSIIndicator(new ClosePriceIndicator(data), 3);

        assertNotEquals(100d, rsi.getValue(14));
        assertNotEquals(100d, rsi.getValue(15));
    }
}
