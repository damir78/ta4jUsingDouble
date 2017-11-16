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
package org.ta4j.core.indicators.candles;


import org.ta4j.core.Indicator;
import org.ta4j.core.MathUtils;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.AbsoluteIndicator;

/**
 * Doji indicator.
 * <p>
 * A candle/tick is considered Doji if its body height is lower than the average multiplied by a factor.
 *
 * @see http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks#doji
 */
public class DojiIndicator extends CachedIndicator<Boolean> {

    /**
     * Body height
     */
    private final Indicator<Double> bodyHeightInd;
    /**
     * Average body height
     */
    private final SMAIndicator averageBodyHeightInd;

    private final Double factor;

    /**
     * Constructor.
     *
     * @param series     a time series
     * @param timeFrame  the number of ticks used to calculate the average body height
     * @param bodyFactor the factor used when checking if a candle is Doji
     */
    public DojiIndicator(TimeSeries series, int timeFrame, Double bodyFactor) {
        super(series);
        bodyHeightInd = new AbsoluteIndicator(new RealBodyIndicator(series));
        averageBodyHeightInd = new SMAIndicator(bodyHeightInd, timeFrame);
        factor = bodyFactor;
    }

    @Override
    protected Boolean calculate(int index) {
        if (index < 1) {
            return MathUtils.isZero(bodyHeightInd.getValue(index));
        }

        Double averageBodyHeight = averageBodyHeightInd.getValue(index - 1);
        Double currentBodyHeight = bodyHeightInd.getValue(index);

        return currentBodyHeight < (averageBodyHeight * (factor));
    }
}
