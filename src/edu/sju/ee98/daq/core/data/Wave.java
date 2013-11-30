/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.daq.core.data;

import org.apache.commons.math3.complex.Complex;

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

    public Object getData() {
        return data;
    }

}
