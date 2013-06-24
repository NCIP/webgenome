/*
$Revision: 1.5 $
$Date: 2007-09-10 21:00:40 $


*/


package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.graphics.io.ClickBoxes;
import org.rti.webgenome.service.plot.PlotParameters;
import org.rti.webgenome.util.SystemUtils;

/**
 * Represents a plot of data.  A plot is an
 * aggregate of image files with some additional
 * metadata.  If there are N bioassays in the
 * plot, there will be N + 1 separate image files.
 * In one file, no bioassays will be highlighted.
 * In the remainder, one bioassay will be highlighted.
 */
public class Plot implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // =============================
    //      Attributes
    // =============================
    
    /** Primary key identifier for persistence. */
    private Long id = null;
    
    /** Name of default image files. */
    private String defaultImageFileName = null;
    
    /**
     * Map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
     */
    private Map<String, String> imageFileMap = new HashMap<String, String>();
    
    /**
     * Click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
     */
    private Set<ClickBoxes> clickBoxes = null;
    
    /**
     * Click boxes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
     */
    private String clickBoxesFileName = null;
    
    /**
     * Mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
     */
    private Set<MouseOverStripes> mouseOverStripes = null;
    
    /**
     * Mouse over stripes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
     */
    private String mouseOverStripesFileName = null;
    
    /** Width of entire plot image in pixels. */
    private int width = 0;
    
    /** Height of entire plot image in pixels. */
    private int height = 0;
    
    /** Plot parameters. */
    private PlotParameters plotParameters = null;
    
    /** Experiments in plot. */
    private Set<Experiment> experiments = new HashSet<Experiment>();
    
    
    // ================================
    //       Getters/setters
    // ================================

    /**
     * Get primary key identifier.
     * @return Primary key identifier.
     */
	public final Long getId() {
		return id;
	}

	/**
	 * Set primary key identifier.
	 * @param id Primary key identifier.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	
	/**
	 * Click boxes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
	 * @return File name where click boxes are persisted.
	 */
	public String getClickBoxesFileName() {
		return clickBoxesFileName;
	}

	/**
	 * Sets file name where click boxes are persisted.
	 * Click boxes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
	 * @param clickBoxesFileName File name
	 */
	public void setClickBoxesFileName(final String clickBoxesFileName) {
		this.clickBoxesFileName = clickBoxesFileName;
	}

	/**
	 * Mouse over stripes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
	 * @return File name
	 */
	public String getMouseOverStripesFileName() {
		return mouseOverStripesFileName;
	}

	/**
	 * Sets name of file where mouseover stripes are persisted.
	 * Mouse over stripes are persisted via serialization for
     * performance.  This attribute gives the name of
     * the file (not full path) in which they are serialized.
	 * @param mouseOverStripesFileName File 
	 */
	public void setMouseOverStripesFileName(
			final String mouseOverStripesFileName) {
		this.mouseOverStripesFileName = mouseOverStripesFileName;
	}

	/**
	 * Get experiments in plot.
	 * @return Experiment IDs
	 */
	public final Set<Experiment> getExperiments() {
		return experiments;
	}

	/**
	 * Set experiments in plot.
	 * @param experiments Experiments
	 */
	public final void setExperiments(final Set<Experiment> experiments) {
		this.experiments = experiments;
	}

	/**
	 * Get plot parameters used in creation of plot.
	 * @return Plot parameters.
	 */
	public final PlotParameters getPlotParameters() {
		return plotParameters;
	}


	/**
	 * Set plot parameters used in creation of plot.
	 * @param plotParameters Plot parameters
	 */
	public final void setPlotParameters(
			final PlotParameters plotParameters) {
		this.plotParameters = plotParameters;
	}

	/**
	 * Get height of entire plot image in pixels.
	 * @return Height of entire plot image in pixels.
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Set height of entire plot image.
	 * @param height Height in pixels.
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Get width of entire plot image in pixels.
	 * @return Height of entire plot image in pixels.
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Set width of entire plot image.
	 * @param width Width of entire plot image in pixels.
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Get name of default image file.
	 * @return Default image file name.
	 */
	public final String getDefaultImageFileName() {
		return defaultImageFileName;
	}

	/**
	 * Set name of default image file.
	 * @param defaultImageFileName Default image file name.
	 */
	public final void setDefaultImageFileName(
			final String defaultImageFileName) {
		this.defaultImageFileName = defaultImageFileName;
	}

	/**
	 * Get map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @return Map of image names to image file names.
	 */
	public final Map<String, String> getImageFileMap() {
		return imageFileMap;
	}

	
	/**
	 * Set map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @param imageFileMap Map of image names to image file
	 * names.
	 */
	public final void setImageFileMap(final Map<String, String> imageFileMap) {
		this.imageFileMap = imageFileMap;
	}

	/**
	 * Get click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
	 * @return Click boxes
	 */
	public final Set<ClickBoxes> getClickBoxes() {
		return clickBoxes;
	}

	
	/**
	 * Set click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
	 * @param clickBoxes Click boxes
	 */
	public final void setClickBoxes(final Set<ClickBoxes> clickBoxes) {
		this.clickBoxes = clickBoxes;
	}

	/**
	 * Get mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
	 * @return Mouse over stripes.
	 */
	public final Set<MouseOverStripes> getMouseOverStripes() {
		return mouseOverStripes;
	}

	/**
	 * Set mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
	 * @param mouseOverStripes Mouse over stripes
	 */
	public final void setMouseOverStripes(
			final Set<MouseOverStripes> mouseOverStripes) {
		this.mouseOverStripes = mouseOverStripes;
	}
	
	// ============================
	//     Constructors
	// ============================
	

	/**
	 * Constructor.
	 */
	public Plot() {
		
	}
	
	
	// ===================================
	//        Business methods
	// ===================================
	
	/**
	 * Add image file.
	 * @param imageName Name of image.
	 * @param fileName Name of image file.  Note this
	 * is not an absolute path; clients should know how
	 * to generate an absolute path.
	 */
	public final void addImageFile(final String imageName,
			final String fileName) {
		this.imageFileMap.put(imageName, fileName);
	}
	
	
	/**
	 * Get names of all image files associated with plot.
	 * @return Names of all image files associated with plot.
	 */
	public final Collection<String> getAllImageFileNames() {
		Collection<String> names = new ArrayList<String>();
		names.add(this.defaultImageFileName);
		names.addAll(this.imageFileMap.values());
		return names;
	}
	
	
	/**
	 * Add experiment to plot.
	 * @param experiment Experiment.
	 */
	public final void addExperiment(final Experiment experiment) {
		this.experiments.add(experiment);
	}
}
