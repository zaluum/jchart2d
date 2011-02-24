/*
 *
 *  AdvancedStaticChart  jchart2d
 *  Copyright (C) Achim Westermann, created on 10.12.2004, 13:48:55
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 * 
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  If you modify or optimize the code in a useful way please let me know.
 *  Achim.Westermann@gmx.de
 *
 */
package aw.gui.chart.demo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;

import aw.gui.chart.Axis;
import aw.gui.chart.Chart2D;
import aw.gui.chart.ITrace2D;
import aw.gui.chart.LabelFormatterDate;
import aw.gui.chart.Trace2DSimple;

/**
 * Demonstrates advanced features of static charts in jchart2d.
 * <p>
 *
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 *
 */
public final class AdvancedStaticChart {

  /**
   * Application startup hook.
   * <p>
   *
   * @param args
   *          ignored
   *
   * @throws ParseException
   *           if sth. goes wrong.
   *
   */
  public static void main(final String[] args) throws ParseException {
    // Create a chart:
    Chart2D chart = new Chart2D();
    // Create an ITrace:
    ITrace2D trace = new Trace2DSimple();
    Axis yAxis = new Axis();
    yAxis.setFormatter(new LabelFormatterDate(new SimpleDateFormat()));
    chart.setAxisY(yAxis);
    // Add all points, as it is static:
    double high = DateFormat.getInstance().parse("01.08.05 18:00").getTime();
    for (double i = 0; i < 200; i++) {
      trace.addPoint(i, high);
      high += 1000 * 50;

    }
    // Add the trace to the chart:
    chart.addTrace(trace);

    // Make it visible:
    // Create a frame.
    JFrame frame = new JFrame("AdvancedStaticChart");
    // add the chart to the frame:
    frame.getContentPane().add(chart);
    frame.setSize(600, 600);
    // Enable the termination button [cross on the upper right edge]:
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(final WindowEvent e) {
        System.exit(0);
      }
    });
    frame.show();
  }

  /**
   * Utility constructor.
   * <p>
   */
  private AdvancedStaticChart() {
    super();
  }
}
