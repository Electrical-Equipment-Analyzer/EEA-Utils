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
    private double voltage;

    private double minFrequency;
    private double maxFrequrncy;
    private double baseFrequency;
    private int length;

    public FrequencyResponseConfig(String inputChannel, String outputChannel, double voltage, double minFrequency, double maxFrequrncy, int length) {
        this.inputChannel = inputChannel;
        this.outputChannel = outputChannel;
        this.voltage = voltage;

        this.minFrequency = Math.log10(minFrequency);
        this.maxFrequrncy = Math.log10(maxFrequrncy);
        this.length = length;
        this.baseFrequency = (this.maxFrequrncy - this.minFrequency) / length;
    }

    public double getFrequency(int step) {
        return Math.pow(10, minFrequency + (baseFrequency * step));
    }

    public ContGenIntClk createOutput(double frequency) {
        AnalogGenerator analogGenerator = new AnalogGenerator(frequency * 100, configLength(frequency), this.voltage, frequency);
        AnalogConfig outputConfig = getOutputConfig(frequency);
        System.out.println(outputConfig);
        return new ContGenIntClk(outputConfig, outputConfig, analogGenerator.getData());
    }

    public AcqIntClk createInput(double frequency) {
        AnalogConfig intputConfig = getIntputConfig(frequency);
        System.out.println(intputConfig);
        return new AcqIntClk(intputConfig, intputConfig);
    }

    public AnalogConfig getOutputConfig(double frequency) {
        return new AnalogConfig(outputChannel, -10, 10, frequency * 100, configLength(frequency));
    }

    public AnalogConfig getIntputConfig(double frequency) {
        return new AnalogConfig(inputChannel, -42, 42, frequency * 100, configLength(frequency));
    }

    public int getLength() {
        return this.length;
    }

    private static int configLength(double frequency) {
        return 1024;
//        return (int) (Math.pow(2, Math.ceil(Math.log(frequency * 1000) / Math.log(2))));
    }
    
    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "inputChannel=" + inputChannel + ", outputChannel=" + outputChannel + ", voltage=" + voltage + ", minFrequency=" + minFrequency + ", maxFrequrncy=" + maxFrequrncy + ", baseFrequency=" + baseFrequency + ", length=" + length + '}';
    }

}
