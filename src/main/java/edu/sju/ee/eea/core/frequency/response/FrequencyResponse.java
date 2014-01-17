/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package edu.sju.ee.eea.core.frequency.response;

import edu.sju.ee.eea.core.data.Wave;
import edu.sju.ee.eea.jni.daqmx.DAQmx;
import edu.sju.ee.eea.math.WaveGenerator;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author 102m05008
 */
public class FrequencyResponse {

    private FrequencyResponseConfig config;
    //
    private double baseFrequency;
    private int generateLength = 1000;

    public FrequencyResponse(FrequencyResponseConfig config) {
        this.config = config;

        this.baseFrequency = (Math.log10(this.config.getMaxFrequrncy()) - Math.log10(this.config.getMinFrequency())) / this.config.getLength();
    }

    private double getFrequency(int step) {
        return Math.pow(10, Math.log10(this.config.getMinFrequency()) + (baseFrequency * step));
    }

    public DAQmx createGenerate(double frequency) throws Exception {
        WaveGenerator analogGenerator = new WaveGenerator(frequency * 20, generateLength, this.config.getVoltage(), frequency);
        DAQmx generate = new DAQmx();
        generate.createTask("");
        generate.createAOVoltageChan(this.config.getGenerateChannel(), "", -10, 10, DAQmx.Val_Volts, null);
        generate.cfgSampClkTiming("", frequency * 20, DAQmx.Val_Rising, DAQmx.Val_ContSamps, generateLength);
        generate.writeAnalogF64(generateLength, false, 10.0, DAQmx.Val_GroupByChannel, analogGenerator.getData());
        return generate;
    }

    public Response createResponse(double frequency) throws Exception {
        return new Response(frequency);
    }

//    public int getLength() {
//        return this.config.getLength();
//    }
    @Override
    public String toString() {
        return "FrequencyResponse{" + "config=" + config + ", baseFrequency=" + baseFrequency + ", generateLength=" + generateLength + '}';
    }

    public FrequencyResponseFile process() {
        Complex[] input = new Complex[this.config.getLength()];
        Complex[] output = new Complex[this.config.getLength()];
        DAQmx generate;
        Response response;
        for (int i = 0; i < this.config.getLength(); i++) {
            try {
                double frequency = this.getFrequency(i);
                generate = this.createGenerate(frequency);
                generate.startTask();
//                Thread.sleep(10);
                response = this.createResponse(frequency);
                input[i] = response.input;
                output[i] = response.output;
                generate.stopTask();
                generate.clearTask();
            } catch (Exception ex) {
                Logger.getLogger(FrequencyResponse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new FrequencyResponseFile(config, input, output);
    }

    private class Response {

        private final Complex input;
        private final Complex output;

        public Response(double frequency) throws Exception {
            DAQmx task = new DAQmx();
            task.createTask("");
            task.createAIVoltageChan(config.getResponseChannel(), "", DAQmx.Val_Cfg_Default, -10.0, 10.0, DAQmx.Val_Volts, null);
            task.cfgSampClkTiming("", frequency * 20, DAQmx.Val_Rising, DAQmx.Val_FiniteSamps, 1024);
            task.startTask();
            double data[] = new double[2048];
            task.readAnalogF64(1024, 10, DAQmx.Val_GroupByChannel, data, data.length, null);
            input = new Wave(frequency * 20, Arrays.copyOfRange(data, 0, 1024)).getValue(frequency);
            output = new Wave(frequency * 20, Arrays.copyOfRange(data, 1024, 2048)).getValue(frequency);
            task.stopTask();
            task.clearTask();
        }

        public Complex getInput() {
            return input;
        }

        public Complex getOutput() {
            return output;
        }

    }
}
