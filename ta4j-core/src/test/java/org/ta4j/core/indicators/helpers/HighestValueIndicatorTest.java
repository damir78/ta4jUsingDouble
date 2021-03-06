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

public class HighestValueIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {
        data = new MockTimeSeries(1, 2, 3, 4, 3, 4, 5, 6, 4, 3, 3, 4, 3, 2);
    }

    @Test
    public void highestValueUsingTimeFrame5UsingClosePrice() {
        HighestValueIndicator highestValue = new HighestValueIndicator(new ClosePriceIndicator(data), 5);

        assertDoubleEquals(highestValue.getValue(4), "4");
        assertDoubleEquals(highestValue.getValue(5), "4");
        assertDoubleEquals(highestValue.getValue(6), "5");
        assertDoubleEquals(highestValue.getValue(7), "6");
        assertDoubleEquals(highestValue.getValue(8), "6");
        assertDoubleEquals(highestValue.getValue(9), "6");
        assertDoubleEquals(highestValue.getValue(10), "6");
        assertDoubleEquals(highestValue.getValue(11), "6");
        assertDoubleEquals(highestValue.getValue(12), "4");
    }

    @Test
    public void firstHighestValueIndicatorValueShouldBeEqualsToFirstDataValue() {
        HighestValueIndicator highestValue = new HighestValueIndicator(new ClosePriceIndicator(data), 5);
        assertDoubleEquals(highestValue.getValue(0), "1");
    }

    @Test
    public void highestValueIndicatorWhenTimeFrameIsGreaterThanIndex() {
        HighestValueIndicator highestValue = new HighestValueIndicator(new ClosePriceIndicator(data), 500);
        assertDoubleEquals(highestValue.getValue(12), "6");
    }

  /*  @Test
    public void onlyNaNValues(){
        List<Tick> ticks = new ArrayList<>();
        for (long i = 0; i<= 10000; i++){
            Tick tick = new BaseTick(ZonedDateTime.now().plusDays(i), Double.NaN, Double.NaN,Double.NaN, Double.NaN, Double.NaN);
            ticks.add(tick);
        }

        BaseTimeSeries series = new BaseTimeSeries("NaN test",ticks);
        HighestValueIndicator highestValue = new HighestValueIndicator(new ClosePriceIndicator(series), 5);
        for (int i = series.getBeginIndex(); i<= series.getEndIndex(); i++){
            assertEquals(Double.NaN.toString(),highestValue.getValue(i).toString());
        }
    }

    @Test
    public void naNValuesInIntervall(){
        List<Tick> ticks = new ArrayList<>();
        for (long i = 0; i<= 10; i++){ // (0, NaN, 2, NaN, 3, NaN, 4, NaN, 5, ...)
            Double closePrice = i % 2 == 0 ? Double.valueOf(i): Double.NaN;
            Tick tick = new BaseTick(ZonedDateTime.now().plusDays(i),Double.NaN, Double.NaN,Double.NaN, closePrice, Double.NaN);
            ticks.add(tick);
        }

        BaseTimeSeries series = new BaseTimeSeries("NaN test",ticks);
        HighestValueIndicator highestValue = new HighestValueIndicator(new ClosePriceIndicator(series), 2);

        // index is the biggest of (index, index-1)
        for (int i = series.getBeginIndex(); i<= series.getEndIndex(); i++){
            if (i % 2 != 0) // current is NaN take the previous as highest
                assertEquals(series.getTick(i-1).getClosePrice().toString(),highestValue.getValue(i).toString());
            else // current is not NaN but previous, take the current
                assertEquals(series.getTick(i).getClosePrice().toString(),highestValue.getValue(i).toString());
        }
    }*/
}
