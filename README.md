# ta4j using only doubles..


![bildschirmfoto 2017-11-16 um 00 12 34](https://user-images.githubusercontent.com/12588674/32865324-e1302260-ca62-11e7-9e48-c4bd02f62f0e.png)



Using double implementation:

SMALauncher:

h = 2
average = 5.0
Time elapsed: 732




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
