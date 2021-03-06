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
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.*;


/**
 * Stochastic oscillator K.
 * <p>
 * Receives timeSeries and timeFrame and calculates the StochasticOscillatorKIndicator
 * over ClosePriceIndicator, or receives an indicator, MaxPriceIndicator and
 * MinPriceIndicator and returns StochasticOsiclatorK over this indicator.
 */
public class StochasticOscillatorKIndicator extends CachedIndicator<Double> {
    private final Indicator<Double> indicator;

    private final int timeFrame;

    private MaxPriceIndicator maxPriceIndicator;

    private MinPriceIndicator minPriceIndicator;

    public StochasticOscillatorKIndicator(TimeSeries timeSeries, int timeFrame) {
        this(new ClosePriceIndicator(timeSeries), timeFrame, new MaxPriceIndicator(timeSeries), new MinPriceIndicator(
                timeSeries));
    }

    public StochasticOscillatorKIndicator(Indicator<Double> indicator, int timeFrame,
                                          MaxPriceIndicator maxPriceIndicator, MinPriceIndicator minPriceIndicator) {
        super(indicator);
        this.indicator = indicator;
        this.timeFrame = timeFrame;
        this.maxPriceIndicator = maxPriceIndicator;
        this.minPriceIndicator = minPriceIndicator;
    }

    @Override
    protected Double calculate(int index) {
        HighestValueIndicator highestHigh = new HighestValueIndicator(maxPriceIndicator, timeFrame);
        LowestValueIndicator lowestMin = new LowestValueIndicator(minPriceIndicator, timeFrame);

        Double highestHighPrice = highestHigh.getValue(index);
        Double lowestLowPrice = lowestMin.getValue(index);

        return (indicator.getValue(index) - lowestLowPrice)
                / (highestHighPrice - lowestLowPrice)
                * 100d;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
