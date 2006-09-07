/*
$Revision: 1.1 $
$Date: 2006-09-07 18:54:53 $

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

package org.rti.webcgh.service.plot;

/**
 * Plot parameters specific to scatter plots.
 * @author dhall
 *
 */
public final class ScatterPlotParameters extends PlotParameters {
    
    // ============================
    //      Attributes
    // ============================
    
    /** Minimum Y-axis value specified by user. */
    private float minY = Float.NaN;

    /** Maximum Y-axis value specified by user. */
    private float maxY = Float.NaN;
    
    // ==========================
    //      Getters/setters
    // ==========================

    /**
     * Get maximum Y-axis value specified by user.
     * @return Maximum Y-axis value specified by user
     */
    public float getMaxY() {
        return maxY;
    }

    
    /**
     * Set maximum Y-axis value specified by user.
     * @param maxY Maximum Y-axis value specified by user
     */
    public void setMaxY(final float maxY) {
        this.maxY = maxY;
    }

    
    /**
     * Get minimum Y-axis value specified by user.
     * @return Minimum Y-axis value specified by user
     */
    public float getMinY() {
        return minY;
    }

    
    /**
     * Set minimum Y-axis value specified by user.
     * @param minY Minimum Y-axis value specified by user
     */
    public void setMinY(final float minY) {
        this.minY = minY;
    }
    
    // ==============================
    //        Constructors
    // ==============================

    /**
     * Constructor.
     */
    public ScatterPlotParameters() {
        super();
    }



    /**
     * Constructor.
     * @param chromosome Chromosome number
     * @param startLocation Left endpoint of chromosome interval
     * to plot
     * @param endLocation Right endpoint of chromosome interval to
     * plot
     * @param minY Minimum y-axis value specified by user
     * @param maxY Maximum y-axis value specified by user
     */
    public ScatterPlotParameters(final short chromosome,
            final long startLocation, final long endLocation,
            final float minY, final float maxY) {
        super(chromosome, startLocation, endLocation);
        this.minY = minY;
        this.maxY = maxY;
    }
    
    
}
