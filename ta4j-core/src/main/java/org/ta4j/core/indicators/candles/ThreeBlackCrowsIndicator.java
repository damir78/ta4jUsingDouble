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


import org.ta4j.core.Tick;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;

/**
 * Three black crows indicator.
 * <p>
 * @see http://www.investopedia.com/terms/t/three_black_crows.asp
 */
public class ThreeBlackCrowsIndicator extends CachedIndicator<Boolean> {

    private final TimeSeries series;

    /** Lower shadow */
    private final LowerShadowIndicator lowerShadowInd;
    /** Average lower shadow */
    private final SMAIndicator averageLowerShadowInd;
    /** Factor used when checking if a candle has a very short lower shadow */
    private final Double factor;

    private int whiteCandleIndex = -1;

    /**
     * Constructor.
     * @param series a time series
     * @param timeFrame the number of ticks used to calculate the average lower shadow
     * @param factor the factor used when checking if a candle has a very short lower shadow
     */
    public ThreeBlackCrowsIndicator(TimeSeries series, int timeFrame, Double factor) {
        super(series);
        this.series = series;
        lowerShadowInd = new LowerShadowIndicator(series);
        averageLowerShadowInd = new SMAIndicator(lowerShadowInd, timeFrame);
        this.factor = factor;
    }

    @Override
    protected Boolean calculate(int index) {
        if (index < 3) {
            // We need 4 candles: 1 white, 3 black
            return false;
        }
        whiteCandleIndex = index - 3;
        return series.getTick(whiteCandleIndex).isBullish()
                && isBlackCrow(index - 2)
                && isBlackCrow(index - 1)
                && isBlackCrow(index);
    }

    /**
     * @param index the tick/candle index
     * @return true if the tick/candle has a very short lower shadow, false otherwise
     */
    private boolean hasVeryShortLowerShadow(int index) {
        Double currentLowerShadow = lowerShadowInd.getValue(index);
        // We use the white candle index to remove to bias of the previous crows
        Double averageLowerShadow = averageLowerShadowInd.getValue(whiteCandleIndex);

        return currentLowerShadow < (averageLowerShadow * (factor));
    }

    /**
     * @param index the current tick/candle index
     * @return true if the current tick/candle is declining, false otherwise
     */
    private boolean isDeclining(int index) {
        Tick prevTick = series.getTick(index - 1);
        Tick currTick = series.getTick(index);
        final Double prevOpenPrice = prevTick.getOpenPrice();
        final Double prevClosePrice = prevTick.getClosePrice();
        final Double currOpenPrice = currTick.getOpenPrice();
        final Double currClosePrice = currTick.getClosePrice();

        // Opens within the body of the previous candle
        return currOpenPrice < (prevOpenPrice) && currOpenPrice > (prevClosePrice)
                // Closes below the previous close price
                && currClosePrice < (prevClosePrice);
    }

    /**
     * @param index the current tick/candle index
     * @return true if the current tick/candle is a black crow, false otherwise
     */
    private boolean isBlackCrow(int index) {
        Tick prevTick = series.getTick(index - 1);
        Tick currTick = series.getTick(index);
        if (currTick.isBearish()) {
            if (prevTick.isBullish()) {
                // First crow case
                return hasVeryShortLowerShadow(index)
                        && currTick.getOpenPrice() < (prevTick.getMaxPrice());
            } else {
                return hasVeryShortLowerShadow(index) && isDeclining(index);
            }
        }
        return false;
    }
}
