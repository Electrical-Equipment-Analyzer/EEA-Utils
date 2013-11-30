/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.daq.core.data;

import edu.sju.ee.daq.core.math.ComplexArrays;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author Leo
 */
public class Wave {

    private double rate;
    private Object data;

    public Wave(double rate, double[] data) {
        this.rate = rate;
        this.data = data;
    }

    public Wave(double rate, Complex[] data) {
        this.rate = rate;
        this.data = data;
    }

    public double getRate() {
        return rate;
    }

    public double[] getReal() {
        if (this.data instanceof Complex[]) {
            return ComplexArrays.getReal((Complex[]) data);
        }
        return (double[]) data;
    }

    public double[] getImaginary() {
        if (this.data instanceof Complex[]) {
            return ComplexArrays.getImaginary((Complex[]) data);
        }
        return null;
    }

    public double[] getAbsolute() {
        if (this.data instanceof Complex[]) {
            return ComplexArrays.getAbsolute((Complex[]) data);
        }
        return null;
    }

    public double[] getArgument() {
        if (this.data instanceof Complex[]) {
            return ComplexArrays.getArgument((Complex[]) data);
        }
        return null;
    }

    public Complex[] getComplex() {
        if (this.data instanceof Complex[]) {
            return (Complex[]) data;
        }
        return ComplexUtils.convertToComplex((double[]) data);
    }

    public Complex getValue(double frequency) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] data = null;
        if (this.data instanceof double[]) {
            data = ComplexUtils.convertToComplex((double[]) this.data);
        } else if (this.data instanceof Complex[]) {
            data = (Complex[]) this.data;
        }
        Complex[] transform = fft.transform(data, TransformType.FORWARD);
        int mainF = (int) (frequency * data.length / rate);
        return transform[mainF];
    }
}
