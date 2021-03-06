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
package ta4jexamples.bots;

import org.ta4j.core.*;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;
import ta4jexamples.loaders.CsvTradesLoader;

import java.time.ZonedDateTime;

/**
 * This class is an example of a dummy trading bot using ta4j.
 * <p>
 */
public class TradingBotOnMovingTimeSeries {

    /**
     * Close price of the last tick
     */
    private static Double LAST_TICK_CLOSE_PRICE;

    /**
     * Builds a moving time series (i.e. keeping only the maxTickCount last ticks)
     *
     * @param maxTickCount the number of ticks to keep in the time series (at maximum)
     * @return a moving time series
     */
    private static TimeSeries initMovingTimeSeries(int maxTickCount) {
        TimeSeries series = CsvTradesLoader.loadBitstampSeries();
        System.out.print("Initial tick count: " + series.getTickCount());
        // Limitating the number of ticks to maxTickCount
        series.setMaximumTickCount(maxTickCount);
        LAST_TICK_CLOSE_PRICE = series.getTick(series.getEndIndex()).getClosePrice();
        System.out.println(" (limited to " + maxTickCount + "), close price = " + LAST_TICK_CLOSE_PRICE);
        return series;
    }

    /**
     * @param series a time series
     * @return a dummy strategy
     */
    private static Strategy buildStrategy(TimeSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, 12);

        // Signals
        // Buy when SMA goes over close price
        // Sell when close price goes over SMA
        Strategy buySellSignals = new BaseStrategy(
                new OverIndicatorRule(sma, closePrice),
                new UnderIndicatorRule(sma, closePrice)
        );
        return buySellSignals;
    }

    /**
     * Generates a random Double number between min and max.
     *
     * @param min the minimum bound
     * @param max the maximum bound
     * @return a random Double number between min and max
     */
    private static Double randDouble(Double min, Double max) {
        Double randomDouble = null;
        if (min != null && max != null && min < (max)) {
            randomDouble = max - (min) * (Double.valueOf(Math.random())) + (min);
        }
        return randomDouble;
    }

    /**
     * Generates a random tick.
     *
     * @return a random tick
     */
    private static Tick generateRandomTick() {
        final Double maxRange = Double.valueOf("0.03"); // 3.0%
        double openPrice = LAST_TICK_CLOSE_PRICE;
        double minPrice = openPrice - (openPrice * (maxRange * Math.random()));
        double maxPrice = openPrice + (openPrice * (maxRange * Math.random()));
        double closePrice = randDouble(minPrice, maxPrice);
        LAST_TICK_CLOSE_PRICE = closePrice;
        return new BaseTick(ZonedDateTime.now(), openPrice, maxPrice, minPrice, closePrice, 1d);
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("********************** Initialization **********************");
        // Getting the time series
        TimeSeries series = initMovingTimeSeries(20);

        // Building the trading strategy
        Strategy strategy = buildStrategy(series);

        // Initializing the trading history
        TradingRecord tradingRecord = new BaseTradingRecord();
        System.out.println("************************************************************");

        /**
         * We run the strategy for the 50 next ticks.
         */
        for (int i = 0; i < 50; i++) {

            // New tick
            Thread.sleep(30); // I know...
            Tick newTick = generateRandomTick();
            System.out.println("------------------------------------------------------\n"
                    + "Tick " + i + " added, close price = " + newTick.getClosePrice());
            series.addTick(newTick);

            int endIndex = series.getEndIndex();
            if (strategy.shouldEnter(endIndex)) {
                // Our strategy should enter
                System.out.println("Strategy should ENTER on " + endIndex);
                boolean entered = tradingRecord.enter(endIndex, newTick.getClosePrice(), 10d);
                if (entered) {
                    Order entry = tradingRecord.getLastEntry();
                    System.out.println("Entered on " + entry.getIndex()
                            + " (price=" + entry.getPrice()
                            + ", amount=" + entry.getAmount() + ")");
                }
            } else if (strategy.shouldExit(endIndex)) {
                // Our strategy should exit
                System.out.println("Strategy should EXIT on " + endIndex);
                boolean exited = tradingRecord.exit(endIndex, newTick.getClosePrice(), 10d);
                if (exited) {
                    Order exit = tradingRecord.getLastExit();
                    System.out.println("Exited on " + exit.getIndex()
                            + " (price=" + exit.getPrice()
                            + ", amount=" + exit.getAmount() + ")");
                }
            }
        }
    }
}
