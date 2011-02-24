/*
 * RangePolicyHighestValues.java,  <enter purpose here>.
 * Copyright (C) 2006  Achim Westermann, Achim.Westermann@gmx.de
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
 */
package aw.gui.chart;

import aw.util.Range;

/**
 * <p>
 * Range policy implementation that will force a viewport that only shows the
 * highest values that are in the range of maximum - x.
 * </p>
 *
 * @author zoola
 *
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 *
 * @version $Revision: 1.4 $
 *
 */
public final class RangePolicyHighestValues extends AbstractRangePolicy {

  /** The value range for the highest values to show. */
  private double m_highestValueRangeToShow;

  /**
   * Constructor with a range and the value range for the highest values to show
   * only.
   * <p>
   *
   * @param range
   *          unused, maximum bound is always returned.
   *
   * @param highestValueRangeToShow
   *          the value range for the highest values to show.
   */
  public RangePolicyHighestValues(final Range range, final double highestValueRangeToShow) {
    super(range);
    this.m_highestValueRangeToShow = highestValueRangeToShow;
  }

  /**
   * Returns the maximum of the chart always.
   * <p>
   *
   * @return Returns the maximum of the chart always.
   *
   * @param chartMin
   *          ignored.
   *
   * @param chartMax
   *          returned always.
   *
   * @see IRangePolicy#getMax(double, double)
   */
  public double getMax(final double chartMin, final double chartMax) {
    return chartMax;
  }

  /**
   * Returns the maximum of the chart - interal highestValueRangeToShow or
   * chartMin if greater.
   * <p>
   *
   *
   * @param chartMin
   *          used for return value if chartMax - highestValuesToShow is smaller
   *          than this minimum of the chart.
   *
   * @param chartMax
   *          upper bound to compute down to the start of the latest highest
   *          values.
   *
   * @return the maximum of the chart - interal highestValueRangeToShow or
   *         chartMin if greater.
   *
   * @see IRangePolicy#getMin(double, double)
   */
  public double getMin(final double chartMin, final double chartMax) {
    return Math.max(chartMax - this.m_highestValueRangeToShow, chartMin);
  }

}
