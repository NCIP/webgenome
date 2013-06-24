/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2007-09-09 17:16:05 $


*/

package org.rti.webgenome.service.plot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.domain.AnnotationType;

/**
 * Parameters for generating an annotation plot.
 * @author dhall
 *
 */
public class AnnotationPlotParameters extends HeatMapPlotParameters {
	
	//
	//  C O N S T A N T S
	//
	
	/** Default value for drawing feature labels. */
	public static final boolean DEF_DRAW_FEATURE_LABELS = false;

	//
	//     ATTRIBUTES
	//
	
	/** Annotation types to plot. */
	private Set<AnnotationType> annotationTypes =
		new HashSet<AnnotationType>();

	/** Width of plot excluding track labels to the left in pixels. */
	private int width = DEF_WIDTH;
	
	/** Draw labels above features? */
	private boolean drawFeatureLabels = DEF_DRAW_FEATURE_LABELS;
	
	//
	//     GETTERS/SETTERS
	//
	
	/**
	 * Get annotation types to plot.
	 * @return Annotation type names
	 */
	public final Set<AnnotationType> getAnnotationTypes() {
		return annotationTypes;
	}

	/**
	 * Set annotation types to plot.
	 * @param annotationTypes Annotation type names
	 */
	public final void setAnnotationTypes(
			final Set<AnnotationType> annotationTypes) {
		this.annotationTypes = annotationTypes;
	}

	/**
	 * Draw labels above features?
	 * @return T/F
	 */
	public final boolean isDrawFeatureLabels() {
		return drawFeatureLabels;
	}

	/**
	 * Sets whether to draw labels above features.
	 * @param drawFeatureLabels Draw labels above features?
	 */
	public final void setDrawFeatureLabels(final boolean drawFeatureLabels) {
		this.drawFeatureLabels = drawFeatureLabels;
	}

	/**
	 * Get width of plot excluding track labels to the left in pixels.
	 * @return Width in pixels
	 */
	public final int getWidth() {
		return width;
	}

	
	/**
	 * Set width of plot excluding track labels to the left in pixels.
	 * @param width Width in pixels
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	public AnnotationPlotParameters() {
		
	}
	
	/**
	 * Constructor that uses deep copy to initialize properties.
	 * @param params Parameters to deep copy
	 */
	public AnnotationPlotParameters(final AnnotationPlotParameters params) {
		super(params);
		this.drawFeatureLabels = params.drawFeatureLabels;
		this.width = params.width;
		this.annotationTypes = new HashSet<AnnotationType>(
				params.getAnnotationTypes());
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Add annotation type.
	 * @param type An annotation type
	 */
	public final void add(final AnnotationType type) {
		this.annotationTypes.add(type);
	}
	
	
	/**
	 * Get annotatation types by name.  This is used to persist
	 * the enumeration values.
	 * @return Annotation types by name.
	 */
	public final Set<String> getAnnotationTypesByName() {
		Set<String> names = new HashSet<String>();
		for (AnnotationType type : this.annotationTypes) {
			names.add(type.name());
		}
		return names;
	}
	
	
	/**
	 * Set annotation types by names of enumeration values.
	 * @param names Names of enumeration values
	 */
	public final void setAnnotationTypesByName(
			final Collection<String> names) {
		for (String name : names) {
			this.add(AnnotationType.valueOf(name));
		}
	}
	
	
	//
	//     ABSTRACTS
	//
	
    /**
     * Return clone of this object derived by deep copy of
     * all attributes.
     * @return Clone of this object
     */
    public PlotParameters deepCopy() {
    	return new AnnotationPlotParameters(this);
    }
}
