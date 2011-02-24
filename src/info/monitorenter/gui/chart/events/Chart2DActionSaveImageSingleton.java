/*
 *  Chart2DActionSaveImageSingleton, 
 *  singleton action that sets a custom grid color to the chart.
 *  Copyright (C) Achim Westermann, created on 05.06.2006, 11:06:55
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

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.io.FileFilterExtensions;
import info.monitorenter.gui.chart.layout.LayoutFactory.PropertyChangeCheckBoxMenuItem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Singleton <code>Action</code> that saves the current chart to an image at
 * the location specified by showing a modal file chooser save dialog.
 * <p>
 * Only one instance per target component may exist.
 * <p>
 * 
 * @see info.monitorenter.gui.chart.events.Chart2DActionSetCustomGridColor
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 * 
 * @version $Revision: 1.1 $
 */
public final class Chart2DActionSaveImageSingleton
    extends AChart2DAction {
  /**
   * Generated <code>serial version UID</code>.
   * <p>
   * 
   */
  private static final long serialVersionUID = -2800571545563022874L;

  /**
   * The <code>JFileChooser</code> used to choose the location for saving
   * snapshot images.
   * <p>
   */
  private JFileChooser m_filechooser;

  /**
   * Map for instances.
   */
  private static Map instances = new HashMap();

  /** Creates a key for the component for internal storage. */
  private static String key(final Chart2D chart) {
    return chart.getClass().getName() + chart.hashCode();
  }

  /**
   * Returns the single instance for the given component, potentially creating
   * it.
   * <p>
   * 
   * If an instance for the given component had been created the description
   * String is ignored.
   * <p>
   * 
   * @param chart
   *          the target the action will work on
   * @param colorName
   *          the descriptive <code>String</code> that will be displayed by
   *          {@link javax.swing.AbstractButton} subclasses that get this
   *          <code>Action</code> assigned (
   *          {@link javax.swing.AbstractButton#setAction(javax.swing.Action)}).
   * 
   * @return the single instance for the given component.
   */
  public static Chart2DActionSaveImageSingleton getInstance(final Chart2D chart,
      final String colorName) {
    Chart2DActionSaveImageSingleton result = (Chart2DActionSaveImageSingleton) Chart2DActionSaveImageSingleton.instances
        .get(key(chart));
    if (result == null) {
      result = new Chart2DActionSaveImageSingleton(chart, colorName);
      Chart2DActionSaveImageSingleton.instances.put(key(chart), result);
    }
    return result;
  }

  /**
   * Reference to the last custom color chosen to check wether the corresponding
   * menu is selected.
   */
  private Color m_lastChosenColor;

  /**
   * Create an <code>Action</code> that accesses the trace and identifies
   * itself with the given action String.
   * <p>
   * 
   * @param chart
   *          the target the action will work on
   * @param colorName
   *          the descriptive <code>String</code> that will be displayed by
   *          {@link javax.swing.AbstractButton} subclasses that get this
   *          <code>Action</code> assigned (
   *          {@link javax.swing.AbstractButton#setAction(javax.swing.Action)}).
   */
  private Chart2DActionSaveImageSingleton(final Chart2D chart, final String colorName) {
    super(chart, colorName);
    chart.addPropertyChangeListener(Chart2D.PROPERTY_GRID_COLOR, this);
    // configure the file chooser:
    this.m_filechooser = new JFileChooser();
    this.m_filechooser.setAcceptAllFileFilterUsed(false);
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    // Immediately get the image:
    BufferedImage img = this.m_chart.snapShot();
    // clear file filters (uncool API)

    FileFilter[] farr = this.m_filechooser.getChoosableFileFilters();
    for (int i = 0; i < farr.length; i++) {
      this.m_filechooser.removeChoosableFileFilter(farr[i]);
    }
    // collect capable writers by format name (API even gets worse!)
    String[] encodings = ImageIO.getWriterFormatNames();
    Set writers = new TreeSet();
    ImageTypeSpecifier spec = ImageTypeSpecifier.createFromRenderedImage(img);
    Iterator itWriters;
    for (int i = 0; i < encodings.length; i++) {
      itWriters = ImageIO.getImageWriters(spec, encodings[i]);
      if (itWriters.hasNext()) {
        writers.add(encodings[i].toLowerCase());
      }
    }
    // add the file filters:
    itWriters = writers.iterator();
    String encoding;
    while (itWriters.hasNext()) {
      encoding = (String) itWriters.next();
      this.m_filechooser.addChoosableFileFilter(new FileFilterExtensions(new String[] {encoding }));
    }

    int ret = this.m_filechooser.showSaveDialog(this.m_chart);
    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = this.m_filechooser.getSelectedFile();
      // get the encoding
      encoding = this.m_filechooser.getFileFilter().getDescription().substring(2);
      try {
        ImageIO.write(img, encoding, new File(file.getAbsolutePath() + "." + encoding));
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  public void propertyChange(final PropertyChangeEvent evt) {
    String property = evt.getPropertyName();
    if (property.equals(Chart2D.PROPERTY_GRID_COLOR)) {
      Color newColor = (Color) evt.getNewValue();
      if (newColor.equals(this.m_lastChosenColor)) {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(false), new Boolean(true));

      } else {
        this.firePropertyChange(PropertyChangeCheckBoxMenuItem.PROPERTY_SELECTED,
            new Boolean(true), new Boolean(false));
      }
    }
  }
}