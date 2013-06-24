/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.graphics.util;

import java.awt.Color;

/**
 * <p>
 * This class converts data values into colors
 * and is used to create a heat map (i.e., heat plot).
 * The color scale goes from green hues to blue
 * hues to red hues.  Smaller values map
 * to greens.  Intermediate values map to blues.
 * High values map to reds.  There is a low and
 * high saturation value.  Values below and above
 * these saturation points are mapped to
 * pure green (#00FF00) and pure red (#FF0000),
 * respecitively.  The value at the midpoint is
 * mapped to pure blue (#0000FF).
 * </p>
 * 
 * The colors are binned.  A bin is a range
 * of values that is mapped to the same color.
 * @author dhall
 *
 */
public class HeatMapColorFactory {
	
    // ========================================
    //       Attributes
    // ========================================
    
	/** Ordered pallate of colors. */
    private final Color[] pallate;
    
    /**
     * Low saturation value.  All values
     * less than or equal to this will be
     * mapped to pure green (#00FF00).
     */
    private final float lowSaturation;
    
    /**
     * High saturation value.  All values
     * greater than or equal to this will be
     * mapped to pure red (#FF0000).
     */
    private final float highSaturation;
    
    /**
     * Index of last bin, or <code>pallate.length - 1.</code>.
     * For computational efficiency, this arithmetic
     * operation is performed once.
     */
    private final int maxBinIndex;
    
    /** Range of values represented by a single bin. */
    private final float binRange;
    
    
    // ==================================================
    //     Constructors
    // ==================================================
    
    /**
     * Constructor.
     * @param lowSaturation Lower saturation value (i.e. all values below this
     * will produce RGB(0, 255, 0))
     * @param highSaturation Upper saturation value (i.e. all values above this
     * will produce RGB(255, 0, 0))
     * @param numBins Number of bins
     */
    public HeatMapColorFactory(final float lowSaturation,
    		final float highSaturation, final int numBins) {
        if (numBins <= 0) {
            throw new IllegalArgumentException(
            		"Num bins must be a positive integer");
        }
        if (lowSaturation > highSaturation) {
            throw new IllegalArgumentException(
            		"Low saturation value cannot be greater "
            		+ "than high saturation value");
        }
        this.pallate = new Color[numBins];
        this.lowSaturation = lowSaturation;
        this.highSaturation = highSaturation;
        this.maxBinIndex = numBins - 1;
        this.binRange = (highSaturation - lowSaturation) / numBins;
        this.initializePallate();
    }

    
    /**
     * Initialize pallate.
     */
    private void initializePallate() {
        
        // Generate colors from green to blue
        Color startColor = Color.green;
        Color endColor = Color.blue;
        int startBin = 0;
        int endBin = 0;
        if (this.lowSaturation < 0.0 && this.highSaturation > 0.0) {
            endBin = this.binNumber((float) 0.0);
        } else {
            endBin = this.binNumber((this.highSaturation
            		- this.lowSaturation) / (float) 2.0);
        }
        this.initializeBinColors(startBin, endBin, startColor, endColor);
        
        // Generate colors blue to red
        startColor = Color.blue;
        endColor = Color.red;
        startBin = endBin;
        endBin = this.maxBinIndex;
        this.initializeBinColors(startBin, endBin, startColor, endColor);
    }
    
    
    /**
     * Initialize bin colors.
     * @param startBin Starting bin index
     * @param endBin Ending bin index
     * @param startColor Starting color
     * @param endColor Ending color
     */
    private void initializeBinColors(final int startBin,
    		final int endBin, final Color startColor, final Color endColor) {
        assert endBin >= startBin
        	&& startColor != null && endColor != null;
        int r = startColor.getRed();
        int g = startColor.getGreen();
        int b = startColor.getBlue();
        float range = (float) (endBin - startBin);
        float rSlope = (float) (endColor.getRed() - r) / range;
        float gSlope = (float) (endColor.getGreen() - g) / range;
        float bSlope = (float) (endColor.getBlue() - b) / range;
        for (int i = startBin; i <= endBin; i++) {
            int relativeBinNum = i - startBin;
            int newR = r + (int) (rSlope * relativeBinNum);
            int newG = g + (int) (gSlope * relativeBinNum);
            int newB = b + (int) (bSlope * relativeBinNum);
            this.pallate[i] = new Color(newR, newG, newB);
        }
    }
    
    
    // =================================
    //      Business methods
    // =================================
    
    /**
     * Get number of bin associated with value.
     * @param value A value
     * @return Bin number
     */
    public final int binNumber(final float value) {
        int binNum = (int) Math.floor((value - this.lowSaturation)
        		/ this.binRange);
        if (binNum < 0) {
            binNum = 0;
        }
        if (binNum > this.maxBinIndex) {
            binNum = this.maxBinIndex;
        }
        return binNum;
    }
    
    /**
     * Get color associated with given value.
     * @param value A value
     * @return A color
     */
    public final Color getColor(final float value) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be real number");
        }
        int binNum = this.binNumber(value);
        return this.pallate[binNum];
    }
    
    
    /**
     * Get color associated with given bin.
     * @param bin Bin number
     * @return A color
     */
    public final Color getBinColor(final int bin) {
    	if (bin < 0 || bin >= this.pallate.length) {
    		throw new IllegalArgumentException("Bin number out of range");
    	}
    	return this.pallate[bin];
    }
}
