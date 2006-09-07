/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/


package org.rti.webcgh.graph.unit_test;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.SvgDrawingCanvas;
import org.rti.webcgh.graphics.primitive.Line;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.util.IOUtils;
import org.rti.webcgh.util.XmlUtils;
import org.w3c.dom.Document;

/**
 * Instances of this class are created to help with the testing
 * of plotting components including individual widgits and entire
 * plots.  The class creates a drawing canvas that enables
 * the plot components to create an SVG document.  It also provides
 * a method for writing the SVG document to a file.
 *
 */
public final class SvgTestPanel extends PlotPanel {
    
    // ============================
    //    Constants
    // ============================
	
    /** Path to SVG template file relative to the classpath. */
	private static final String CLASSPATH_RELATIVE_PATH_TO_SVG_TEMPLATE =
		"svg/plotTemplate.svg";
    
    /**
     * Default X-axis coordinate for top left point of test graphic
     * relative to the <code>baseCanvas</code>.
     */ 
    private static final int DEF_TOP_LEFT_X = 100;
    
    
    /**
     * Default Y-axis coordinate for top left point of test graphic
     * relative to the <code>baseCanvas</code>.
     */
    private static final int DEF_TOP_LEFT_Y = 100;
    
	
	// =====================================
	//      Attributes
	// =====================================
	
	/** Directory where generated SVG files are deposited. */
	private File svgDirectory = new File("C:\\temp\\svg");
	
	/** Document object containing SVG. */
	private Document doc = null;
	
	/**
     * Base canvas underneath canvas that is drawn on.  This
	 * canvas is used to ensure that drawn part of drawing
	 * canvas is completely visible.
     */
	private DrawingCanvas baseCanvas = null;
	
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
	
	/**
	 * Point within viewer (e.g., web browser window)
	 * where top left point of test panel rendered.
     */
	private Point origin = new Point(SvgTestPanel.DEF_TOP_LEFT_X,
            SvgTestPanel.DEF_TOP_LEFT_Y);
	
	/**
	 * Point within viewer (e.g., web browser window)
	 * where top left point of test panel rendered.
	 * @return A point
	 */
	public Point getOrigin() {
		return origin;
	}


	/**
	 * Point within viewer (e.g., web browser window)
	 * where top left point of test panel rendered.
	 * @param origin A point
	 */
	public void setOrigin(final Point origin) {
		this.origin = origin;
	}


	/**
	 * Get color of border that may or may not be drawn around panel
	 * when rendered. This property also controls color of crosshairs
	 * which also may or may not be drawn.
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
	 * @return A color
	 */
	public Color getBorderColor() {
		return borderColor;
	}


	/**
	 * Set color of border that may or may not be drawn around panel
	 * when rendered. This property also controls color of crosshairs
	 * which also may or may not be drawn.
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
	 * @param borderColor A color
	 */
	public void setBorderColor(final Color borderColor) {
		this.borderColor = borderColor;
	}


	/**
	 * Width of border that may or may not be drawn around panel
	 * when rendered. This property also controls width of crosshairs
	 * which also may or may not be drawn.
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
	 * @return Border width in pixels
	 */
	public int getBorderWidth() {
		return borderWidth;
	}


	/**
	 * Width of border that may or may not be drawn around panel
	 * when rendered. This property also controls width of crosshairs
	 * which also may or may not be drawn.
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawBorder(boolean)
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setDrawCrossHairs(boolean)
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
	 * Get directory that output SVG files are written to.
	 * @return A directory
	 */
	public File getSvgDirectory() {
		return svgDirectory;
	}


	/**
	 * Set directory that output SVG files are written to.
	 * @param svgDirectory A directory
	 */
	public void setSvgDirectory(final File svgDirectory) {
		this.svgDirectory = svgDirectory;
	}



	// ====================================
	//      Constructors
	// ====================================
	
	/**
	 * Constructor.
	 * @param baseCanvas Underlying canvas
	 * @param drawingCanvas Top canvas, which is drawn upon
	 * @param doc A document
	 */
	private SvgTestPanel(final DrawingCanvas baseCanvas,
            final DrawingCanvas drawingCanvas, final Document doc) {
		super(drawingCanvas);
		this.baseCanvas = baseCanvas;
		this.doc = doc;
	}
	
	
	// ===================================
	//    Static methods
	// ===================================
		
	/**
	 * Factory method for creating a new SvgTestPanel object.
     * @return An SvgTestPanel instance
	 */
	public static SvgTestPanel newSvgTestPanel() {
		
		// Load SVG plotting template document
		Document doc = XmlUtils.loadDocument(
                CLASSPATH_RELATIVE_PATH_TO_SVG_TEMPLATE, false);
		if (doc == null) {
			throw new WebcghSystemException(
                    "Unable to load SVG template document");
        }
		
		// Instantiate canvases
		DrawingCanvas baseCanvas = new SvgDrawingCanvas(doc);
		DrawingCanvas drawingCanvas = baseCanvas.newTile();
		
		// Return new panel
		return new SvgTestPanel(baseCanvas, drawingCanvas, doc);
	}
	
	
	// =====================================
	//       Other public methods
	// =====================================
	
	/**
	 * Create SVG file with given file name.  Note that
	 * the directory to which this file is written should be specified
	 * by setting the <code>svgDirectory</code> property.
	 * The parameter <code>fileName</code> should not be
	 * an absolute path.
	 * @see org.rti.webcgh.graph.unit_test.SvgTestPanel#setSvgDirectory(File)
	 * @param fileName Name of file
	 */
	public void toSvgFile(final String fileName) {
		if (this.drawBorder) {
			this.addBorder();
        }
		if (this.drawCrossHairs) {
			this.addCrossHairs();
        }
		this.baseCanvas.add(this.getDrawingCanvas(), this.origin.x
                - this.topLeftPoint().x,
				this.origin.y - this.topLeftPoint().y);
		File outFile = 
			new File(this.svgDirectory.getAbsolutePath() + "/" + fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outFile);
			XmlUtils.forwardDocument(this.doc, new FileWriter(outFile));
		} catch (IOException e) {
			throw new WebcghSystemException("Error creating SVG file", e);
		} finally {
			IOUtils.close(writer);
		}
	}
	
	
	
	// ==========================================
	//        Private methods
	// ==========================================
	
	/**
	 * Add border around graphic.
	 */
	private void addBorder() {
		int minX = this.topLeftPoint().x;
		int minY = this.topLeftPoint().y;
		int maxX = minX + this.width();
		int maxY = minY + this.height();
		
		// Top border
		this.getDrawingCanvas().add(new Line(minX, minY, maxX, minY, 
				this.borderWidth, this.borderColor));
		
		// Left border
		this.getDrawingCanvas().add(new Line(minX, minY, minX, maxY, 
				this.borderWidth, this.borderColor));
		
		// Right border
		this.getDrawingCanvas().add(new Line(maxX, minY, maxX, maxY, 
				this.borderWidth, this.borderColor));
		
		// Bottom border
		this.getDrawingCanvas().add(new Line(minX, maxY, maxX, maxY, 
				this.borderWidth, this.borderColor));
	}
	
	
	/**
	 * Add centered crosshairs to graphic.
	 *
	 */
	private void addCrossHairs() {
		int minX = this.topLeftPoint().x;
		int minY = this.topLeftPoint().y;
		int maxX = minX + this.width();
		int maxY = minY + this.height();
		
		// Vertical crosshair
		int x = (maxX + minX) / 2;
		this.getDrawingCanvas().add(new Line(x, minY, x, maxY, 
				this.borderWidth, this.borderColor));
		
		// Horizontal crosshair
		int y = (maxY + minY) / 2;
		this.getDrawingCanvas().add(new Line(minX, y, maxX, y, 
				this.borderWidth, this.borderColor));
	}
}
