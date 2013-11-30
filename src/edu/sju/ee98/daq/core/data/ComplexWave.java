/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.daq.core.data;

import edu.sju.ee98.ni.daqmx.data.NIWaveData;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

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

    public ComplexWave(double rate, double[] data) {
        this.rate = rate;
        this.data = ComplexUtils.convertToComplex(data);
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

    public Complex getMainFrequency(double frequency) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transform = fft.transform(data, TransformType.FORWARD);
        int mainF = (int) (frequency * data.length / rate);
        return transform[mainF];
    }
}
