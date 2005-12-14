/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/Legend.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

package org.rti.webcgh.graph;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ExperimentIterator;
import org.rti.webcgh.drawing.Cursor;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.GraphicRect;
import org.rti.webcgh.drawing.GraphicText;
import org.rti.webcgh.drawing.HorizontalAlignment;

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
	
	private int minX = 0;
	private int minY = 0;
	private int maxX = 0;
	private int maxY = 0;
	
	private final DataSet dataSet;
	private final PlotParameters plotParams;
	
	
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
    	if (dataSet != null)
    		for (ExperimentIterator it = this.dataSet.experimentIterator(); it.hasNext();)
    			this.paint(canvas, it.next());
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return new Point(0, 0);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(0, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.maxX, 0);
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
    	return new Point(0, 0);
    }
    
    
    // ===========================================
    //        Private methods
    // ===========================================
    
    private void paint(DrawingCanvas canvas, Experiment experiment) {
    	if (this.maxY > 0)
    		this.maxY += this.padding;
    	this.maxY += this.fontSize;
    	
    	// Print experiment name
    	GraphicText text = canvas.newGraphicText(experiment.getName(), 0, 
    			this.maxY, this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED,
				this.textColor);
    	canvas.add(text);
    	
    	// Adjust maximum X-coordinate
    	int candidateMaxX = canvas.renderedWidth(experiment.getName(), this.fontSize);
    	if (candidateMaxX > this.maxX)
    		this.maxX = candidateMaxX;
    	
    	// Print bioassays names and color blocks
    	Collection bioAssays = experiment.getBioAssays();
    	if (bioAssays != null)
    		for (Iterator it = bioAssays.iterator(); it.hasNext();)
    			this.paint(canvas, (BioAssay)it.next());
    }
    
    
    private void paint(DrawingCanvas canvas, BioAssay bioAssay) {
    	this.maxY += this.fontSize + this.padding;
    	
    	// Draw box
    	int x = 0;
    	int y = this.maxY - this.fontSize;
    	int width = this.fontSize;
    	int height = this.fontSize;
    	Color color = this.plotParams.color(bioAssay);
    	GraphicRect rect = new GraphicRect(x, y, width, height, color);
    	canvas.add(rect);
    	
    	// Draw text
    	x += this.padding + width;
    	GraphicText text = canvas.newGraphicText(bioAssay.getName(), x, this.maxY, 
    			this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED, this.textColor);
    	text.setCursor(Cursor.POINTER);
    	text.addGraphicEventResponse(GraphicEvent.mouseClickEvent, "highlight('" +
    			bioAssay.getName() + "')");
    	canvas.add(text);
    	
    	// Adjust max X-coordinate
    	int candidateMaxX = x + canvas.renderedWidth(bioAssay.getName(), this.fontSize);
    	if (candidateMaxX > this.maxX)
    		this.maxX = candidateMaxX;
    }

}
