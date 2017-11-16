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
import org.ta4j.core.MathUtils;
import org.ta4j.core.indicators.helpers.AverageGainIndicator;
import org.ta4j.core.indicators.helpers.AverageLossIndicator;

/**
 * Relative strength index indicator.
 * <p>
 * This calculation of RSI uses traditional moving averages
 * as opposed to Wilder's accumulative moving average technique.
 * <p>
 * <p>See reference
 * <a href="https://www.barchart.com/education/technical-indicators#/studies/std_rsi_mod">
 * RSI calculation</a>.
 *
 * @see SmoothedRSIIndicator
 */
public class RSIIndicator extends CachedIndicator<Double> {

    private Indicator<Double> averageGainIndicator;
    private Indicator<Double> averageLossIndicator;

    public RSIIndicator(Indicator<Double> indicator, int timeFrame) {
        this(new AverageGainIndicator(indicator, timeFrame),
                new AverageLossIndicator(indicator, timeFrame));
    }

    public RSIIndicator(Indicator<Double> avgGainIndicator, Indicator<Double> avgLossIndicator) {
        super(avgGainIndicator);
        averageGainIndicator = avgGainIndicator;
        averageLossIndicator = avgLossIndicator;
    }

    @Override
    protected Double calculate(int index) {
        if (index == 0) {
            return 0d;
        }

        // Relative strength
        Double averageLoss = averageLossIndicator.getValue(index);
        if (MathUtils.isZero(averageLoss)) {
            return 100d;
        }
        Double averageGain = averageGainIndicator.getValue(index);
        Double relativeStrength = averageGain / (averageLoss);

        // Nominal case
        Double ratio = 100d / (1d + (relativeStrength));
        return 100d - (ratio);
    }

}
