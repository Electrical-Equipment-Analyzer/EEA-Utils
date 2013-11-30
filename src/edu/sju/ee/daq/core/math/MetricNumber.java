/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee.daq.core.math;

import java.text.DecimalFormat;

/**
 *
 * @author Leo
 */
public class MetricNumber extends Number {

    private static final int RATE = 1000;
    private static final int NONE = 4;
    private double value;

    public MetricNumber(double value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    private enum metric {

        T, G, M, k, none, m, u, n, p
    }

    public int getFirst() {
        double value = this.value;
        if (value > 1) {
            while (value >= 10) {
                value /= 10;
            }
        } else {
            while (value < 1) {
                value *= 10;
            }
        }
        return (int) value;
    }

    @Override
    public String toString() {
        int index = NONE;
        double value = this.value;
        if (value > 1) {
            while (value > RATE) {
                value /= RATE;
                index--;
            }
        } else {
            while (value < 1) {
                value *= RATE;
                index++;
            }
        }
        return new DecimalFormat("#,##0.###").format(value) + (index == NONE ? "" : metric.values()[index].name());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MetricNumber other = (MetricNumber) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }
}
