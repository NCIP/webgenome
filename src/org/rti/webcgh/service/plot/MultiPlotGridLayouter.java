/*
$Revision: 1.3 $
$Date: 2007-02-06 17:48:53 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.plot;


import java.awt.Color;

import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.graphics.widget.Axis;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.Grid;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;

/**
 * Class lays out multiple plots on a panel in
 * a grid pattern.  Plots are layed out column-wise
 * against an axis.
 * @author dhall
 *
 */
public class MultiPlotGridLayouter {
	
	//
	//     STATICS
	//
	
	/** Color of grid. */
	private static final Color GRID_COLOR = Color.BLACK;
	
	//
	//     NON-ACCESSIBLE ATTRIBUTES
	//
	
	/** Number of columns. */
	private final int numCols;
	
	/** Root plot panel. */
	private final PlotPanel rootPanel;
	
	/** Plot panel representing current row. */
	private PlotPanel currentRow = null;

	/** Current column number. */
	private int currentColNum = 0;
	
	/** Minimum plotted value. */
	private final float minValue;
	
	/** Maximum plotted value. */
	private final float maxValue;
	
	/** Height of rows in pixels. */
	private final int rowHeight;
	
	/** Current axis. */
	private Axis currentAxis = null;
	
	/** Quantitation type. */
	private final QuantitationType quantitationType;
		
	
	//
	//     ACCESSIBLE ATTRIBUTES
	//
	
	/** Padding between cells in grid in pixels. */
	private int padding = 30;
	
	
	//
	//     GETTERS/SETTERS
	//
	
	/**
	 * Get padding between grid cells.
	 * @return Padding in pixels
	 */
	public final int getPadding() {
		return padding;
	}


	/**
	 * Set padding between grid cells.
	 * @param padding Padding in pixels
	 */
	public final void setPadding(final int padding) {
		this.padding = padding;
	}
	
	//
	//     CONSTRUCTORS
	//

	/**
	 * Constructor.
	 * @param numCols Number of columns
	 * @param rootPanel Root panel
	 * @param minValue Minimum plotted value
	 * @param maxValue Maximum plotted value
	 * @param rowHeight Height of rows in pixels
	 * @param quantitationType Quantitation type
	 */
	public MultiPlotGridLayouter(final int numCols,
			final PlotPanel rootPanel, final float minValue,
			final float maxValue, final int rowHeight,
			final QuantitationType quantitationType) {
		
		// Check args
		if (rootPanel == null) {
			throw new IllegalArgumentException("Root panel is null");
		}
		if (numCols < 1) {
			throw new IllegalArgumentException(
					"Number of columns must be a positive integer");
		}
		
		// Set properties
		this.numCols = numCols;
		this.rootPanel = rootPanel;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.rowHeight = rowHeight;
		this .quantitationType = quantitationType;
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Add given panel representing a column to grid.
	 * @param panel Panel representing a column
	 * @param horizontalAlignment Horizontal alignment
	 * @param verticalAlignment Vertical alignment
	 */
	public final void addColumn(final PlotPanel panel,
			final HorizontalAlignment horizontalAlignment,
			final VerticalAlignment verticalAlignment) {
		if (this.currentRow == null) {
			this.currentRow = rootPanel.newChildPlotPanel();
			this.currentRow.setPadding(this.padding);
			this.currentAxis = this.addYAxis(this.currentRow);
		}
		this.currentRow.add(panel, horizontalAlignment, verticalAlignment);
		this.currentColNum++;
		if (this.currentColNum >= this.numCols) {
			this.flush();
			this.currentRow = null;
			this.currentColNum = 0;
		}
	}
	
	
	/**
	 * Add Y-axis to given panel.
	 * @param panel A panel
	 * @return Newly-created axis
	 */
	private Axis addYAxis(final PlotPanel panel) {
		
		// Axis
		Axis axis = new Axis(this.minValue, this.maxValue,
				this.rowHeight, Orientation.VERTICAL, Location.LEFT_OF,
				panel.getDrawingCanvas());
		panel.add(axis, HorizontalAlignment.LEFT_OF,
				VerticalAlignment.ON_ZERO, true);
		
		// Caption
		Caption caption = new Caption(this.quantitationType.getName(),
				Orientation.HORIZONTAL, true,
				panel.getDrawingCanvas());
		panel.add(caption, HorizontalAlignment.LEFT_OF,
				VerticalAlignment.CENTERED);
		return axis;
	}
	
	
	/**
	 * Add any buffered graphic elements to the
	 * root panel.
	 */
	public final void flush() {
		if (this.currentRow != null) {
			Grid grid = this.currentAxis.newGrid(this.currentRow.width(),
					this.currentAxis.height(), GRID_COLOR,
					this.currentRow);
			this.currentRow.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
					VerticalAlignment.ON_ZERO);
			this.currentRow.moveToBack(grid);
			this.rootPanel.add(this.currentRow,
					HorizontalAlignment.LEFT_JUSTIFIED,
					VerticalAlignment.BELOW);
		}
	}
}
