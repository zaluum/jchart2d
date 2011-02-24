/*
 * FixedViewportDisplayTest2.java,  junit human display test.
 * Copyright (C) 2006  Achim Westermann, Achim.Westermann@gmx.de
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  If you modify or optimize the code in a useful way please let me know.
 *  Achim.Westermann@gmx.de
 */
package info.monitorenter.gui.chart.demo;

import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.demos.StaticCollectorChart;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterDisc;
import info.monitorenter.gui.chart.traces.painters.TracePainterLine;
import info.monitorenter.util.Range;

import java.awt.Color;
import java.io.IOException;


/**
 * Test implementation that uses a chart with a {@link RangePolicyFixedViewport}
 * with a range from 0 to 100 for the x axis and a range from -40 to 40 for the
 * y axis.
 * <p>
 * A {@link Trace2DSimple} that has a painter for discs and a painter for lines
 * (in that order) is used.
 * <p>
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 * 
 * @version $Revision: 1.7 $
 * 
 */
public class FixedViewportDisplayTest2 extends AbstractDisplayTest {

  /**
   * Returns a {@link Trace2DSimple} that has a painter for discs and a painter
   * for lines (in that order).
   * <p>
   * 
   * @return a {@link Trace2DSimple} that has a painter for discs and a painter
   *         for lines (in that order).
   * 
   * @see info.monitorenter.gui.chart.demo.AbstractDisplayTest#createTrace()
   */
  protected ITrace2D createTrace() {
    ITrace2D result = new Trace2DSimple();
    result.setTracePainter(new TracePainterDisc());
    result.addTracePainter(new TracePainterLine());
    result.setColor(Color.RED);
    return result;
  }

  /**
   * Sets up a {@link RangePolicyFixedViewport} with a range from 0 to 100 for
   * the x axis and a range from -40 to 40 for the y axis.
   * <p>
   * 
   * @see info.monitorenter.gui.chart.demo.AbstractDisplayTest#configure(info.monitorenter.gui.chart.demo.StaticCollectorChart)
   */
  protected void configure(final StaticCollectorChart chart) {
    IAxis axis = chart.getChart().getAxisX();
    axis.setRangePolicy(new RangePolicyFixedViewport(new Range(0, 100)));
    axis = chart.getChart().getAxisY();
    axis.setRangePolicy(new RangePolicyFixedViewport(new Range(-40, 40)));
  }

  /**
   * Main debug hook.
   * <p>
   * 
   * @param args
   *          ignored.
   * 
   * @throws IOException
   *           if something goes wrong reading data files.
   * 
   * @throws InterruptedException
   *           if sleeping fails.
   */
  public static void main(final String[] args) throws IOException, InterruptedException {
    FixedViewportDisplayTest2 test = new FixedViewportDisplayTest2();
    try {
      test.testDisplay();
    } catch (Throwable f) {
      f.printStackTrace(System.err);
    }
  }
}