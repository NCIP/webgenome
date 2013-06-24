/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Class to assist testing of graphic widgets and
 * plots.  This class enables widgets to be added
 * like a normal <code>PlotPanel</code>.  It contains
 * additional methods for rendering the contents
 * to a raster file (e.g. PNG, JPEG).
 * @author dhall
 *
 */
public final class RasterFileTestPlotPanel extends PlotPanel {
    
    // ============================
    //      Attributes
    // ============================
    
    /**
     * Absolute path to directory where output graphic files
     * will be written.
     */
    private String outputDirPath = null;
    
    /**
     * Width of border that may or may not be drawn around panel
     * when rendered. This property also controls width of crosshairs
     * which also may or may not be drawn.
     */
    private int borderWidth = 2;
    
    /**
     * Color of border that may or may not be drawn around panel
     * when rendered. This property also controls color of crosshairs
     * which also may or may not be drawn.
     */
    private Color borderColor = Color.BLACK;
    
    /** Draw border around panel when rendered? */
    private boolean drawBorder = false;
    
    /** Draw centered crosshairs in panel when rendered? */
    private boolean drawCrossHairs = false;
    
    
    // ============================
    //      Getters/setters
    // ============================
    
    /**
     * Get color of border that may or may not be drawn around panel
     * when rendered. This property also controls color of crosshairs
     * which also may or may not be drawn.
     * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
     * @return A color
     */
    public Color getBorderColor() {
        return borderColor;
    }


    /**
     * Set color of border that may or may not be drawn around panel
     * when rendered. This property also controls color of crosshairs
     * which also may or may not be drawn.
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
     * @param borderColor A color
     */
    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }


    /**
     * Width of border that may or may not be drawn around panel
     * when rendered. This property also controls width of crosshairs
     * which also may or may not be drawn.
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
     * @see 
     * rg.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
     * @return Border width in pixels
     */
    public int getBorderWidth() {
        return borderWidth;
    }


    /**
     * Width of border that may or may not be drawn around panel
     * when rendered. This property also controls width of crosshairs
     * which also may or may not be drawn.
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
     * @see
     * org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
     * @param borderWidth Width of border in pixels
     */
    public void setBorderWidth(final int borderWidth) {
        this.borderWidth = borderWidth;
    }


    /**
     * Should rendered panel include a border around it?
     * @return T/F
     */
    public boolean isDrawBorder() {
        return drawBorder;
    }


    /**
     * Should rendered panel include a border around it?
     * @param drawBorder T/F
     */
    public void setDrawBorder(final boolean drawBorder) {
        this.drawBorder = drawBorder;
    }


    /**
     * Should rendered panel include centered crosshairs?
     * @return T/F
     */
    public boolean isDrawCrossHairs() {
        return drawCrossHairs;
    }


    /**
     * Should rendered panel include centered crosshairs?
     * @param drawCrossHairs T/F
     */
    public void setDrawCrossHairs(final boolean drawCrossHairs) {
        this.drawCrossHairs = drawCrossHairs;
    }


    /**
     * Get absolute path to directory where output graphic files
     * will be written.
     * @return Absolute path to directory where output graphic files
     * will be written
     */
    public String getOutputDirPath() {
        return outputDirPath;
    }


    /**
     * Set absolute path to directory where output graphic files
     * will be written.
     * @param outputDirPath Absolute path to directory where
     * output graphic files will be written.
     */
    public void setOutputDirPath(final String outputDirPath) {
        this.outputDirPath = outputDirPath;
    }
    
    
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * Constructor.
     */
    public RasterFileTestPlotPanel() {
        
        super(new RasterDrawingCanvas());
        
        // Set test directory path to
        // temporary unit test directory
        // that should be configured in the
        // file 'unit_test.properties'
        this.outputDirPath = UnitTestUtils.getUnitTestProperty("temp.dir");
        if (this.outputDirPath == null) {
            throw new WebGenomeSystemException(
                    "Unit test property 'temp.dir' is not defined");
        }
    }
    
    
    /**
     * Constructor.
     * @param outputDirPath Absolute path to directory where
     * output graphic files will be written
     */
    public RasterFileTestPlotPanel(final String outputDirPath) {
        super(new RasterDrawingCanvas());
        this.outputDirPath = outputDirPath;
    }
    
    
    /**
     * Constructor.
     * @param outputDir Directory where
     * output graphic files will be written
     */
    public RasterFileTestPlotPanel(final File outputDir) {
    	this(outputDir.getAbsolutePath());
    }

    
    // =====================================
    //       Business methods
    // =====================================
    
    /**
     * Render this panel as a PNG graphic in the
     * given file.  The file will be placed
     * in the directory given by the
     * <code>outputDirPath</code> property.
     * @param fileName Name of graphic file
     */
    public void toPngFile(final String fileName) {
        File file = new File(this.outputDirPath + "/" + fileName);
        File dir = file.getParentFile();
        if (!dir.exists()) {
        	dir.mkdir();
        }
        this.paint(this.getDrawingCanvas());
        this.getDrawingCanvas().setWidth(this.width());
        this.getDrawingCanvas().setHeight(this.height());
        ((RasterDrawingCanvas) this.getDrawingCanvas()).
            setOrigin(new Point(this.minX(), this.minY()));
        BufferedImage img = ((RasterDrawingCanvas)
                this.getDrawingCanvas()).toBufferedImage();
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            throw new WebGenomeSystemException("Error writing image to file", e);
        }
    }
}
