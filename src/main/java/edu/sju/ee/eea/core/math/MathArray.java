/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee.eea.core.math;

/**
 *
 * @author Leo
 */
public class MathArray {

    public static double sum(double[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum;
    }

    public static double average(double[] data) {
        return sum(data) / data.length;
    }

    public static double[] minus(double[] data, double minus) {
        double[] temp = new double[data.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = data[i] - minus;
        }
        return temp;
    }

}
