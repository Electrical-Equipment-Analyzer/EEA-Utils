/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.config;

import edu.sju.ee98.ni.daqmx.config.NIClkTiming;
import edu.sju.ee98.ni.daqmx.config.NIVoltageChan;
import java.io.Serializable;

/**
 *
 * @author 102m05008
 */
public class AnalogInputConfig implements NIVoltageChan, NIClkTiming, Serializable {

    private String physicalChannel;
    private double minVoltage;
    private double maxVoltage;
    private double rate;
    private long sampsPerChanToAcquire;

    public AnalogInputConfig(String physicalChannel, double minVoltage, double maxVoltage, double rate, long sampsPerChanToAcquire) {
        this.physicalChannel = physicalChannel;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.rate = rate;
        this.sampsPerChanToAcquire = sampsPerChanToAcquire;
    }

    @Override
    public String getPhysicalChannel() {
        return physicalChannel;
    }

    @Override
    public double getMinVoltage() {
        return minVoltage;
    }

    @Override
    public double getMaxVoltage() {
        return maxVoltage;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public long getSampsPerChanToAcquire() {
        return sampsPerChanToAcquire;
    }

    @Override
    public String toString() {
        return "NIAnalogConfig{" + "physicalChannel=" + physicalChannel + ", minVoltage=" + minVoltage + ", maxVoltage=" + maxVoltage + ", rate=" + rate + ", length=" + sampsPerChanToAcquire + '}';
    }
}
