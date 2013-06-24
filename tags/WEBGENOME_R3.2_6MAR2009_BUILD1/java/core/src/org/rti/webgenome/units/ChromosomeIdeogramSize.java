/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/


package org.rti.webgenome.units;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Size of chromosome ideogram.
 */
public final class ChromosomeIdeogramSize {
    
    // ========================================
    //         Constants
    // ========================================

    /**
     * Small.
     */
    public static final ChromosomeIdeogramSize SMALL =
    	new ChromosomeIdeogramSize("S", 200.0 / 300000000.0);
    
    /**
     * Medium.
     */
    public static final ChromosomeIdeogramSize MEDIUM =
    	new ChromosomeIdeogramSize("M", 400.0 / 300000000.0);
    
    /**
     * Large.
     */
    public static final ChromosomeIdeogramSize LARGE =
    	new ChromosomeIdeogramSize("L", 600.0 / 300000000.0);
    
    
    /** Map of index sizes to names. */
    private static final Map<String, ChromosomeIdeogramSize>
    	SIZE_INDEX = new HashMap<String, ChromosomeIdeogramSize>();
    
    static {
        SIZE_INDEX.put(SMALL.name, SMALL);
        SIZE_INDEX.put(MEDIUM.name, MEDIUM);
        SIZE_INDEX.put(LARGE.name, LARGE);
    }
    
    
    // ============================================
    //        Attributes
    // ============================================
    
    /** Name of ideogram size. */
    private final String name;
    
    /** Number of pixels per base pair. */
    private final double pixelScale;
    
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    
    
    // ======================================
    //       Constructor
    // ======================================
    
    /**
     * Constructor.
     * @param name Name of ideogram size
     * @param pixelScale Number of pixels per base pair
     */
    private ChromosomeIdeogramSize(final String name,
    		final double pixelScale) {
        this.name = name;
        this.pixelScale = pixelScale;
    }
    
    
    // ==========================================
    //      Public methods
    // ==========================================
    
    /**
     * Number of pixels that rendered.
     * @param bp Base pair length
     * @return Number of pixels
     */
    public int pixels(final long bp) {
        return (int) ((double) bp * pixelScale);
    }
    
    /**
     * Get scale of native units to pixels.
     * @return Scale of native units to pixels
     */
    public double getPixelScale() {
    	return this.pixelScale;
    }
    
    
    // ========================================
    //   Static methods
    // ========================================
    
    /**
     * Get chromosome ideogram size.
     * @param name Size name
     * @return Chromosome ideogram size
     */
    public static ChromosomeIdeogramSize getChromosomeIdeogramSize(
    		final String name) {
        return SIZE_INDEX.get(name);
    }
    
    
    /**
     * Get all sizes.
     * @return All chromosome ideogam sizes
     */
    public static ChromosomeIdeogramSize[] chromosomeIdeogramSizes() {
        Collection<ChromosomeIdeogramSize> sizeCol = SIZE_INDEX.values();
        ChromosomeIdeogramSize[] sizes = new ChromosomeIdeogramSize[0];
        sizes = sizeCol.toArray(sizes);
        return sizes;
    }
}
