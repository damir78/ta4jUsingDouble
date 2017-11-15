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
import org.ta4j.core.indicators.RecursiveCachedIndicator;

/**
 * Average gain indicator calculated using smoothing
 * <p>
 */
public class SmoothedAverageGainIndicator extends RecursiveCachedIndicator<Double> {

    private final AverageGainIndicator averageGains;
    private final Indicator<Double> indicator;

    private final int timeFrame;

    public SmoothedAverageGainIndicator(Indicator<Double> indicator, int timeFrame) {
        super(indicator);
        this.indicator = indicator;
        this.averageGains = new AverageGainIndicator(indicator, timeFrame);
        this.timeFrame = timeFrame;
    }

    @Override
    protected Double calculate(int index) {
        if (index > timeFrame) {
            return getValue(index - 1)
                    * ((timeFrame - 1))
                    + (calculateGain(index))
                    / (timeFrame);
        }
        return averageGains.getValue(index);
    }

    private Double calculateGain(int index) {
        Double gain = indicator.getValue(index) - (indicator.getValue(index - 1));
        return gain > 0d ? gain : 0d;
    }
}
