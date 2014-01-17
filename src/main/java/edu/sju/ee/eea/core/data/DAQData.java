/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee.eea.core.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class DAQData implements Serializable {

    public void save(File file) {
        try {
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(file));
            oo.writeObject(this);
            oo.flush();
            oo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAQData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DAQData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DAQData open(File file) {
        try {
            ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file));
            Object readObject = oi.readObject();
            oi.close();
            if (readObject instanceof DAQData) {
                return (DAQData) readObject;
            }
        } catch (IOException ex) {
            Logger.getLogger(DAQData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAQData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
