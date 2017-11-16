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

public class BollingerBandsUpperIndicatorTest {

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
    public void bollingerBandsUpperUsingSMAAndStandardDeviation() {

        BollingerBandsMiddleIndicator bbmSMA = new BollingerBandsMiddleIndicator(sma);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, timeFrame);
        BollingerBandsUpperIndicator bbuSMA = new BollingerBandsUpperIndicator(bbmSMA, standardDeviation);

        assertDoubleEquals(bbuSMA.getK(), 2);

        assertDoubleEquals(bbuSMA.getValue(0), 1);
        assertDoubleEquals(bbuSMA.getValue(1), 2.5);
        assertDoubleEquals(bbuSMA.getValue(2), 3.633);
        assertDoubleEquals(bbuSMA.getValue(3), 4.633);
        assertDoubleEquals(bbuSMA.getValue(4), 4.2761);
        assertDoubleEquals(bbuSMA.getValue(5), 4.6094);
        assertDoubleEquals(bbuSMA.getValue(6), 5.633);
        assertDoubleEquals(bbuSMA.getValue(7), 5.2761);
        assertDoubleEquals(bbuSMA.getValue(8), 5.633);
        assertDoubleEquals(bbuSMA.getValue(9), 4.2761);

        BollingerBandsUpperIndicator bbuSMAwithK = new BollingerBandsUpperIndicator(bbmSMA, standardDeviation, Double.valueOf("1.5"));

        assertDoubleEquals(bbuSMAwithK.getK(), 1.5);

        assertDoubleEquals(bbuSMAwithK.getValue(0), 1);
        assertDoubleEquals(bbuSMAwithK.getValue(1), 2.25);
        assertDoubleEquals(bbuSMAwithK.getValue(2), 3.2247);
        assertDoubleEquals(bbuSMAwithK.getValue(3), 4.2247);
        assertDoubleEquals(bbuSMAwithK.getValue(4), 4.0404);
        assertDoubleEquals(bbuSMAwithK.getValue(5), 4.3737);
        assertDoubleEquals(bbuSMAwithK.getValue(6), 5.2247);
        assertDoubleEquals(bbuSMAwithK.getValue(7), 5.0404);
        assertDoubleEquals(bbuSMAwithK.getValue(8), 5.2247);
        assertDoubleEquals(bbuSMAwithK.getValue(9), 4.0404);
    }
}
