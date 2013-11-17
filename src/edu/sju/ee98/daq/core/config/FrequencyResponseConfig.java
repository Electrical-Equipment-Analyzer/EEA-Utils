/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.config;

import edu.sju.ee98.ni.daqmx.analog.AcqIntClk;
import edu.sju.ee98.ni.daqmx.analog.ContGenIntClk;
import edu.sju.ee98.ni.daqmx.analog.AnalogGenerator;
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

    public ContGenIntClk createOutput() {
        AnalogGenerator analogGenerator = new AnalogGenerator(frequency.getRate(), length, this.voltage, frequency.frequency);
        AnalogConfig outputConfig = getOutputConfig();
        System.out.println(outputConfig);
        System.out.println(analogGenerator.getData());
        return new ContGenIntClk(outputConfig, outputConfig, analogGenerator.getData());
    }

    public AcqIntClk createInput() {
        AnalogConfig intputConfig = getIntputConfig();
        return new AcqIntClk(intputConfig, intputConfig);
    }

    public AnalogConfig getOutputConfig() {
        return new AnalogConfig(outputChannel, -10, 10, frequency.getRate(), frequency.getLength());
    }

    public AnalogConfig getIntputConfig() {
        return new AnalogConfig(inputChannel, -42, 42, frequency.getRate(), frequency.getLength());
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

        public double getFrequency() {
            return this.frequency;
        }

        public double getRate() {
            return this.frequency * 1000;
        }

        public int getLength() {
            return (int) (Math.pow(2, Math.ceil(Math.log(getRate()) / Math.log(2))));
        }
    }

    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "inputChannel=" + inputChannel + ", outputChannel=" + outputChannel + ", frequency=" + frequency + ", voltage=" + voltage + '}';
    }
}
