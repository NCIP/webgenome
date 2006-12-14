/*
$Revision: 1.1 $
$Date: 2006-12-14 00:27:54 $

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

package org.rti.webcgh.graphics.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that performs compression of lists of points
 * by removing all points sharing same x-coordinate
 * except one, who will have a new y-coordinate equal to the mean
 * of all x-coordinates with same value.
 * @author dhall
 *
 */
public final class PointListCompressor {
	
	/**
	 * Constuctor.
	 */
	private PointListCompressor() {
		
	}
	
	/**
     * Two given lists contain x- and y-coordinates for a set of points.
     * Compress lists removing all points sharing same x-coordinate
     * except one, who will have a new y-coordinate equal to the mean
     * of all x-coordinates with same value.  It is assumed that
     * <code>xx</code> will be sorted in ascending order.
     * @param xx X-coordinates for a set of points
     * @param yy Y-coordinates for a set of points
     */
    public static void compress(final List<Double> xx, final List<Double> yy) {
    	
    	// Check args
    	if (xx == null || yy == null) {
    		throw new IllegalArgumentException(
    				"List of points must not be null");
    	}
    	if (xx.size() != yy.size()) {
    		throw new IllegalArgumentException(
    				"X- and Y-coordinate lists of different size");
    	}
    	
    	// Only compress lists if there are points with same x
    	if (pointsWithSameX(xx)) {
    		List<Double> origXX = new ArrayList<Double>(xx);
    		List<Double> origYY = new ArrayList<Double>(yy);
    		xx.clear();
    		yy.clear();
    		int runStart = 0;
    		int n = origXX.size();
    		while (runStart < n) {
    			double x = origXX.get(runStart);
    			int runEnd = runStart + 1;
    			while (runEnd < n && origXX.get(runEnd) == x) {
    				runEnd++;
    			}
    			int runLength = runEnd - runStart;
    			xx.add(x);
    			if (runLength == 1) {
    				yy.add(origYY.get(runStart));
    			} else {
    				double yTot = 0;
    				for (int i = runStart; i < runEnd; i++) {
    					yTot += origYY.get(i);
    				}
    				yy.add(yTot / (double) runLength);
    			}
    			runStart = runEnd;
    		}
    	}
    }
    
    
    /**
     * Determines if given list of x-coordinates contains
     * duplicate values.
     * @param xx X-coordinates
     * @return T/F
     */
    private static boolean pointsWithSameX(final List<Double> xx) {
    	boolean duplicates = false;
    	double lastVal = Double.NaN;
    	for (double x : xx) {
    		if (Double.isNaN(lastVal)) {
    			lastVal = x;
    		} else if (lastVal == x) {
    			duplicates = true;
    			break;
    		}
    		lastVal = x;
    	}
    	return duplicates;
    }
}
