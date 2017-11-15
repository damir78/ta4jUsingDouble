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
package org.ta4j.core.trading.rules;


import org.ta4j.core.Indicator;
import org.ta4j.core.MathUtils;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.ConstantIndicator;

/**
 * Indicator-equal-indicator rule.
 * <p>
 * Satisfied when the value of the first {@link Indicator indicator} is equal to the value of the second one.
 */
public class IsEqualRule extends AbstractRule {

    /** The first indicator */
    private Indicator<Double> first;
    /** The second indicator */
    private Indicator<Double> second;

    /**
     * Constructor.
     * @param first the first indicator
     * @param value
     */
    public IsEqualRule(Indicator<Double> indicator, Double value) {
        this(indicator, new ConstantIndicator<Double>(value));
    }

    /**
     * Constructor.
     * @param first the first indicator
     * @param second the second indicator
     */
    public IsEqualRule(Indicator<Double> first, Indicator<Double> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        final boolean satisfied = MathUtils.isEqual(first.getValue(index), second.getValue(index));
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }
}
