/*
 * Copyright (C) 2014 Leo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package edu.sju.ee.eea.core;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import tw.edu.sju.ee.eea.core.data.Wave;
import tw.edu.sju.ee.eea.jni.scope.NIScope;
import tw.edu.sju.ee.eea.jni.scope.NIScopeException;

/**
 *
 * @author Leo
 */
public class MainFrequency {

    public static void main(String[] args) {
        NIScope niScope = null;
        try {
            niScope = new NIScope();
            // Open the NI-SCOPE instrument handle
            niScope.init("PXI1Slot4", false, false);

            // Call auto setup, finds a signal and configures all necessary parameters
            niScope.autoSetup();

            // Get the actual record length and actual sample rate that will be used
            int actualRecordLength = niScope.actualRecordLength();
            double sampleRate = niScope.sampleRate();

            // Read the data (Initiate the acquisition, and fetch the data)
            double waveform[] = new double[actualRecordLength * 2];
            NIScope.WFMInfo wfmInfo[] = new NIScope.WFMInfo[2];
            niScope.read("0,1", 5, actualRecordLength, waveform, wfmInfo);

            System.out.println("Actual record length: " + actualRecordLength);
            System.out.println("Actual sample rate: " + sampleRate);

            double[] input = Arrays.copyOfRange(waveform, 0, actualRecordLength);
            double[] output = Arrays.copyOfRange(waveform, actualRecordLength, actualRecordLength * 2);

            System.out.println(Arrays.toString(input));

            int length = (int) Math.pow(2, Math.floor(Math.log10(actualRecordLength) / Math.log10(2)));;

            Wave waveIn = new Wave(sampleRate, Arrays.copyOf(input, 2048));
            
            System.out.println(waveIn);
            System.out.println(waveIn.getValue(500).abs()/1024);

        } catch (NIScopeException ex) {
            Logger.getLogger(MainFrequency.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            niScope.close();
            niScope = null;
        }
    }
}
