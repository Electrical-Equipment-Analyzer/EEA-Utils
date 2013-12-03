
import edu.sju.ee.ni.math.WaveGenerator;
import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Leo
 */
public class FFT {

    public static void main(String[] args) {
        int length = 1024;
        double frequency = 1000;
        double rate = 100 * frequency;
        double position = rate / frequency / 360 * 0;
        WaveGenerator wave = new WaveGenerator(rate, length, 1, frequency, position);

        System.out.println(Arrays.toString(wave.getData()));
        System.out.println(max(wave.getData()));

        Complex[] transform = transform(wave.getData(), TransformType.FORWARD);
        System.out.println(Arrays.toString(transform));

        int mainF = (int) (frequency * length / rate);
        Complex mainFre = transform[mainF];
        System.out.println(mainFre.toString());
        System.out.println("abs = " + mainFre.abs() / length * 2);
        System.out.println("arg = " + (mainFre.getArgument() / (2 * Math.PI) * 360 + 90));

        double[] absoluteArray = getAbsoluteArray(transform);
        System.out.println(max(absoluteArray));
        double[] argumentArray = getArgumentArray(transform);
        System.out.println(max(argumentArray));

    }

    public static double max(double[] array) {
        double max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static Complex[] transform(Object input, TransformType type) {
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        try {
            Complex[] complx = null;
            if (input instanceof double[]) {
                complx = transformer.transform((double[]) input, type);
            } else if (input instanceof Complex[]) {
                complx = transformer.transform((Complex[]) input, type);
            }
            return complx;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return null;
    }

    protected static double[] getRealArray(Complex[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getReal();
        }
        return temp;
    }

    protected static double[] getImaginaryArray(Complex[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getImaginary();
        }
        return temp;
    }

    protected static double[] getAbsoluteArray(Complex[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].abs();
        }
        return temp;
    }

    protected static double[] getArgumentArray(Complex[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].getArgument();
        }
        return temp;
    }
}
