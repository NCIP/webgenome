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

package org.rti.webcgh.graph.widget;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ExperimentIterator;
import org.rti.webcgh.drawing.Cursor;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.drawing.Text;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.graph.PlotParameters;

/**
 * Plot legend
 */
public class Legend implements PlotElement {
	
	
	// ======================================
	//      Attributes
	// ======================================
	
	private int padding = 10;
	private int fontSize = 12;
	private Color textColor = Color.black;
	private int numCols = 4;
	private int colPadding = 30;
	private int borderWidth = 2;
	private Color borderColor = Color.black;
	
	private int minX = -this.padding;
	private int minY = -this.padding;
	private int maxX = this.padding;
	private int maxY = this.padding;
	
	private final DataSet dataSet;
	private final PlotParameters plotParams;
	
	
	public Color getBorderColor() {
		return borderColor;
	}


	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}


	public int getBorderWidth() {
		return borderWidth;
	}


	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}


	public int getColPadding() {
		return colPadding;
	}


	public void setColPadding(int colPadding) {
		this.colPadding = colPadding;
	}


	public int getNumCols() {
		return numCols;
	}


	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}


	/**
	 * @param textColor The textColor to set.
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	
	/**
	 * @param padding The padding to set.
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	
	/**
	 * @param fontSize The fontSize to set.
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	// ======================================
	//        Constructors
	// ======================================
	
	/**
	 * Constructor
	 * @param dataSet Data set
	 * @param plotParams Plot parameters
	 */
	public Legend(DataSet dataSet, PlotParameters plotParams) {
		this.dataSet = dataSet;
		this.plotParams = plotParams;
	}
	
	
	// =======================================
	//      Methods in plot element interface
	// =======================================
	
	
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	DrawingCoords drawingCoords = new DrawingCoords();
    	if (dataSet != null)
    		for (ExperimentIterator it = this.dataSet.experimentIterator(); it.hasNext();) {
    			Experiment exp = it.next();
    			if (exp.numBioAssays() > 0)
    				this.paint(canvas, exp, drawingCoords);
    		}
    	this.paintBorder(canvas);
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(this.minX, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.maxX, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.maxX, this.maxY);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
    	return this.maxX - this.minX;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
    	return this.maxY - this.minY;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    // ===========================================
    //        Private methods
    // ===========================================
    
    private void paint(DrawingCanvas canvas, Experiment experiment, DrawingCoords drawingCoords) {
    	
    	// Print experiment name
    	Point point = drawingCoords.next(experiment.getName(), canvas);
    	Text text = canvas.newGraphicText(experiment.getName(), point.x, 
    			point.y + this.fontSize, this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED,
				this.textColor);
    	canvas.add(text);
    	
    	// Adjust maximum X-coordinate
    	int candidateMaxX = canvas.renderedWidth(experiment.getName(), this.fontSize) + point.x + this.padding;
    	if (candidateMaxX > this.maxX)
    		this.maxX = candidateMaxX;
    	int candidateMaxY = point.y + this.fontSize + this.padding;
    	if (candidateMaxY > this.maxY)
    		this.maxY = candidateMaxY;
    	
    	// Print bioassays names and color blocks
    	for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();)
    		this.paint(canvas, it.next(), drawingCoords);
    }
    
    
    private void paint(DrawingCanvas canvas, BioAssay bioAssay, DrawingCoords drawingCoords) {
    	Point point = drawingCoords.next(bioAssay.getName(), canvas);
    	
    	// Draw box
    	int x = point.x;
    	int width = this.fontSize;
    	int height = this.fontSize;
    	Color color = this.plotParams.color(bioAssay);
    	Rectangle rect = new Rectangle(x, point.y, width, height, color);
    	String command = "toggleColor('" + bioAssay.getName() + "')";
    	rect.setProperty(ScatterPlot.GRP_ATT_NAME, bioAssay.getName());
    	rect.addGraphicEventResponse(GraphicEvent.mouseClickEvent, command);
    	rect.setCursor(Cursor.POINTER);
    	canvas.add(rect);
    	
    	// Draw text
    	x += this.padding + width;
    	Text text = canvas.newGraphicText(bioAssay.getName(), x, point.y + this.fontSize, 
    			this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED, this.textColor);
    	text.setCursor(Cursor.POINTER);
    	text.addGraphicEventResponse(GraphicEvent.mouseClickEvent, "highlight('" +
    			bioAssay.getName() + "')");
    	text.setProperty(ScatterPlot.GRP_ATT_NAME, bioAssay.getName());
    	canvas.add(text);
    	
    	// Adjust max X-coordinate
    	int candidateMaxX = x + canvas.renderedWidth(bioAssay.getName(), this.fontSize) + this.padding;
    	if (candidateMaxX > this.maxX)
    		this.maxX = candidateMaxX;
    	int candidateMaxY = point.y + this.fontSize + this.padding;
    	if (candidateMaxY > this.maxY)
    		this.maxY = candidateMaxY;
    }
    
    
    private void paintBorder(DrawingCanvas canvas) {
    	canvas.add(new Line(this.minX, this.minY, this.minX, this.maxY, this.borderWidth, this.borderColor));
    	canvas.add(new Line(this.minX, this.maxY, this.maxX, this.maxY, this.borderWidth, this.borderColor));
    	canvas.add(new Line(this.maxX, this.maxY, this.maxX, this.minY, this.borderWidth, this.borderColor));
    	canvas.add(new Line(this.minX, this.minY, this.maxX, this.minY, this.borderWidth, this.borderColor));
    }
    
    
    // ====================================
    //       Inner classes
    // ====================================
    
    
    class DrawingCoords {
    	
    	private int x = 0;
    	private int y = 0;
    	private int row = 0;
    	private int col = 0;
    	private final int numRows;
    	private int maxWidthInCol = 0;
    	
    	
    	public DrawingCoords() {
    		numRows = (dataSet.numExperiments() + dataSet.numBioAssays()) / numCols;
    	}
    	
    	
    	public Point next(String label, DrawingCanvas canvas) {
    		int width = canvas.renderedWidth(label, fontSize);
    		if (width > maxWidthInCol)
    			maxWidthInCol = width;
    		Point point = new Point(x, y);
    		row++;
    		y += fontSize + padding;
    		if (row >= this.numRows) {
    			col++;
    			row = 0;
    			x += maxWidthInCol + colPadding;
    			y = 0;
    			maxWidthInCol = 0;
    		}
    		return point;
    	}
    }

}
