/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.trading.rules;


import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;

/**
 * Indicator-highest-indicator rule.
 * <p>
 * Satisfied when the value of the {@link Indicator indicator} is the highest
 * within the timeFrame.
 */
public class IsHighestRule extends AbstractRule {

	/** The actual indicator */
	private Indicator<Double> ref;
	/** The timeFrame */
	private int timeFrame;

	/**
	 * Constructor.
	 *
	 * @param ref
	 * @param timeFrame
	 */
	public IsHighestRule(Indicator<Double> ref, int timeFrame) {
		this.ref = ref;
		this.timeFrame = timeFrame;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		HighestValueIndicator highest = new HighestValueIndicator(ref, timeFrame);
		Double highestVal = highest.getValue(index);
		Double refVal = ref.getValue(index);

		final boolean satisfied = !refVal.isNaN() && !highestVal.isNaN() && refVal.equals(highestVal);
		traceIsSatisfied(index, satisfied);
		return satisfied;
	}
}
