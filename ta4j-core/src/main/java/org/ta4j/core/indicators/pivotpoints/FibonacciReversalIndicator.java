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
 * Fibonacci Reversal Indicator.
 * <p>
 *
 * @author team172011(Simon-Justus Wimmer), 09.10.2017
 * @see <a href="http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points">chart_school: pivotpoints</a>
 */
public class FibonacciReversalIndicator extends RecursiveCachedIndicator<Double> {

    private final PivotPointIndicator pivotPointIndicator;
    private final FibReversalTyp fibReversalTyp;
    private final Double fibonacciFactor;

    /**
     * Constructor.
     * <p>
     * Calculates a (fibonacci) reversal
     *
     * @param pivotPointIndicator the {@link PivotPointIndicator} for this reversal
     * @param fibonacciFactor     the fibonacci factor for this reversal
     * @param fibReversalTyp      the FibonacciReversalIndicator.FibReversalTyp of the reversal (SUPPORT, RESISTANCE)
     */
    public FibonacciReversalIndicator(PivotPointIndicator pivotPointIndicator, Double fibonacciFactor, FibReversalTyp fibReversalTyp) {
        super(pivotPointIndicator);
        this.pivotPointIndicator = pivotPointIndicator;
        this.fibonacciFactor = fibonacciFactor;
        this.fibReversalTyp = fibReversalTyp;
    }

    /**
     * Constructor.
     * <p>
     * Calculates a (fibonacci) reversal
     *
     * @param pivotPointIndicator the {@link PivotPointIndicator} for this reversal
     * @param fibonacciFactor     the {@link FibonacciFactor} factor for this reversal
     * @param fibReversalTyp      the FibonacciReversalIndicator.FibReversalTyp of the reversal (SUPPORT, RESISTANCE)
     */
    public FibonacciReversalIndicator(PivotPointIndicator pivotPointIndicator, FibonacciFactor fibonacciFactor, FibReversalTyp fibReversalTyp) {
        this(pivotPointIndicator, fibonacciFactor.getFactor(), fibReversalTyp);
    }

    @Override
    protected Double calculate(int index) {
        List<Integer> ticksOfPreviousPeriod = pivotPointIndicator.getTicksOfPreviousPeriod(index);
        if (ticksOfPreviousPeriod.isEmpty())
            return Double.NaN;
        Tick tick = getTimeSeries().getTick(ticksOfPreviousPeriod.get(0));
        Double high = tick.getMaxPrice();
        Double low = tick.getMinPrice();
        for (int i : ticksOfPreviousPeriod) {
            high = Math.max(getTimeSeries().getTick(i).getMaxPrice(), high);
            low = Math.min(getTimeSeries().getTick(i).getMinPrice(), low);
        }

        if (fibReversalTyp == FibReversalTyp.RESISTANCE) {
            return pivotPointIndicator.getValue(index) + (fibonacciFactor * (high - (low)));
        }
        return pivotPointIndicator.getValue(index) - (fibonacciFactor * (high - (low)));
    }

    public enum FibReversalTyp {
        SUPPORT,
        RESISTANCE
    }

    /**
     * Standard Fibonacci factors
     */
    public enum FibonacciFactor {
        Factor1(0.382d),
        Factor2(0.618d),
        Factor3(1d);

        private final Double factor;

        FibonacciFactor(Double factor) {
            this.factor = factor;
        }

        public Double getFactor() {
            return this.factor;
        }

    }
}
