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
package org.ta4j.core.mocks;

import org.ta4j.core.BaseTick;

import java.time.ZonedDateTime;

/**
 * A mock tick with sample data.
 */
public class MockTick extends BaseTick {

    private Double amount = 0d;

    private int trades = 0;

    public MockTick(double closePrice) {
        this(ZonedDateTime.now(), closePrice);
    }

    public MockTick(double closePrice, double volume) {
        super(ZonedDateTime.now(), 0, 0, 0, closePrice, volume);
    }

    public MockTick(ZonedDateTime endTime, double closePrice) {
        super(endTime, 0, 0, 0, closePrice, 0);
    }

    public MockTick(double openPrice, double closePrice, double maxPrice, double minPrice) {
        super(ZonedDateTime.now(), openPrice, maxPrice, minPrice, closePrice, 1);
    }

    public MockTick(double openPrice, double closePrice, double maxPrice, double minPrice, double volume) {
        super(ZonedDateTime.now(), openPrice, maxPrice, minPrice, closePrice, volume);
    }

    public MockTick(ZonedDateTime endTime, double openPrice, double closePrice, double maxPrice, double minPrice, double amount, double volume, int trades) {
        super(endTime, openPrice, maxPrice, minPrice, closePrice, volume);
        this.amount = Double.valueOf(amount);
        this.trades = trades;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public int getTrades() {
        return trades;
    }
}
