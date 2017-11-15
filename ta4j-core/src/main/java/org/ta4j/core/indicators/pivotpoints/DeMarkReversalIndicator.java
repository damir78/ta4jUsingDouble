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
package org.ta4j.core.indicators.pivotpoints;


import org.ta4j.core.Tick;
import org.ta4j.core.indicators.RecursiveCachedIndicator;

import java.util.List;

/**
 * DeMark Reversal Indicator.
 * <p>
 *
 * @author team172011(Simon-Justus Wimmer), 11.10.2017
 * @see <a href="http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points">chart_school: pivotpoints</a>
 */
public class DeMarkReversalIndicator extends RecursiveCachedIndicator<Double> {

    private final DeMarkPivotPointIndicator pivotPointIndicator;
    private final DeMarkPivotLevel level;

    /**
     * Constructor.
     * <p>
     * Calculates the DeMark reversal for the corresponding pivot level
     *
     * @param pivotPointIndicator the {@link DeMarkPivotPointIndicator} for this reversal
     * @param level               the {@link DeMarkPivotLevel} for this reversal (RESISTANT, SUPPORT)
     */
    public DeMarkReversalIndicator(DeMarkPivotPointIndicator pivotPointIndicator, DeMarkPivotLevel level) {
        super(pivotPointIndicator);
        this.pivotPointIndicator = pivotPointIndicator;
        this.level = level;
    }

    @Override
    protected Double calculate(int index) {
        Double x = pivotPointIndicator.getValue(index) * (Double.valueOf(4));
        Double result;

        if (level == DeMarkPivotLevel.SUPPORT) {
            result = calculateSupport(x, index);
        } else {
            result = calculateResistance(x, index);
        }

        return result;

    }

    private Double calculateResistance(Double x, int index) {
        List<Integer> ticksOfPreviousPeriod = pivotPointIndicator.getTicksOfPreviousPeriod(index);
        if (ticksOfPreviousPeriod.isEmpty()) {
            return Double.NaN;
        }
        Tick tick = getTimeSeries().getTick(ticksOfPreviousPeriod.get(0));
        Double low = tick.getMinPrice();
        for (int i : ticksOfPreviousPeriod) {
            low = Math.min(getTimeSeries().getTick(i).getMinPrice(), low);
        }

        return x / (2d) - (low);
    }

    private Double calculateSupport(Double x, int index) {
        List<Integer> ticksOfPreviousPeriod = pivotPointIndicator.getTicksOfPreviousPeriod(index);
        if (ticksOfPreviousPeriod.isEmpty()) {
            return Double.NaN;
        }
        Tick tick = getTimeSeries().getTick(ticksOfPreviousPeriod.get(0));
        Double high = tick.getMaxPrice();
        for (int i : ticksOfPreviousPeriod) {
            high = Math.max(getTimeSeries().getTick(i).getMaxPrice(), high);
        }

        return x / (2d) - (high);
    }

    public enum DeMarkPivotLevel {
        RESISTANCE,
        SUPPORT,
    }
}
