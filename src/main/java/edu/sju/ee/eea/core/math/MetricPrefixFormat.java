/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee.eea.core.math;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;

/**
 *
 * @author 薛聿明
 */
public class MetricPrefixFormat extends DecimalFormat {

    private static final int UNIT = 1000;
    private static final int NONE = 8;

    private enum Prefix {

        Y, Z, E, P, T, G, M, k, none, m, u, n, p, f, a, z, y;

        @Override
        public String toString() {
            return (this == none ? "" : this.name());
        }

        public static Prefix valueOf(int exp) {
            return Prefix.values()[NONE - exp];
        }

        public int exp() {
            Prefix[] values = Prefix.values();
            for (int i = 0; i < values.length; i++) {
                if (this.equals(values)) {
                    return i - NONE;
                }
            }
            return NONE;
        }
    }

    public MetricPrefixFormat(String pattern) {
        super(pattern);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        try {
            int exp = (int) Math.floor(Math.log(Math.abs(number)) / Math.log(UNIT));
            String prefix = Prefix.valueOf(exp).toString();
            super.format(number / Math.pow(UNIT, exp), toAppendTo, pos);
            toAppendTo.append(prefix);
            return toAppendTo;
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            return super.format(number, toAppendTo, pos);
        }
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return format((double) number, toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        String number = source;
        int exp = 0;
        char lastChar = source.charAt(source.length() - 1);
        if (Character.isLetter(lastChar)) {
            number = number.substring(0, source.length() - 1);
            try {
                exp = Prefix.valueOf(String.valueOf(lastChar)).exp();
            } catch (java.lang.IllegalArgumentException ex) {
                throw new NumberFormatException("Char '" + lastChar + "' is not a SI Prefix");
            }
        }
        parsePosition.setIndex(number.length());
        Double value = Double.valueOf(number);
        value *= Math.pow(UNIT, exp);
        return value;
    }
}
