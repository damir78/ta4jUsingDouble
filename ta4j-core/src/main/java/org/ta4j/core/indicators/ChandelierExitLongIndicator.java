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


import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.AverageTrueRangeIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.MaxPriceIndicator;

/**
 * The Chandelier Exit (long) Indicator.
 * @see http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:chandelier_exit
 */
public class ChandelierExitLongIndicator extends CachedIndicator<Double> {

    private final HighestValueIndicator high;

    private final AverageTrueRangeIndicator atr;

    private final Double k;

    /**
     * Constructor.
     * @param series the time series
     */
    public ChandelierExitLongIndicator(TimeSeries series) {
        this(series, 22, 3d);
    }

    /**
     * Constructor.
     * @param series the time series
     * @param timeFrame the time frame (usually 22)
     * @param k the K multiplier for ATR (usually 3.0)
     */
    public ChandelierExitLongIndicator(TimeSeries series, int timeFrame, Double k) {
        super(series);
        high = new HighestValueIndicator(new MaxPriceIndicator(series), timeFrame);
        atr = new AverageTrueRangeIndicator(series, timeFrame);
        this.k = k;
    }

    @Override
    protected Double calculate(int index) {
        return high.getValue(index) - (atr.getValue(index) * (k));
    }
}
