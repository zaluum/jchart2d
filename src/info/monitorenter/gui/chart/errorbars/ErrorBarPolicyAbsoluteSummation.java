/*
 *  ErrorBarPolicyRelative.java of project jchart2d, configurable 
 *  info.monitorenter.gui.chart.IErrorBarPolicy that adds an 
 *  absolute error to the points to render.
 *  Copyright (c) 2007 Achim Westermann, created on 10.08.2006 19:37:54.
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
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA*
 *  If you modify or optimize the code in a useful way please let me know.
 *  Achim.Westermann@gmx.de
 *
 */
package info.monitorenter.gui.chart.errorbars;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Configurable <code>{@link info.monitorenter.gui.chart.IErrorBarPolicy}</code>
 * that adds an absolute error (relative to the absolute values) to the points
 * to render.
 * <p>
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann</a>
 * 
 * 
 * @version $Revision: 1.4 $
 */
public class ErrorBarPolicyAbsoluteSummation
    extends AErrorBarPolicyConfigurable {

  /** The absolute x error to add. */
  private double m_xError = 4;

  /** The absolute y error to add. */
  private double m_yError = 4;

  /**
   * Creates an instance with the given absolute errors to add in x and y
   * direction.
   * <p>
   * 
   * The absolute errors are added to / subtracted from the absolut x and y
   * values to render.
   * <p>
   * 
   * @param xError
   *          a positive value that is added to / subtracted from the x value to
   *          render.
   * 
   * @param yError
   *          a positive value that is added to / subtracted from the y value to
   *          render.
   * 
   * @throws IllegalArgumentException
   *           if arguments are < 0.
   */
  public ErrorBarPolicyAbsoluteSummation(final double xError, final double yError)
      throws IllegalArgumentException {
    this.setXError(xError);
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#getCustomConfigurator()
   */
  public JComponent getCustomConfigurator() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createEtchedBorder());

    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(2, 2, 2, 2);

    JLabel xErrorLable = new JLabel("Absolute X error ");

    panel.add(xErrorLable, gbc);

    SpinnerModel numberXModel = new SpinnerNumberModel(ErrorBarPolicyAbsoluteSummation.this
        .getXError(), 0, 10000, 10);
    JSpinner xErrorSelector = new JSpinner(numberXModel);

    gbc.gridx = 1;
    gbc.weightx = 0.5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(xErrorSelector, gbc);

    // fill a dummy component that may be resized:
    gbc.gridx = 2;
    gbc.gridheight = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 0.5;
    panel.add(Box.createHorizontalGlue(), gbc);
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;

    JLabel yErrorLable = new JLabel("Absolute Y error ");

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridheight = 1;
    
    panel.add(yErrorLable, gbc);

    SpinnerModel numberYModel = new SpinnerNumberModel(ErrorBarPolicyAbsoluteSummation.this
        .getYError(), 0, 10000, 10);
    JSpinner yErrorSelector = new JSpinner(numberYModel);

    gbc.gridx = 1;
    gbc.weightx = 0.5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(yErrorSelector, gbc);

    // actions:
    xErrorSelector.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        Number number = model.getNumber();
        ErrorBarPolicyAbsoluteSummation.this.setXError(number.doubleValue());
      }
    });

    yErrorSelector.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        Number number = model.getNumber();
        ErrorBarPolicyAbsoluteSummation.this.setYError(number.doubleValue());
      }
    });

    return panel;
  }

  /**
   * Returns the absolute x error that is added to / subtracted from the values
   * to display.
   * <p>
   * 
   * @return the absolute x error that is added to / subtracted from the values
   *         to display.
   */
  public final double getXError() {
    return this.m_xError;
  }

  /**
   * Returns the absolute y error that is added to / subtracted from the values
   * to display.
   * <p>
   * 
   * @return the absolute y error that is added to / subtracted from the values
   *         to display.
   */
  public final double getYError() {
    return this.m_yError;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetNegativeXError(double,
   *      double)
   */
  protected double internalGetNegativeXError(final double absoluteX, final double absoluteY) {
    return absoluteX - this.m_xError;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetNegativeYError(double,
   *      double)
   */
  protected double internalGetNegativeYError(final double absoluteX, final double absoluteY) {
    return absoluteY - this.m_yError;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetPositiveXError(double,
   *      double)
   */
  protected double internalGetPositiveXError(final double absoluteX, final double absoluteY) {
    return this.m_xError + absoluteX;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetPositiveYError(double,
   *      double)
   */
  protected double internalGetPositiveYError(final double absoluteX, final double absoluteY) {
    return this.m_yError + absoluteY;
  }

  /**
   * Sets the absolute x error to add to each error bar.
   * <p>
   * 
   * The absolute error is added to the absolut x values to render. It has to be >
   * 0.
   * <p>
   * 
   * @param xError
   *          a positive value.
   * 
   * @throws IllegalArgumentException
   *           if the argument is < 0.
   */
  public final void setXError(final double xError) throws IllegalArgumentException {
    if (xError < 0.0) {
      throw new IllegalArgumentException("Given absolute error (" + xError + ")has to be > 0.");
    }
    boolean change = this.m_xError != xError;
    if (change) {
      this.m_xError = xError;
      this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
    }
  }

  /**
   * Sets the absolute y error to add to each error bar.
   * <p>
   * 
   * The absolute error is added to the absolut y values to render. It has to be >
   * 0.
   * <p>
   * 
   * @param yError
   *          a positive value.
   * 
   * @throws IllegalArgumentException
   *           if the argument is < 0.
   */
  public final void setYError(final double yError) throws IllegalArgumentException {
    if (yError < 0.0) {
      throw new IllegalArgumentException("Given absolute error (" + yError + ")has to be > 0.");
    }
    boolean change = this.m_yError != yError;
    if (change) {
      this.m_yError = yError;
      this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
    }
  }
}