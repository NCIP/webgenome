package org.rti.webcgh.analytic;

import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.graph.PlotParameters;

public class AcghOperation implements NormalizationOperation {

	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataSetInvalidations validate(Experiment[] data,
			PlotParameters params) {
		// TODO Auto-generated method stub
		return null;
	}

	public Experiment[] perform(Experiment[] data, PlotParameters params)
			throws AnalyticException {
		try {
			// TODO: Add business logic here
		} catch (Exception e) {
			throw new AnalyticException("Error performing aCGH operation", e);
		}
		// TODO Auto-generated method stub
		return null;
	}

}
