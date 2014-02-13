/*
 * Copyright (c) 2013, St. John's University and/or its affiliates. All rights reserved.
 * Department of Electrical Engineering.
 */
package tw.edu.sju.ee.eea.core.frequency.response;

import tw.edu.sju.ee.eea.core.data.Wave;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;
import tw.edu.sju.ee.eea.jni.fgen.NIFgen;
import tw.edu.sju.ee.eea.jni.fgen.NIFgenException;
import tw.edu.sju.ee.eea.jni.scope.NIScope;
import tw.edu.sju.ee.eea.jni.scope.NIScopeException;

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

    public NIFgen createGenerate(double frequency) throws NIFgenException {
        NIFgen niFgen = new NIFgen();
        niFgen.init(this.config.getGenerateDevice(), true, true);
        niFgen.configureChannels("0");
        niFgen.configureOutputMode(NIFgen.VAL_OUTPUT_FUNC);
        niFgen.configureStandardWaveform("0", NIFgen.VAL_WFM_SINE, this.config.getVoltage(), 0, frequency, 0);
        return niFgen;
    }

    public Response createResponse(double frequency) throws NIScopeException {
        return new Response(frequency);
    }

//    public int getLength() {
//        return this.config.getLength();
//    }
    @Override
    public String toString() {
        return "FrequencyResponse{" + "config=" + config + ", baseFrequency=" + baseFrequency + ", generateLength=" + generateLength + '}';
    }

    public FrequencyResponseFile process() throws NIFgenException, NIScopeException {
        Complex[] input = new Complex[this.config.getLength()];
        Complex[] output = new Complex[this.config.getLength()];
        NIFgen niFgen = null;
        Response response = null;
        for (int i = 0; i < this.config.getLength(); i++) {
            try {
                double frequency = this.getFrequency(i);
                niFgen = this.createGenerate(frequency);
                niFgen.initiateGeneration();
//                Thread.sleep(100);
                response = this.createResponse(frequency);
                niFgen.abortGeneration();
                input[i] = response.input;
                output[i] = response.output;
            } catch (NIFgenException | NIScopeException ex) {
                Logger.getLogger(FrequencyResponse.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(FrequencyResponse.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                niFgen.close();
                niFgen = null;
            }
        }
        return new FrequencyResponseFile(config, input, output);
    }

    private class Response {

        private final Complex input;
        private final Complex output;

        public Response(double frequency) throws NIScopeException {
            String channelList = "0,1";
            NIScope niScope = null;
            try {
                niScope = new NIScope();
                niScope.init(config.getResponseDevice(), false, false);
                niScope.configureAcquisition(NIScope.VAL_NORMAL);
                niScope.configureVertical(channelList, 10, 0, NIScope.VAL_DC, 1, true);
                niScope.configureChanCharacteristics(channelList, NIScope.VAL_1_MEG_OHM, 0);
                niScope.configureHorizontalTiming(frequency * config.getRateMultiple(), 1024, 50.0, 1, true);
                niScope.setAttributeViBoolean(channelList, NIScope.ATTR_ENABLE_TIME_INTERLEAVED_SAMPLING, false);
                niScope.configureTriggerImmediate();
                niScope.initiateAcquisition();

                int actualNumWfms = niScope.actualNumWfms(channelList);
                int actualRecordLength = niScope.actualRecordLength();
                NIScope.WFMInfo wfmInfo[] = new NIScope.WFMInfo[actualNumWfms];
                double waveform[] = new double[actualRecordLength * actualNumWfms];

                niScope.fetch(channelList, 5, actualRecordLength, waveform, wfmInfo);
                double sampleRate = niScope.sampleRate();

                Wave waveIn = new Wave(sampleRate, Arrays.copyOfRange(waveform, 0, 1024));
                Wave waveOut = new Wave(sampleRate, Arrays.copyOfRange(waveform, actualRecordLength, actualRecordLength +1024));

                input = waveIn.getValue(frequency);
                output = waveOut.getValue(frequency);
            } catch (NIScopeException ex) {
                Logger.getLogger(FrequencyResponse.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                niScope.close();
            }
            niScope = null;
        }

        public Complex getInput() {
            return input;
        }

        public Complex getOutput() {
            return output;
        }

    }
}
