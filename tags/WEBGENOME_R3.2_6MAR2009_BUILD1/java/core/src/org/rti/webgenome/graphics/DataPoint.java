/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/DataPoint.java,v $
$Revision: 1.3 $
$Date: 2007-12-17 17:42:58 $



*/
package org.rti.webgenome.graphics;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.ArrayDatum;

/**
 * A data point
 */
public class DataPoint {
    
    
    // ==========================================
    //       Attributes with accessors
    // ==========================================
    
    private double value1 = Double.NaN;
    private double value2 = Double.NaN;
    private double error = Double.NaN;
    private String label = null;
    private boolean selected = false;
        

    public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	/**
     * @return Returns the error.
     */
    public double getError() {
        return error;
    }
    
    
    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return label;
    }
    
    
    /**
     * @return Returns the value1.
     */
    public double getValue1() {
        return value1;
    }
    
    
    /**
     * @return Returns the value2.
     */
    public double getValue2() {
        return value2;
    }
    
    
    /**
     * @param error The error to set.
     */
    public void setError(double error) {
        this.error = error;
    }
    
    
    /**
     * @param label The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    
    /**
     * @param value1 The value1 to set.
     */
    public void setValue1(double value1) {
        this.value1 = value1;
    }
    
    
    /**
     * @param value2 The value2 to set.
     */
    public void setValue2(double value2) {
        this.value2 = value2;
    }
    
    
    // ===========================================
    //       Constructors
    // ===========================================
    
    /**
     * Constructor
     */
    public DataPoint() {}
    
    
    /**
     * Constructor
     * @param value1 First value
     * @param value2 Second value
     */
    public DataPoint(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
    
    
    /**
     * Constructor
     * @param value1 First value
     * @param value2 Second value
     * @param label Label
     */
    public DataPoint(double value1, double value2, String label) {
        this(value1, value2);
        this.label = label;
    }
    
    
    /**
     * Constructor
     * @param value1 First value
     * @param value2 Second value
     * @param error Standard error
     * @param label Label
     */
    public DataPoint(double value1, double value2, double error, String label) {
        this(value1, value2, label);
        this.error = error;
    }
    
    
    /**
     * Constructor 
     * @param dataPoint A data point
     */
    public DataPoint(DataPoint dataPoint) {
        this.error = dataPoint.error;
        this.label = dataPoint.label;
        this.value1 = dataPoint.value1;
        this.value2 = dataPoint.value2;
    }
    
    
    
    // ===================================
    //       Public methods
    // ===================================
    
    /**
     * Bulk set properties of this object.
     * using property values from given
     * datum.
     * @param datum An array datum
     */
    public void bulkSet(ArrayDatum datum) {
        this.value2 = datum.getValue();
        this.error = datum.getError();
        this.value1 = datum.getReporter().getLocation();
    }
    

    /**
     * Modify value1 to fit on line defined by given data points.  Value 2 does not change.
     * @param point1 First data point
     * @param point2 Second data point
     */
    public void fitValue1ToLine(DataPoint point1, DataPoint point2) {
        double slope = slope(point1, point2);
        if (Double.isNaN(slope))
            this.value1 = point1.value1;
        else if (slope > 0.0)
            this.value1 = point1.value1 + (this.value2 - point1.value2) / slope;
    }
    
    
    /**
     * Modify value2 to fit on line defined by given data points.  Value 1 does not change.
     * @param point1 First data point
     * @param point2 Second data point
     */
    public void fitValue2ToLine(DataPoint point1, DataPoint point2) {
        double slope = slope(point1, point2);
        if (slope == 0.0)
            this.value2 = point1.value2;
        else if (! Double.isNaN(slope))
            this.value2 = point1.value2 + (this.value1 - point1.value1) * slope;
    }
    
    
    /**
     * Cartesian difference
     * @param dataPoint A data point
     * @return Cartesian difference
     */
    public DataPoint minus(DataPoint dataPoint) {
        DataPoint newDataPoint = new DataPoint();
        newDataPoint.value1 = this.value1 - dataPoint.value1;
        newDataPoint.value2 = this.value2 - dataPoint.value2;
        return newDataPoint;
    }
    
    
    /**
     * Get value2 plus error
     * @return Value 2 plus error
     */
    public double value2PlusError() {
    	double error = 0.0;
    	if (! Double.isNaN(this.error)) {
    		error = this.error;
    		if (this.value2 < 0.0)
    			error = -error;
    	}
    	return this.value2 + error;
    }
    
    /**
     * Return representation of point formatted for viewing.
     * @return Representation of point formatted for viewing.
     */
    public String toPrettyString() {
    	return "(" + this.value1 + "," + this.value2 + ")";
    }
    
    
    // =============================
    //    Static methods
    // =============================
    
    /**
     * Slope of line specified by given end points
     * @param point1 First line endpoint
     * @param point2 Second line endpoint
     * @return Slope
     */
    public static double slope(DataPoint point1, DataPoint point2) {
        double rise = point2.value2 - point1.value2;
        double run = point2.value1 - point1.value1;
        
        // Special case: slope is infinite
        if (run == 0.0)
            return Double.NaN; 
        return rise /run;
    }
    
    
    /**
     * Create new data point corresponding to the "left" side of
     * the given feature.
     * @param feat Feature
     * @return New data point
     */
    public static DataPoint leftDataPoint(final AnnotatedGenomeFeature feat) {
    	return new DataPoint(feat.getStartLocation(), feat.getQuantitation());
    }
    
    
    /**
     * Create new data point corresponding to the "right" side of
     * the given feature.
     * @param feat Feature
     * @return New data point
     */
    public static DataPoint rightDataPoint(final AnnotatedGenomeFeature feat) {
    	return new DataPoint(feat.getEndLocation(), feat.getQuantitation());
    }
}
