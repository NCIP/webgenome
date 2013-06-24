/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.graphics.util;

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
