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
package org.ta4j.core.indicators;

import org.junit.Test;

import org.ta4j.core.Indicator;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockTimeSeries;

import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class WMAIndicatorTest {

    @Test
    public void calculate() {
        MockTimeSeries series = new MockTimeSeries(1d, 2d, 3d, 4d, 5d, 6d);
        Indicator<Double> close = new ClosePriceIndicator(series);
        Indicator<Double> wmaIndicator = new WMAIndicator(close, 3);

        assertDoubleEquals(wmaIndicator.getValue(0), 1);
        assertDoubleEquals(wmaIndicator.getValue(1), 1.6667);
        assertDoubleEquals(wmaIndicator.getValue(2), 2.3333);
        assertDoubleEquals(wmaIndicator.getValue(3), 3.3333);
        assertDoubleEquals(wmaIndicator.getValue(4), 4.3333);
        assertDoubleEquals(wmaIndicator.getValue(5), 5.3333);
    }

    @Test
    public void wmaWithTimeFrameGreaterThanSeriesSize() {
        MockTimeSeries series = new MockTimeSeries(1d, 2d, 3d, 4d, 5d, 6d);
        Indicator<Double> close = new ClosePriceIndicator(series);
        Indicator<Double> wmaIndicator = new WMAIndicator(close, 55);

        assertDoubleEquals(wmaIndicator.getValue(0), 1);
        assertDoubleEquals(wmaIndicator.getValue(1), 1.6667);
        assertDoubleEquals(wmaIndicator.getValue(2), 2.3333);
        assertDoubleEquals(wmaIndicator.getValue(3), 3);
        assertDoubleEquals(wmaIndicator.getValue(4), 3.6666);
        assertDoubleEquals(wmaIndicator.getValue(5), 4.3333);
    }

    @Test
    public void wmaUsingTimeFrame9UsingClosePrice() {
        // Example from http://traders.com/Documentation/FEEDbk_docs/2010/12/TradingIndexesWithHullMA.xls
        TimeSeries data = new MockTimeSeries(
                84.53, 87.39, 84.55,
                82.83, 82.58, 83.74,
                83.33, 84.57, 86.98,
                87.10, 83.11, 83.60,
                83.66, 82.76, 79.22,
                79.03, 78.18, 77.42,
                74.65, 77.48, 76.87
        );

        WMAIndicator wma = new WMAIndicator(new ClosePriceIndicator(data), 9);
        assertDoubleEquals(wma.getValue(8), 84.4958);
        assertDoubleEquals(wma.getValue(9), 85.0158);
        assertDoubleEquals(wma.getValue(10), 84.6807);
        assertDoubleEquals(wma.getValue(11), 84.5387);
        assertDoubleEquals(wma.getValue(12), 84.4298);
        assertDoubleEquals(wma.getValue(13), 84.1224);
        assertDoubleEquals(wma.getValue(14), 83.1031);
        assertDoubleEquals(wma.getValue(15), 82.1462);
        assertDoubleEquals(wma.getValue(16), 81.1149);
        assertDoubleEquals(wma.getValue(17), 80.0736);
        assertDoubleEquals(wma.getValue(18), 78.6907);
        assertDoubleEquals(wma.getValue(19), 78.1504);
        assertDoubleEquals(wma.getValue(20), 77.6133);
    }
}
