/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.frequency.response;

import java.io.Serializable;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author 薛聿明
 */
public class FrequencyResponseFile implements Serializable {

    private FrequencyResponseConfig config;
    private Complex[] in;
    private Complex[] out;

    public FrequencyResponseFile(FrequencyResponseConfig config, Complex[] in, Complex[] out) {
        this.config = config;
        this.in = in;
        this.out = out;
    }

    public FrequencyResponseConfig getConfig() {
        return config;
    }

    public Complex[] getIn() {
        return in;
    }

    public Complex[] getOut() {
        return out;
    }

    public void save(String url) {

    }

    public void open(String url) {

    }
}
