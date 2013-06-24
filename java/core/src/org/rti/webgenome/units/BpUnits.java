/*
$Revision: 1.3 $
$Date: 2007-08-23 21:19:20 $


*/

package org.rti.webgenome.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rti.webgenome.graphics.util.BPFormatter;
import org.rti.webgenome.graphics.util.NumberFormatter;

/**
 * Units of measurement related to base pairs.
 */
public final class BpUnits {
	
	// =============================
	//      Constants
	// =============================
	
	/**
	 * Base pairs.
	 */
	public static final BpUnits BP = new BpUnits("BP", 1);
	
	
	/**
	 * Kilobases.
	 */
	public static final BpUnits KB = new BpUnits("KB", 1000);
	
	
	/**
	 * Megabases.
	 */
	public static final BpUnits MB = new BpUnits("MB", 1000000);
	
	
	// ==================================
	//     Other static variables
	// ==================================
	
	/** Index providing mapping from unit names to objects. */
	private static final Map<String, BpUnits>
		INDEX = new HashMap<String, BpUnits>();
	
	
	// Initialize index
	static {
		INDEX.put(BP.name, BP);
		INDEX.put(KB.name, KB);
		INDEX.put(MB.name, MB);
	}
	
	
	// ===================================
	//      Attributes
	// ===================================
	
	/** Name of units. */
	private final String name;
	
	/**
	 * Numeric base for converting units
	 * into base pairs.
	 */
	private final int numericBase;
	
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	
	// ===================================
	//        Constructors
	// ===================================
	
	/**
	 * Consturctor.
	 * @param name Name of units
	 * @param numericBase Numeric base for
	 * converting units into base pairs
	 */
	private BpUnits(final String name, final int numericBase) {
		this.name = name;
		this.numericBase = numericBase;
	}
	
	
	// =====================================
	//    Public methods
	// =====================================
	
	/**
	 * Return printable string.
	 * @return A string
	 */
	public String toPrettyString() {
		return this.name;
	}
	
	
	/**
	 * Get number formatter.
	 * @return A number formatter
	 */
	public NumberFormatter numberFormatter() {
	    return new BPFormatter(this.numericBase);
	}
	
	
	/**
	 * Convert given value to BP.
	 * @param value A value
	 * @return Value in BP
	 */
	public long toBp(final double value) {
		return (long) (value * this.numericBase);
	}
	
	
	/**
	 * Convert given value in BP units to an equivalent
	 * value in these units.
	 * @param bpValue Base pair value
	 * @return Equivalent value in these units
	 */
	public long fromBp(final double bpValue) {
		return (long) (bpValue / (double) this.numericBase);
	}
	
	/**
	 * Override of toString() methods.
	 * @return Name of units
	 */
	public String toString() {
		return this.name;
	}
	
	// =====================================
	//       Static methods
	// =====================================
	
	/**
	 * Return units corresponding to name.
	 * @param name Name of units
	 * @return Units
	 */
	public static BpUnits getUnits(final String name) {
		return (BpUnits) INDEX.get(name.toUpperCase());
	}
	
	
	/**
	 * Get all known units.
	 * @return Units
	 */
	public static List<BpUnits> getUnits() {
		return new ArrayList<BpUnits>(INDEX.values());
	}

	
}
