/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/graph/FrequencyPlot.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:32 $

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
package org.rti.webcgh.deprecated.graph;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.deprecated.array.QuantifiedInterval;
import org.rti.webcgh.deprecated.array.QuantifiedIntervals;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.widget.PlotElement;


/**
 * 
 */
public class FrequencyPlot implements PlotElement {
	
	private static final int DEFAULT_BAR_WIDTH = 10;
    
    
    // ======================================
    //      Attributes
    // ======================================
    
    private final int width;
    private final double xScale;
    private final double yScale;
    private final long startBp;
    private final long endBp;
    
    private final static NumberFormat FORMAT = new DecimalFormat("###.###");
    
    private int maxX = 0;
    private int maxY = 0;
    private int minX = 0;
    private int minY = 0;
    private List<QuantifiedIntervals> quantifiedIntervals = new ArrayList<QuantifiedIntervals>();
    
    private final int height;
    
    
    /**
     * @return Returns the thickness.
     */
    public int getHeight() {
        return height;
    }
   
    
    
    // =======================================
    //        Constructor
    // =======================================
    
    /**
     * Constructor
     * @param width Width in pixels
     * @param height Height in pixels
     * @param startBp Starting point in base pairs of plot
     * @param endBp Ending point in base pairs of plot
     * @param minFequency Minimum frequency in plot
     * @param maxFrequency Maximum frequency in plot
     */
    public FrequencyPlot(int width, int height, long startBp, long endBp, double minFrequency, 
    		double maxFrequency) {
    	
    	// Check arguments
    	if (width < 1)
    		throw new IllegalArgumentException("Width of frequency plot must be a positive integer");
    	if (height < 1)
    		throw new IllegalArgumentException("Height of frequency plot must be a positive integer");
    	if (startBp < (long)0)
    		throw new IllegalArgumentException("Starting base pair in frequency plot must be a positive integer or zero");
    	if (endBp < (long)0)
    		throw new IllegalArgumentException("End base pair in frequency plot must be a positive integer or zero");
    	if (startBp >= endBp)
    		throw new IllegalArgumentException("Starting base pair in frequency plot must be smaller than end base pair");
    	if (minFrequency >= maxFrequency)
    		throw new IllegalArgumentException("Minimum frequency plot value must be smaller than maximum value");
    	
    	// Initialize properties
        this.width = width;
        this.height = height;
        this.maxX = this.width;
        this.maxY = this.height;
        this.startBp = startBp;
        this.endBp = endBp;
        this.xScale = (double)width / (double)(endBp - startBp);
        this.yScale = (double)height / (maxFrequency - minFrequency);
    }
    
    
    // =============================================
    //        DataPlotter interface
    // =============================================
    
    
    /**
     * Graph quantified intervals
     * @param qi Quantified intervals
     */
    public void graphQuantifiedInterval(QuantifiedIntervals qi) {
        this.quantifiedIntervals.add(qi);
    }
        
    
    // =============================================
    //         PlotElement interface
    // =============================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	
    	// Iterate over data points and draw
    	for (QuantifiedIntervals qis : this.quantifiedIntervals) {
    		
    		for (QuantifiedInterval qi : qis.getIntervals()) {

    			long startBp = qi.getStart();
    			long endBp = qi.getEnd();
    			if (this.inRange(startBp, endBp)) {
    				
    				if (startBp < this.startBp)
    					startBp = this.startBp;
    				if (endBp > this.endBp)
    					endBp = this.endBp;
    			
		        	// Calculate bar position and dimensions
		        	int x = 0, width = 0;
		        	if (startBp == endBp) { // This should only occur if there is only 1 data point
		        		x = this.xPixel((double)startBp) - DEFAULT_BAR_WIDTH / 2;
		        		width = DEFAULT_BAR_WIDTH;
		        	} else {
		        		x = this.xPixel((double)startBp);
		        		width = this.xPixel((double)endBp) - x;
		        	}
		        	int barHeight = (int)(qi.getValue() * this.yScale);
		        	int y = this.height - barHeight;
		        	
		        	// Draw bar
		            Rectangle rect = new Rectangle(x, y, width, barHeight, Color.BLACK);
		            long startMb = startBp / 1000000;
		            long endMb = endBp / 1000000;
		            String mouseOver = FORMAT.format(qi.getValue()) + " [" + startMb + "MB-" + endMb + "MB]";
		            rect.setToolTipText(mouseOver);
		            canvas.add(rect);
    			}
    		}
        }
    }
    
    
    private boolean inRange(long start, long end) {
    	return this.inRange(start) || this.inRange(end);
    }
    
    
    private boolean inRange(long p) {
    	return p >= this.startBp && p <= this.endBp;
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
        return new Point(this.minX, this.maxX);
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
    
    
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	this.minX += deltaX;
    	this.maxX += deltaX;
    	this.minX += deltaY;
    	this.maxY += deltaY;
    }
    
    
    // =============================================
    //           Private methods
    // =============================================
    
    
    private int xPixel(double bp) {
        return (int)(this.xScale * (bp - (double)this.startBp));
    }
}
