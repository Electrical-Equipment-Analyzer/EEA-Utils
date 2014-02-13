/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.edu.sju.ee.eea.core.data;

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
public class EEAFile implements Serializable {

    public void write(ObjectOutputStream oo) {
        try {
            oo.writeObject(this);
            oo.flush();
            oo.close();
        } catch (IOException ex) {
            Logger.getLogger(EEAFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static EEAFile read(ObjectInputStream oi) {
        try {
            Object readObject = oi.readObject();
            oi.close();
            if (readObject instanceof EEAFile) {
                return (EEAFile) readObject;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EEAFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EEAFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
