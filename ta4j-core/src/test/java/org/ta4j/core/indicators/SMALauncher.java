package org.ta4j.core.indicators;

import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.AnotherMockTimeSeries;

/**
 * Created by damir on 15.11.17.
 */
public class SMALauncher {

    public static void main(String[] args) {

        int initialCapacity = 54 * 5 * 24 * 60 * 3;

        double[] input = new double[initialCapacity];
        for (int i = 0; i < input.length; i++) {
            input[i] = 5d;
        }

        /**
         * just adding close price instead timestamp with close price:
         *   AnotherMockTimeSeries: ticks.add(new MockTick(data[i]));
         //MockTimeSeries: ticks.add(new MockTick(ZonedDateTime.now().with(ChronoField.MILLI_OF_SECOND, i), data[i]));
         */
        TimeSeries timeSeries = new AnotherMockTimeSeries(input);
        long start = System.currentTimeMillis();
        Double average = null;
        for (int h = 2; h < 3; h++) {
            System.out.println("h = " + h);
            SMAIndicator quoteSMA = new SMAIndicator(new ClosePriceIndicator(timeSeries), h);
            for (int i = 0; i < timeSeries.getTickCount(); i++) {
                average = quoteSMA.getValue(i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("average = " + average);

        System.out.println("Time elapsed: " + (end - start));
    }
}
