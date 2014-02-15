/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package tw.edu.sju.ee.eea.core.frequency.response;

import tw.edu.sju.ee.eea.core.data.EEAFile;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author 薛聿明
 */
public class FrequencyResponseFile extends EEAFile implements Serializable {

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

    public Map<Double, Complex> getGain() {
        LinkedHashMap<Double, Complex> map = new LinkedHashMap<Double, Complex>();
        for (int i = 0; i < config.getPoints(); i++) {
            map.put(config.getFrequency(i), out[i].divide(in[i]));
        }
        return map;
    }
}
