/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Leo
 */
public class Log {

    private double minFrequency;
    private double maxFrequrncy;
    private double baseFrequency;
    private int length;

    public Log(double minFrequency, double maxFrequrncy, int length) {
        this.minFrequency = Math.log10(minFrequency);
        this.maxFrequrncy = Math.log10(maxFrequrncy);
        this.length = length;
        this.baseFrequency = (this.maxFrequrncy - this.minFrequency) / length;
    }

    public static void main(String[] args) {
        Log log = new Log(100, 100000, 10);
        System.out.println(log);
        System.out.println(log.getFrequency(1));
//        int from = 100;
//        int to = 10000000;
//        double frequency[] = new double[100];
//
//        double min = Math.log10(from);
//        double max = Math.log10(to);
//        double cc = (max - min) / frequency.length;
//
//        for (int i = 0; i < frequency.length; i++) {
//            System.out.println(Math.pow(10, min + (cc * i)));
//        }
    }

    public double getFrequency(int step) {
        System.out.println(Math.pow(10, minFrequency));
        return Math.pow(10, minFrequency + (baseFrequency * step));
    }

    @Override
    public String toString() {
        return "Log{" + "minFrequency=" + minFrequency + ", maxFrequrncy=" + maxFrequrncy + ", baseFrequency=" + baseFrequency + ", length=" + length + '}';
    }

}
