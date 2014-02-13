/*
 * Copyright (C) 2014 Leo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package tw.edu.sju.ee.eea.core.standard;

/**
 *
 * @author Leo
 */
public class FRAChina {

    private double[] variable;

    public FRAChina(double[] variable) {
        this.variable = variable;
    }

    public double average() {
        double total = 0;
        for (int i = 0; i < variable.length; i++) {
            total += variable[i];
        }
        return total / variable.length;
    }

    public double variance() {
        double average = average();
        double tmp = 0;
        for (int i = 0; i < variable.length; i++) {
            tmp += Math.pow(variable[i] - average, 2);
        }
        return tmp / variable.length;
    }

    public static double covariance(FRAChina x, FRAChina y) {
        if (x.variable.length != y.variable.length) {
            return 0;
        }
        double xAverage = x.average();
        double yAverage = y.average();
        double tmp = 0;
        for (int i = 0; i < x.variable.length; i++) {
            tmp += (x.variable[i] - xAverage) * (y.variable[i] - yAverage);
        }
        return tmp / x.variable.length;
    }

    public static double autocovariance(FRAChina x, FRAChina y) {
        return x.covariance(x, y) / Math.sqrt(x.variance() * y.variance());
    }

    public static double coefficient(FRAChina x, FRAChina y) {
        double LR = autocovariance(x, y);
        if (1 - LR < Math.pow(10, -10)) {
            return 10;
        } else {
            return -Math.log10(1 - LR);
        }
    }

}
