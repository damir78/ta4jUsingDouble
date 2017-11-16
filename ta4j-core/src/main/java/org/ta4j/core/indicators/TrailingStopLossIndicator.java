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


import org.ta4j.core.Indicator;

/**
 * This class implenents a basic trailing stop loss indicator.
 *
 * Basic idea:
 * Your stop order limit is automatically adjusted while price is rising.
 * On falling prices the initial StopLossDistance is reduced.
 * Sell signal: When StopLossDistance becomes '0'
 *
 * Usage:
 *
 * // Buying rule
 * Rule buyingRule = new BooleanRule(true); // No real buying rule
 *
 * // Selling rule
 * Rule sellingRule = new CrossedDownIndicatorRule(ClosePrice_Indicator, TrailingStopLoss_Indicator).and(new JustOnceRule());
 *
 * // Strategy
 * Strategy strategy = new Strategy(buyingRule, sellingRule);
 *
 * Hints:
 * There are two constructors for two use cases:
 *  - Constructor 1: No initialStopLimit is needed. It is taken from the first indicator value
 *  - Constructor 2: You can set an initialStopLimit
 * It may influence the trade signals of the strategy depending which constructor you choose.
 *
 * @author Bastian Engelmann
 */
public class TrailingStopLossIndicator extends CachedIndicator<Double> {

    private final Indicator<Double> indicator;
    private final Double stopLossDistance;
    private Double stopLossLimit;

    /**
     * Constructor.
     * @param indicator an indicator
     * @param stopLossDistance the stop-loss distance (absolute)
     */
    public TrailingStopLossIndicator(Indicator<Double> indicator, Double stopLossDistance) {
        this(indicator, stopLossDistance, Double.NaN);
    }

    /**
     * Constructor.
     * @param indicator an indicator
     * @param stopLossDistance the stop-loss distance (absolute)
     * @param initialStopLossLimit the initial stop-loss limit
     */
    public TrailingStopLossIndicator(Indicator<Double> indicator, Double stopLossDistance, Double initialStopLossLimit) {
        super(indicator);
        this.indicator = indicator;
        this.stopLossDistance = stopLossDistance;
        this.stopLossLimit = initialStopLossLimit;
    }

    /**
     * Simple implementation of the trailing stop-loss concept.
     * Logic:
     * IF CurrentPrice - StopLossDistance > StopLossLimit THEN StopLossLimit = CurrentPrice - StopLossDistance
     * @param index
     * @return Double
     */
    @Override
    protected Double calculate(int index) {
        if (stopLossLimit.isNaN()) {
            // Case without initial stop-loss limit value
            stopLossLimit = indicator.getValue(0) - (stopLossDistance);
        }
        Double currentValue = indicator.getValue(index);
        Double referenceValue = stopLossLimit + (stopLossDistance);

        if (currentValue > (referenceValue)) {
            stopLossLimit = currentValue - (stopLossDistance);
        }
        return stopLossLimit;
    }
}
