/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.graphics.util.HeatMapColorFactory;
import org.rti.webgenome.units.HorizontalAlignment;

/**
 * This class plots data in a horizontal "track" where
 * the X-coordinate corresponds to chromosomal location
 * and the color to the experimental measurement (e.g. copy number).
 * @author dhall
 * @see org.rti.webgenome.graphics.util.HeatMapColorFactory
 */
public class DataTrack implements PlotElement {

	//
	//     STATICS
	//
	
	/** Height of overall track in pixels. */
	private static final int TRACK_HEIGHT = 30;
	
	/**
	 * Height of colored bar that gives experimental
	 * measurements.  Height is in units of pixels.
	 */
	private static final int BAR_HEIGHT = 15;
	
	/** Size of font for track label. */
	private static final int FONT_SIZE = 11;
	
	/** Color of track label text. */
	private static final Color TEXT_COLOR = Color.BLACK;
	
	/** Padding around graphic elements in pixels. */
	private static final int PADDING = 10;
	
	/**
	 * Number of distinct color "bins" that
	 * experimental measurements will be mapped to.
	 */
	private static final int NUM_BINS = 16;
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Graphic primitives that are instantiated by the
	 * constructor and later rendered.
	 */
	private final Collection<GraphicPrimitive> graphicPrimitives =
		new ArrayList<GraphicPrimitive>();
	
	/** X-coordinate of origin of plot in pixels. */
	private int minX = 0;
	
	/** Y-coordinate of origin of plot in pixels. */
	private int minY = 0;
	
	/** Maximum X-coordinate in plot. */
	private int maxX = 0;
	
	/**
	 * X-coordinate of the beginning of the data area, which
	 * is immediately right of the track label.
	 */
	private int trackStartX = 0;
	
	/** Scale of pixels to base pairs. */
	private final double scale;
	
	/**
	 * Start location of track relative to chromosome
	 * in base pair units.
	 */
	private final long startLoc;
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param chromosomeArrayData Data to plot
	 * @param startLoc Start location of plot on chromosome
	 * in base pair units
	 * @param endLoc End location of plot on chromosome
	 * in base pair units
	 * @param minSaturation Minimum saturation value below which
	 * the corresponding color will be pure green
	 * @param maxSaturation Maximum saturation value above which
	 * the corresponding color will be pure red
	 * @param trackWidth Width of data portion of track
	 * (i.e. not including the track label) in pixels
	 * @param trackLabel Track label
	 * @param canvas Canvas that the track will be rendered on
	 */
	public DataTrack(final ChromosomeArrayData chromosomeArrayData,
			final long startLoc,
			final long endLoc, final float minSaturation,
			final float maxSaturation, final int trackWidth,
			final String trackLabel,
			final DrawingCanvas canvas) {
		
		// Set attributes
		this.trackStartX = this.minX + PADDING
			+ canvas.renderedWidth(trackLabel, FONT_SIZE);
		this.maxX = this.trackStartX + trackWidth;
		this.scale = (double) trackWidth / (double) (endLoc - startLoc);
		this.startLoc = startLoc;
		
		// Add track label
		int textY = (this.minY + TRACK_HEIGHT) / 2 + FONT_SIZE / 2;
		Text text = canvas.newText(trackLabel, this.minX,
				textY, FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED,
				TEXT_COLOR);
		this.graphicPrimitives.add(text);
		
		// Add colored rectangles
		HeatMapColorFactory colorFac = new HeatMapColorFactory(
				minSaturation, maxSaturation, NUM_BINS);
		List<ArrayDatum> arrayData = chromosomeArrayData.getArrayData();
		int y = this.minY + TRACK_HEIGHT / 2 - BAR_HEIGHT / 2;
		for (int i = 0; i < arrayData.size(); i++) {
			ArrayDatum datum = arrayData.get(i);
			Reporter r = datum.getReporter();
			int startX = this.toPixels(r.getLocation());
			int endX = startX;
			if (i > 0) {
				ArrayDatum leftDatum = arrayData.get(i - 1);
				Reporter leftRep = leftDatum.getReporter();
				long midPoint = (r.getLocation() + leftRep.getLocation())
					/ 2;
				startX = this.toPixels(midPoint);
			}
			if (i < arrayData.size() - 1) {
				ArrayDatum rightDatum = arrayData.get(i + 1);
				Reporter rightRep = rightDatum.getReporter();
				long midPoint = (r.getLocation() + rightRep.getLocation())
					/ 2;
				endX = this.toPixels(midPoint);
			}
			if (startX < this.maxX && endX > this.trackStartX) {
				if (startX < this.trackStartX) {
					startX = this.trackStartX;
				}
				if (endX > this.maxX) {
					endX = this.maxX;
				}
				Color intensity = colorFac.getColor(datum.getValue());
				Rectangle rect = new Rectangle(startX, y,
						endX - startX, BAR_HEIGHT, intensity);
				this.graphicPrimitives.add(rect);
			}
		}
	}
	
	
	/**
	 * Convert from base pair units to X-coordinate pixel relative
	 * to the left side of the plot.
	 * @param value Value to convert
	 * @return Pixel equivalent
	 */
	private int toPixels(final long value) {
		return this.trackStartX + (int)
		(((double) value - this.startLoc) * this.scale);
	}
	
	
	//
	//     PlotElement INTERFACE
	//
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		canvas.add(prim);
    	}
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.trackStartX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.trackStartX, this.maxY());
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
    	return new Point(this.maxX, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.maxX, this.maxY());
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
    	return this.maxX - this.minX;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
    	return TRACK_HEIGHT;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	this.maxX += deltaX;
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.trackStartX += deltaX;
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		prim.move(deltaX, deltaY);
    	}
    }
    
    /**
     * Get maximum Y-coordinate.
     * @return Maximum Y-coordinate in pixels
     */
    private int maxY() {
    	return this.minY + TRACK_HEIGHT;
    }
}
