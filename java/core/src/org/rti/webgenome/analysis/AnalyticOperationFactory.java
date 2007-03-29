/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

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

package org.rti.webgenome.analysis;

import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.core.WebcghSystemException;

/**
 * Factory for <code>AnalyticOperation</code> objects.
 * @author dhall
 *
 */
public class AnalyticOperationFactory {

	/** Index of keys to analytic operation classes. */
	private Map<String, Class> operationIndex =
		new HashMap<String, Class>();
	
	
	/**
	 * Constructor.
	 */
	public AnalyticOperationFactory() {
		this.operationIndex.put("1", AcghAnalyticOperation.class);
		this.operationIndex.put("2", Averager.class);
		this.operationIndex.put("3", RangeBasedFilterer.class);
		this.operationIndex.put("4", SimpleBioAssayNormalizer.class);
		this.operationIndex.put("5", SimpleExperimentNormalizer.class);
		this.operationIndex.put("6", SlidingWindowSmoother.class);
		this.operationIndex.put("7",
				MinimumCommonAlteredRegionOperation.class);
		this.operationIndex.put("8", LinearRegressionAnalyticOperation.class);
		this.operationIndex.put("9", LoessRegressionAnalyticOperation.class);
	}
	
	
	/**
	 * Get map of analytic operation keys and names.
	 * @return Map of analytic operation keys and names.
	 */
	public final Map<String, String> getOperationKeysAndNames() {
		Map<String, String> map = new HashMap<String, String>();
		for (String key : this.operationIndex.keySet()) {
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
		Class c = this.operationIndex.get(key);
		if (c == null) {
			throw new WebcghSystemException(
					"Analytic operation with key '" + key + "' not found");
		}
		AnalyticOperation op = null;
		try {
			op = (AnalyticOperation) c.newInstance();
		} catch (Exception e) {
			throw new WebcghSystemException(
					"Error instantiating analytic operation", e);
		}
		return op;
	}
}
