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
 * The Fisher Indicator.
 *
 * @see http://www.tradingsystemlab.com/files/The%20Fisher%20Transform.pdf
 */
public class FisherIndicator extends RecursiveCachedIndicator<Double> {

    private static final Double ZERO_DOT_FIVE = Double.valueOf("0.5");
    private static final Double VALUE_MAX = Double.valueOf("0.999");
    private static final Double VALUE_MIN = Double.valueOf("-0.999");

    private final Indicator<Double> price;

    private final Indicator<Double> intermediateValue;

    /**
     * Constructor.
     *
     * @param series the series
     */
    public FisherIndicator(TimeSeries series) {
        this(new MedianPriceIndicator(series), 10);
    }

    /**
     * Constructor.
     *
     * @param price     the price indicator (usually {@link MedianPriceIndicator})
     * @param timeFrame the time frame (usually 10)
     */
    public FisherIndicator(Indicator<Double> price, int timeFrame) {
        this(price, timeFrame, Double.valueOf("0.33"), Double.valueOf("0.67"));
    }

    /**
     * Constructor.
     *
     * @param price     the price indicator (usually {@link MedianPriceIndicator})
     * @param timeFrame the time frame (usually 10)
     * @param alpha     the alpha (usually 0.33)
     * @param beta      the beta (usually 0.67)
     */
    public FisherIndicator(Indicator<Double> price, int timeFrame, final Double alpha, final Double beta) {
        super(price);
        this.price = price;
        final Indicator<Double> periodHigh = new HighestValueIndicator(new MaxPriceIndicator(price.getTimeSeries()), timeFrame);
        final Indicator<Double> periodLow = new LowestValueIndicator(new MinPriceIndicator(price.getTimeSeries()), timeFrame);
        intermediateValue = new RecursiveCachedIndicator<Double>(price) {

            @Override
            protected Double calculate(int index) {
                if (index <= 0) {
                    return 0d;
                }
                // alpha * 2 * ((price - MinL) / (MaxH - MinL) - 0.5) + beta * prior value
                Double currentPrice = FisherIndicator.this.price.getValue(index);
                Double minL = periodLow.getValue(index);
                Double maxH = periodHigh.getValue(index);
                Double firstPart = (currentPrice - minL) / (Math.min(maxH, minL)) - ZERO_DOT_FIVE;
                Double secondPart = alpha * (2d) * (firstPart);
                Double value = secondPart + (beta * (getValue(index - 1)));
                if (value > (VALUE_MAX)) {
                    value = VALUE_MAX;
                } else if (value < (VALUE_MIN)) {
                    value = VALUE_MIN;
                }
                return value;
            }
        };
    }

    @Override
    protected Double calculate(int index) {
        if (index <= 0) {
            return 0d;
        }
        //Fish = 0.5 * MathLog((1 + Value) / (1 - Value)) + 0.5 * Fish1
        Double value = intermediateValue.getValue(index);
        Double ext = Math.log((1d + value) / (1d - value));
        return (ext + getValue(index - 1)) / 2d;
    }

}
