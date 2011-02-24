/*
 *  ErrorBarPolicyRelative.java of project jchart2d, configurable 
 *  info.monitorenter.gui.chart.IErrorBarPolicy that adds a 
 *  relative error to the points to render.
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
 * that adds a relative error (relative to the absolute values) to the points to
 * render.
 * <p>
 * 
 * You should not use this if you have a small value range but very high values:
 * The relative value will cause exteremely long lines (be much higher than the
 * value range to display) and fold the trace to a minimum line.
 * <p>
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann</a>
 * 
 * 
 * @version $Revision: 1.7 $
 */
public class ErrorBarPolicyRelative
    extends AErrorBarPolicyConfigurable {

  /** The relative x error to render. */
  private double m_relativeXError = 0.02;

  /** The relative y error to render. */
  private double m_relativeYError = 0.02;

  /**
   * Creates an instance with the given relative errors.
   * <p>
   * 
   * The relative error is related to the absolut x and y values to render. It
   * has to be between 0.0 and 1.0.
   * <p>
   * 
   * @param relativeError
   *          the relative error value between 0.0 and 1.0 for x and y
   *          dimension.
   * 
   * @throws IllegalArgumentException
   *           if the argument is not between 0.0 and 1.0.
   * 
   * @see #ErrorBarPolicyRelative(double, double)
   */
  public ErrorBarPolicyRelative(final double relativeError) throws IllegalArgumentException {
    this(relativeError, relativeError);
  }

  /**
   * Creates an instance with the given relative errors.
   * <p>
   * 
   * The relative error is related to the absolut x and y values to render. It
   * has to be between 0.0 and 1.0.
   * <p>
   * 
   * @param relativeXError
   *          the relative x error value between 0.0 and 1.0.
   * 
   * @param relativeYError
   *          the relative y error value between 0.0 and 1.0.
   * 
   * @throws IllegalArgumentException
   *           if the argument is not between 0.0 and 1.0.
   */
  public ErrorBarPolicyRelative(final double relativeXError, final double relativeYError)
      throws IllegalArgumentException {
    this.setRelativeXError(relativeXError);
    this.setRelativeYError(relativeYError);
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

    JLabel xErrorLable = new JLabel("Relative X error (%) ");

    panel.add(xErrorLable, gbc);

    SpinnerModel numberXModel = new SpinnerNumberModel(ErrorBarPolicyRelative.this
        .getRelativeXError() * 100, 0, 100, 1);
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

    JLabel yErrorLable = new JLabel("Relative Y error (%) ");

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridheight = 1;

    panel.add(yErrorLable, gbc);

    SpinnerModel numberYModel = new SpinnerNumberModel(ErrorBarPolicyRelative.this
        .getRelativeYError() * 100, 0, 100, 1);
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
        ErrorBarPolicyRelative.this.setRelativeXError(number.doubleValue() / 100);
      }
    });

    yErrorSelector.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        Number number = model.getNumber();
        ErrorBarPolicyRelative.this.setRelativeYError(number.doubleValue() / 100);
      }
    });

    return panel;
  }

  /**
   * @return the relative x Error.
   */
  public final double getRelativeXError() {
    return this.m_relativeXError;
  }

  /**
   * @return the relative y Error.
   */
  public final double getRelativeYError() {
    return this.m_relativeYError;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetNegativeXError(double,
   *      double)
   */
  protected double internalGetNegativeXError(final double absoluteX, final double absoluteY) {
    return (1.0 - this.m_relativeXError) * absoluteX;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetNegativeYError(double,
   *      double)
   */
  protected double internalGetNegativeYError(final double absoluteX, final double absoluteY) {
    return (1.0 - this.m_relativeYError) * absoluteY;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetPositiveXError(double,
   *      double)
   */
  protected double internalGetPositiveXError(final double absoluteX, final double absoluteY) {
    return (1.0 + this.m_relativeXError) * absoluteX;
  }

  /**
   * @see info.monitorenter.gui.chart.errorbars.AErrorBarPolicyConfigurable#internalGetPositiveYError(double,
   *      double)
   */
  protected double internalGetPositiveYError(final double absoluteX, final double absoluteY) {
    return (1.0 + this.m_relativeYError) * absoluteY;
  }

  /**
   * Sets the relative X error to add to each error bar.
   * <p>
   * 
   * The relative error is related to the absolut x values to render. It has to
   * be between 0.0 and 1.0.
   * <p>
   * 
   * @param relativeXError
   *          a value between 0.0 and 1.0.
   * 
   * @throws IllegalArgumentException
   *           if the argument is not between 0.0 and 1.0.
   */
  public final void setRelativeXError(final double relativeXError) throws IllegalArgumentException {
    if (relativeXError <= 0.0 || relativeXError >= 1.0) {
      throw new IllegalArgumentException("Given relative error (" + relativeXError
          + ")has to be between 0.0 and 1.0.");
    }
    boolean change = this.m_relativeXError != relativeXError;
    if (change) {
      this.m_relativeXError = relativeXError;
      this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
    }
  }

  /**
   * Sets the relative Y error to add to each error bar.
   * <p>
   * 
   * The relative error is related to the absolut y values to render. It has to
   * be between 0.0 and 1.0.
   * <p>
   * 
   * @param relativeYError
   *          a value between 0.0 and 1.0.
   * 
   * @throws IllegalArgumentException
   *           if the argument is not between 0.0 and 1.0.
   */
  public final void setRelativeYError(final double relativeYError) throws IllegalArgumentException {
    if (relativeYError <= 0.0 || relativeYError >= 1.0) {
      throw new IllegalArgumentException("Given relative error (" + relativeYError
          + ")has to be between 0.0 and 1.0.");
    }
    boolean change = this.m_relativeXError != relativeYError;
    if (change) {
      this.m_relativeYError = relativeYError;
      this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
    }
  }
}