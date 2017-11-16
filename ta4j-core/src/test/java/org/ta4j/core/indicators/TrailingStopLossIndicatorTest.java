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
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockTimeSeries;

import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class TrailingStopLossIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {

        data = new MockTimeSeries(
                18, 19, 23, 22, 21,
                22, 17, 18, 21, 25,
                26, 29, 29, 28, 29,
                26, 25, 26, 26, 28
        );
    }

    @Test
    public void withoutInitialLimitUsingClosePrice() {
        ClosePriceIndicator price = new ClosePriceIndicator(data);
        TrailingStopLossIndicator tsl = new TrailingStopLossIndicator(price, Double.valueOf(4));

        assertDoubleEquals(tsl.getValue(1), 15);
        assertDoubleEquals(tsl.getValue(2), 19);
        assertDoubleEquals(tsl.getValue(3), 19);

        assertDoubleEquals(tsl.getValue(8), 19);
        assertDoubleEquals(tsl.getValue(9), 21);
        assertDoubleEquals(tsl.getValue(10), 22);
        assertDoubleEquals(tsl.getValue(11), 25);
        assertDoubleEquals(tsl.getValue(12), 25);
    }

    @Test
    public void withInitialLimitUsingClosePrice() {
        ClosePriceIndicator price = new ClosePriceIndicator(data);
        TrailingStopLossIndicator tsl = new TrailingStopLossIndicator(price,
                Double.valueOf(3), Double.valueOf(21));

        assertDoubleEquals(tsl.getValue(0), 21);
        assertDoubleEquals(tsl.getValue(1), 21);
        assertDoubleEquals(tsl.getValue(2), 21);

        assertDoubleEquals(tsl.getValue(8), 21);
        assertDoubleEquals(tsl.getValue(9), 22);
        assertDoubleEquals(tsl.getValue(10), 23);
        assertDoubleEquals(tsl.getValue(11), 26);
        assertDoubleEquals(tsl.getValue(12), 26);
        assertDoubleEquals(tsl.getValue(13), 26);
    }

}
