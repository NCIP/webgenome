/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-09-09 17:16:05 $


*/

package org.rti.webgenome.service.plot;

import java.util.Collection;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.graphics.InterpolationType;


/**
 * Base class for genomic plot parameters.  Genomic
 * plots are plots where one dimension corresponds
 * to genomic location.
 * @author dhall
 *
 */
public abstract class BaseGenomicPlotParameters extends PlotParameters {
	
	// =======================
	//   Constants
	// =======================
		
	/**
	 * Default threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 */
	public static final float DEF_LOH_THRESHOLD = (float) 0.5;
	
	/** Default interpolation type. */
	public static final InterpolationType DEF_INTERPOLATION_TYPE =
		InterpolationType.SPLINE;
	
	/** Default value for interpolation of endpoints. */
	public static final boolean DEF_INTERPOLATE_ENDPOINTS = false;
	
	/** Default value for the drawing of row LOH probabilities. */
	public static final boolean DEF_DRAW_ROW_LOH_PROBABILITIES = true;
	
	/** Default value for showing reporter annotations as mouseover. */
	public static final boolean DEF_SHOW_ANNOTATION = false;
	
	/** Default value for showing nearby genes as mouseover. */
	public static final boolean DEF_SHOW_GENES = true;
	
	/** Default value for showing reporter names as mouseover. */
	public static final boolean DEF_SHOW_REPORTER_NAMES = true;
    
    // ==========================
    //       Attributes
    // ==========================
    
        
    /**
     * Threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
     */
    private float lohThreshold = DEF_LOH_THRESHOLD;
    
    /**
     * Interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
     */
    private boolean interpolateLohEndpoints = DEF_INTERPOLATE_ENDPOINTS;
    
    /** Draw raw LOH probabilities along with scored data? */
    private boolean drawRawLohProbabilities = DEF_DRAW_ROW_LOH_PROBABILITIES;
    
    /** Type of interpolation to perform between data points. */
    private InterpolationType interpolationType =
    	DEF_INTERPOLATION_TYPE;
    
    /** Show annotation in mouseover text? */
    private boolean showAnnotation = DEF_SHOW_ANNOTATION;
    
    /** Show gene names in mouseover text? */
    private boolean showGenes = DEF_SHOW_GENES;
    
    /** Show reporter names in mouseover text? */
    private boolean showReporterNames = DEF_SHOW_REPORTER_NAMES;
    
    // ===========================
    //    Getters/setters
    // ===========================

	/**
	 * Draw raw LOH probabilities?
	 * @return T/F
	 */
	public final boolean isDrawRawLohProbabilities() {
		return drawRawLohProbabilities;
	}


	/**
	 * Set whether to draw raw LOH probabilities.
	 * @param drawRawLohProbabilities Draw raw LOH probabilities?
	 */
	public final void setDrawRawLohProbabilities(
			final boolean drawRawLohProbabilities) {
		this.drawRawLohProbabilities = drawRawLohProbabilities;
	}


	/**
	 * Show reporter names in mouseover text?
	 * @return T/F
	 */
	public final boolean isShowReporterNames() {
		return showReporterNames;
	}


	/**
	 * Sets whether to show reporter names in mouseover text.
	 * @param showReporterNames Show reporter names in mouseover text?
	 */
	public final void setShowReporterNames(
			final boolean showReporterNames) {
		this.showReporterNames = showReporterNames;
	}


	/**
	 * Will annotation be shown in mouseover text?
	 * @return T/F
	 */
	public final boolean isShowAnnotation() {
		return showAnnotation;
	}


	/**
	 * Determines whether annotation will be shown in mouseover
	 * text.
	 * @param showAnnotation Show annotation in mouseover text?
	 */
	public final void setShowAnnotation(final boolean showAnnotation) {
		this.showAnnotation = showAnnotation;
	}


	/**
	 * Show genes names in mouseover text?
	 * @return T/F
	 */
	public final boolean isShowGenes() {
		return showGenes;
	}


	/**
	 * Set whether to show gene names in mouseover text.
	 * @param showGenes Show gene names in mouseover text?
	 */
	public final void setShowGenes(final boolean showGenes) {
		this.showGenes = showGenes;
	}


	/**
	 * Get interpolation type.
	 * @return Interpolation type
	 */
	public final InterpolationType getInterpolationType() {
		return interpolationType;
	}


	/**
	 * Set interpolation type.
	 * @param interpolationType Interpolation type
	 */
	public final void setInterpolationType(
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
	public final boolean isInterpolateLohEndpoints() {
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
	public final void setInterpolateLohEndpoints(
			final boolean interpolateLohEndpoints) {
		this.interpolateLohEndpoints = interpolateLohEndpoints;
	}


	/**
	 * Get threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @return LOH threshold probability.
	 */
	public final float getLohThreshold() {
		return lohThreshold;
	}


	/**
	 * Set threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @param lohThreshold LOH threshold probability.
	 */
	public final void setLohThreshold(final float lohThreshold) {
		this.lohThreshold = lohThreshold;
	}


    
    // ==================================
    //       Constructors
    // ==================================
    
    /**
     * Constructor.
     */
    public BaseGenomicPlotParameters() {
        super();
    }
    
    
    /**
     * Constructor that performs a deep copy of input
     * plot parameters.
     * @param params Plot parameters
     */
    public BaseGenomicPlotParameters(
    		final BaseGenomicPlotParameters params) {
    	super(params);
    	this.lohThreshold = params.lohThreshold;
    	this.interpolateLohEndpoints = params.interpolateLohEndpoints;
    	this.drawRawLohProbabilities = params.drawRawLohProbabilities;
    	this.interpolationType = params.interpolationType;
    	this.showAnnotation = params.showAnnotation;
    	this.showGenes = params.showGenes;
    	this.showReporterNames = params.showReporterNames;
    }
    
    
    //
    //     BUSINESS METHODS
    //
    
    /**
     * Derive any attributes not supplied by the user
     * from the given experiments.
     * @param experiments Experiments from which to derive
     * attributes not supplied by user.
     */
    public void deriveMissingAttributes(
    		final Collection<Experiment> experiments) {
		super.deriveMissingAttributes(experiments);
    }
    
    /**
     * Get string equivalent of interpolation type.
     * @return String equivalent of interpolation type
     */
    public String getInterpolationTypeByName() {
    	return this.interpolationType.name();
    }
    
    
    /**
     * Set interpolation type by specifying string equivalent.
     * @param name String equivalent of interpolation type
     */
    public void setInterpolationTypeByName(final String name) {
    	this.interpolationType = InterpolationType.valueOf(name);
    }
}
