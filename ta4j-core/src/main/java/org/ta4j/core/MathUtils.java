package org.ta4j.core;

/**
 * Created by damir on 15.11.17.
 */
public class MathUtils {

    public final static int PRECISION = 10000;
    public final static double DELTA = 0.00001;

    public static boolean isEqual(double d1, double d2) {
        long a1 = (long) d1 * PRECISION;
        long a2 = (long) d2 * PRECISION;
        return Long.compare(a1, a2) == 0;
    }

    public static boolean isZero(double d1) {
        long a1 = (long) d1 * PRECISION;
        return a1 == 0;
    }

}
