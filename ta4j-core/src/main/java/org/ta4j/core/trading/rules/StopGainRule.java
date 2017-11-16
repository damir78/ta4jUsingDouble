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


import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 * A stop-gain rule.
 * <p>
 * Satisfied when the close price reaches the gain threshold.
 */
public class StopGainRule extends AbstractRule {

    /**
     * The close price indicator
     */
    private ClosePriceIndicator closePrice;

    /**
     * The gain ratio threshold (e.g. 1.03 for 3%)
     */
    private Double gainRatioThreshold;

    /**
     * Constructor.
     *
     * @param closePrice     the close price indicator
     * @param gainPercentage the gain percentage
     */
    public StopGainRule(ClosePriceIndicator closePrice, Double gainPercentage) {
        this.closePrice = closePrice;
        this.gainRatioThreshold = (100d + gainPercentage) / (100d);
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        // No trading history or no trade opened, no gain
        if (tradingRecord != null) {
            Trade currentTrade = tradingRecord.getCurrentTrade();
            if (currentTrade.isOpened()) {
                Double entryPrice = currentTrade.getEntry().getPrice();
                Double currentPrice = closePrice.getValue(index);
                Double threshold = entryPrice * (gainRatioThreshold);
                if (currentTrade.getEntry().isBuy()) {
                    satisfied = currentPrice >= (threshold);
                } else {
                    satisfied = currentPrice <= (threshold);
                }
            }
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }
}
