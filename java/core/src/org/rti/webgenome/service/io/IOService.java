/*
$Revision: 1.13 $
$Date: 2007-11-28 19:51:20 $

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


package org.rti.webgenome.service.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.DataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.ZipEntryMetaData;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.util.IdGenerator;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.IOUtils;


/**
 * Facade class for performing IO of data files.  Normally
 * clients will only interact with this class for IO.
 * @author dhall
 */
public class IOService {
	
	//
	//     STATICS
	//
	
	/**
	 * Size of buffer array used for streaming uploaded
	 * bits to a file.
	 */
	private static final int BUFFER_SIZE = 1000000; //1MB
	
	/**
	 * File name extension used for files saved in the
	 * working directory.
	 */
	private static final String FILE_EXTENSION = ".smd";
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(IOService.class);
	
	/** Name of manifest file in a JAR archive. */
	private static final String JAR_MANIFEST_FILE_NAME = "MANIFEST.MF";
	

	//
	//     ATTRIBUTES
	//
	
	/** Working directory for parsing data. */
	private final File workingDir;
	
	/**
	 * Generates unique file names for uploaded files in the
	 * <code>workingDir</code> directory.
	 */
	private final UniqueFileNameGenerator fileNameGenerator;
	
	/** Data file manager. */
	private final DataFileManager dataFileManager;
	
	/** Generator of primary key values for experiments. */
	private IdGenerator experimentIdGenerator = null;
	
	/** Generator of primary key values for bioassays. */
	private IdGenerator bioAssayIdGenerator = null;
	
	//
	//  S E T T E R S
	//
	
	/**
	 * Set generator for bioassay primary key values.
	 * @param bioassayIdGenerator Generator of IDs
	 */
	public void setBioAssayIdGenerator(
			final IdGenerator bioassayIdGenerator) {
		this.bioAssayIdGenerator = bioassayIdGenerator;
	}


	/**
	 * Set generator for experiment primary key values.
	 * @param experimentIdGenerator Generator of IDs
	 */
	public void setExperimentIdGenerator(
			final IdGenerator experimentIdGenerator) {
		this.experimentIdGenerator = experimentIdGenerator;
	}
	
	//
	//     CONSTRUCTORS
	//


	/**
	 * Constructor.
	 * @param workingDirPath Path to working directory used
	 * for parsing data.
	 * @param dataFileManager Manages serialization/deserialization
	 * of array data
	 */
	public IOService(final String workingDirPath,
			final DataFileManager dataFileManager) {
		this.workingDir = new File(workingDirPath);
		if (!this.workingDir.exists()) {
			try {
				LOGGER.info("Creating directory for file uploads: "
						+ workingDirPath);
				FileUtils.createDirectory(workingDirPath);
			} catch (Exception e) {
				throw new WebGenomeSystemException(
						"Error creating file upload directory", e);
			}
		}
		if (!this.workingDir.isDirectory()) {
			throw new IllegalArgumentException(
					"Working directory path does not reference a "
					+ "real directory");
		}
		this.fileNameGenerator = new UniqueFileNameGenerator(
				this.workingDir, FILE_EXTENSION);
		this.dataFileManager = dataFileManager;
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	
	/**
     * Upload data from given input stream into a new file in
     * the working directory.
     * @param in Upload inputstream.
     * @return File containing uploaded data.  The name of this
     * file will be a randomly-generated unique file name.
     */		
	public File upload(final InputStream in) {
		
		// Buffer for uploading
		byte[] buffer = new byte[BUFFER_SIZE];
		
		// Generate file for holding uploaded data
		String fname = this.fileNameGenerator.next();
		String path =
			this.workingDir.getAbsolutePath() + File.separator + fname;
		File file = new File(path);
		
		// Stream data into file
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int bytesRead = in.read(buffer);
			while (bytesRead > 0) {
				out.write(buffer, 0, bytesRead);
				bytesRead = in.read(buffer);
			}
			out.flush();
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error uploading file", e);
		} finally {
			IOUtils.close(out);
		}
		
		return file;
	}
	
	
	/**
	 * Upload and extract individual data files from ZIP file.  Individual
	 * files will be stored locally.  The original ZIP file will not.
	 * @param in Input stream to ZIP file.
	 * @param zipFileName Zip file name on remote system.
	 * @param format Rectangular data file format
	 * @return Metadata for zip data.
	 */
    public ZipFileMetaData uploadZipFile(final InputStream in,
    		final String zipFileName, final RectangularTextFileFormat format) {
    	
    	// Stream ZIP file to disk
    	File zipFile = this.upload(in);
    	
    	// Instantiate zip file metadata
    	ZipFileMetaData meta = new ZipFileMetaData(zipFileName);
    	meta.setFileFormat(format);
    	
    	// Extract zip entry files
    	ZipInputStream zipIn = null;
    	try {
			zipIn = new ZipInputStream(
					new FileInputStream(zipFile));
			ZipEntry zipEntry = zipIn.getNextEntry();
			while (zipEntry != null) {
				if (this.probableDataFile(zipEntry)) {
					File zipEntryFile = this.upload(zipIn);
					ZipEntryMetaData zeMeta = new ZipEntryMetaData(
							zipEntryFile, zipEntry.getName());
					meta.add(zeMeta);
					RectangularFileReader reader =
						new RectangularFileReader(zipEntryFile);
					reader.setDelimiter(format.getDelimiter());
					zeMeta.setColumnHeadings(reader.getColumnHeadings());
				}
				zipEntry = zipIn.getNextEntry();
			}
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error extracint ZIP file", e);
		} finally {
			IOUtils.close(zipIn);
		}
		
		// Delete ZIP file
		if (!zipFile.delete()) {
			LOGGER.warn("Unable to delete ZIP file");
		}
    	
    	return meta;
    }
    
    
    /**
     * Determine if given ZIP file entry likely represents a data
     * containing file.
     * @param zipEntry ZIP file entry
     * @return T/F
     */
    private boolean probableDataFile(final ZipEntry zipEntry) {
    	return
    		!zipEntry.isDirectory()
    		&& zipEntry.getName().indexOf(JAR_MANIFEST_FILE_NAME) < 0;
    }
    
	
	/**
	 * Delete file with given name form working directory.
	 * @param fileName Name of file to delete (not absolute path).
	 */
	public void delete(final String fileName) {
		String path =
			this.workingDir.getAbsolutePath() + File.separator + fileName;
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			LOGGER.info("Deleting serialized data file '"
					+ file.getAbsolutePath() + "'");
			if (!file.delete()) {
				LOGGER.warn("Unable to delete uploaded file '" + path + "'");
			}
		}
	}
	
	/**
	 * Load SMD format data from named file and put in shopping cart.
	 * @param upload Upload data source properties
	 * @param shoppingCart Shopping cart
	 * @return New experiment that was added to shopping cart.
	 * Clients should not subquently add this experiment object to
	 * the cart.  It has already been added.
	 * @throws SmdFormatException If file does not contain
	 * valid SMD format data
	 */
	public Experiment loadSmdData(final UploadDataSourceProperties upload,
			final ShoppingCart shoppingCart)
	throws SmdFormatException {
		Experiment experiment = new Experiment(upload.getExperimentName());
		experiment.setOrganism(upload.getOrganism());
		experiment.setId(this.experimentIdGenerator.nextId());
		experiment.setQuantitationType(upload.getQuantitationType());
		File reporterFile = null;
		String reporterNameColName = null;
		RectangularTextFileFormat format = null;
		if (upload.getReporterLocalFileName() == null) {
			Set<DataFileMetaData> meta = upload.getDataFileMetaData();
			if (meta == null || meta.size() < 1) {
				throw new IllegalArgumentException("No data file specified");
			}
			DataFileMetaData firstMeta = meta.iterator().next();
			reporterFile = this.getWorkingFile(
					firstMeta.getLocalFileName());
			reporterNameColName = firstMeta.getReporterNameColumnName();
			format = firstMeta.getFormat();
		} else {
			reporterFile = this.getWorkingFile(
				upload.getReporterLocalFileName());
			reporterNameColName =
				upload.getReporterFileReporterNameColumnName();
			format = upload.getReporterFileFormat();
		}
		SmdFileReader reader = new SmdFileReader(reporterFile,
				reporterNameColName,
				upload.getChromosomeColumnName(),
				upload.getPositionColumnName(),
				upload.getPositionUnits(), format);
		Array array = this.dataFileManager.serializeReporters(reader);
		for (DataFileMetaData meta : upload.getDataFileMetaData()) {
			File dataFile = this.getWorkingFile(meta.getLocalFileName());
			this.dataFileManager.convertSmdData(reader, experiment, dataFile,
					meta, upload.getOrganism(), array);
		}
		experiment.setDataSourceProperties(upload);
		ColorChooser colorChooser = shoppingCart.getBioassayColorChooser();
		for (BioAssay ba : experiment.getBioAssays()) {
			ba.setId(this.bioAssayIdGenerator.nextId());
			ba.setColor(colorChooser.nextColor());
		}
		shoppingCart.add(experiment);
		return experiment;
	}
	
	
	/**
	 * Delete all serialized data files associated with
	 * given experiment. 
	 * @param exp An experiment
	 */
	public void deleteDataFiles(final Experiment exp) {
		if (!exp.dataInMemory()) {
			
			// If data originated from file upload, there
			// will be one reporter file for upload which
			// will not be used by any other data.  Thus,
			// it should be deleted.  Also delete temp
			// upload file.
			boolean deleteReporters = false;
			DataSourceProperties props = exp.getDataSourceProperties();
			if (props instanceof UploadDataSourceProperties) {
				deleteReporters = true;
				UploadDataSourceProperties uProps =
					(UploadDataSourceProperties) props;
				this.delete(uProps.getReporterLocalFileName());
				for (DataFileMetaData meta : uProps.getDataFileMetaData()) {
					this.delete(meta.getLocalFileName());
				}
			}
			
			// Delete files and possibly reporters
			this.dataFileManager.deleteDataFiles(exp, deleteReporters);
		}
	}
	
	/**
	 * Get a serialized data transformer that is configured to
	 * use the same data file directory as this.
	 * @return Serialized data transformer.
	 */
	public SerializedDataTransformer getSerializedDataTransformer() {
		return new SerializedDataTransformer(this.dataFileManager);
	}
	
	/**
	 * Get a serialized chromosome array data getter that is
	 * configured to use the same data file directory as this.
	 * @return Serialized chromosome array data getter
	 */
	public SerializedChromosomeArrayDataGetter
	getSerializedChromosomeArrayDataGetter() {
		return new SerializedChromosomeArrayDataGetter(this.dataFileManager);
	}
	
	/**
	 * Get all column headings from given file.
	 * @param fileName Name of file in working directory.  This is
	 * not an absolute path.
	 * @param format File format
	 * @return Column headings
	 */
	public Set<String> getColumnHeadings(final String fileName,
			final RectangularTextFileFormat format) {
		Set<String> cols = new HashSet<String>();
		File file = this.getWorkingFile(fileName);
		if (!file.exists() || !file.isFile()) {
			throw new WebGenomeSystemException(
					"Cannot get headings from file '" + fileName + "'.");
		}
		RectangularFileReader reader = new RectangularFileReader(file);
		reader.setDelimiter(format.getDelimiter());
		cols.addAll(reader.getColumnHeadings());
		return cols;
	}
	
	/**
	 * Get all column headings from given data files.
	 * @param dataFileMetaData Metadata on data files
	 * @return Union of all column headings
	 */
	public Set<String> getColumnHeadings(
			final Collection<DataFileMetaData> dataFileMetaData) {
		Set<String> cols = new HashSet<String>();
		for (DataFileMetaData meta : dataFileMetaData) {
			String fileName = meta.getLocalFileName();
			System.out.println("Meta: " + meta.getRemoteFileName() + meta.getLocalFileName());
			cols.addAll(this.getColumnHeadings(fileName, meta.getFormat()));
		}
		return cols;
	}
	
	/**
	 * Get file referenced by given file name, which is in
	 * {@code workingDir}.
	 * @param fileName Name of file (not absolute path)
	 * @return Working file
	 */
	public File getWorkingFile(final String fileName) {
		String path = this.workingDir.getAbsolutePath() + "/" + fileName;
		return new File(path);
	}
}
