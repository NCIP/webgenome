/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-09-29 05:24:19 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.DataPoint;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.InterpolationType;
import org.rti.webgenome.graphics.PlotBoundaries;
import org.rti.webgenome.graphics.primitive.Diamond;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.util.GenomePositionMapper;
import org.rti.webgenome.graphics.util.PointListCompressor;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;

import flanagan.interpolation.CubicSpline;

/**
 * This is a type of scatter plot that shows the entire genome
 * in one snapshot, so that regions of chromosomal alteration
 * stand out.
 * @author dhall
 *
 */
public class GenomeSnapshotPlot implements PlotElement {
	
	//
	//  C O N S T A N T S
	//
	
    /** Chromosomal alteration line width. */
    private static final int ALTERATION_LINE_WIDTH = 5;
    
    /** Minimum width of a region of LOH in pixels. */
    private static final int MIN_LOH_WIDTH = 10;
    
    /** Opacity (i.e. alpha) of expression data colors. */
    private static final int EXPRESSION_OPACITY = 125;
    
    /**
     * Size of diamond-shaped expression data points in pixels.
     * See documentation in {@code Diamond} to get the definition
     * of diamond size.
     */ 
    private static final int DIAMOND_SIZE = 6;
    
    /** Width of plot lines in pixels. */
    private static final int LINE_WIDTH = 1;

    
    //
    //  A T T R I B U T E S
    //
    
    /** Experiments to plot. */
    private final Collection<Experiment> experiments;
    
    /**
     * Gets chromosome array data making the location of those
     * data transparent.  Data may be in memory or on disk.
     */
    private final ChromosomeArrayDataGetter chromosomeArrayDataGetter;
    
    
    /** Width of plot in pixels. */
    private final int width;
    
    /** Height of plot in pixels. */
    private final int height;
    
    /** Boundaries of plot. */
    private final PlotBoundaries plotBoundaries;
    
    /** Maps chromosome positions to genome positions. */
    private final GenomePositionMapper genomePositionMapper;
    
    /** X-coordinate of plot origin (i.e, upper left-most point). */
    private int x = 0;
    
    /** Y-coordinate of plot origin (i.e., upper left-most point). */
    private int y = 0;
    
    /**
     * Data point object that is reused during plot creation
     * in order to economize memory.
     */
    private final DataPoint reusableDataPoint1 = new DataPoint();
    
    /**
     * Data point object that is reused during plot creation
     * in order to economize memory.
     */
    private final DataPoint reusableDataPoint2 = new DataPoint();
    
    /**
     * Threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
     */
    private float lohThreshold = (float) 0.5;
    
    /**
     * Interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
     */
    private boolean interpolateLohEndpoints = false;
    
    /** Draw raw LOH probabilities along with scored data? */
    private boolean drawRawLohProbabilities = true;
    
    /** Quantitation type for copy number data. */
    private QuantitationType copyNumberQType = null;
    
    /** Quantitation type for expression data. */
    private QuantitationType expressionQType = null;
    
    /** Interpolation type. */
    private InterpolationType interpolationType = InterpolationType.NONE;
    
    //
    //  G E T T E R S / S E T T E R S
    //
    
	/**
	 * Draw raw LOH probabilities?
	 * @return T/F
	 */
	public boolean isDrawRawLohProbabilities() {
		return drawRawLohProbabilities;
	}


	/**
	 * Set whether to draw raw LOH probabilities.
	 * @param drawRawLohProbabilities Draw raw LOH probabilities?
	 */
	public void setDrawRawLohProbabilities(
			final boolean drawRawLohProbabilities) {
		this.drawRawLohProbabilities = drawRawLohProbabilities;
	}
	
	/**
	 * Get interpolation type.
	 * @return Interpolation type
	 */
	public InterpolationType getInterpolationType() {
		return interpolationType;
	}


	/**
	 * Set interpolation type.
	 * @param interpolationType Interpolation type
	 */
	public void setInterpolationType(
			final InterpolationType interpolationType) {
		this.interpolationType = interpolationType;
	}


	/**
	 * Interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @return T/F
	 */
	public boolean isInterpolateLohEndpoints() {
		return interpolateLohEndpoints;
	}


	/**
	 * Set whether to interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @param interpolateLohEndpoints Interpolate LOH endpoints?
	 */
	public void setInterpolateLohEndpoints(
			final boolean interpolateLohEndpoints) {
		this.interpolateLohEndpoints = interpolateLohEndpoints;
	}


	/**
	 * Get threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @return LOH threshold probability.
	 */
	public float getLohThreshold() {
		return lohThreshold;
	}


	/**
	 * Set threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @param lohThreshold LOH threshold probability.
	 */
	public void setLohThreshold(final float lohThreshold) {
		this.lohThreshold = lohThreshold;
	}
	
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param experiments Experiments to plot
	 * @param chromosomeArrayDataGetter Data getter
	 * @param width Width of plot in pixels
	 * @param height Height of plot in pixels
	 * @param minY Minimum Y-axis value
	 * @param maxY Maximum Y-axis value
	 */
	public GenomeSnapshotPlot(final Collection<Experiment> experiments,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter,
			final int width, final int height, final float minY,
			final float maxY) {
		super();
		// Make sure args okay
    	if (experiments == null) {
    		throw new IllegalArgumentException("Experiments cannot be null");
    	}
    	if (chromosomeArrayDataGetter == null) {
    		throw new IllegalArgumentException(
    				"Chromosome array data getter cannot be null");
    	}
    	this.expressionQType = null;
    	this.copyNumberQType = null;
    	for (Experiment exp : experiments) {
    		QuantitationType qt = exp.getQuantitationType();
    		if (qt.isExpressionData()) {
    			if (this.expressionQType == null) {
    				this.expressionQType = qt;
    			} else if (this.expressionQType != qt) {
    				throw new IllegalArgumentException(
						"Cannot mix expression quantitation types in plot");
    			}
    		} else {
    			if (this.copyNumberQType == null) {
    				this.copyNumberQType = qt;
    			} else if (this.copyNumberQType != qt) {
    				throw new IllegalArgumentException(
						"Cannot mix copy number quantitation types in plot");
    			}
    		}
    	}
		this.experiments = experiments;
		this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
		this.width = width;
		this.height = height;
		this.genomePositionMapper = new GenomePositionMapper(experiments);
		this.plotBoundaries = new PlotBoundaries(0, minY,
				this.genomePositionMapper.getGenomeLength(), maxY);
	}
	
	//
	//  I N T E R F A C E : PlotElement
	//
	
    /**
     * Return point at top left of element.
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.x, this.y);
    }
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
    	for (Experiment exp : this.experiments) {
    		SortedSet<Short> chromosomes = exp.getChromosomes();
            for (BioAssay bioAssay : exp.getBioAssays()) {
            	this.paint(canvas, bioAssay, chromosomes,
            				exp.getQuantitationType());
            }
        }
    }
    
    /**
     * Paint given bioassay.
     * @param canvas Canvas
     * @param bioAssay Bioassay to paint
     * @param chromosomes Chromosomes in plot
     * @param qType Quantitation type
     */
    private void paint(final DrawingCanvas canvas, final BioAssay bioAssay,
    		final SortedSet<Short> chromosomes, 
    		final QuantitationType qType) {

    	for (Short chromosome : chromosomes) {
	    	ChromosomeArrayData cad = this.chromosomeArrayDataGetter.
	    		getChromosomeArrayData(bioAssay, chromosome);
	    	if (cad != null) {
	    		if (cad.getChromosomeAlterations() == null) {
	    			if (qType.isExpressionData()) {
	    				Color origColor = bioAssay.getColor();
	    				Color newColor = new Color(origColor.getRed(),
	    						origColor.getGreen(), origColor.getBlue(),
	    						EXPRESSION_OPACITY);
	    				this.paintExpressionData(canvas, cad, newColor);
	    			} else {
	    				this.paintCopyNumberData(canvas, cad,
	    						bioAssay.getColor());
	    			}
	    		} else {
	    			if (cad.getChromosomeAlterations().size() > 0) {
	    				List<AnnotatedGenomeFeature> feats =
	    					cad.getChromosomeAlterations();
	    				AnnotationType type = feats.get(0).getAnnotationType();
		    			this.paintAlterations(canvas, cad, bioAssay.getColor(),
		    					type);
	    			}
	    		}
	        }
    	}
    }
    
    
    /**
     * Paint gene expression data.
     * @param canvas Canvas on which to paint
     * @param cad Data to paint
     * @param color Color to paint data
     */
    private void paintExpressionData(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color) {
    	List<ArrayDatum> arrayData = cad.getArrayData();
    	if (arrayData != null) {
	        for (ArrayDatum datum : cad.getArrayData()) {
	            this.paintPoint(canvas, datum, color, cad.getChromosome());
	        }
    	}
    }
    
    
    /**
     * Paint a single point of expression data.
     * @param canvas Canvas on which to paint point
     * @param datum Datum to plot
     * @param color Color of datum
     * @param chromosome Chromosome from which datum was derived
     */
    private void paintPoint(final DrawingCanvas canvas,
    		final ArrayDatum datum, final Color color,
    		final short chromosome) {
    	this.reusableDataPoint1.bulkSet(datum);
    	this.toGenomicCoordinate(this.reusableDataPoint1, chromosome);
    	int x = this.transposeX(this.reusableDataPoint1);
        int y = this.transposeY(this.reusableDataPoint1);
        Diamond diamond = new Diamond(x, y, DIAMOND_SIZE, color);
        canvas.add(diamond);
    }
    
    
    /**
     * Convert given data point, which is on a chromosomal coordinate
     * position, into a genomic coordinates.
     * @param dataPoint A data point
     * @param chromosome Chromosome number
     */
    private void toGenomicCoordinate(final DataPoint dataPoint,
    		final short chromosome) {
    	float genPos = this.genomePositionMapper.chromToGenomicLocation(
    			chromosome, (long) dataPoint.getValue1());
    	dataPoint.setValue1((double) genPos);
    }
    
    
    /**
     * Transpose X-axis value of given data point to a corresponding
     * pixel value on the plot.
     * @param dataPoint Data point to transpose.  This point must be
     * on a genomic scale, not a chromosomal scale
     * @return X-pixel location corresponding to given data point
     */
    private int transposeX(final DataPoint dataPoint) {
    	return this.x + (int) (this.plotBoundaries.fractionalDistanceFromLeft(
    			dataPoint) * (double) this.width);
    }
    
    /**
     * Transpose Y-axis vale of given data point to a corresponding
     * pixel value on the plot.
     * @param dataPoint Data point to transpose
     * @return Y-pixel location corresponding to given data point
     */
    private int transposeY(final DataPoint dataPoint) {
    	return this.y + height - (int) ((double) height
                * this.plotBoundaries.fractionalDistanceFromBottom(dataPoint));
    }
    
    
    /**
     * Paint copy number of LOH data.
     * @param canvas Canvas on which to paint
     * @param cad Data to paint
     * @param color Color to paint data
     */
    private void paintCopyNumberData(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color) {
    	
    	// Lines
    	if (this.copyNumberQType != QuantitationType.LOH
				|| (this.copyNumberQType == QuantitationType.LOH
						&& this.drawRawLohProbabilities)) {
    		if (this.interpolationType
    				== InterpolationType.STRAIGHT_LINE) {
	            this.paintStraightConnectingLines(canvas,
	            		cad, color);
    		} else if (this.interpolationType
    				== InterpolationType.SPLINE) {
    			this.paintConnectingSpline(canvas,
    					cad, color);
    		} else if (this.interpolationType
    				== InterpolationType.STEP) {
    			this.paintConnectingSteps(canvas, cad, color);
    		}
    	}
    		
    	// LOH scored data
    	if (this.copyNumberQType == QuantitationType.LOH) {
			this.paintAlterations(canvas, cad, color,
					AnnotationType.LOH_SEGMENT);
		}
    }
    
    /**
     * Paint straight connecting lines between data points.
     * @param canvas Canvas on which to paint
     * @param cad Data to plot
     * @param color Color of line
     */
    private void paintStraightConnectingLines(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color) {
    	for (int i = 1; i < cad.getArrayData().size(); i++) {
            ArrayDatum d1 = cad.getArrayData().get(i - 1);
            ArrayDatum d2 = cad.getArrayData().get(i);
            this.reusableDataPoint1.bulkSet(d1);
            this.reusableDataPoint2.bulkSet(d2);
            this.toGenomicCoordinate(this.reusableDataPoint1,
            		cad.getChromosome());
            this.toGenomicCoordinate(this.reusableDataPoint2,
            		cad.getChromosome());
            this.paintLine(canvas, this.reusableDataPoint1,
            		this.reusableDataPoint2, color);
        }
    }
    
    /**
     * Draw line between two data points.
     * @param canvas Canvas on which to draw
     * @param dp1 First data point
     * @param dp2 Second data point
     * @param color Color of line
     */
    private void paintLine(final DrawingCanvas canvas,
    		final DataPoint dp1, final DataPoint dp2, final Color color) {
    	if (this.plotBoundaries.atLeastPartlyOnPlot(dp1, dp2)) {
            if (!this.plotBoundaries.withinBoundaries(dp1)
                    || !this.plotBoundaries.withinBoundaries(dp2)) {
                this.plotBoundaries.truncateToFitOnPlot(dp1, dp2);
            }
            int x1 = this.transposeX(dp1);
            int y1 = this.transposeY(dp1);
            int x2 = this.transposeX(dp2);
            int y2 = this.transposeY(dp2);
            Line line = new Line(x1, y1, x2, y2, LINE_WIDTH, color);
            canvas.add(line);
        }
    }
    
    /**
     * Paint splines between data points.
     * @param canvas Canvas on which to paint
     * @param cad Data to plot
     * @param color Color of line
     */
    private void paintConnectingSpline(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color) {
    	List<ArrayDatum> arrayData = cad.getArrayData();
    	if (arrayData.size() > 0) {
    		int minY = this.y;
    		int maxY = minY + this.height;
	    	CubicSpline spline = this.newCubicSpline(cad);
	    	ArrayDatum firstDatum = arrayData.get(0);
	    	ArrayDatum lastDatum = arrayData.get(arrayData.size() - 1);
	    	int startX = this.x;
	    	long firstGenomicLoc =
	    		this.genomePositionMapper.chromToGenomicLocation(
	    				cad.getChromosome(),
	    				firstDatum.getReporter().getLocation());
	    	int firstDatumX = this.transposeX(firstGenomicLoc);
	    	if (firstDatumX > startX) {
	    		startX = firstDatumX;
	    	}
	    	int endX = this.x + this.width;
	    	long lastGenomicLoc =
	    		this.genomePositionMapper.chromToGenomicLocation(
	    				cad.getChromosome(),
	    				lastDatum.getReporter().getLocation());
	    	int lastDatumX =
	    		this.transposeX(lastGenomicLoc);
	    	if (lastDatumX < endX) {
	    		endX = lastDatumX;
	    	}
	    	int x1 = startX;
	    	int y1 = (int) spline.interpolate((double) x1);
	        for (int i = startX; i < endX - 1; i++) {
	        	int x2 = i + 1;
	        	int y2 = (int) spline.interpolate((double) x2);
	        	if (y1 < minY && y2 >= minY) {
	        		y1 = minY;
	        	} else if (y1 > maxY && y2 <= maxY) {
	        		y1 = maxY;
	        	}
	        	if (y2 < minY && y1 >= minY) {
	        		y2 = minY;
	        	}
	        	if (y2 > maxY && y1 <= maxY) {
	        		y2 = maxY;
	        	}
	        	if (y1 >= minY && y1 <= maxY
	        			&& y2 >= minY && y2 <= maxY) {
		        	Line line = new Line(x1, y1, x2, y2, LINE_WIDTH, color);
		        	canvas.add(line);
	        	}
	        	x1 = x2;
	        	y1 = y2;
	        }
    	}
    }
    
    
    /**
     * Generate cubic spline.
     * @param cad Chromosome array data that provided control points
     * @return Cubic spline
     */
    private CubicSpline newCubicSpline(final ChromosomeArrayData cad) {
    	List<ArrayDatum> arrayData = cad.getArrayData();
    	int n = arrayData.size();
    	List<Double> xxList = new ArrayList<Double>();
    	List<Double> yyList = new ArrayList<Double>();
    	for (int i = 0; i < n; i++) {
    		ArrayDatum datum = arrayData.get(i);
    		long genomicPos = this.genomePositionMapper.chromToGenomicLocation(
    				cad.getChromosome(), datum.getReporter().getLocation());
    		xxList.add((double) this.transposeX(genomicPos));
    		yyList.add((double) this.transposeY(datum.getValue()));
    	}
    	PointListCompressor.compress(xxList, yyList);
    	n = xxList.size();
    	double[] xx = new double[n];
    	double[] yy = new double[n];
    	for (int i = 0; i < n; i++) {
    		xx[i] = xxList.get(i);
    		yy[i] = yyList.get(i);
    	}
    	return new CubicSpline(xx, yy);
    }
    
    
    /**
     * Transpose the given x-coordinate from
     * the genomic location to pixels.
     * @param x An x-coordinate genomic location
     * @return Transposed x-coordinate in pixels
     */
    private int transposeX(final long x) {
        return this.x + (int) ((double) width
                * this.plotBoundaries.fractionalDistanceFromLeft(x));
    }
    
    
    /**
     * Transpose the given y-coordinate from genomic units to pixels.
     * @param y A y-coordinate in native units
     * @return Transposed y-coordinate in pixels
     */
    private int transposeY(final float y) {
        return this.y + height - (int) ((double) height
                * this.plotBoundaries.fractionalDistanceFromBottom(y));
    }
    
    
    /**
     * Paint steps connecting data points.
     * @param canvas Canvas on which to paint
     * @param cad Data to plot
     * @param color Color of line
     */
    private void paintConnectingSteps(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color) {
        for (int i = 1; i < cad.getArrayData().size(); i++) {
            ArrayDatum d1 = cad.getArrayData().get(i - 1);
            ArrayDatum d2 = cad.getArrayData().get(i);
            long start = this.genomePositionMapper.chromToGenomicLocation(
            		cad.getChromosome(), d1.getReporter().getLocation());
            long end = this.genomePositionMapper.chromToGenomicLocation(
            		cad.getChromosome(), d2.getReporter().getLocation());
            long mid = (start + end) / (long) 2;
            
            // First line - right horizontal from first datum
            this.reusableDataPoint1.bulkSet(d1);
            this.toGenomicCoordinate(this.reusableDataPoint1,
            		cad.getChromosome());
            this.reusableDataPoint2.setValue1(mid);
            this.reusableDataPoint2.setValue2(d1.getValue());
            this.paintLine(canvas, this.reusableDataPoint1,
            		this.reusableDataPoint2,
            		color);
            
            // Second line - vertical
            this.reusableDataPoint1.setValue1(mid);
            this.reusableDataPoint1.setValue2(d2.getValue());
            this.paintLine(canvas, this.reusableDataPoint1,
            		this.reusableDataPoint2,
            		color);
            
            // Third line - left horizontal into second datum
            this.reusableDataPoint2.bulkSet(d2);
            this.toGenomicCoordinate(this.reusableDataPoint2,
            		cad.getChromosome());
            this.paintLine(canvas, this.reusableDataPoint1,
            		this.reusableDataPoint2, color);
        }
    }
    
    
    /**
     * Paint chromosomal alterations.
     * @param canvas Canvas on which to paint
     * @param cad Data to paint
     * @param color Color to paint data
     * @param type Type of alteration
     */
    private void paintAlterations(final DrawingCanvas canvas,
    		final ChromosomeArrayData cad, final Color color,
			final AnnotationType type) {
    	// Iterate over alterations
    	Iterator<AnnotatedGenomeFeature> it = cad.alteredSegmentIterator(
    			type);
    	if (it == null) {
    		it = cad.alteredSegmentIterator(
        			this.lohThreshold, this.interpolateLohEndpoints,
        			type);
    	}
    	while (it.hasNext()) {
    		AnnotatedGenomeFeature feat = it.next();
    				    				
			// Draw altered segment
    		long genomicStart =
    			this.genomePositionMapper.chromToGenomicLocation(
    					cad.getChromosome(), feat.getStartLocation());
    		long genomicEnd =
    			this.genomePositionMapper.chromToGenomicLocation(
    					cad.getChromosome(), feat.getEndLocation());
			int startX = this.transposeX(genomicStart);
			int endX = this.transposeX(genomicEnd);
			if (startX == endX) {
				startX -= MIN_LOH_WIDTH / 2;
				if (startX < this.x) {
					startX = this.x;
				}
				endX += MIN_LOH_WIDTH / 2;
				if (endX > this.x + this.width) {
					endX = this.x + this.width;
				}
			}
			int topY = this.transposeY(feat.getQuantitation());
			canvas.add(new Line(startX, topY, endX, topY,
					ALTERATION_LINE_WIDTH, color));
		}
    }
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(this.x, this.y);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.x, this.y + this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.x + this.width, this.y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.x + this.width, this.y + this.height);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
