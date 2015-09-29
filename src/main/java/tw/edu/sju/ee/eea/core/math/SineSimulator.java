/*
 * Copyright (C) 2015 D10307009
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
package tw.edu.sju.ee.eea.core.math;

import java.util.Calendar;

/**
 *
 * @author D10307009
 */
public class SineSimulator {

    private double samplerate;
    private double frequency;
    private double amp;
    private long timeInMillis = Calendar.getInstance().getTimeInMillis();
    private int index;

    public SineSimulator(double samplerate, double frequency, double amp) {
        this.samplerate = samplerate;
        this.frequency = frequency;
        this.amp = amp;
    }

    public double getX(int index) {
        return index / samplerate;
    }
    
    public double getY() {
        return getY(index++);
    }

    public double getY(int index) {
        double sin = Math.sin(frequency * 2 * Math.PI * (timeInMillis + index) / samplerate);
        return sin * amp;
    }
}
