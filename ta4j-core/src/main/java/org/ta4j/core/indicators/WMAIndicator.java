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

/**
 * WMA indicator.
 * <p>
 */
public class WMAIndicator extends CachedIndicator<Double> {

    private int timeFrame;

    private Indicator<Double> indicator;

    public WMAIndicator(Indicator<Double> indicator, int timeFrame) {
        super(indicator);
        this.indicator = indicator;
        this.timeFrame = timeFrame;
    }

    @Override
    protected Double calculate(int index) {
        if (index == 0) {
            return indicator.getValue(0);
        }
        Double value = 0d;
        if (index - timeFrame < 0) {

            for (int i = index + 1; i > 0; i--) {
                value = value + (Double.valueOf(i) * (indicator.getValue(i - 1)));
            }
            return value / (Double.valueOf(((index + 1) * (index + 2)) / 2));
        }

        int actualIndex = index;
        for (int i = timeFrame; i > 0; i--) {
            value = value + (Double.valueOf(i) * (indicator.getValue(actualIndex)));
            actualIndex--;
        }
        return value / (Double.valueOf((timeFrame * (timeFrame + 1)) / 2));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
