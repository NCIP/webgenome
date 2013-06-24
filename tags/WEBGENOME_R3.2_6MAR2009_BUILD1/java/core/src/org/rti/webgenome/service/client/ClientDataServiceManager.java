/*
$Revision: 1.2 $
$Date: 2007-07-27 22:21:19 $


*/

package org.rti.webgenome.service.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.domain.DataSourceProperties;
import org.rti.webgenome.domain.EjbDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.SimulatedDataSourceProperties;

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
		Set<EjbDataSourceProperties> props =
			this.findDistinctEjbDataSourceProperties(inputExperiments);
		
		// Collect new data
		Collection<Experiment> outputExperiments =
			new ArrayList<Experiment>();
		BioAssayDataConstraints[] conArr = new BioAssayDataConstraints[0];
		conArr = constraints.toArray(conArr);
		for (EjbDataSourceProperties p : props) {
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
		Set<EjbDataSourceProperties> props =
			this.findDistinctEjbDataSourceProperties(experiments);
		
		// Add data
		for (EjbDataSourceProperties prop : props) {
			ClientDataService service = this.getClientDataService(prop);
			Collection<Experiment> exps =
				this.findExperiments(experiments, prop);
			service.addData(exps, constraints, prop.getClientId());
		}
	}
	
	
	/**
	 * Finds all distinct EJB data source properties in input experiments.
	 * @param experiments Experiments
	 * @return Distinct data source properties
	 */
	private Set<EjbDataSourceProperties> findDistinctEjbDataSourceProperties(
			final Collection<Experiment> experiments) {
		Set<EjbDataSourceProperties> props =
			new HashSet<EjbDataSourceProperties>();
		for (Experiment exp : experiments) {
			DataSourceProperties prop = exp.getDataSourceProperties();
			if (prop == null) {
				throw new IllegalArgumentException("Unknown data source");
			}
			if (prop instanceof EjbDataSourceProperties) {
				props.add((EjbDataSourceProperties) prop);
			}
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
