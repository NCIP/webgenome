/*
$Revision: 1.2 $
$Date: 2007-07-18 21:42:49 $


*/

package org.rti.webgenome.service.util;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.service.io.DataFileManager;

/**
 * Implementation of <code>ChromosomeArrayDataGetter</code>
 * for serialized data.
 * @author dhall
 *
 */
public final class SerializedChromosomeArrayDataGetter implements
	ChromosomeArrayDataGetter {
	
	// ========================
	//      Attributes
	// ========================
	
	/**
	 * Data file manager for de-serilizing data.
	 * This should be set by injection.
	 */
	private DataFileManager dataFileManager = null;
	
	// =========================
	//     Getters/setters
	// =========================
	
	/**
	 * Get data file manager used for de-serilization
	 * of data.
	 * @return Data file manager
	 */
	public DataFileManager getDataFileManager() {
		return dataFileManager;
	}

	/**
	 * Set data file manager used for de-serilization
	 * of data.
	 * @param dataFileManager Data file manager
	 */
	public void setDataFileManager(final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}
	
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public SerializedChromosomeArrayDataGetter() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param dataFileManager Manager of serialized
	 * data files
	 */
	public SerializedChromosomeArrayDataGetter(
			final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}
	
	// =============================================
	//     ChromosomeArrayDataGetter interface
	// =============================================

	/**
	 * Get chromosome array data from given bioassay
	 * and chromosome.
	 * @param bioAssay A bioassay
	 * @param chromosome A chromosome
	 * @return Chromosome array data
	 */
	public ChromosomeArrayData getChromosomeArrayData(
			final BioAssay bioAssay,
			final short chromosome) {
		if (!(bioAssay instanceof DataSerializedBioAssay)) {
			throw new IllegalArgumentException(
					"Bioassay must be of type DataSerializedBioAssay");
		}
		return dataFileManager.loadChromosomeArrayData(
				(DataSerializedBioAssay) bioAssay, chromosome);
	}

}
