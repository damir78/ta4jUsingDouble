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


import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;

/**
 * Ulcer index indicator.
 * <p>
 *
 * @see http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:ulcer_index
 * @see https://en.wikipedia.org/wiki/Ulcer_index
 */
public class UlcerIndexIndicator extends CachedIndicator<Double> {

    private Indicator<Double> indicator;

    private HighestValueIndicator highestValueInd;

    private int timeFrame;

    /**
     * Constructor.
     *
     * @param indicator the indicator
     * @param timeFrame the time frame
     */
    public UlcerIndexIndicator(Indicator<Double> indicator, int timeFrame) {
        super(indicator);
        this.indicator = indicator;
        this.timeFrame = timeFrame;
        highestValueInd = new HighestValueIndicator(indicator, timeFrame);
    }

    @Override
    protected Double calculate(int index) {
        final int startIndex = Math.max(0, index - timeFrame + 1);
        final int numberOfObservations = index - startIndex + 1;
        Double squaredAverage = 0d;
        for (int i = startIndex; i <= index; i++) {
            Double currentValue = indicator.getValue(i);
            Double highestValue = highestValueInd.getValue(i);
            Double percentageDrawdown = (currentValue - highestValue) / (highestValue) * (100d);
            squaredAverage = squaredAverage + Math.pow(percentageDrawdown, 2);
        }
        squaredAverage = squaredAverage / numberOfObservations;
        return Math.sqrt(squaredAverage);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
