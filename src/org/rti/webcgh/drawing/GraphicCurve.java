/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/GraphicCurve.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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
package org.rti.webcgh.drawing;

import java.awt.Color;

/**
 * 
 */
public class GraphicCurve extends GraphicPrimitive {
    
    
    // ========================================
    //       Attributes
    // ========================================
    
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int height;
    private final Orientation orientation;
    
    private int lineWidth = 2;
    
    
    /**
     * @return Returns the orientation.
     */
    public Orientation getOrientation() {
        return orientation;
    }
    
    
    /**
     * @return Returns the lineWidth.
     */
    public int getLineWidth() {
        return lineWidth;
    }
    
    
    /**
     * @param lineWidth The lineWidth to set.
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    
    /**
     * @return Returns the height.
     */
    public int getHeight() {
        return height;
    }
    
    
    /**
     * @return Returns the x1.
     */
    public int getX1() {
        return x1;
    }
    
    
    /**
     * @return Returns the x2.
     */
    public int getX2() {
        return x2;
    }
    
    
    /**
     * @return Returns the y1.
     */
    public int getY1() {
        return y1;
    }
    
    
    /**
     * @return Returns the y2.
     */
    public int getY2() {
        return y2;
    }
    
    
    // =====================================
    //        Constructors
    // =====================================
    
    
    /**
     * Constructor
     * @param x1 X-coordinate of first end point
     * @param y1 Y-coordinate of first end point
     * @param x2 X-coordinate of second end point
     * @param y2 Y-coordinate of second end point
     * @param height Height (can be negative)
     * @param orientation Orientation
     * @param color Color
     */
    public GraphicCurve(int x1, int y1, int x2, int y2, int height, Orientation orientation, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.height = height;
        this.orientation = orientation;
        this.color = color;
    }

}
