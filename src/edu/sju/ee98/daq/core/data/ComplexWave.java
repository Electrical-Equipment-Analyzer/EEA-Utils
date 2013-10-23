/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.daq.core.data;

import edu.sju.ee98.ni.daqmx.data.NIWaveData;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Leo
 */
public class ComplexWave implements NIWaveData {

    private double rate;
    private Complex[] data;

    public ComplexWave(double rate, Complex[] data) {
        this.rate = rate;
        this.data = data;
    }

    public void setData(Complex[] data) {
        this.data = data;
    }

    @Override
    public double getRate() {
        return this.rate;
    }

    @Override
    public Complex[] getData() {
        return data;
    }

    protected double[] getRealArray() {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getReal();
        }
        return temp;
    }

    protected double[] getImaginaryArray() {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getImaginary();
        }
        return temp;
    }

    protected double[] getAbsoluteArray() {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].abs();
        }
        return temp;
    }

    protected double[] getArgumentArray() {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getArgument();
        }
        return temp;
    }

    protected double[] getZZArray() {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = Math.atan(data[i].getImaginary() / data[i].getReal());
//            temp[i] = Math.atan2(data[i].getReal(), data[i].getImaginary());
        }
        return temp;
    }
}
