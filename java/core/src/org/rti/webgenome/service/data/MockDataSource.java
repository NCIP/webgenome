/*
$Revision: 1.1 $
$Date: 2008-02-14 23:12:30 $

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

package org.rti.webgenome.service.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.QuantitationTypes;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.client.SimulatedDataClientDataService;

/**
 * An implementation of <code>DataSource</code> used only for testing.
 * @author dhall
 *
 */
public class MockDataSource implements DataSource {
	
	//
	//  C O N S T A N T S
	//
	
	/** Number of experiments provided by this data source. */
	private static final int NUM_EXPERIMENTS = 6;
	
	/** Mock name of client from which mock data are obtained. */
	private static final String CLIENT_ID = "mock";
	
	/** Chromosome sizes. */
	private static final long[] CHROM_SIZES = new long[] {
		300000000, 250000000, 170000000, 150000000, 100000000};
	
	/** Quantitation type for data. */
	private static final String QTYPE = QuantitationTypes.COPY_NUMBER;
	
	/** Domain name for authentication. */
	private static final String DOMAIN = "mock";
	
	//
	//  C L A S S    M E M B E R S
	//
	
	/** The experiments provided by this data source. */
	private final Map<String, Experiment> experiments =
		new HashMap<String, Experiment>();
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public MockDataSource() {
		
		// Instantiate simulated data service
		SimulatedDataClientDataService service =
			new SimulatedDataClientDataService();
		
		// Create list of simulated experiments
		String[] expIds = new String[NUM_EXPERIMENTS];
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			expIds[i] = String.valueOf(i);
		}
		BioAssayDataConstraints[] constraints =
			new BioAssayDataConstraints[CHROM_SIZES.length];
		for (int i = 0; i < CHROM_SIZES.length; i++) {
			BioAssayDataConstraints constr = new BioAssayDataConstraints();
			constr.setChromosome(String.valueOf(i + 1));
			constr.setPositions((long) 0, (long) CHROM_SIZES[i]);
			constr.setQuantitationType(QTYPE);
			constraints[i] = constr;
		}
		Collection<Experiment> experimentsCol = service.getClientData(
				constraints, expIds, CLIENT_ID);
		
		// Populate map of member experiments
		for (Experiment exp : experimentsCol) {
			this.experiments.put(exp.getName(), exp);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Experiment getExperiment(final String id)
	throws DataSourceAccessException {
		return this.experiments.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, String> getExperimentIdsAndNames(
			final Principal principal)
	throws DataSourceAccessException {
		Map<String, String> idsAndNames = new HashMap<String, String>();
		for (String key : this.experiments.keySet()) {
			Experiment exp = this.experiments.get(key);
			idsAndNames.put(key, exp.getName());
		}
		return idsAndNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Experiment> getExperiments(
			final Collection<String> ids) throws DataSourceAccessException {
		Collection<Experiment> exps = new ArrayList<Experiment>();
		for (String id : ids) {
			Experiment exp = this.experiments.get(id);
			if (exp != null) {
				exps.add(exp);
			}
		}
		return exps;
	}

	/**
	 * {@inheritDoc}
	 */
	public Principal login(final String userName,
			final String password) {
		return new Principal(userName, password, DOMAIN);
	}
}
