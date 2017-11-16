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
package org.ta4j.core.indicators.statistics;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Tick;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.mocks.MockTick;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class CorrelationCoefficientIndicatorTest {
    private TimeSeries data;

    private Indicator<Double> close, volume;

    @Before
    public void setUp() {
        List<Tick> ticks = new ArrayList<Tick>();
        // close, volume
        ticks.add(new MockTick(6, 100));
        ticks.add(new MockTick(7, 105));
        ticks.add(new MockTick(9, 130));
        ticks.add(new MockTick(12, 160));
        ticks.add(new MockTick(11, 150));
        ticks.add(new MockTick(10, 130));
        ticks.add(new MockTick(11, 95));
        ticks.add(new MockTick(13, 120));
        ticks.add(new MockTick(15, 180));
        ticks.add(new MockTick(12, 160));
        ticks.add(new MockTick(8, 150));
        ticks.add(new MockTick(4, 200));
        ticks.add(new MockTick(3, 150));
        ticks.add(new MockTick(4, 85));
        ticks.add(new MockTick(3, 70));
        ticks.add(new MockTick(5, 90));
        ticks.add(new MockTick(8, 100));
        ticks.add(new MockTick(9, 95));
        ticks.add(new MockTick(11, 110));
        ticks.add(new MockTick(10, 95));

        data = new BaseTimeSeries(ticks);
        close = new ClosePriceIndicator(data);
        volume = new VolumeIndicator(data, 2);
    }

    @Test
    public void usingTimeFrame5UsingClosePriceAndVolume() {
        CorrelationCoefficientIndicator coef = new CorrelationCoefficientIndicator(close, volume, 5);

        assertTrue(coef.getValue(0).isNaN());

        assertDoubleEquals(coef.getValue(1), 1);
        assertDoubleEquals(coef.getValue(2), 0.8773);
        assertDoubleEquals(coef.getValue(3), 0.9073);
        assertDoubleEquals(coef.getValue(4), 0.9219);
        assertDoubleEquals(coef.getValue(5), 0.9205);
        assertDoubleEquals(coef.getValue(6), 0.4565);
        assertDoubleEquals(coef.getValue(7), -0.4622);
        assertDoubleEquals(coef.getValue(8), 0.05747);
        assertDoubleEquals(coef.getValue(9), 0.1442);
        assertDoubleEquals(coef.getValue(10), -0.1263);
        assertDoubleEquals(coef.getValue(11), -0.5345);
        assertDoubleEquals(coef.getValue(12), -0.7275);
        assertDoubleEquals(coef.getValue(13), 0.1676);
        assertDoubleEquals(coef.getValue(14), 0.2506);
        assertDoubleEquals(coef.getValue(15), -0.2938);
        assertDoubleEquals(coef.getValue(16), -0.3586);
        assertDoubleEquals(coef.getValue(17), 0.1713);
        assertDoubleEquals(coef.getValue(18), 0.9841);
        assertDoubleEquals(coef.getValue(19), 0.9799);
    }
}
