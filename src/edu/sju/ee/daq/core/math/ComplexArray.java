package edu.sju.ee.daq.core.math;

import org.apache.commons.math3.complex.Complex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Leo
 */
public class ComplexArray {

    public static double[] getReal(Complex data[]) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getReal();
        }
        return temp;
    }

    public static double[] getImaginary(Complex data[]) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getImaginary();
        }
        return temp;
    }

    public static double[] getAbsolute(Complex data[]) {
        double temp[] = new double[data.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = data[i].abs();
        }
        return temp;
    }

    public static double[] getArgument(Complex data[]) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getArgument();
        }
        return temp;
    }

}
