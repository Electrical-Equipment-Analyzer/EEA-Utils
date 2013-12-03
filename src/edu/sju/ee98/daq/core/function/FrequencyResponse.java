/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.function;

import edu.sju.ee.ni.daqmx.DAQmx;
import edu.sju.ee98.daq.core.data.Wave;
import edu.sju.ee.ni.math.WaveGenerator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author 102m05008
 */
public class FrequencyResponse implements Serializable {

    private String generateChannel;
    private String responseChannel;
    private double voltage;
//
    private double minFrequency;
    private double maxFrequrncy;
    private double baseFrequency;
    private int length;
    //
    private int generateLength = 1000;

    public FrequencyResponse(String generateChannel, String responseChannel, double voltage, double minFrequency, double maxFrequrncy, int length) {
        this.generateChannel = generateChannel;
        this.responseChannel = responseChannel;
        this.voltage = voltage;

        this.minFrequency = Math.log10(minFrequency);
        this.maxFrequrncy = Math.log10(maxFrequrncy);
        this.length = length;
        this.baseFrequency = (this.maxFrequrncy - this.minFrequency) / length;
    }

    private double getFrequency(int step) {
        return Math.pow(10, minFrequency + (baseFrequency * step));
    }

    public DAQmx createGenerate(double frequency) throws Exception {
        WaveGenerator analogGenerator = new WaveGenerator(frequency * 20, generateLength, this.voltage, frequency);
        DAQmx generate = new DAQmx();
        generate.createTask("");
        generate.createAOVoltageChan(generateChannel, "", -10, 10, DAQmx.Val_Volts, null);
        generate.cfgSampClkTiming("", frequency * 20, DAQmx.Val_Rising, DAQmx.Val_ContSamps, generateLength);
        generate.writeAnalogF64(generateLength, false, 10.0, DAQmx.Val_GroupByChannel, analogGenerator.getData());
        return generate;
    }

    public Response createResponse(double frequency) throws Exception {
        return new Response(frequency);
    }

    public int getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        return "FrequencyResponseConfig{" + "inputChannel=" + responseChannel + ", outputChannel=" + generateChannel + ", voltage=" + voltage + ", minFrequency=" + minFrequency + ", maxFrequrncy=" + maxFrequrncy + ", baseFrequency=" + baseFrequency + ", length=" + length + '}';
    }

    public Complex[] process() {
        Complex[] data = new Complex[this.length];
        DAQmx generate;
        FrequencyResponse.Response createResponse;
        for (int i = 0; i < data.length; i++) {
            try {
                double frequency = this.getFrequency(i);
                generate = this.createGenerate(frequency);
                generate.startTask();
//                Thread.sleep(10);
                createResponse = this.createResponse(frequency);
                Complex H = createResponse.H();
                data[i] = H;
                generate.stopTask();
                generate.clearTask();
            } catch (Exception ex) {
                Logger.getLogger(FrequencyResponse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    private class Response {

        private DAQmx task = new DAQmx();
        private double frequency;

        public Response(double frequency) throws Exception {
            this.frequency = frequency;
            task.createTask("");
            task.createAIVoltageChan(responseChannel, "", DAQmx.Val_Cfg_Default, -10.0, 10.0, DAQmx.Val_Volts, null);
            task.cfgSampClkTiming("", frequency * 20, DAQmx.Val_Rising, DAQmx.Val_FiniteSamps, 1024);
            task.startTask();
        }

        public Complex H() throws Exception {
            double data[] = new double[2048];
            task.readAnalogF64(1024, 10, DAQmx.Val_GroupByChannel, data, data.length, null);
            Complex in = new Wave(frequency * 20, Arrays.copyOfRange(data, 0, 1024)).getValue(frequency);
            Complex out = new Wave(frequency * 20, Arrays.copyOfRange(data, 1024, 2048)).getValue(frequency);

            task.stopTask();
            task.clearTask();
//            System.out.println(Arrays.toString(data));
            System.out.println("H=" + out + "/" + out);
            return out.divide(in);
        }

    }
}
