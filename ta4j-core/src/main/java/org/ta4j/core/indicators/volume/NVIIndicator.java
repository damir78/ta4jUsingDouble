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
package org.ta4j.core.indicators.volume;


import org.ta4j.core.Tick;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.RecursiveCachedIndicator;

/**
 * Negative Volume Index (NVI) indicator.
 * <p>
 *
 * @see http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:negative_volume_inde
 * @see http://www.metastock.com/Customer/Resources/TAAZ/Default.aspx?p=75
 * @see http://www.investopedia.com/terms/n/nvi.asp
 */
public class NVIIndicator extends RecursiveCachedIndicator<Double> {

    private final TimeSeries series;

    public NVIIndicator(TimeSeries series) {
        super(series);
        this.series = series;
    }

    @Override
    protected Double calculate(int index) {
        if (index == 0) {
            return 1000d;
        }

        Tick currentTick = series.getTick(index);
        Tick previousTick = series.getTick(index - 1);
        Double previousValue = getValue(index - 1);

        if (currentTick.getVolume() < (previousTick.getVolume())) {
            Double currentPrice = currentTick.getClosePrice();
            Double previousPrice = previousTick.getClosePrice();
            Double priceChangeRatio = (currentPrice - previousPrice) / (previousPrice);
            return previousValue + (priceChangeRatio * (previousValue));
        }
        return previousValue;
    }

}
