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
import org.ta4j.core.Tick;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.mocks.MockTick;
import org.ta4j.core.mocks.MockTimeSeries;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class MedianPriceIndicatorTest {
    TimeSeries timeSeries;
    private MedianPriceIndicator average;

    @Before
    public void setUp() {
        List<Tick> ticks = new ArrayList<Tick>();

        ticks.add(new MockTick(0, 0, 16, 8));
        ticks.add(new MockTick(0, 0, 12, 6));
        ticks.add(new MockTick(0, 0, 18, 14));
        ticks.add(new MockTick(0, 0, 10, 6));
        ticks.add(new MockTick(0, 0, 32, 6));
        ticks.add(new MockTick(0, 0, 2, 2));
        ticks.add(new MockTick(0, 0, 0, 0));
        ticks.add(new MockTick(0, 0, 8, 1));
        ticks.add(new MockTick(0, 0, 83, 32));
        ticks.add(new MockTick(0, 0, 9, 3));


        this.timeSeries = new MockTimeSeries(ticks);
        average = new MedianPriceIndicator(timeSeries);
    }

    @Test
    public void indicatorShouldRetrieveTickClosePrice() {
        Double result;
        for (int i = 0; i < 10; i++) {
            result = (timeSeries.getTick(i).getMaxPrice() + timeSeries.getTick(i).getMinPrice()) / 2d;
            assertEquals(average.getValue(i), result);
        }
    }
}
