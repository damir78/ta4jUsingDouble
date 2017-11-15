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
import org.ta4j.core.indicators.helpers.MeanDeviationIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;

/**
 * Commodity Channel Index (CCI) indicator.
 * <p>
 *
 * @see http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:commodity_channel_in
 */
public class CCIIndicator extends CachedIndicator<Double> {

    public static final Double FACTOR = Double.valueOf("0.015");

    private TypicalPriceIndicator typicalPriceInd;

    private SMAIndicator smaInd;

    private MeanDeviationIndicator meanDeviationInd;

    private int timeFrame;

    /**
     * Constructor.
     *
     * @param series    the time series
     * @param timeFrame the time frame
     */
    public CCIIndicator(TimeSeries series, int timeFrame) {
        super(series);
        typicalPriceInd = new TypicalPriceIndicator(series);
        smaInd = new SMAIndicator(typicalPriceInd, timeFrame);
        meanDeviationInd = new MeanDeviationIndicator(typicalPriceInd, timeFrame);
        this.timeFrame = timeFrame;
    }

    @Override
    protected Double calculate(int index) {

        System.out.println("=======================");
        System.out.println("index = " + index);

        final Double typicalPrice = typicalPriceInd.getValue(index);
        final Double typicalPriceAvg = smaInd.getValue(index);
        final Double meanDeviation = meanDeviationInd.getValue(index);
        System.out.println("typicalPrice = " + typicalPrice);
        System.out.println("typicalPriceAvg = " + typicalPriceAvg);
        System.out.println("meanDeviation = " + meanDeviation);

        if (0d == meanDeviation) {
            return 0d;
        }
        double minus = typicalPrice - (typicalPriceAvg);
        double divisor = meanDeviation * (FACTOR);

        System.out.println("minus = " + minus);
        System.out.println("divisor = " + divisor);

        return minus / divisor;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
