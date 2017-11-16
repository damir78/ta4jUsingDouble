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
package org.ta4j.core;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Base implementation of a {@link Tick}.
 * <p>
 */
public class BaseTick implements Tick {

    private static final long serialVersionUID = 8038383777467488147L;
    /** Time period (e.g. 1 day, 15 min, etc.) of the tick */
    private Duration timePeriod;
    /** End time of the tick */
    private ZonedDateTime endTime;
    /** Begin time of the tick */
    private ZonedDateTime beginTime;
    /** Open price of the period */
    private Double openPrice = null;
    /** Close price of the period */
    private Double closePrice = null;
    /** Max price of the period */
    private Double maxPrice = null;
    /** Min price of the period */
    private Double minPrice = null;
    /** Traded amount during the period */
    private Double amount = 0d;
    /** Volume of the period */
    private Double volume = 0d;
    /** Trade count */
    private int trades = 0;

    /**
     * Constructor.
     * @param timePeriod the time period
     * @param endTime the end time of the tick period
     */
    public BaseTick(Duration timePeriod, ZonedDateTime endTime) {
        checkTimeArguments(timePeriod, endTime);
        this.timePeriod = timePeriod;
        this.endTime = endTime;
        this.beginTime = endTime.minus(timePeriod);
    }

    /**
     * Constructor.
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     */
    public BaseTick(ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        this(endTime, Double.valueOf(openPrice),
                Double.valueOf(highPrice),
                Double.valueOf(lowPrice),
                Double.valueOf(closePrice),
                Double.valueOf(volume));
    }

    /**
     * Constructor.
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     */
    public BaseTick(ZonedDateTime endTime, String openPrice, String highPrice, String lowPrice, String closePrice, String volume) {
        this(endTime, Double.valueOf(openPrice),
                Double.valueOf(highPrice),
                Double.valueOf(lowPrice),
                Double.valueOf(closePrice),
                Double.valueOf(volume));
    }

    /**
     * Constructor.
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     */
    public BaseTick(ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume) {
        this(Duration.ofDays(1), endTime, openPrice, highPrice, lowPrice, closePrice, volume);
    }

    /**
     * Constructor.
     * @param timePeriod the time period
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     */
    public BaseTick(Duration timePeriod, ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume) {
        this(timePeriod, endTime, openPrice, highPrice, lowPrice, closePrice, volume, 0d);
    }

    /**
     * Constructor.
     * @param timePeriod the time period
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     * @param amount the amount of the tick period
     */
    public BaseTick(Duration timePeriod, ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume, Double amount) {
        checkTimeArguments(timePeriod, endTime);
        this.timePeriod = timePeriod;
        this.endTime = endTime;
        this.beginTime = endTime.minus(timePeriod);
        this.openPrice = openPrice;
        this.maxPrice = highPrice;
        this.minPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.amount = amount;
    }

    /**
     * @return the open price of the period
     */
    public Double getOpenPrice() {
        return openPrice;
    }

    /**
     * @return the min price of the period
     */
    public Double getMinPrice() {
        return minPrice;
    }

    /**
     * @return the max price of the period
     */
    public Double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @return the close price of the period
     */
    public Double getClosePrice() {
        return closePrice;
    }

    /**
     * @return the whole traded volume in the period
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * @return the number of trades in the period
     */
    public int getTrades() {
        return trades;
    }

    /**
     * @return the whole traded amount of the period
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @return the time period of the tick
     */
    public Duration getTimePeriod() {
        return timePeriod;
    }

    /**
     * @return the begin timestamp of the tick period
     */
    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * @return the end timestamp of the tick period
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Adds a trade at the end of tick period.
     * @param tradeVolume the traded volume
     * @param tradePrice the price
     */
    public void addTrade(Double tradeVolume, Double tradePrice) {
        if (openPrice == null) {
            openPrice = tradePrice;
        }
        closePrice = tradePrice;

        if (maxPrice == null) {
            maxPrice = tradePrice;
        } else {
            maxPrice = maxPrice < (tradePrice) ? tradePrice : maxPrice;
        }
        if (minPrice == null) {
            minPrice = tradePrice;
        } else {
            minPrice = minPrice > (tradePrice) ? tradePrice : minPrice;
        }
        volume = volume + (tradeVolume);
        amount = amount + (tradeVolume * (tradePrice));
        trades++;
    }

    @Override
    public String toString() {
        return String.format("{end time: %1s, close price: %2$f, open price: %3$f, min price: %4$f, max price: %5$f, volume: %6$f}",
                endTime.withZoneSameInstant(ZoneId.systemDefault()), closePrice, openPrice, minPrice, maxPrice, volume);
    }

    /**
     * @param timePeriod the time period
     * @param endTime the end time of the tick
     * @throws IllegalArgumentException if one of the arguments is null
     */
    private void checkTimeArguments(Duration timePeriod, ZonedDateTime endTime) {
        if (timePeriod == null) {
            throw new IllegalArgumentException("Time period cannot be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }
    }
}
