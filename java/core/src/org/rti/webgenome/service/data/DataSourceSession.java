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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.service.io.DataFileManager;


/**
 * Represents a workflow session of interacting with a data source.
 * Also provides a facade to classes related to data sources.
 * Typically the workflow will proceed as follows:
 * <pre>
 * (1) System displays list of data sources.  User selects one.
 * (2) User logs into data source.
 * (3) System displays lists of experiments the user can access, and the user
 * selects one or more.
 * (4) System imports selected experiments into the users shopping cart.
 * </pre>
 * @author dhall
 *
 */
public class DataSourceSession {

	//
	//  A T T R I B U T E S
	//
	
	/** Principal of user associated with session. */
	private Principal principal = null;
	
	/** Data source selected by user. */
	private final DataSource selectedDataSource;
	
	/** Manages serialization of data. */
	private final DataFileManager dataFileManger;
	
	/**
	 * Cache of arrays indexed on remote ID used so that all bioassays
	 * with the same logical array point to the same object.
	 */
	private final Map<String, Array> arrays = new HashMap<String, Array>();
	
	/**
	 * Cache of reporters indexed by name, which is used in grouping
	 * experiment values by chromosome.
	 */
	private final Map<String, Reporter> reporters =
		new HashMap<String, Reporter>();
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param selectedDataSource Data source selected by user
	 * @param dataFileManager Manages serialization of data
	 */
	public DataSourceSession(final DataSource selectedDataSource,
			final DataFileManager dataFileManager) {
		if (selectedDataSource == null) {
			throw new WebGenomeSystemException(
					"Data source cannot be null");
		}
		this.dataFileManger = dataFileManager;
		this.selectedDataSource = selectedDataSource;
	}
	
	//
	//  B U S I N E S S    M E T H O D S
	//
	
	/**
	 * Log user into previously selected data source.
	 * @param user User account name
	 * @param password User password
	 * @throws DataSourceSessionException if a valid data source was not
	 * previously selected
	 * @throws DataSourceAccessException if the given credentials could
	 * not be validated by the selected data source
	 */
	public void loginToSelectedDataSource(
			final String user, final String password)
	throws DataSourceSessionException, DataSourceAccessException {
		if (this.selectedDataSource == null) {
			throw new DataSourceSessionException(
					"Valid data source has not been selected");
		}
		this.principal = this.selectedDataSource.login(user, password);
		if (this.principal == null) {
			throw new DataSourceAccessException("User could not be logged in");
		}
	}
	
	
	/**
	 * Get map of available experiment IDs (keys) and associated
	 * experiment names (values) from the previously selected
	 * data source that are available to the user.
	 * @return Map of available experiment IDs (keys) and associated
	 * experiment names (values)
	 * @throws DataSourceSessionException If a user has not successfully
	 * logged into the selected data source.
	 * @throws DataSourceAccessException If there is some problem accessing
	 * data from the data source
	 */
	public Map<String, String> getExperimentIdsAndNames()
	throws DataSourceSessionException, DataSourceAccessException {
		if (this.principal == null) {
			throw new DataSourceSessionException("This method cannot be called "
					+ "unless user has logged into a data source");
		}
		return this.selectedDataSource.getExperimentIdsAndNames(this.principal);
	}
	
	
	/**
	 * Fetch experiments with given IDs from remot data source and
	 * deposit all data in the experiments objects.
	 * @param experimentIds IDs of experiments to fetch
	 * @param quantitationType Quantitation type of data
	 * @return Requested experiments
	 * @throws DataSourceAccessException if there is an error
	 * retrieving data from the remote data source
	 */
	public Collection<Experiment> fetchExperiments(
			final Collection<String> experimentIds,
			final QuantitationType quantitationType)
	throws DataSourceAccessException {
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		for (String id : experimentIds) {
			ExperimentDto dto = this.selectedDataSource.getExperimentDto(id);
			Experiment exp = this.depositData(dto, quantitationType);
			experiments.add(exp);
		}
		return experiments;
	}
	
	
	/**
	 * Fetches and deposits data from given DTO experiment object.
	 * @param dto Data transfer object
	 * @param quantitationType Quantitation type of data
	 * @return Requested experiment
	 * @throws DataSourceAccessException if there is an exception accessing
	 * data from the remote data source
	 */
	private Experiment depositData(final ExperimentDto dto,
			final QuantitationType quantitationType)
	throws DataSourceAccessException {
		Experiment exp = new Experiment();
		// Following text commented out because persistence
		// has been be modified to store this subclass
		// TODO: Modify persistence (i.e. database and Hibernate mapping)
		// to store this subclass
//		RemoteApiDataSourceProperties dataSourceProps =
//			new RemoteApiDataSourceProperties(this.principal.getName(),
//					this.principal.getPassword(), this.principal.getDomain());
//		exp.setDataSourceProperties(dataSourceProps);
		exp.setName(dto.getName());
		exp.setQuantitationType(quantitationType);
		for (String id : dto.getRemoteBioAssayIds()) {
			BioAssayDto bioAssayDto =
				this.selectedDataSource.getBioAssayDto(id);
			BioAssay ba = this.depositData(bioAssayDto);
			exp.add(ba);
		}
		return exp;
	}
	
	
	/**
	 * Deposit data from the given DTO into a new bioassay object.
	 * @param dto DTO containing data
	 * @return New bioassay object containing data
	 * @throws DataSourceAccessException if there is an error getting
	 * array data from remote data source
	 */
	private BioAssay depositData(final BioAssayDto dto)
	throws DataSourceAccessException {
		DataSerializedBioAssay ba = new DataSerializedBioAssay();
		Array array = this.getArray(dto.getRemoteArrayId());
		ba.setArray(array);
		ba.setName(dto.getName());
		ChromosomeArrayData cad = null;
		short currChrom = -1;
		Iterator<Float> valIt = dto.getValues().iterator();
		Iterator<String> nameIt = dto.getReporterNames().iterator();
		while (valIt.hasNext() && nameIt.hasNext()) {
			float value = valIt.next();
			String reporterName = nameIt.next();
			Reporter reporter = this.reporters.get(reporterName);
			if (reporter != null) {
				short chrom = reporter.getChromosome();
				if (cad == null || chrom != currChrom) {
					if (cad != null) {
						this.dataFileManger.saveChromosomeArrayData(ba, cad);
					}
					cad = new ChromosomeArrayData(chrom);
				}
				cad.add(new ArrayDatum(value, reporter));
				currChrom = chrom;
			}
		}
		if (cad != null) {
			this.dataFileManger.saveChromosomeArrayData(ba, cad);
		}
		return ba;
	}
	
	/**
	 * Get an array object with given ID.  This object may be cached
	 * or may have to be obtained through the remote data source.
	 * @param id ID of array in remote data source
	 * @return An array object
	 * @throws DataSourceAccessException if these is a problem obtaining
	 * data from the remote data source
	 */
	private Array getArray(final String id) throws DataSourceAccessException {
		Array array = this.arrays.get(id);
		if (array == null) {
			ArrayDto dto = this.selectedDataSource.getArrayDto(id);
			array = this.dataFileManger.serializeReporters(id,
					dto.getReporterNames(), dto.getChromosomeNumbers(),
					dto.getChromosomeLocations());
			array.setDisposable(true);
			this.arrays.put(id, array);
			List<Reporter> reporters =
				this.dataFileManger.recoverReporters(array);
			for (Reporter r : reporters) {
				this.reporters.put(r.getName(), r);
			}
		}
		return array;
	}
}
