/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.units.HorizontalAlignment;

/**
 * This class is a plotting widget that represents a
 * track in an annotation plot.  Annotated genome features
 * are plotted as boxes where the endpoints give the start
 * and end chromosomal position.
 * @author dhall
 *
 */
public class AnnotationTrack implements PlotElement {
	
	//
	//     STATICS
	//
	
	/** Height of features in pixels. */
	private static final int FEATURE_HEIGHT = 15;
	
	/** Color of features. */
	private static final Color FEATURE_COLOR = Color.BLACK;
	
	/** Size of font for feature labels. */
	private static final int FONT_SIZE = 11;
	
	/** Padding around graphic elements in pixels. */
	private static final int PADDING = 10;
	
	/** Stroke (i.e. width) of line in pixels connecting exons. */
	private static final int STROKE = 2;
	
	/** Minimum width in features that a feature can be. */
	private static final int MIN_FEAT_WIDTH = 2;
	
	//
	//     ATTRIBUTES
	//
	
	/** Minimum X-coordinate in track. */
	private int minX = 0;
	
	/** Minimum Y-coordinate in track. */
	private int minY = 0;
	
	/** Maximum X-coordinate in track. */
	private int maxX = 0;
	
	/** Maximum Y-coordinate in track. */
	private int maxY = 0;
	
	/**
	 * Starting X-coordinate of actual rendered
	 * tracks.  This will be to the right of the
	 * track label.
	 */
	private int trackStartX = 0;
	
	/**
	 * Starting position of track relative to chromosome
	 * in units of base pairs.
	 */
	private final long startPos;
	
	/**
	 * Graphic primitives that are laid out
	 * and later rendered on demand.
	 */
	private Collection<GraphicPrimitive> graphicPrimitives =
		new ArrayList<GraphicPrimitive>();
	
	/** Draw feature names above features? */
	private final boolean drawFeatureLabels;
	
	/** Drawing canvas used to instantiate text. */
	private final DrawingCanvas drawingCanvas;
	
	/** Scale of pixels to base pairs. */
	private final double scale;
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param features Features that will be plotted in track
	 * @param width Width of track in pixels.  This only includes
	 * area where features are drawn and not the track label
	 * to the left.
	 * @param startPos Starting position of track relative
	 * to chromosome
	 * int units of base pairs.
	 * @param endPos Ending position of track relative to chromosome
	 * in units of base pairs.
	 * @param trackLabel Label of track, which will be rendered to the
	 * left of the track.
	 * @param drawFeatureLabels Draw feature names above features?
	 * @param drawingCanvas Canvas that this track will be
	 * rendered on.
	 */
	public AnnotationTrack(
			final SortedSet<AnnotatedGenomeFeature> features,
			final int width, final long startPos,
			final long endPos, final String trackLabel,
			final boolean drawFeatureLabels,
			final DrawingCanvas drawingCanvas) {
		this.startPos = startPos;
		this.drawFeatureLabels = drawFeatureLabels;
		this.drawingCanvas = drawingCanvas;
		this.trackStartX = this.minX 
			+ drawingCanvas.renderedWidth(trackLabel, FONT_SIZE)
			+ PADDING;
		this.maxX = this.trackStartX + width;
		this.scale = (double) width / (double) (endPos - startPos);
		YCoordinateAssigner yCoordinateAssigner = new YCoordinateAssigner();
		for (AnnotatedGenomeFeature feat : features) {
			int startX = this.toXPixelCoord(feat.getStartLocation());
			int endX = this.toXPixelCoord(feat.getEndLocation());
			int y = yCoordinateAssigner.getYCoordinate(feat);
			int candidateMaxY = y + FEATURE_HEIGHT / 2;
			if (candidateMaxY > this.maxY) {
				this.maxY = candidateMaxY;
			}
			if (feat.getAnnotationType() == AnnotationType.GENE) {
				this.addGene(feat, startX, endX, y);
			} else {
				this.addNonGene(feat, startX, endX, y);
			}
			if (this.drawFeatureLabels) {
				int textStartX = startX;
				if (textStartX < this.trackStartX) {
					textStartX = this.trackStartX;
				}
				int textEndX = endX;
				if (textEndX > this.maxX) {
					textEndX = this.maxX;
				}
				this.addLabel(feat.getName(), (textStartX + textEndX) / 2, y);
			}
		}
		int midY = (this.minY + this.maxY) / 2;
		Text text = drawingCanvas.newText(trackLabel, this.minX, midY,
				FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED, FEATURE_COLOR);
		this.graphicPrimitives.add(text);
	}
	
	
	/**
	 * Add gene graphic showing location of all exons.
	 * @param feat Gene feature
	 * @param startX Starting X-coordinate of feature in pixels
	 * @param endX Ending X-coordinate of feature in pixels
	 * @param subTrackMidY Y-coordinate in pixels of center of subtrack
	 */
	private void addGene(final AnnotatedGenomeFeature feat,
			final int startX, final int endX, final int subTrackMidY) {
		assert feat.getAnnotationType() == AnnotationType.GENE;
		
		// Draw line connecting exons
		int x1 = startX;
		if (x1 < this.trackStartX) {
			x1 = this.trackStartX;
		}
		int x2 = endX;
		if (x2 > this.maxX) {
			x2 = this.maxX;
		}
		Line line = new Line(x1, subTrackMidY, x2, subTrackMidY,
				STROKE, FEATURE_COLOR);
		this.graphicPrimitives.add(line);
		
		// Add exons
		for (AnnotatedGenomeFeature exon : feat.getChildFeatures()) {
			assert exon.getAnnotationType() == AnnotationType.EXON;
			int exonStartX = this.toXPixelCoord(exon.getStartLocation());
			int exonEndX = this.toXPixelCoord(exon.getEndLocation());
			if (exonStartX <= this.maxX && exonEndX >= this.trackStartX) {
				if (exonStartX < this.trackStartX) {
					exonStartX = this.trackStartX;
				}
				if (exonEndX > this.maxX) {
					exonEndX = this.maxX;
				}
				this.addNonGene(feat, exonStartX, exonEndX,
						subTrackMidY);
			}
		}
	}
	
	
	/**
	 * Add graphic showing location of given feature.
	 * @param feat Feature
	 * @param startX Starting X-coordinate of feature in pixels
	 * @param endX Ending X-coordinate of feature in pixels
	 * @param subTrackMidY Y-coordinate in pixels of center of subtrack
	 */
	private void addNonGene(final AnnotatedGenomeFeature feat,
			final int startX, final int endX,
			final int subTrackMidY) {
		int topY = subTrackMidY - FEATURE_HEIGHT / 2;
		int width = endX - startX;
		if (width < MIN_FEAT_WIDTH) {
			width = MIN_FEAT_WIDTH;
		}
		Rectangle rect = new Rectangle(startX, topY, width,
				FEATURE_HEIGHT, FEATURE_COLOR);
		this.graphicPrimitives.add(rect);
	}
	
	
	/**
	 * Add feature label to graphic.
	 * @param featLabel Feature label
	 * @param midX X-coordinate in center of label
	 * @param subTrackMidY Y-coordinate of middle of subtrack
	 */
	private void addLabel(final String featLabel,
			final int midX,
			final int subTrackMidY) {
		if (featLabel != null) {
			int y = subTrackMidY - FEATURE_HEIGHT / 2 - PADDING;
			Text text = this.drawingCanvas.newText(featLabel, midX, y,
					FONT_SIZE, HorizontalAlignment.CENTERED, FEATURE_COLOR);
			this.graphicPrimitives.add(text);
		}
	}
	
	/**
	 * Convert given position in base pairs to a pixel X-coordinate
	 * relative to the left side of the graphic.
	 * @param position Position in base pairs
	 * @return Pixel X-coordinate
	 */
	private int toXPixelCoord(final long position) {
		return this.trackStartX + (int)
			((double) (position - this.startPos) * this.scale);
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
    	return new Point(this.trackStartX, this.maxY);
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
    	return new Point(this.maxX, this.maxY);
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
    	return this.maxY - this.minY;
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
    public final void move(final int deltaX,
    		final int deltaY) {
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.trackStartX += deltaX;
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		prim.move(deltaX, deltaY);
    	}
    }
    
    //
    //     HELPER CLASSES
    //

    
    /**
     * This helper class assigns Y-coordinates for features
     * so that neighboring features that overlap logically
     * are rendered in stacking sub-tracks.
     * @author dhall
     *
     */
    private class YCoordinateAssigner {
    	
    	//
    	//     ATTRIBUTES
    	//
    	
    	/** X-coordinate of the end of each subtrack. */
    	private List<Integer> subTrackEndX = new ArrayList<Integer>();
    	
    	//
    	//     BUSINESS METHODS
    	//
    	
    	
    	/**
    	 * Get a Y-coordinate for the given feature such that
    	 * it does not overlap any feature previously laid down.
    	 * @param feature A feature
    	 * @return Y-coordinate in pixels.
    	 */
    	private int getYCoordinate(
    			final AnnotatedGenomeFeature feature) {
    		int subTrack = getSubTrackNumber(feature);
    		int baseY = minY;
    		if (drawFeatureLabels) {
    			baseY += FONT_SIZE + PADDING;
    		}
    		return baseY + subTrack * subTrackHeight();
    	}
    	
    	/**
    	 * Find the first subtrack moving from top to bottom
    	 * where the given feature does not overlap
    	 * the last feature laid down in that subtrack.  The returned
    	 * subtrack may be one larger than the previous number if
    	 * the given feature overlaps with feature in existing subtracks.
    	 * This method updates the property <code>subTrackEndX</code>.
    	 * @param feat A feature
    	 * @return Subtrack number
    	 */
    	private int getSubTrackNumber(final AnnotatedGenomeFeature feat) {
    		int num = 0;
    		
    		// Find starting and ending pixel X-coordinates
    		// for given feature
    		int startX = toXPixelCoord(feat.getStartLocation());
    		int endX = toXPixelCoord(feat.getEndLocation());
    		if (drawFeatureLabels && feat.getName() != null) {
    			int centerX = (startX + endX) / 2;
    			int textWidth = drawingCanvas.renderedWidth(
    					feat.getName(), FONT_SIZE);
    			int startText = centerX - textWidth / 2;
    			int endText = startText + textWidth;
    			if (startText < startX) {
    				startX = startText;
    			}
    			if (endText > endX) {
    				endX = endText;
    			}
    		}
    		
    		// Select subtrack number and update subTrackEndX property
    		endX += PADDING;
    		while (num < this.subTrackEndX.size()) {
    			if (startX >= this.subTrackEndX.get(0)) {
    				break;
    			}
    			num++;
    		}
    		if (num < this.subTrackEndX.size()) {
    			this.subTrackEndX.remove(num);
    		}
    		this.subTrackEndX.add(num, endX);
    		
    		return num;
    	}
    	
    	/**
    	 * Calculate height of a sub track.
    	 * @return Height in pixels
    	 */
    	private int subTrackHeight() {
    		int height = PADDING * 2 + FEATURE_HEIGHT;
    		if (drawFeatureLabels) {
    			height += PADDING + FONT_SIZE;
    		}
    		return height;
    	}
    }
}
