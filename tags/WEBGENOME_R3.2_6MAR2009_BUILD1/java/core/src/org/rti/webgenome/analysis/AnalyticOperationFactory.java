/*
$Revision: 1.3 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.analysis;

import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Factory for <code>AnalyticOperation</code> objects.
 * @author dhall
 *
 */
public class AnalyticOperationFactory {

	/** Index of keys to analytic operation classes. */
	private static final Map<String, Class> OPERATION_INDEX =
		new HashMap<String, Class>();
	
	/** Indexof analytic operation classes to keys. */
	private static final Map<Class, String> REVERSE_OPERATION_INDEX =
		new HashMap<Class, String>();
	
	static {
		OPERATION_INDEX.put("1", AcghAnalyticOperation.class);
		REVERSE_OPERATION_INDEX.put(AcghAnalyticOperation.class, "1");
		OPERATION_INDEX.put("2", Averager.class);
		REVERSE_OPERATION_INDEX.put(Averager.class, "2");
		OPERATION_INDEX.put("3", RangeBasedFilterer.class);
		REVERSE_OPERATION_INDEX.put(RangeBasedFilterer.class, "3");
		OPERATION_INDEX.put("4", SimpleBioAssayNormalizer.class);
		REVERSE_OPERATION_INDEX.put(SimpleBioAssayNormalizer.class, "4");
		OPERATION_INDEX.put("5", SimpleExperimentNormalizer.class);
		REVERSE_OPERATION_INDEX.put(SimpleExperimentNormalizer.class, "5");
		OPERATION_INDEX.put("6", SlidingWindowSmoother.class);
		REVERSE_OPERATION_INDEX.put(SlidingWindowSmoother.class, "6");
		OPERATION_INDEX.put("7",
				MinimumCommonAlteredRegionOperation.class);
		REVERSE_OPERATION_INDEX.put(
				MinimumCommonAlteredRegionOperation.class, "7");
		OPERATION_INDEX.put("8", LinearRegressionAnalyticOperation.class);
		REVERSE_OPERATION_INDEX.put(
				LinearRegressionAnalyticOperation.class, "8");
		OPERATION_INDEX.put("9", LoessRegressionAnalyticOperation.class);
		REVERSE_OPERATION_INDEX.put(
				LoessRegressionAnalyticOperation.class, "9");
	}
	
	
	/**
	 * Constructor.
	 */
	public AnalyticOperationFactory() {
		
	}
	
	
	/**
	 * Get map of analytic operation keys and names.
	 * @return Map of analytic operation keys and names.
	 */
	public final Map<String, String> getOperationKeysAndNames() {
		Map<String, String> map = new HashMap<String, String>();
		for (String key : OPERATION_INDEX.keySet()) {
			AnalyticOperation op = this.newAnalyticOperation(key);
			String name = op.getName();
			map.put(key, name);
		}
		return map;
	}
	
	
	/**
	 * Create new analytic operation whose key matches the given key.
	 * @param key Key of analytic operation.
	 * @return An analytic operation.
	 */
	public final AnalyticOperation newAnalyticOperation(final String key) {
		Class c = OPERATION_INDEX.get(key);
		if (c == null) {
			throw new WebGenomeSystemException(
					"Analytic operation with key '" + key + "' not found");
		}
		AnalyticOperation op = null;
		try {
			op = (AnalyticOperation) c.newInstance();
		} catch (Exception e) {
			throw new WebGenomeSystemException(
					"Error instantiating analytic operation", e);
		}
		return op;
	}
	
	
	/**
	 * Get key associated with given analytic operation class.
	 * @param clazz Analytic operation class
	 * @return Key associated with given class or
	 * {@code null} if class is not registered.
	 */
	public static final String getKey(final Class clazz) {
		return REVERSE_OPERATION_INDEX.get(clazz);
	}
}
