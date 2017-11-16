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
package org.ta4j.core.indicators.bollinger;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.mocks.MockTimeSeries;

import static org.ta4j.core.TATestsUtils.assertDoubleEquals;

public class BollingerBandsLowerIndicatorTest {

    private TimeSeries data;

    private int timeFrame;

    private ClosePriceIndicator closePrice;

    private SMAIndicator sma;

    @Before
    public void setUp() {
        data = new MockTimeSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2);
        timeFrame = 3;
        closePrice = new ClosePriceIndicator(data);
        sma = new SMAIndicator(closePrice, timeFrame);
    }

    @Test
    public void bollingerBandsLowerUsingSMAAndStandardDeviation() {

        BollingerBandsMiddleIndicator bbmSMA = new BollingerBandsMiddleIndicator(sma);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, timeFrame);
        BollingerBandsLowerIndicator bblSMA = new BollingerBandsLowerIndicator(bbmSMA, standardDeviation);

        assertDoubleEquals(bblSMA.getK(), 2);

        assertDoubleEquals(bblSMA.getValue(0), 1);
        assertDoubleEquals(bblSMA.getValue(1), 0.5);
        assertDoubleEquals(bblSMA.getValue(2), 0.367);
        assertDoubleEquals(bblSMA.getValue(3), 1.367);
        assertDoubleEquals(bblSMA.getValue(4), 2.3905);
        assertDoubleEquals(bblSMA.getValue(5), 2.7239);
        assertDoubleEquals(bblSMA.getValue(6), 2.367);

        BollingerBandsLowerIndicator bblSMAwithK = new BollingerBandsLowerIndicator(bbmSMA, standardDeviation, Double.valueOf("1.5"));

        assertDoubleEquals(bblSMAwithK.getK(), 1.5);

        assertDoubleEquals(bblSMAwithK.getValue(0), 1);
        assertDoubleEquals(bblSMAwithK.getValue(1), 0.75);
        assertDoubleEquals(bblSMAwithK.getValue(2), 0.7752);
        assertDoubleEquals(bblSMAwithK.getValue(3), 1.7752);
        assertDoubleEquals(bblSMAwithK.getValue(4), 2.6262);
        assertDoubleEquals(bblSMAwithK.getValue(5), 2.9595);
        assertDoubleEquals(bblSMAwithK.getValue(6), 2.7752);
    }
}
