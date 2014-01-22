/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package tw.edu.sju.ee.eea.core.function;

import tw.edu.sju.ee.eea.core.data.Wave;
import tw.edu.sju.ee.eea.jni.daqmx.DAQmx;
import java.io.Serializable;

/**
 *
 * @author 102m05008
 */
public class AnalogVoltage implements Serializable {

    private String physicalChannel;
    private double minVoltage;
    private double maxVoltage;
    private double rate;
    private int length;

    public AnalogVoltage(String physicalChannel, double minVoltage, double maxVoltage, double rate, int length) {
        this.physicalChannel = physicalChannel;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.rate = rate;
        this.length = length;
    }

    public String getPhysicalChannel() {
        return physicalChannel;
    }

    public double getMinVoltage() {
        return minVoltage;
    }

    public double getMaxVoltage() {
        return maxVoltage;
    }

    public double getRate() {
        return rate;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "NIAnalogConfig{" + "physicalChannel=" + physicalChannel + ", minVoltage=" + minVoltage + ", maxVoltage=" + maxVoltage + ", rate=" + rate + ", length=" + length + '}';
    }

    public Wave process() throws Exception {
        DAQmx analog = new DAQmx();
        analog.createTask("");
        analog.createAIVoltageChan(physicalChannel, "", DAQmx.Val_Cfg_Default, minVoltage, maxVoltage, DAQmx.Val_Volts, null);
        analog.cfgSampClkTiming("", rate, DAQmx.Val_Rising, DAQmx.Val_FiniteSamps, length);
        analog.startTask();
        double data[] = new double[length];
        analog.readAnalogF64(length, 10, DAQmx.Val_GroupByChannel, data, length, null);
        analog.stopTask();
        analog.clearTask();
        return new Wave(this.rate, data);
    }
}
