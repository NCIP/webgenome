/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $


*/

package org.rti.webgenome.service.plot;


import java.awt.Color;

import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.widget.Axis;
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.graphics.widget.Grid;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;

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
