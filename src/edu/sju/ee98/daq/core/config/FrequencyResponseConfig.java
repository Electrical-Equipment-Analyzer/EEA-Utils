/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.config;

import java.io.Serializable;

/**
 *
 * @author 102m05008
 */
public class FrequencyResponseConfig implements Serializable {

    private String inputChannel;
    private String outputChannel;
    private Frequency frequency;
    private double voltage;
    private static int length = 1024;

    public FrequencyResponseConfig(String inputChannel, String outputChannel, double voltage, double minFrequency, double maxFrequrncy, int log) {
        this.inputChannel = inputChannel;
        this.outputChannel = outputChannel;
        this.voltage = voltage;
        this.frequency = new Frequency(minFrequency, maxFrequrncy, log);
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public AnalogConfig getOutputConfig() {
        return new AnalogConfig(outputChannel, -voltage, voltage, frequency.frequency, length);
    }

    public AnalogConfig getIntputConfig() {
        return new AnalogConfig(inputChannel, -voltage, voltage, frequency.frequency, length);
    }

    public int getLength() {
        return (int) (this.frequency.max - this.frequency.min);
    }

    public class Frequency {

        private double min;
        private double max;
        private int log;
        private double frequency;

        private Frequency(double min, double max, int log) {
            this.min = min;
            this.max = max;
            this.log = log;
            this.init();
        }

        public void init() {
            this.frequency = this.min;
        }

        public boolean next() {
            this.frequency = this.frequency + this.log;
            return this.frequency > max;
        }
    }

    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "inputChannel=" + inputChannel + ", outputChannel=" + outputChannel + ", frequency=" + frequency + ", voltage=" + voltage + '}';
    }
}
