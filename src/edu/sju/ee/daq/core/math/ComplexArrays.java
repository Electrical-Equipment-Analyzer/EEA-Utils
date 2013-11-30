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
public class ComplexArrays {

    public static double[] getAbsolute(Complex data[]) {
        double absolute[] = new double[data.length];
        for (int i = 0; i < absolute.length; i++) {
            absolute[i] = 20 * Math.log10(data[i].abs());
        }
        return absolute;
    }

}
