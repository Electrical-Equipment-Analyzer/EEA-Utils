
import edu.sju.ee98.daq.core.config.AnalogInputConfig;
import edu.sju.ee98.ni.daqmx.LoadLibraryException;
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
public class FRA {

    public static void main(String[] args) {
        AnalogInputConfig config = new AnalogInputConfig("Dev1/ao0", -10.0, 10.0, 1000.0, 1000);

        double[] data = new double[(int) config.getLength()];
        for (int x = 0; x < data.length; x++) {
            data[x] = Math.sin(x / config.getRate() * 2 * Math.PI) * 2;
        }

        ContGenIntClk contGenIntClk = new ContGenIntClk(config, config, data);
            contGenIntClk.write();

    }
}
