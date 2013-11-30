/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee98.daq.core.config;

import edu.sju.ee.ni.daqmx.DAQmx;
import edu.sju.ee98.daq.core.data.ComplexWave;
import edu.sju.ee98.ni.daqmx.analog.AcqIntClk;
import edu.sju.ee98.ni.daqmx.analog.ContGenIntClk;
import edu.sju.ee98.ni.daqmx.analog.AnalogGenerator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author 102m05008
 */
public class FrequencyResponseConfig implements Serializable {

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
    
    public FrequencyResponseConfig(String generateChannel, String responseChannel, double voltage, double minFrequency, double maxFrequrncy, int length) {
        this.generateChannel = generateChannel;
        this.responseChannel = responseChannel;
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
        AnalogGenerator analogGenerator = new AnalogGenerator(frequency * 100, generateLength, this.voltage, frequency);
        AnalogConfig outputConfig = getOutputConfig(frequency);
//        System.out.println(outputConfig);
        return new ContGenIntClk(outputConfig, outputConfig, analogGenerator.getData());
    }
    
    public FrequencyResponse createResponse(double frequency) throws Exception {
        return new FrequencyResponse(frequency);
    }

    public AnalogConfig getOutputConfig(double frequency) {
        return new AnalogConfig(generateChannel, -10, 10, frequency * 100, generateLength);
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
        ContGenIntClk out;
        FrequencyResponseConfig.FrequencyResponse createResponse;
        for (int i = 0; i < data.length; i++) {
            try {
                double frequency = this.getFrequency(i);
                out = this.createOutput(frequency);
                out.write();
                out.start();
                createResponse = this.createResponse(frequency);
                Complex H = createResponse.H();
                data[i] = H;
                out.stop();
            } catch (Exception ex) {
                Logger.getLogger(FrequencyResponseConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public class FrequencyResponse {

        private DAQmx task = new DAQmx();
        private double frequency;

        public FrequencyResponse(double frequency) throws Exception {
            this.frequency = frequency;
            task.createTask("");
            task.createAIVoltageChan(responseChannel, "", DAQmx.Val_Cfg_Default, -42.0, 42.0, DAQmx.Val_Volts, null);
            task.cfgSampClkTiming("", frequency * 100, DAQmx.Val_Rising, DAQmx.Val_FiniteSamps, 1024);
            task.startTask();
        }

        public Complex H() throws Exception {
            double data[] = new double[2048];
            task.readAnalogF64(1024, 10, DAQmx.Val_GroupByChannel, data, data.length, null);
            Complex in = new ComplexWave(frequency * 100, Arrays.copyOfRange(data, 0, 1024)).getMainFrequency(frequency);
            Complex out = new ComplexWave(frequency * 100, Arrays.copyOfRange(data, 1024, 2048)).getMainFrequency(frequency);

            task.stopTask();
            task.clearTask();

            return out.divide(in);
        }

    }
}
