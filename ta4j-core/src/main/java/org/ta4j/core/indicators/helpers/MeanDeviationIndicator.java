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


import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;

/**
 * Mean deviation indicator.
 * <p>
 *
 * @see http://en.wikipedia.org/wiki/Mean_absolute_deviation#Average_absolute_deviation
 */
public class MeanDeviationIndicator extends CachedIndicator<Double> {

    private Indicator<Double> indicator;

    private int timeFrame;

    private SMAIndicator sma;

    /**
     * Constructor.
     *
     * @param indicator the indicator
     * @param timeFrame the time frame
     */
    public MeanDeviationIndicator(Indicator<Double> indicator, int timeFrame) {
        super(indicator);
        this.indicator = indicator;
        this.timeFrame = timeFrame;
        sma = new SMAIndicator(indicator, timeFrame);
    }

    @Override
    protected Double calculate(int index) {
        Double absoluteDeviations = 0d;

        final Double average = sma.getValue(index);
        final int startIndex = Math.max(0, index - timeFrame + 1);
        final int nbValues = index - startIndex + 1;

        for (int i = startIndex; i <= index; i++) {
            // For each period...
            absoluteDeviations = absoluteDeviations + (indicator.getValue(i) - Math.abs(average));
        }
        return absoluteDeviations / nbValues;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
