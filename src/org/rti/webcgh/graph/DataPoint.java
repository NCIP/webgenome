/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/DataPoint.java,v $
$Revision: 1.2 $
$Date: 2005-12-16 15:16:59 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.graph;

/**
 * A data point
 */
public class DataPoint implements Groupable {
    
    
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
}
