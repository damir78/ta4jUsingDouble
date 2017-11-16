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
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.mocks.MockTimeSeries;

import static junit.framework.TestCase.assertEquals;
import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class RSIIndicatorTest {

    private TimeSeries data;

    private FixedDecimalIndicator gains;
    private FixedDecimalIndicator losses;

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


        gains = new FixedDecimalIndicator(1, 1, 0.8, 0.84, 0.672, 0.5376, 0.43008);
        losses = new FixedDecimalIndicator(2, 0, 0.2, 0.16, 0.328, 0.4624, 0.36992);
    }

    @Test
    public void rsiUsingTimeFrame14UsingClosePrice() {
        RSIIndicator rsi = new RSIIndicator(new ClosePriceIndicator(data), 14);

        assertDoubleEquals(rsi.getValue(15), 62.7451);
        assertDoubleEquals(rsi.getValue(16), 66.6667);
        assertDoubleEquals(rsi.getValue(17), 75.2294);
        assertDoubleEquals(rsi.getValue(18), 71.9298);
        assertDoubleEquals(rsi.getValue(19), 73.3333);
        assertDoubleEquals(rsi.getValue(20), 77.7778);
        assertDoubleEquals(rsi.getValue(21), 74.6667);
        assertDoubleEquals(rsi.getValue(22), 77.8523);
        assertDoubleEquals(rsi.getValue(23), 81.5642);
        assertDoubleEquals(rsi.getValue(24), 85.2459);
    }

    @Test
    public void rsiCalculationFromMockedGainsAndLosses() {
        RSIIndicator rsiCalc = new RSIIndicator(gains, losses);

        assertDoubleEquals(rsiCalc.getValue(2), 80.0);
        assertDoubleEquals(rsiCalc.getValue(3), 84.0);
        assertDoubleEquals(rsiCalc.getValue(4), 67.2);
        assertDoubleEquals(rsiCalc.getValue(5), 53.76);
        assertDoubleEquals(rsiCalc.getValue(6), 53.76);
    }

    @Test
    public void rsiFirstValueShouldBeZero() {
        RSIIndicator rsi = new RSIIndicator(new ClosePriceIndicator(data), 14);
        assertEquals(0d, rsi.getValue(0));
    }

    @Test
    public void rsiCalcFirstValueShouldBeZero() {
        RSIIndicator rsiCalc = new RSIIndicator(gains, losses);
        assertEquals(0d, rsiCalc.getValue(0));
    }

    @Test
    public void rsiHundredIfNoLoss() {
        RSIIndicator rsi = new RSIIndicator(new ClosePriceIndicator(data), 3);
        assertEquals(100d, rsi.getValue(14));
        assertEquals(100d, rsi.getValue(15));
    }

    @Test
    public void rsiCalcHundredIfNoLoss() {
        RSIIndicator rsiCalc = new RSIIndicator(gains, losses);
        assertEquals(100d, rsiCalc.getValue(1));
    }
}
