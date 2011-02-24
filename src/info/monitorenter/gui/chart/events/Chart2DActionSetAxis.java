/*
 *  Chart2DActionSetAxis, action for setting an axis implementation of the chart.
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
package info.monitorenter.gui.chart.events;

import info.monitorenter.gui.chart.AAxis;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.layout.LayoutFactory.PropertyChangeCheckBoxMenuItem;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

/**
 * <code>Action</code> for setting an axis implementation of the chart.
 * <p>
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 * 
 * @version $Revision: 1.2 $
 */
public final class Chart2DActionSetAxis extends AChart2DAction {

  /**
   * Identifies where to set the axis on the chart. Either {@link Chart2D#X} or
   * {@link Chart2D#Y}.
   * <p>
   */
  private final int m_axisTarget;

  /** The axis implementation to use. */
  private final AAxis m_axis;

  /**
   * Create an <code>Action</code> that accesses the trace and identifies
   * itself with the given action String.
   * <p>
   * 
   * @param chart
   *          the target the action will work on.
   * 
   * @param axis
   *          the axis implementation to use.
   * 
   * @param description
   *          the description of this action to show in the UI.
   * 
   * @param axisTarget
   *          Identifies where to set the axis on the chart: either
   *          {@link Chart2D#X} or {@link Chart2D#Y}
   * 
   * @throws IllegalArgumentException
   *           if the axis argument is invalid.
   */
  public Chart2DActionSetAxis(final Chart2D chart, final AAxis axis, final String description,
      final int axisTarget) throws IllegalArgumentException {
    super(chart, description);
    if (axisTarget != Chart2D.X && axisTarget != Chart2D.Y) {
      throw new IllegalArgumentException(
          "Argument axisTarget is invalid, choose one of Chart2D.X, Chart2D.Y.");
    }
    this.m_axisTarget = axisTarget;
    this.m_axis = axis;
    chart.addPropertyChangeListener(Chart2D.PROPERTY_AXIS_X, this);
    chart.addPropertyChangeListener(Chart2D.PROPERTY_AXIS_Y, this);
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    switch (this.m_axisTarget) {
      case Chart2D.X:
        this.m_chart.setAxisX(this.m_axis);
        break;

      case Chart2D.Y:
        this.m_chart.setAxisY(this.m_axis);
        break;

      default:
        // nop
        break;

    }
  }

  /**
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  public void propertyChange(final PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(Chart2D.PROPERTY_AXIS_X) && this.m_axisTarget == Chart2D.X) {

      Class axisClass = evt.getNewValue().getClass();
      if (this.m_axis.getClass() == axisClass) {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(false), new Boolean(true));
      } else {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(true), new Boolean(false));
      }
    } else if (evt.getPropertyName().equals(Chart2D.PROPERTY_AXIS_Y)
        && this.m_axisTarget == Chart2D.Y) {

      Class axisClass = evt.getNewValue().getClass();
      if (this.m_axis.getClass() == axisClass) {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(false), new Boolean(true));
      } else {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(true), new Boolean(false));
      }
    }

  }

}