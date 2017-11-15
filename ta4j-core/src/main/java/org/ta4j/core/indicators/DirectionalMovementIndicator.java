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
import org.ta4j.core.indicators.helpers.DirectionalDownIndicator;
import org.ta4j.core.indicators.helpers.DirectionalUpIndicator;

/**
 * Directional movement indicator.
 * <p>
 */
public class DirectionalMovementIndicator extends CachedIndicator<Double> {

    private final int timeFrame;
    private final DirectionalUpIndicator dup;
    private final DirectionalDownIndicator ddown;

    public DirectionalMovementIndicator(TimeSeries series, int timeFrame) {
        super(series);
        this.timeFrame = timeFrame;
        dup = new DirectionalUpIndicator(series, timeFrame);
        ddown = new DirectionalDownIndicator(series, timeFrame);
    }

    @Override
    protected Double calculate(int index) {
        Double dupValue = dup.getValue(index);
        Double ddownValue = ddown.getValue(index);
        Double difference = dupValue - (ddownValue);
        return Math.abs(difference) / (dupValue + (ddownValue)) * (100d);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
