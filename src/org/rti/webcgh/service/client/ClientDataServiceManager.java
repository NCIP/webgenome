/*
$Revision: 1.2 $
$Date: 2006-12-12 21:37:52 $

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

package org.rti.webcgh.service.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.rti.webcgh.domain.DataSourceProperties;
import org.rti.webcgh.domain.EjbDataSourceProperties;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.SimulatedDataSourceProperties;
import org.rti.webgenome.client.BioAssayDataConstraints;

/**
 * Manages <code>ClientDataService</code> beans.
 * @author dhall
 *
 */
public class ClientDataServiceManager {
	
	/** Index of client data service keyed on data source properties. */
	private Map<DataSourceProperties, ClientDataService> dataServiceIndex =
		new HashMap<DataSourceProperties, ClientDataService>();
	
	/**
	 * Get a client data service instance.
	 * @param dataSourceProperties Data source properties
	 * @return A client data service instance
	 */
	public final ClientDataService getClientDataService(
			final DataSourceProperties dataSourceProperties) {
		ClientDataService service =
			this.dataServiceIndex.get(dataSourceProperties);
		if (service == null) {
			if (dataSourceProperties instanceof SimulatedDataSourceProperties) {
				service = new SimulatedDataClientDataService();
			} else if (dataSourceProperties
					instanceof EjbDataSourceProperties) {
				service = new BioAssayMgrEjbClientDataService();
				String jndiName = ((EjbDataSourceProperties)
						dataSourceProperties).getJndiName();
				String jndiProviderURL = ((EjbDataSourceProperties)
						dataSourceProperties).getJndiProviderURL();
				((BioAssayMgrEjbClientDataService)
						service).setJndiName(jndiName);
				((BioAssayMgrEjbClientDataService)
						service).setJndiProviderURL(jndiProviderURL);
			} else {
				throw new IllegalArgumentException(
						"Unrecognized DataSourceProperties class");
			}
			this.dataServiceIndex.put(dataSourceProperties, service);
		}
		return service;
	}
	
	
	/**
	 * This method is used to obtain data from client applications
	 * of a different quantitation type than in the given
	 * experiments.  Application clients may mix quantitation types
	 * within the same experiment as long as all data from a given
	 * bioassay are of the same type.  However, in webGenome all data
	 * in the same experiment must be of the same type.  This method
	 * creates new <code>Experiment</code> instances to hold newly
	 * imported ata.
	 * @param inputExperiments Experiments that are already in the session
	 * for which additional data of a different quantitation type
	 * is sought.
	 * @param constraints Data query constraints.  All of these
	 * constraints must have the same quantitation type or an
	 * <code>IllegalArgumentException</code> will be thrown.
	 * @return New experiment instances containing imported data
	 */
	public final Collection<Experiment> importData(
			final Collection<Experiment> inputExperiments,
			final Collection<BioAssayDataConstraints> constraints) {
		
		// Check args
		if (inputExperiments == null || constraints == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		if (inputExperiments.size() < 1) {
			throw new IllegalArgumentException(
					"At least one experiment required");
		}
		if (constraints.size() < 1) {
			throw new IllegalArgumentException(
					"At least on constraint required");
		}
		
		// Make sure all constraints have same quantitation type
		String qType = null;
		for (BioAssayDataConstraints c : constraints) {
			if (c.getQuantitationType() == null) {
				throw new IllegalArgumentException("Quantitation type is null");
			}
			if (qType == null) {
				qType = c.getQuantitationType();
			} else {
				if (!qType.equals(c.getQuantitationType())) {
					throw new IllegalArgumentException(
							"Cannot have mixed quantitation types");
				}
			}
		}
		
		// Get set of distinct data source properties from input
		// experiments
		Set<DataSourceProperties> props =
			this.findDistinctDataSourceProperties(inputExperiments);
		
		// Collect new data
		Collection<Experiment> outputExperiments =
			new ArrayList<Experiment>();
		BioAssayDataConstraints[] conArr = new BioAssayDataConstraints[0];
		conArr = constraints.toArray(conArr);
		for (DataSourceProperties p : props) {
			String[] expIds = this.findExperimentSourceDbIds(
					inputExperiments, p);
			ClientDataService service = this.getClientDataService(p);
			Collection<Experiment> exps = service.getClientData(
					conArr, expIds, p.getClientId());
			for (Experiment exp : exps) {
				exp.setDataSourceProperties(p);
			}
			outputExperiments.addAll(exps);
		}
		
		return outputExperiments;
	}
	
	
	/**
	 * Add data from different genome regions to the given
	 * experiments.  These genome regions are defined
	 * in the given constraints.
	 * @param experiments Experiments
	 * @param constraints Data constraints
	 */
	public final void addData(final Collection<Experiment> experiments,
			final BioAssayDataConstraints[] constraints) {
		
		// Check args
		if (experiments == null || constraints == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		if (experiments.size() < 1) {
			throw new IllegalArgumentException(
					"At least one experiment required");
		}
		if (constraints.length < 1) {
			throw new IllegalArgumentException(
					"At least on constraint required");
		}
		
		// Make sure all constraints have same quantitation type
		String qType = null;
		for (BioAssayDataConstraints c : constraints) {
			if (c.getQuantitationType() == null) {
				throw new IllegalArgumentException("Quantitation type is null");
			}
			if (qType == null) {
				qType = c.getQuantitationType();
			} else {
				if (!qType.equals(c.getQuantitationType())) {
					throw new IllegalArgumentException(
							"Cannot have mixed quantitation types");
				}
			}
		}
		
		// Get set of distinct data source properties from input
		// experiments
		Set<DataSourceProperties> props =
			this.findDistinctDataSourceProperties(experiments);
		
		// Add data
		for (DataSourceProperties prop : props) {
			ClientDataService service = this.getClientDataService(prop);
			Collection<Experiment> exps =
				this.findExperiments(experiments, prop);
			service.addData(exps, constraints, prop.getClientId());
		}
	}
	
	
	/**
	 * Finds all distinct data source properties in input experiments.
	 * @param experiments Experiments
	 * @return Distinct data source properties
	 */
	private Set<DataSourceProperties> findDistinctDataSourceProperties(
			final Collection<Experiment> experiments) {
		Set<DataSourceProperties> props = new HashSet<DataSourceProperties>();
		for (Experiment exp : experiments) {
			DataSourceProperties prop = exp.getDataSourceProperties();
			if (prop == null) {
				throw new IllegalArgumentException("Unknown data source");
			}
			props.add(prop);
		}
		return props;
	}
	
	
	/**
	 * Find IDs in source databases
	 * of experiments in given input experiments with
	 * given data source properties. 
	 * @param inputExperiments Input experiments
	 * @param props Data source properties
	 * @return Experiment IDs
	 */
	private String[] findExperimentSourceDbIds(
			final Collection<Experiment> inputExperiments,
			final DataSourceProperties props) {
		Collection<String> idCol = new ArrayList<String>();
		for (Experiment exp : inputExperiments) {
			if (props.equals(exp.getDataSourceProperties())) {
				idCol.add(exp.getSourceDbId());
			}
		}
		String[] idArr = new String[0];
		idArr = idCol.toArray(idArr);
		return idArr;
	}
	
	
	/**
	 * Find experiments from given collection whose data source
	 * properties is equal to the given.
	 * @param inputExperiments Experiments
	 * @param props Data source properties
	 * @return Experiments from given collection whose data source
	 * properties is equal to the given.
	 */
	private Collection<Experiment> findExperiments(
			final Collection<Experiment> inputExperiments,
			final DataSourceProperties props) {
		Collection<Experiment> outputExperiments = new ArrayList<Experiment>();
		for (Experiment exp : inputExperiments) {
			if (props.equals(exp.getDataSourceProperties())) {
				outputExperiments.add(exp);
			}
		}
		return outputExperiments;
	}
}
