
import edu.sju.ee98.daq.core.config.AnalogConfig;
import edu.sju.ee98.ni.daqmx.LoadLibraryException;
import edu.sju.ee98.ni.daqmx.analog.AnalogGenerator;
import edu.sju.ee98.ni.daqmx.analog.ContGenIntClk;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Leo
 */
public class NIAO {

    public static void main(String[] args) {
        double frequency = 1000;
        double rate = frequency * 1000.0;
        int length = (int) (rate * 1.024);
        AnalogConfig ac = new AnalogConfig("Dev1/ao0", -10.0, 10.0, rate, length);
        AnalogGenerator ag = new AnalogGenerator(rate, length, 2, frequency, 0);
        ContGenIntClk contGenIntClk = new ContGenIntClk(ac, ac, ag.getData());
            contGenIntClk.write();
            contGenIntClk.start();
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NIAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}