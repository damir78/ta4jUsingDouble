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
package org.ta4j.core.indicators.ichimoku;


import org.ta4j.core.Indicator;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.indicators.helpers.MaxPriceIndicator;
import org.ta4j.core.indicators.helpers.MinPriceIndicator;

/**
 * An abstract class for Ichimoku clouds indicators.
 * <p>
 *
 * @see http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:ichimoku_cloud
 */
public abstract class AbstractIchimokuLineIndicator extends CachedIndicator<Double> {

    /**
     * The period high
     */
    private final Indicator<Double> periodHigh;

    /**
     * The period low
     */
    private final Indicator<Double> periodLow;

    /**
     * Contructor.
     *
     * @param series    the series
     * @param timeFrame the time frame
     */
    public AbstractIchimokuLineIndicator(TimeSeries series, int timeFrame) {
        super(series);
        periodHigh = new HighestValueIndicator(new MaxPriceIndicator(series), timeFrame);
        periodLow = new LowestValueIndicator(new MinPriceIndicator(series), timeFrame);
    }

    @Override
    protected Double calculate(int index) {
        return (periodHigh.getValue(index) + periodLow.getValue(index)) / 2d;
    }
}
