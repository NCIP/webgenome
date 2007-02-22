/*
$Revision: 1.30 $
$Date: 2007-02-22 01:58:53 $

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

package org.rti.webcgh.webui.struts.cart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.core.PlotType;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.GenomeIntervalFormatException;
import org.rti.webcgh.graphics.InterpolationType;
import org.rti.webcgh.service.plot.BarPlotParameters;
import org.rti.webcgh.service.plot.BaseGenomicPlotParameters;
import org.rti.webcgh.service.plot.HeatMapPlotParameters;
import org.rti.webcgh.service.plot.IdeogramPlotParameters;
import org.rti.webcgh.service.plot.PlotParameters;
import org.rti.webcgh.service.plot.ScatterPlotParameters;
import org.rti.webcgh.units.BpUnits;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.util.NumericUtils;
import org.rti.webcgh.util.StringUtils;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.util.ValidationUtils;
import org.rti.webcgh.webui.struts.BaseForm;

/**
 * Form for input of plot parameters.
 * @author dhall
 *
 */
public class PlotParametersForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// ===========================
	//      Constants
	// ===========================
	
	/** Default genome intervals. */
	private static final String DEF_GENOME_INTERVALS = "1";
	
	/** Default plot type. */
	private static final String DEF_PLOT_TYPE =
		PlotType.SCATTER.toString();
	
	/** Default units. */
	private static final String DEF_UNITS = BpUnits.KB.getName();
	
	/** Default plots per row. */
	private static final String DEF_NUM_PLOTS_PER_ROW = "4";
	
	/** Default plot width. */
	private static final String DEF_WIDTH = "550";
	
	/** Default plot height. */
	private static final String DEF_HEIGHT = "250";
	
	/** Default bar plot bar width in pixels. */
	private static final String DEF_BAR_WIDTH = "10";
	
	
	/**
	 * Name of HTTP query parameter that would indicate
	 * the request came from a form for setting scatter plot
	 * parameters.
	 */
	private static final String
		SCATTER_PLOT_PARAMETERS_FORM_INDICATOR_PARAMETER = "width";
	
	
	/**
	 * Name of HTTP query parameter that would indicate
	 * the request came from a form for setting genomic plot
	 * parameters.
	 */
	private static final String
	GENOMIC_PLOT_PARAMETERS_FORM_INDICATOR_PARAMETER = 
		"genomeIntervals";
	
	
	// ===========================
	//     Attributes
	// ===========================
	
	/** Name of plot type. */
	private String plotType = DEF_PLOT_TYPE;
	
	/**
	 * Genome intervals encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 */
	private String genomeIntervals = DEF_GENOME_INTERVALS;
	
	/** Genome interval units (i.e., BP, KB, MB). */
	private String units = DEF_UNITS;
	
	/**
	 * Number of plots per row.  If more than one genome
	 * interval is specified, each will be plotted in
	 * a separate graph within the same graphics file.
	 */
	private String numPlotsPerRow = DEF_NUM_PLOTS_PER_ROW;

	/** Minimum Y-axis value. */
	private String minY = "";
	
	/** Maximum Y-axis value. */
	private String maxY = "";
	
	/** Width of plot in pixels. */
	private String width = DEF_WIDTH;
	
	/** Height of plot in pixels. */
	private String height = DEF_HEIGHT;
	
	/** Plot name. */
	private String name = "";
	
	/** Ideogram size. */
	private String ideogramSize =
		IdeogramPlotParameters.DEF_IDEOGRAM_SIZE.getName();
	
	/** Width of ideogram plot data tracks. */
	private String trackWidth =
		String.valueOf(IdeogramPlotParameters.DEF_TRACK_WIDTH);
	
	/** Ideogram plot minimum saturation. */
	private String minSaturation = String.valueOf(
			HeatMapPlotParameters.DEF_MIN_SATURATION);
	
	/** Maximum saturation. */
	private String maxSaturation = String.valueOf(
			HeatMapPlotParameters.DEF_MAX_SATURATION);
	
	/** Minimum data mask value for ideogram plot. */
	private String minMask = "";
	
	/** Maximum data mask value for ideogram plot. */
	private String maxMask = "";
	
	/** Ideogram thickness. */
	private String ideogramThickness =
		String.valueOf(IdeogramPlotParameters.DEF_IDEOGRAM_THICKNESS);
	
	/** Draw horizontal grid lines in scatter plot? */
	private String drawHorizGridLines = "on";
	
	/** Draw vertical grid lines in scatter plot? */
	private String drawVertGridLines = "on";
	
	/** Draw data points in scatter plot? */
	private String drawPoints = "on";
	
	/** Draw regression lines in scatter plot? */
	private String drawLines = "on";
	
	/** Draw error bars in scatter plot? */
	private String drawErrorBars = "";
	
	/**
	 * Threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 */
	private String lohThreshold =
		String.valueOf(BaseGenomicPlotParameters.DEF_LOH_THRESHOLD);
	
	/**
	 * Interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 */
	private String interpolateLohEndpoints = "";
	
	/** Draw raw LOH probabilities? */
	private String drawRawLohProbabilities = "on";
	
	/** Type of interpolation to perform between data points. */
	private String interpolationType =
		InterpolationType.STRAIGHT_LINE.toString();
	
	/** Show reporter annotation in mouseover? */
	private String showAnnotation = "";
	
	/** Show gene names in mouseover? */
	private String showGenes = "on";
	
	/** Show reporter anmes in mouseover? */
	private String showReporterNames = "on";
	
	/** Width of bar plot bar in pixels. */
	private String barWidth = DEF_BAR_WIDTH;
	
	/** Selected annotation types. */
	private String[] annotationTypes = null;
	
	// ================================
	//      Getters/setters
	// ================================

	/**
	 * Get genome intervals.
	 * Genome intervals are encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 * @return Genome intervals
	 */
	public final String getGenomeIntervals() {
		return genomeIntervals;
	}

	/**
	 * Set genome intervals.
	 * Genome intervals are encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 * @param genomeIntervals Genome intervals
	 */
	public final void setGenomeIntervals(final String genomeIntervals) {
		this.genomeIntervals = genomeIntervals;
	}
	
	/**
	 * Get selected annotation types.
	 * @return Selected annotation types.
	 */
	public final String[] getAnnotationTypes() {
		return annotationTypes;
	}

	/**
	 * Set selected annotation types.
	 * @param annotationTypes Selected annotation types
	 */
	public final void setAnnotationTypes(final String[] annotationTypes) {
		this.annotationTypes = annotationTypes;
	}

	/**
	 * Get bar plot bar width.
	 * @return Width in pixels
	 */
	public final String getBarWidth() {
		return barWidth;
	}

	/**
	 * Set bar plot bar width.
	 * @param barWidth Width in pixels
	 */
	public final void setBarWidth(final String barWidth) {
		this.barWidth = barWidth;
	}

	/**
	 * Show reporter names in mouseover?
	 * @return <code>on</code> or empty string
	 */
	public final String getShowReporterNames() {
		return showReporterNames;
	}

	
	/**
	 * Specifies whether to show reporter names in
	 * mouseover text.
	 * @param showReporterNames Show reporter names in mouseover?
	 */
	public final void setShowReporterNames(
			final String showReporterNames) {
		this.showReporterNames = showReporterNames;
	}

	/**
	 * Show reporter annotation in mouseover?
	 * @return Whether reporter annotation should be shown
	 * in mouseover
	 */
	public final String getShowAnnotation() {
		return showAnnotation;
	}

	
	/**
	 * Set whether reporter annotation should be shown in mouseover.
	 * @param showAnnotation Should reporter annotation be
	 * shown in mouseover?
	 */
	public final void setShowAnnotation(final String showAnnotation) {
		this.showAnnotation = showAnnotation;
	}

	
	/**
	 * Should gene names be shown in mouseover?
	 * @return Whether gene names should be shown in
	 * mouseover.
	 */
	public final String getShowGenes() {
		return showGenes;
	}

	
	/**
	 * Set whether gene names should be shown in mouseover.
	 * @param showGenes Should gene names be shown in
	 * mouseover?
	 */
	public final void setShowGenes(final String showGenes) {
		this.showGenes = showGenes;
	}

	/**
	 * Get type of interpolation to perform between data points.
	 * @return Type of interpolation
	 */
	public final String getInterpolationType() {
		return interpolationType;
	}

	/**
	 * Set type of interpolation to perform between data points.
	 * @param interpolationType Type of interpolation
	 */
	public final void setInterpolationType(
			final String interpolationType) {
		this.interpolationType = interpolationType;
	}

	/**
	 * Draw raw LOH probabilities?
	 * @return "on" or ""
	 */
	public final String getDrawRawLohProbabilities() {
		return drawRawLohProbabilities;
	}

	/**
	 * Set whether to draw raw LOH probabilities.
	 * @param drawRawLohProbabilities Draw raw LOH probabilities?
	 */
	public final void setDrawRawLohProbabilities(
			final String drawRawLohProbabilities) {
		this.drawRawLohProbabilities = drawRawLohProbabilities;
	}

	/**
	 * Interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @return T/F
	 */
	public final String getInterpolateLohEndpoints() {
		return interpolateLohEndpoints;
	}

	/**
	 * Set whether to interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @param interpolateLohEndpoints Interpolate LOH endpoints?
	 */
	public final void setInterpolateLohEndpoints(
			final String interpolateLohEndpoints) {
		this.interpolateLohEndpoints = interpolateLohEndpoints;
	}

	/**
	 * Get threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @return LOH threshold probability
	 */
	public final String getLohThreshold() {
		return lohThreshold;
	}

	
	/**
	 * Set threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @param lohThreshold LOH threshold probability
	 */
	public final void setLohThreshold(final String lohThreshold) {
		this.lohThreshold = lohThreshold;
	}

	/**
	 * Draw error bars in scatter plot?
	 * @return "on" or ""
	 */
	public final String getDrawErrorBars() {
		return drawErrorBars;
	}

	/**
	 * Set whether error bars should be drawn in scatter plots.
	 * @param drawErrorBars Draw error bars in scatter plots?
	 */
	public final void setDrawErrorBars(final String drawErrorBars) {
		this.drawErrorBars = drawErrorBars;
	}

	/**
	 * Draw regression lines in scatter plots?
	 * @return "on" or ""
	 */
	public final String getDrawLines() {
		return drawLines;
	}

	/**
	 * Sets whether regression lines should be drawn in scatter plots.
	 * @param drawLines Draw lines in scatter plots?
	 */
	public final void setDrawLines(final String drawLines) {
		this.drawLines = drawLines;
	}

	/**
	 * Draw data points in scatter plots?
	 * @return "on" or ""
	 */
	public final String getDrawPoints() {
		return drawPoints;
	}

	/**
	 * Set whether data points should be drawn in scatter plots.
	 * @param drawPoints Draw points in scatter plots?
	 */
	public final void setDrawPoints(final String drawPoints) {
		this.drawPoints = drawPoints;
	}

	/**
	 * Draw horizontal grid lines?
	 * @return T/F
	 */
	public final String getDrawHorizGridLines() {
		return drawHorizGridLines;
	}

	/**
	 * Sets whether horizontal grid lines should be drawn.
	 * @param drawHorizGridLines Draw horizontal grid lines?
	 */
	public final void setDrawHorizGridLines(
			final String drawHorizGridLines) {
		this.drawHorizGridLines = drawHorizGridLines;
	}

	/**
	 * Draw vertical grid lines?
	 * @return T/F
	 */
	public final String getDrawVertGridLines() {
		return drawVertGridLines;
	}

	/**
	 * Sets whether vertical grid lines should be drawn.
	 * @param drawVertGridLines Draw vertical grid lines?
	 */
	public final void setDrawVertGridLines(
			final String drawVertGridLines) {
		this.drawVertGridLines = drawVertGridLines;
	}

	/**
	 * Get plot name.
	 * @return Plot name.
	 */
	public final String getName() {
		return name;
	}

	
	/**
	 * Set plot name.
	 * @param name Plot name.
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get maximum Y-axis value.
	 * @return Maximum Y-axis value.
	 */
	public final String getMaxY() {
		return maxY;
	}

	/**
	 * Set maximum Y-axis value.
	 * @param maxY Maximum Y-axis value.
	 */
	public final void setMaxY(final String maxY) {
		this.maxY = maxY;
	}

	/**
	 * Get minimum Y-axis value.
	 * @return Minimum Y-axis value.
	 */
	public final String getMinY() {
		return minY;
	}

	/**
	 * Set minimum Y-axis value.
	 * @param minY Minimum Y-axis value.
	 */
	public final void setMinY(final String minY) {
		this.minY = minY;
	}

	/**
	 * Get number of plots per row.
	 * @return Number of plots per row.
	 */
	public final String getNumPlotsPerRow() {
		return numPlotsPerRow;
	}

	/**
	 * Set number of plots per row.
	 * @param numPlotPerRow Number of plots per row.
	 */
	public final void setNumPlotsPerRow(final String numPlotPerRow) {
		this.numPlotsPerRow = numPlotPerRow;
	}

	/**
	 * Get plot type.
	 * @return Plot type.
	 */
	public final String getPlotType() {
		return plotType;
	}

	/**
	 * Set plot type.
	 * @param plotType Plot type.
	 */
	public final void setPlotType(final String plotType) {
		this.plotType = plotType;
	}
	

	/**
	 * Get plot height.
	 * @return Height in pixels.
	 */
	public final String getHeight() {
		return height;
	}

	/**
	 * Set plot height.
	 * @param height Height in pixels.
	 */
	public final void setHeight(final String height) {
		this.height = height;
	}

	/**
	 * Get plot width.
	 * @return Width in pixels.
	 */
	public final String getWidth() {
		return width;
	}

	/**
	 * Set plot width.
	 * @param width Width in pixels.
	 */
	public final void setWidth(final String width) {
		this.width = width;
	}

	/**
	 * Get base pair units.
	 * @return Base pair units.
	 */
	public final String getUnits() {
		return units;
	}

	/**
	 * Set base pair units.
	 * @param units Base pair units.
	 */
	public final void setUnits(final String units) {
		this.units = units;
	}
	
	
	/**
	 * Get ideogram size.
	 * @return Ideogram size
	 */
	public final String getIdeogramSize() {
		return ideogramSize;
	}

	/**
	 * Set ideogram size.
	 * @param ideogramSize Ideogram size
	 */
	public final void setIdeogramSize(final String ideogramSize) {
		this.ideogramSize = ideogramSize;
	}

	/**
	 * Get thickness of ideogram.
	 * @return Ideogram thickness.
	 */
	public final String getIdeogramThickness() {
		return ideogramThickness;
	}

	
	/**
	 * Set ideogram thickness.
	 * @param ideogramThickness Ideogram thickness.
	 */
	public final void setIdeogramThickness(
			final String ideogramThickness) {
		this.ideogramThickness = ideogramThickness;
	}

	/**
	 * Get maximum data mask value.
	 * @return Maximum data mask value.
	 */
	public final String getMaxMask() {
		return maxMask;
	}

	
	/**
	 * Set maximum data mask value.
	 * @param maxMask Maximum data mask value.
	 */
	public final void setMaxMask(final String maxMask) {
		this.maxMask = maxMask;
	}

	/**
	 * Get maximum saturation value for color coding plots.
	 * @return Maximum saturation value.
	 */
	public final String getMaxSaturation() {
		return maxSaturation;
	}

	
	/**
	 * Set maximum saturation value for color coding plots.
	 * @param maxSaturation Maximum saturation value.
	 */
	public final void setMaxSaturation(final String maxSaturation) {
		this.maxSaturation = maxSaturation;
	}

	/**
	 * Get minimum data mask value.
	 * @return Minimum data maks value.
	 */
	public final String getMinMask() {
		return minMask;
	}

	
	/**
	 * Set minimum data mask value.
	 * @param minMask Minimum data mask value.
	 */
	public final void setMinMask(final String minMask) {
		this.minMask = minMask;
	}

	
	/**
	 * Get minimum saturation value for color coding plots.
	 * @return Minimum saturation value.
	 */
	public final String getMinSaturation() {
		return minSaturation;
	}

	
	/**
	 * Set minimum saturation value for color coding plots.
	 * @param minSaturation Minimum saturation value.
	 */
	public final void setMinSaturation(final String minSaturation) {
		this.minSaturation = minSaturation;
	}

	
	/**
	 * Get width of data tracks.
	 * @return Data track width.
	 */
	public final String getTrackWidth() {
		return trackWidth;
	}

	
	/**
	 * Set data track width.
	 * @param trackWidth Data track width.
	 */
	public final void setTrackWidth(final String trackWidth) {
		this.trackWidth = trackWidth;
	}
	
	
	// ===================================
	//       Overrides
	// ===================================
	
	
	/**
	 * Reset form fields.
	 * @param mapping Action mapping
	 * @param request Servlet request
	 */
	@Override
	public final void reset(final ActionMapping mapping,
			final HttpServletRequest request) {
		
		// Turn off scatter plot checkbox fields.
		// This should only
		// be done if the JSP immediately upstream actually
		// included an HTML form for setting scatter plot
		// parameters.
		if (this.scatterPlotParamsHtmlFormUpstream(request)) {
			this.drawHorizGridLines = "";
			this.drawVertGridLines = "";
			this.drawErrorBars = "";
			this.drawLines = "";
			this.drawPoints = "";
		}
		
		// Turn off general genomic plot parameter checkbox fields.
		// This should only
		// be done if the JSP immediately upstream actually
		// included an HTML form for setting plot
		// parameters.
		if (this.genomicPlotParamsHtmlFormUpstream(request)) {
			this.interpolateLohEndpoints = "";
			this.drawRawLohProbabilities = "";
			this.showAnnotation = "";
			this.showGenes = "";
			this.showReporterNames = "";
		}
	}
	
	
	/**
	 * Determines if the immediate upstream JSP included an
	 * HTML form for setting scatter plot parameters.
	 * @param request Servlet request
	 * @return T/F
	 */
	private boolean scatterPlotParamsHtmlFormUpstream(
			final HttpServletRequest request) {
		return request.getParameter(
				SCATTER_PLOT_PARAMETERS_FORM_INDICATOR_PARAMETER) != null;
	}
	
	
	/**
	 * Determines if the immediate upstream JSP included an
	 * HTML form for setting genomic plot parameters.
	 * @param request Servlet request
	 * @return T/F
	 */
	private boolean genomicPlotParamsHtmlFormUpstream(
			final HttpServletRequest request) {
		return request.getParameter(
				GENOMIC_PLOT_PARAMETERS_FORM_INDICATOR_PARAMETER)
				!= null;
	}

	/**
	 * Validate form fields.
	 * @param actionMappings Action mappings.
	 * @param request Servlet request.
	 * @return Action errors
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		PlotType plotType = PlotType.valueOf(this.plotType);
		
		// Fields common to all plot types
		this.validateCommonFields(errors);
		
		// Bar plot-specific fields
		if (plotType == PlotType.BAR) {
			this.validateBarPlotFields(errors);
			
		// Genomic plot types
		} else {
		
			// Fields common to all remaining plot types
			this.validateCommonGenomicPlotFields(errors);
			
			// Scatter plot-specific fields
			if (plotType == PlotType.SCATTER) {
				this.validateScatterPlotFields(errors);
				
			// Ideogram plot-specific fields
			} else if (plotType == PlotType.IDEOGRAM) {
				this.validateIdeogramPlotFields(errors);
			}
		}
		
		if (errors.size() > 0) {
			errors.add("global", new ActionError("invalid.fields"));
		}
		
		return errors;
	}
	
	
	/**
	 * Validate fields common to all plots types.
	 * @param errors Errors
	 */
	private void validateCommonFields(final ActionErrors errors) {
		
		// numPlotsPerRow
		if (!ValidationUtils.validNumber(this.numPlotsPerRow)) {
			errors.add("numPlotsPerRow", new ActionError("invalid.field"));
		}
		
		// genomeIntervals
		if (this.genomeIntervals != null && this.genomeIntervals.length() > 0) {
			try {
				List<GenomeInterval> intervals = GenomeInterval.decode(
						this.genomeIntervals);
				for (GenomeInterval interval : intervals) {
					short chrom = interval.getChromosome();
					long start = interval.getStartLocation();
					long end = interval.getEndLocation();
					if (chrom < 0 || end < start || (start < 0 && end >= 0)
							|| (start >= 0 && end < 0)) {
						errors.add("genomeIntervals",
								new ActionError("invalid.field"));
						break;
					}
				}
			} catch (GenomeIntervalFormatException e) {
				errors.add("genomeIntervals", new ActionError("invalid.field"));
			}
		}
	}
	
	
	/**
	 * Validate bar plot fields.
	 * @param errors Errors
	 */
	private void validateBarPlotFields(final ActionErrors errors) {
		
		// height
		if (!ValidationUtils.validNumber(this.height)) {
			errors.add("height", new ActionError("invalid.field"));
		}
		
		// barWidth
		if (!ValidationUtils.validNumber(this.barWidth)) {
			errors.add("barWidth", new ActionError("invalid.field"));
		}
	}
	
	
	/**
	 * Validate fields common to all genomic plot types.
	 * @param errors Action errors.
	 */
	private void validateCommonGenomicPlotFields(final ActionErrors errors) {
		
		// lohThreshold
		if (!ValidationUtils.validNumber(this.lohThreshold)) {
			errors.add("lohThreshold", new ActionError(
					"invalid.field"));
		}
	}
	
	
	/**
	 * Validate scatter plot-specific fields.
	 * @param errors Action errors
	 */
	private void validateScatterPlotFields(
			final ActionErrors errors) {
		
		// minY and maxY
		boolean validateRelationship = true;
		if (this.minY != null && this.minY.length() > 0) {
			if (!ValidationUtils.validNumber(this.minY)) {
				errors.add("minY", new ActionError("invalid.field"));
				validateRelationship = false;
			}
		} else {
			validateRelationship = false;
		}
		if (this.maxY != null && this.maxY.length() > 0) {
			if (!ValidationUtils.validNumber(this.maxY)) {
				errors.add("maxY", new ActionError("invalid.field"));
				validateRelationship = false;
			}
		} else {
			validateRelationship = false;
		}
		if (validateRelationship) {
			if (Double.parseDouble(this.minY) > Double.parseDouble(this.maxY)) {
				errors.add("minY", new ActionError("invalid.field"));
				errors.add("maxY", new ActionError("invalid.field"));
			}
		}
		
		// width
		if (!ValidationUtils.validNumber(this.width)) {
			errors.add("width", new ActionError("invalid.field"));
		}
		
		// height
		if (!ValidationUtils.validNumber(this.height)) {
			errors.add("height", new ActionError("invalid.field"));
		}
	}
	
	
	/**
	 * Validate ideogram plot-specific fields.
	 * @param errors Action errors
	 */
	private void validateIdeogramPlotFields(
			final ActionErrors errors) {
		
		// trackWidth
		if (!ValidationUtils.validNumber(this.trackWidth)) {
			errors.add("trackWidth", new ActionError("invalid.field"));
		}
		
		// minSaturation
		if (!StringUtils.isEmpty(this.minSaturation)) {
			if (!ValidationUtils.validNumber(this.minSaturation)) {
				errors.add("minSaturation", new ActionError("invalid.field"));
			}
		}
		
		// maxSaturation
		if (!StringUtils.isEmpty(this.maxSaturation)) {
			if (!ValidationUtils.validNumber(this.maxSaturation)) {
				errors.add("maxSaturation", new ActionError("invalid.field"));
			}
		}
		
		// minMask
		if (!StringUtils.isEmpty(this.minMask)) {
			if (!ValidationUtils.validNumber(this.minMask)) {
				errors.add("minMask", new ActionError("invalid.field"));
			}
		}
		
		// maxMask
		if (!StringUtils.isEmpty(this.maxMask)) {
			if (!ValidationUtils.validNumber(this.maxMask)) {
				errors.add("maxMask", new ActionError("invalid.field"));
			}
		}
		
		// ideogramThickness
		if (!ValidationUtils.validNumber(this.ideogramThickness)) {
			errors.add("ideogramThickness", new ActionError("invalid.field"));
		}
	}

	
	// ====================================
	//     Additional business methods
	// ====================================
	
	/**
	 * Initialize form values.
	 */
	public final void init() {
		this.plotType = DEF_PLOT_TYPE;
		this.genomeIntervals = DEF_GENOME_INTERVALS;
		this.units = DEF_UNITS;
		this.numPlotsPerRow = DEF_NUM_PLOTS_PER_ROW;
		this.minY = "";
		this.maxY = "";
		this.width = DEF_WIDTH;
		this.height = DEF_HEIGHT;
		this.name = "";
		this.ideogramSize = IdeogramPlotParameters.DEF_IDEOGRAM_SIZE.getName();
		this.ideogramThickness = String.valueOf(
				IdeogramPlotParameters.DEF_IDEOGRAM_THICKNESS);
		this.maxMask = "";
		this.maxSaturation = String.valueOf(
				HeatMapPlotParameters.DEF_MAX_SATURATION);
		this.minMask = "";
		this.minSaturation = String.valueOf(
				HeatMapPlotParameters.DEF_MIN_SATURATION);
		this.drawHorizGridLines = "on";
		this.drawVertGridLines = "on";
		this.drawErrorBars = "";
		this.drawLines = "on";
		this.drawPoints = "on";
		this.lohThreshold = String.valueOf(
				BaseGenomicPlotParameters.DEF_LOH_THRESHOLD);
		this.interpolateLohEndpoints = "";
		this.drawRawLohProbabilities = "on";
		this.interpolationType =
			BaseGenomicPlotParameters.DEF_INTERPOLATION_TYPE.toString();
		this.showAnnotation = "";
		this.showGenes = "on";
		this.showReporterNames = "on";
		this.barWidth = DEF_BAR_WIDTH;
	}
	
	/**
	 * Extract plot parameters.
	 * @return Plot parameters
	 */
	public final PlotParameters getPlotParameters() {
		PlotParameters p = null;
		PlotType type = PlotType.valueOf(this.plotType);
		
		// Bar plot
		if (type == PlotType.BAR) {
			p = new BarPlotParameters();
			this.setBarPlotParameters((BarPlotParameters) p);
			
		
		// Other plot types
		} else {
			
			// Scatter plot
			if (type == PlotType.SCATTER) {
				p = new ScatterPlotParameters();
				this.setScatterPlotParameters((ScatterPlotParameters) p);
				
			// Ideogram plot
			} else if (type == PlotType.IDEOGRAM) {
				p = new IdeogramPlotParameters();
				this.setIdeogramPlotParameters((IdeogramPlotParameters) p);
			}
			
			// Attributes common to heat map plots
			if (type == PlotType.IDEOGRAM || type == PlotType.ANNOTATION) {
				if (p != null) {
					this.setCommonHeatMapPlotParameters(
							(HeatMapPlotParameters) p);
				}
			}
			
			// Attributes common to genomic plots
			if (p != null) {
				this.setCommonGenomicPlotParameters(
						(BaseGenomicPlotParameters) p);
			}
		}
		
		// Attributes common to all plot types
		if (p != null) {
			this.setCommonPlotParameters(p);
		}
		
		return p;
	}
	
	
	/**
	 * Set plot parameters common to all plot types.
	 * @param params Plot parameters
	 */
	private void setCommonPlotParameters(final PlotParameters params) {
		params.setPlotName(this.name);
		params.setNumPlotsPerRow(Integer.parseInt(this.numPlotsPerRow));
		try {
			params.setGenomeIntervals(GenomeInterval.decode(
					this.genomeIntervals));
		} catch (GenomeIntervalFormatException e) {
			throw new WebcghSystemException(
					"Error extracting plot parameters", e);
		}
		params.setUnits(BpUnits.getUnits(this.units));
	}
	
	
	/**
	 * Set bar plot parameters.
	 * @param params Plot parameters
	 */
	private void setBarPlotParameters(final BarPlotParameters params) {
		params.setRowHeight(Integer.parseInt(this.height));
		params.setBarWidth(Integer.parseInt(this.barWidth));
	}
	
	
	/**
	 * Set plot parameters common to all genomic plot types.
	 * @param params Plot parameters
	 */
	private void setCommonGenomicPlotParameters(
			final BaseGenomicPlotParameters params) {
		if (!StringUtils.isEmpty(this.lohThreshold)) {
			params.setLohThreshold(Float.parseFloat(this.lohThreshold));
		}
		if ("on".equals(this.interpolateLohEndpoints)) {
			params.setInterpolateLohEndpoints(true);
		} else {
			params.setInterpolateLohEndpoints(false);
		}
		if ("on".equals(this.drawRawLohProbabilities)) {
			params.setDrawRawLohProbabilities(true);
		} else {
			params.setDrawRawLohProbabilities(false);
		}
		params.setInterpolationType(
				InterpolationType.valueOf(this.interpolationType));
		if ("on".equals(this.showAnnotation)) {
			params.setShowAnnotation(true);
		} else {
			params.setShowAnnotation(false);
		}
		if ("on".equals(this.showGenes)) {
			params.setShowGenes(true);
		} else {
			params.setShowGenes(false);
		}
		if ("on".equals(this.showReporterNames)) {
			params.setShowReporterNames(true);
		} else {
			params.setShowReporterNames(false);
		}
	}
	
	
	/**
	 * Set parameters common to all heat map plots.
	 * @param params Plot parameters
	 */
	private void setCommonHeatMapPlotParameters(
			final HeatMapPlotParameters params) {
		if (!StringUtils.isEmpty(this.maxMask)) {
			params.setMaxMask(Float.parseFloat(this.maxMask));
		} else {
			params.setMaxMask(Float.MIN_VALUE);
		}
		if (!StringUtils.isEmpty(this.maxSaturation)) {
			params.setMaxSaturation(Float.parseFloat(this.maxSaturation));
		} else {
			params.setMaxSaturation(Float.NaN);
		}
		if (!StringUtils.isEmpty(this.minMask)) {
			params.setMinMask(Float.parseFloat(this.minMask));
		} else {
			params.setMinMask(Float.MAX_VALUE);
		}
		if (!StringUtils.isEmpty(this.minSaturation)) {
			params.setMinSaturation(Float.parseFloat(this.minSaturation));
		} else {
			params.setMinSaturation(Float.NaN);
		}
	}
	
	
	/**
	 * Initialize scatter plot parameters.
	 * @param params Plot parameters
	 */
	private void setScatterPlotParameters(final ScatterPlotParameters params) {
		float minYFloat = Float.NaN;
		if (this.minY != null && this.minY.length() > 0) {
			minYFloat = Float.parseFloat(this.minY);
			params.setMinY(minYFloat);
		}
		float maxYFloat = Float.NaN;
		if (this.maxY != null && this.maxY.length() > 0) {
			maxYFloat = Float.parseFloat(this.maxY);
			params.setMaxY(maxYFloat);
		}
		params.setWidth(Integer.parseInt(this.width));
		params.setHeight(Integer.parseInt(this.height));
		if ("on".equals(this.drawHorizGridLines)) {
			params.setDrawHorizGridLines(true);
		} else {
			params.setDrawHorizGridLines(false);
		}
		if ("on".equals(this.drawVertGridLines)) {
			params.setDrawVertGridLines(true);
		} else {
			params.setDrawVertGridLines(false);
		}
		if ("on".equals(this.drawErrorBars)) {
			params.setDrawErrorBars(true);
		} else {
			params.setDrawErrorBars(false);
		}
		if ("on".equals(this.drawPoints)) {
			params.setDrawPoints(true);
		} else {
			params.setDrawPoints(false);
		}
	}
	
	
	/**
	 * Initialize ideogram plot parameters.
	 * @param params Plot parameters
	 */
	private void setIdeogramPlotParameters(
			final IdeogramPlotParameters params) {
		params.setIdeogramSize(
				ChromosomeIdeogramSize.getChromosomeIdeogramSize(
						this.ideogramSize));
		params.setIdeogramThickness(Integer.parseInt(this.ideogramThickness));
		if (!StringUtils.isEmpty(this.trackWidth)) {
			params.setTrackWidth(Integer.parseInt(this.trackWidth));
		}
	}
	
	
	/**
	 * Bulk set properties using properties of given plot parameters.
	 * @param plotParameters Plot parameters
	 */
	public final void bulkSet(final PlotParameters plotParameters) {
		
		// Set plot type
		if (plotParameters instanceof ScatterPlotParameters) {
			this.plotType = PlotType.SCATTER.toString();
		} else if (plotParameters instanceof IdeogramPlotParameters) {
			this.plotType = PlotType.IDEOGRAM.toString();
		} else if (plotParameters instanceof BarPlotParameters) {
			this.plotType = PlotType.BAR.toString();
		}
		PlotType plotType = PlotType.valueOf(this.plotType);
		
		// Attributes common to all plot types
		this.bulkSetCommontPlotAttributes(plotParameters);
		
		// Bar plot attributes
		if (plotType == PlotType.BAR) {
			this.bulkSetBarPlotAttributes(
					(BarPlotParameters) plotParameters);
			
		// Genomic plots
		} else {
			this.bulkSetCommonGenomicPlotAttributes(
					(BaseGenomicPlotParameters) plotParameters);
			
			if (plotType == PlotType.ANNOTATION
					|| plotType == PlotType.IDEOGRAM) {
				this.bulkSetCommonHeatMapPlotAttributes(
						(HeatMapPlotParameters) plotParameters);
			}
			
			// Scatter plot
			if (plotType == PlotType.SCATTER) {
				this.bulkSetScatterPlotAttributes(
						(ScatterPlotParameters) plotParameters);
				
			// Ideogram plot
			} else if (plotType == PlotType.IDEOGRAM) {
				this.bulkSetIdeogramPlotAttributes(
						(IdeogramPlotParameters) plotParameters);
			}
		}
	}
	
	
	/**
	 * Bulk set attributes common to all plot types.
	 * @param plotParameters Plot parameters
	 */
	private void bulkSetCommontPlotAttributes(
			final PlotParameters plotParameters) {
		this.name = plotParameters.getPlotName();
		this.numPlotsPerRow =
			String.valueOf(plotParameters.getNumPlotsPerRow());
		this.genomeIntervals = GenomeInterval.encode(
				plotParameters.getGenomeIntervals());
		this.units = plotParameters.getUnits().getName();
	}
	
	
	/**
	 * Bulk-set bar plot attributes.
	 * @param plotParameters Plot parameters
	 */
	private void bulkSetBarPlotAttributes(
			final BarPlotParameters plotParameters) {
		this.height = String.valueOf(plotParameters.getRowHeight());
		this.barWidth = String.valueOf(plotParameters.getBarWidth());
	}
	
	
	/**
	 * Bulk set attributes common to all heat map plots by deep copy.
	 * @param params Parameters to deep copy
	 */
	private void bulkSetCommonHeatMapPlotAttributes(
			final HeatMapPlotParameters params) {
		if (!StringUtils.isEmpty(this.maxMask)) {
			params.setMaxMask(Float.parseFloat(this.maxMask));
		} else {
			params.setMaxMask(Float.MIN_VALUE);
		}
		if (!StringUtils.isEmpty(this.maxSaturation)) {
			params.setMaxSaturation(Float.parseFloat(this.maxSaturation));
		} else {
			params.setMaxSaturation(Float.NaN);
		}
		if (!StringUtils.isEmpty(this.minMask)) {
			params.setMinMask(Float.parseFloat(this.minMask));
		} else {
			params.setMinMask(Float.MAX_VALUE);
		}
		if (!StringUtils.isEmpty(this.minSaturation)) {
			params.setMinSaturation(Float.parseFloat(this.minSaturation));
		} else {
			params.setMinSaturation(Float.NaN);
		}
	}
	
	
	/**
	 * Bulk set attributes common to all genomic plots.
	 * @param plotParameters Plot parameters
	 */
	private void bulkSetCommonGenomicPlotAttributes(
			final BaseGenomicPlotParameters plotParameters) {
		this.lohThreshold = String.valueOf(plotParameters.getLohThreshold());
		if (plotParameters.isInterpolateLohEndpoints()) {
			this.interpolateLohEndpoints = "on";
		} else {
			this.interpolateLohEndpoints = "";
		}
		if (plotParameters.isDrawRawLohProbabilities()) {
			this.drawRawLohProbabilities = "on";
		} else {
			this.drawRawLohProbabilities = "";
		}
		this.interpolationType =
			plotParameters.getInterpolationType().toString();
		if (plotParameters.isShowAnnotation()) {
			this.showAnnotation = "on";
		} else {
			this.showAnnotation = "";
		}
		if (plotParameters.isShowGenes()) {
			this.showGenes = "on";
		} else {
			this.showGenes = "";
		}
		if (plotParameters.isShowReporterNames()) {
			this.showReporterNames = "on";
		} else {
			this.showReporterNames = "";
		}
	}
	
	
	/**
	 * Bulk set scatter plot attributes.
	 * @param plotParameters Plot parameters
	 */
	private void bulkSetScatterPlotAttributes(
			final ScatterPlotParameters plotParameters) {
		
		this.height = String.valueOf(plotParameters.getHeight());
		this.width = String.valueOf(plotParameters.getWidth());
		
		// minY
		if (NumericUtils.isReal(plotParameters.getMinY())) {
			this.minY = String.valueOf(plotParameters.getMinY());
		} else {
			this.minY = "";
		}
		
		// maxY
		if (NumericUtils.isReal(plotParameters.getMaxY())) {
			this.maxY = String.valueOf(plotParameters.getMaxY());
		} else {
			this.maxY = "";
		}
		
		// drawHorizGridLines
		if (plotParameters.isDrawHorizGridLines()) {
			this.drawHorizGridLines = "on";
		} else {
			this.drawHorizGridLines = "";
		}
		
		// drawVertGridLines
		if (plotParameters.isDrawVertGridLines()) {
			this.drawVertGridLines = "on";
		} else {
			this.drawVertGridLines = "";
		}
		
		// drawErrorBars
		if (plotParameters.isDrawErrorBars()) {
			this.drawErrorBars = "on";
		} else {
			this.drawErrorBars = "";
		}
		
		// drawPoints
		if (plotParameters.isDrawPoints()) {
			this.drawPoints = "on";
		} else {
			this.drawPoints = "";
		}
	}
			
	
	/**
	 * Bulk-set ideogram plot attributes.
	 * @param plotParameters Plot parameters
	 */
	private void bulkSetIdeogramPlotAttributes(
			final IdeogramPlotParameters plotParameters) {
			
		// ideogramSize
		this.ideogramSize = plotParameters.getIdeogramSize().getName();
		
		// ideogramThickness
		this.ideogramThickness =
			String.valueOf(plotParameters.getIdeogramThickness());
		
		// trackWidth
		this.trackWidth = String.valueOf(plotParameters.getTrackWidth());
	}
}
