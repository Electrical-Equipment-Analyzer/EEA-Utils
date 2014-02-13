/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.edu.sju.ee.eea.core.data;

import java.util.Arrays;
import tw.edu.sju.ee.eea.core.math.ComplexArray;
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
            return ComplexArray.getReal((Complex[]) data);
        }
        return (double[]) data;
    }

    public double[] getImaginary() {
        if (this.data instanceof Complex[]) {
            return ComplexArray.getImaginary((Complex[]) data);
        }
        return null;
    }

    public double[] getAbsolute() {
        if (this.data instanceof Complex[]) {
            return ComplexArray.getAbsolute((Complex[]) data);
        }
        return null;
    }

    public double[] getArgument() {
        if (this.data instanceof Complex[]) {
            return ComplexArray.getArgument((Complex[]) data);
        }
        return null;
    }

    public Complex[] getComplex() {
        if (this.data instanceof Complex[]) {
            return (Complex[]) data;
        }
        return ComplexUtils.convertToComplex((double[]) data);
    }

    @Override
    public String toString() {
        return "Wave{" + "rate=" + rate + ", max=" + max(getReal()) + ", min=" + min(getReal()) + '}';
    }

    private double max(double[] data) {
        double max = data[0];
        for (int i = 1; i < data.length; i++) {
            max = Math.max(max, data[i]);
        }
        return max;
    }

    private double min(double[] data) {
        double min = data[0];
        for (int i = 1; i < data.length; i++) {
            min = Math.min(min, data[i]);
        }
        return min;
    }
    
    private int find(double value, double[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
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
//        System.out.println(data.length);
//        double max = max(ComplexArray.getAbsolute(transform));
//        System.out.println(max);
//        System.out.println(find(max, ComplexArray.getAbsolute(transform)));
        int mainF = (int) Math.round(frequency * data.length / rate);
//        System.out.println(frequency * data.length / rate);
//        System.out.println(mainF);
        return transform[mainF];
    }
}
