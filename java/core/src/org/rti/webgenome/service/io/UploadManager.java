/*
$Revision: 1.3 $
$Date: 2007-04-17 15:22:34 $

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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.IOUtils;


/**
 * This class manages the uploading of data files
 * from a user into their session into a working directory.
 * It is intended to be used as a singleton.
 */
public class UploadManager {
	
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
	private static final Logger LOGGER = Logger.getLogger(UploadManager.class);
	

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
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param workingDirPath Path to working directory used
	 * for parsing data.
	 */
	public UploadManager(final String workingDirPath) {
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
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	
	/**
     * Upload data from given input stream into a new file in
     * the working directory.
     * @param in Upload inputstream.
     * @return Name of file containing uploaded data.  The name of this
     * file will be a randomly-generated unique file name.
     * The absolute path is not returned.
     */		
	public String upload(final InputStream in) {
		
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
		
		return fname;
	}
	
	
	/**
	 * Delete file with given name form working directory.
	 * @param fileName Name of file to delete (not absolute path).
	 */
	public void delete(final String fileName) {
		String path =
			this.workingDir.getAbsolutePath() + File.separator + fileName;
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			throw new WebGenomeSystemException("File '" + path + "' not valid");
		}
		if (!file.delete()) {
			LOGGER.warn("Unable to delete uploaded file '" + path + "'");
		}
	}
}
