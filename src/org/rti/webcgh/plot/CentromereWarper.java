/*
$Revision: 1.1 $
$Date: 2006-09-05 14:06:44 $

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


package org.rti.webcgh.plot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * Warps coordinates to give centromere waistline.
 */
public final class CentromereWarper implements Warper {
    
    
    // ================================
    //      Attributes
    // ================================
    
	/**
	 * Height of <code>GenomeFeatureMap</code>
	 * that warper warps.
	 * */
    private final int height;
    
    /** X-coordinate of start of warping. */
    private final int warpZoneStartX;
    
    /** X-coordinate of end of warping. */
    private final int warpZoneEndX;
    
    /** X-coordinate of middle of warping. */
    private final int warpZoneMidX;
    
    /**
     * Y-coordinate of middle of <code>GenomeFeatureMap</code>
     * that warper warps.
     */
    private final int midY;
    
    /** Slope of line warp. */
    private final double slope;
    
    
    // ==================================
    //        Constructors
    // ==================================
    
    /**
     * Constructor.
     * @param height Height of chromosome in pixels
     * @param warpZoneStartX Pixel x-coordinate for start of warp zone
     * @param warpZoneEndX Pixel x-coordinate for end of warp zone
     */
    public CentromereWarper(final int height, final int warpZoneStartX,
    		final int warpZoneEndX) {
        if (warpZoneStartX > warpZoneEndX) {
            throw new IllegalArgumentException(
            		"warpZoneEndX must be >= warpZoneEndX");
        }
        this.height = height;
        this.warpZoneStartX = warpZoneStartX;
        this.warpZoneEndX = warpZoneEndX;
        this.warpZoneMidX = (warpZoneEndX + warpZoneStartX) / 2;
        this.midY = height / 2;
        this.slope = (double) this.midY / (double)
        	(this.warpZoneMidX - this.warpZoneStartX);
    }
    
    
    
    // ===================================
    //   Methods in Warper interface
    // ===================================
    
    /**
     * Transform a polyline defined by given points.
     * @param points Points defining a polyline or polygon
     * @return New set of points representing new polyline or polygon
     */
    public Point[] transform(final Point[] points) {
    	if (points == null) {
    		throw new IllegalArgumentException(
    				"Array 'points' cannot be null");
    	}
    	List<Point> pointsList = new ArrayList<Point>();
    	for (int i = 0; i < points.length; i++) {
    		Point point = points[i];
    		pointsList.add(point);
    		if (i < points.length - 1) {
    			Point nextPoint = points[i + 1];
    			if (this.transectsWarpZone(point, nextPoint)) {
    			    this.addExtraPoints(pointsList, point, nextPoint);
    			}
    		}
    	}
    	this.warp(pointsList);
    	Point[] newPoints = new Point[0];
    	newPoints = (Point[]) pointsList.toArray(newPoints);
    	return newPoints;
    }
    
    
    // ======================================
    //      Private methods
    // ======================================
    
    /**
     * Transform point from horizontal to vertical
     * orientation.
     * @param point A point
     */
    private void transform(final Point point) {
            
        // Determine slope
        double slope = 0.0;
        if (point.x <= this.warpZoneMidX) {
            if (point.y <= this.midY) {
                slope = this.slope;
            } else {
                slope = -this.slope;
            }
        } else {
            if (point.y <= this.midY) {
                slope = -this.slope;
            } else {
                slope = this.slope;
            }
        }
        
        // Determine delta from reference point
        int refPointX = 0;
        int refPointY = 0;
        if (point.x <= this.warpZoneMidX) {
            refPointX = this.warpZoneStartX;
            refPointY = point.y;
        } else {
            refPointX = this.warpZoneMidX;
            refPointY = this.midY;
        }
        double delta = (double) point.x - (double) refPointX;
        
        // Set new y
        point.y = refPointY + (int) (delta * slope);
        if (point.y < 0) {
            point.y = 0;
        }
        if (point.y > this.height) {
            point.y = this.height;
        }
    }
    
    /**
     * Is given point in warp zone?
     * @param point A point
     * @return T/F
     */
    private boolean inWarpZone(final Point point) {
    	return point.x >= this.warpZoneStartX && point.x <= this.warpZoneEndX;
    }
    
    
    /**
     * Does line defined by two given points transect
     * warp zone?
     * @param p1 First point in line
     * @param p2 Second point in line
     * @return T/F
     */
    private boolean transectsWarpZone(final Point p1, final Point p2) {
    	return 
			(p1.x < this.warpZoneStartX && p2.x > this.warpZoneEndX)
			|| (p1.x > this.warpZoneEndX && p2.x < this.warpZoneStartX)
			|| (this.inWarpZone(p1) && !this.inWarpZone(p2))
			|| (this.inWarpZone(p2) && !this.inWarpZone(p1));
    }

    /**
     * Does line defined by two given points
     * transect warp zone ceter?
     * @param p1 First point in line
     * @param p2 Second point in line
     * @return T/F
     */
    private boolean transectsWarpZoneCenter(final Point p1,
    		final Point p2) {
    	return 
			(p1.x < this.warpZoneMidX && p2.x > this.warpZoneMidX)
			|| (p1.x > this.warpZoneMidX && p2.x < this.warpZoneMidX);
    }
    
    
    /**
     * Add extra points to create warp effect.
     * @param points Point around warp zone
     * @param p1 Last point on left or top of warp zone
     * @param p2 First point on right or below of warp zone
     */
    private void addExtraPoints(final List<Point> points, final Point p1,
    		final Point p2) {
        if (p1.x < p2.x) {
            if (p1.x < this.warpZoneStartX) {
                points.add(new Point(this.warpZoneStartX, p1.y));
            }
            if (p2.x > this.warpZoneEndX) {
                points.add(new Point(this.warpZoneEndX, p2.y));
            }
        } else {
            if (p1.x > this.warpZoneEndX) {
                points.add(new Point(this.warpZoneEndX, p1.y));
            }
            if (p2.x < this.warpZoneStartX) {
                points.add(new Point(this.warpZoneStartX, p2.y));
            }
        }
    }
    
    /**
     * Warp given points.
     * @param points Points to warp
     */
    private void warp(final List<Point> points) {
    	for (int i = 0; i < points.size(); i++) {
    		Point p1 = points.get(i);
    		if (this.inWarpZone(p1)) {
    		    this.transform(p1);
    		}
    		if (i < points.size() - 1) {
	    		Point p2 = points.get(i + 1);
	    		if (this.transectsWarpZoneCenter(p1, p2)) {
	    			Point p3 = new Point(this.warpZoneMidX, this.midY);
	    			points.add(i + 1, p3);
	    			i++;
	    		}
    		}
    	}
    }
}
