/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:17 $

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
import java.util.List;
import java.util.Map;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.QuantitationTypes;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.Reporter;
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
	
	/** Display name of this data source. */
	private static final String DISPLAY_NAME = "Mock Data Source";
	
	//
	//  C L A S S    M E M B E R S
	//
	
	/** The experiment DTOs provided by this data source indexed by ID. */
	private final Map<String, ExperimentDto> experiments =
		new HashMap<String, ExperimentDto>();
	
	/** The bioassay DTOs provided by this data source indexed by ID. */
	private final Map<String, BioAssayDto> bioassays =
		new HashMap<String, BioAssayDto>();
	
	/** The array DTOs provided by this data source indexed by ID. */
	private final Map<String, ArrayDto> arrays =
		new HashMap<String, ArrayDto>();
	
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
		
		// Populate maps of member experiments
		this.populateMaps(experimentsCol);
	}
	
	
	/**
	 * Helper method colled by constructor to populate the three
	 * member map properties.
	 * @param expCol Collection of experiments
	 */
	private void populateMaps(final Collection<Experiment> expCol) {
		for (Experiment exp : expCol) {
			this.addExperimentDto(exp);
			this.addBioAssayDtos(exp);
			this.addArrayDtos(exp);
		}
	}
	
	
	/**
	 * Helper method to cache an <code>ExperimentDto</code>.
	 * @param exp Experiment from which to initialize the DTO.
	 */
	private void addExperimentDto(final Experiment exp) {
		ExperimentDto dto = new ExperimentDto();
		dto.setName(exp.getName());
		dto.setOrganismName(Organism.UNKNOWN_ORGANISM.getDisplayName());
		dto.setRemoteId(exp.getName());
		for (BioAssay ba : exp.getBioAssays()) {
			dto.addRemoteBioAssayId(ba.getName());
		}
		this.experiments.put(dto.getRemoteId(), dto);
	}
	
	/**
	 * Helper method to cache a set of <code>BioAssayDto</code>
	 * objects.
	 * @param exp Experiment from which to initialize the DTOs.
	 */
	private void addBioAssayDtos(final Experiment exp) {
		for (BioAssay ba : exp.getBioAssays()) {
			BioAssayDto dto = new BioAssayDto();
			dto.setName(ba.getName());
			dto.setRemoteArrayId(ba.getArray().getName());
			dto.setRemoteId(ba.getName());
			if (!(ba instanceof DataContainingBioAssay)) {
				throw new IllegalArgumentException(
						"Bioassay must be of type DataContainingBioAssay");
			}
			DataContainingBioAssay dataContBioAssay =
				(DataContainingBioAssay) ba;
			List<Float> values = new ArrayList<Float>();
			List<String> reporterNames = new ArrayList<String>();
			for (Short chrom : dataContBioAssay.getChromosomes()) {
				ChromosomeArrayData caData =
					dataContBioAssay.getChromosomeArrayData(chrom);
				for (ArrayDatum datum : caData.getArrayData()) {
					values.add(datum.getValue());
					reporterNames.add(datum.getReporter().getName());
				}
			}
			dto.setValues(values);
			dto.setReporterNames(reporterNames);
			this.bioassays.put(dto.getRemoteId(), dto);
		}
	}
	
	/**
	 * Helper method to cache a set of <code>ArrayDto</code>
	 * objects.
	 * @param exp Experiment from which to initialize the DTOs.
	 */
	private void addArrayDtos(final Experiment exp) {
		for (BioAssay bioAssay : exp.getBioAssays()) {
			Array array = bioAssay.getArray();
			if (!this.arrays.containsKey(array.getName())) {
				ArrayDto dto = new ArrayDto();
				dto.setName(array.getName());
				dto.setRemoteId(array.getName());
				List<String> reporterNames = new ArrayList<String>();
				List<Short> chromosomes = new ArrayList<Short>();
				List<Long> locations = new ArrayList<Long>();
				if (!(bioAssay instanceof DataContainingBioAssay)) {
					throw new IllegalArgumentException(
							"Bioassay must be of type DataContainingBioAssay");
				}
				DataContainingBioAssay dataContBioAssay =
					(DataContainingBioAssay) bioAssay;
				for (Short chrom : bioAssay.getChromosomes()) {
					ChromosomeArrayData cad =
						dataContBioAssay.getChromosomeArrayData(chrom);
					for (ArrayDatum datum : cad.getArrayData()) {
						Reporter reporter = datum.getReporter();
						reporterNames.add(reporter.getName());
						chromosomes.add(chrom);
						locations.add(reporter.getLocation());
					}
				}
				dto.setReporterNames(reporterNames);
				dto.setChromosomeNumbers(chromosomes);
				dto.setChromosomeLocations(locations);
				this.arrays.put(dto.getRemoteId(), dto);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public Map<String, String> getExperimentIdsAndNames(
			final Principal principal)
	throws DataSourceAccessException {
		Map<String, String> idsAndNames = new HashMap<String, String>();
		for (String key : this.experiments.keySet()) {
			ExperimentDto exp = this.experiments.get(key);
			idsAndNames.put(key, exp.getName());
		}
		return idsAndNames;
	}


	/**
	 * {@inheritDoc}
	 */
	public Principal login( final String email,
							final String password) {
		return new Principal(email, password, DOMAIN);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDisplayName() {
		return DISPLAY_NAME;
	}


	/**
	 * {@inheritDoc}
	 */
	public ArrayDto getArrayDto(final String id)
	throws DataSourceAccessException {
		return this.arrays.get(id);
	}


	/**
	 * {@inheritDoc}
	 */
	public BioAssayDto getBioAssayDto(final String id)
	throws DataSourceAccessException {
		return this.bioassays.get(id);
	}


	/**
	 * {@inheritDoc}
	 */
	public ExperimentDto getExperimentDto(final String id)
	throws DataSourceAccessException {
		return this.experiments.get(id);
	}
}
