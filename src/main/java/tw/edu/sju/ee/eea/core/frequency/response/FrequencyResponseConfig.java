/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package tw.edu.sju.ee.eea.core.frequency.response;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 薛聿明
 */
public class FrequencyResponseConfig implements Serializable {

    private String generateDevice;
    private String responseDevice;
    private double voltage;
    private double minFrequency;
    private double maxFrequrncy;
    private int length;
    private int rateMultiple;

    public FrequencyResponseConfig(String generateDevice, String responseDevice, double voltage, double minFrequency, double maxFrequrncy, int length, int rateMultiple) {
        this.generateDevice = generateDevice;
        this.responseDevice = responseDevice;
        this.voltage = voltage;
        this.minFrequency = minFrequency;
        this.maxFrequrncy = maxFrequrncy;
        this.length = length;
        this.rateMultiple = rateMultiple;
    }

    public String getGenerateDevice() {
        return generateDevice;
    }

//    public void setGenerateDevice(String generateDevice) {
//        this.generateDevice = generateDevice;
//    }
    public String getResponseDevice() {
        return responseDevice;
    }

//    public void setResponseDevice(String responseDevice) {
//        this.responseDevice = responseDevice;
//    }
    public double getVoltage() {
        return voltage;
    }

//    public void setVoltage(double voltage) {
//        this.voltage = voltage;
//    }
    public double getMinFrequency() {
        return minFrequency;
    }

//    public void setMinFrequency(double minFrequency) {
//        this.minFrequency = minFrequency;
//    }
    public double getMaxFrequrncy() {
        return maxFrequrncy;
    }

//    public void setMaxFrequrncy(double maxFrequrncy) {
//        this.maxFrequrncy = maxFrequrncy;
//    }
    public int getLength() {
        return length;
    }

//    public void setLength(int length) {
//        this.length = length;
//    }
    public int getRateMultiple() {
        return rateMultiple;
    }

    public double getFrequency(int step) {
        double baseFrequency = (Math.log10(maxFrequrncy) - Math.log10(minFrequency)) / length;
        return Math.pow(10, Math.log10(minFrequency) + (baseFrequency * step));
    }

    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "generateChannel=" + generateDevice + ", responseChannel=" + responseDevice + ", voltage=" + voltage + ", minFrequency=" + minFrequency + ", maxFrequrncy=" + maxFrequrncy + ", length=" + length + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.generateDevice);
        hash = 89 * hash + Objects.hashCode(this.responseDevice);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.voltage) ^ (Double.doubleToLongBits(this.voltage) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.minFrequency) ^ (Double.doubleToLongBits(this.minFrequency) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.maxFrequrncy) ^ (Double.doubleToLongBits(this.maxFrequrncy) >>> 32));
        hash = 89 * hash + this.length;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FrequencyResponseConfig other = (FrequencyResponseConfig) obj;
        if (!Objects.equals(this.generateDevice, other.generateDevice)) {
            return false;
        }
        if (!Objects.equals(this.responseDevice, other.responseDevice)) {
            return false;
        }
        if (Double.doubleToLongBits(this.voltage) != Double.doubleToLongBits(other.voltage)) {
            return false;
        }
        if (Double.doubleToLongBits(this.minFrequency) != Double.doubleToLongBits(other.minFrequency)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxFrequrncy) != Double.doubleToLongBits(other.maxFrequrncy)) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }
        return true;
    }

}
