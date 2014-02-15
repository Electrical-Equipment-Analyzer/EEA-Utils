/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package tw.edu.sju.ee.eea.core.frequency.response;

import java.io.Serializable;
import java.util.Objects;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author 薛聿明
 */
@Messages({
    "name=Configuration",
    "LBL_GenerateDevice=Generate Device",
    "DCT_GenerateDevice=Generate Device Description",
    "LBL_GenerateChannel=Generate Channel",
    "DCT_GenerateChannel=Generate Channel Description",
    "LBL_ResponseDevice=Response Device",
    "DCT_ResponseDevice=Response Device Description",
    "LBL_ResponseChannel=Response Channel",
    "DCT_ResponseChannel=Response Channel Description",
    "LBL_Voltage=Voltage",
    "DCT_Voltage=Voltage Description",
    "LBL_StartFrequency=Start Frequency",
    "DCT_StartFrequency=Start Frequency Description",
    "LBL_StopFrequrncy=Stop Frequency",
    "DCT_StopFrequrncy=Stop Frequency Description",
    "LBL_Points=Testing Points",
    "DCT_Points=Testing Points Description",
    "LBL_RatePerHz=Sampling Rate",
    "DCT_RatePerHz=Sampling Rate Description",
    "LBL_Description=Description",
    "DCT_Description=Description Description"
})
public class FrequencyResponseConfig implements Serializable {

    private final String generateDevice;
    private final String generateChannel;
    private final String responseDevice;
    private final String responseChannel;
    private final double voltage;
    private final double startFrequency;
    private final double stopFrequrncy;
    private final int points;
    private final int ratePerHz;
    private final String description;

    public FrequencyResponseConfig(String generateDevice, String responseDevice, double voltage, double startFrequency, double stopFrequrncy, int points, int ratePerHz) {
        this(generateDevice, "", responseDevice, "", voltage, startFrequency, stopFrequrncy, points, ratePerHz, "");
    }

    public FrequencyResponseConfig(String generateDevice, String generateChannel, String responseDevice, String responseChannel, double voltage, double startFrequency, double stopFrequrncy, int points, int ratePerHz, String description) {
        this.generateDevice = generateDevice;
        this.generateChannel = generateChannel;
        this.responseDevice = responseDevice;
        this.responseChannel = responseChannel;
        this.voltage = voltage;
        this.startFrequency = startFrequency;
        this.stopFrequrncy = stopFrequrncy;
        this.points = points;
        this.ratePerHz = ratePerHz;
        this.description = description;
    }

    public String getGenerateDevice() {
        return generateDevice;
    }

    public String getGenerateChannel() {
        return generateChannel;
    }

    public String getResponseDevice() {
        return responseDevice;
    }

    public String getResponseChannel() {
        return responseChannel;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getStartFrequency() {
        return startFrequency;
    }

    public double getStopFrequrncy() {
        return stopFrequrncy;
    }

    public int getPoints() {
        return points;
    }

    public int getRatePerHz() {
        return ratePerHz;
    }

    public String getDescription() {
        return description;
    }

    public double getFrequency(int point) {
        double baseFrequency = (Math.log10(stopFrequrncy) - Math.log10(startFrequency)) / points;
        return Math.pow(10, Math.log10(startFrequency) + (baseFrequency * point));
    }

    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "generateDevice=" + generateDevice + ", generateChannel=" + generateChannel + ", responseDevice=" + responseDevice + ", responseChannel=" + responseChannel + ", voltage=" + voltage + ", startFrequency=" + startFrequency + ", stopFrequrncy=" + stopFrequrncy + ", points=" + points + ", ratePerHz=" + ratePerHz + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.generateDevice);
        hash = 41 * hash + Objects.hashCode(this.generateChannel);
        hash = 41 * hash + Objects.hashCode(this.responseDevice);
        hash = 41 * hash + Objects.hashCode(this.responseChannel);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.voltage) ^ (Double.doubleToLongBits(this.voltage) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.startFrequency) ^ (Double.doubleToLongBits(this.startFrequency) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.stopFrequrncy) ^ (Double.doubleToLongBits(this.stopFrequrncy) >>> 32));
        hash = 41 * hash + this.points;
        hash = 41 * hash + this.ratePerHz;
        hash = 41 * hash + Objects.hashCode(this.description);
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
        if (!Objects.equals(this.generateChannel, other.generateChannel)) {
            return false;
        }
        if (!Objects.equals(this.responseDevice, other.responseDevice)) {
            return false;
        }
        if (!Objects.equals(this.responseChannel, other.responseChannel)) {
            return false;
        }
        if (Double.doubleToLongBits(this.voltage) != Double.doubleToLongBits(other.voltage)) {
            return false;
        }
        if (Double.doubleToLongBits(this.startFrequency) != Double.doubleToLongBits(other.startFrequency)) {
            return false;
        }
        if (Double.doubleToLongBits(this.stopFrequrncy) != Double.doubleToLongBits(other.stopFrequrncy)) {
            return false;
        }
        if (this.points != other.points) {
            return false;
        }
        if (this.ratePerHz != other.ratePerHz) {
            return false;
        }
        return true;
    }

}
