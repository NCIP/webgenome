/*
$Revision: 1.3 $
$Date: 2007-07-18 21:42:48 $

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
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.IOUtils;

/**
 * Implementation of <code>Serializer</code> interface that
 * serialized to a file.
 *
 */
public final class FileSerializer implements Serializer {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(FileSerializer.class);
	
	/** File extension. */
	public static final String FILE_EXTENSION = ".obj";
	
	// ============================
	//       Attributes
	// ============================
	
	/** Directory containing serialized files. */
	private final File directory;
	
	/** Object ID sequence. */
	private final UniqueFileNameGenerator uniqueFileNameGenerator;
	
	/** Directory name delimiter. */
	private static final String DIRECTORY_DELIMITER = "/";
	

	// ==============================
	//       Constructors
	// ==============================

	/**
	 * Constructor.
	 * @param directoryPath Directory to serialize files into
	 */
	public FileSerializer(final String directoryPath) {
		this.directory = new File(directoryPath);
		
		// Create directory if missing
		if (!this.directory.exists()) {
			LOGGER.info("Creating directory '" + directoryPath
					+ "' for serializing files");
			try {
				FileUtils.createDirectory(directoryPath);
			} catch (Exception e) {
				throw new WebGenomeSystemException(
						"Failed to create directory '"
						+ directoryPath + "' for serializing data", e);
			}
		}
		if (!this.directory.isDirectory()) {
			throw new WebGenomeSystemException(
					"Directory path '" + directoryPath
					+ "' does not reference a real directory");
        }
		
		// Set properties
		this.uniqueFileNameGenerator =
			new UniqueFileNameGenerator(this.directory, FILE_EXTENSION);
	}


	// ===============================
	//      Serializer interface
	// ===============================
	
	
	/**
	 * Serialize given serializable object and return a file
	 * name that can be used to de-serialize object at a later time.
	 * @param serializable A serializable object
	 * @return File name, but not absolute path.
	 */
	public String serialize(final Serializable serializable) {
		String fileName = this.uniqueFileNameGenerator.next();
		String path = this.getAbsolutePath(fileName);
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(serializable);
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error serializing object", e);
		} finally {
			IOUtils.close(out);
		}
		return fileName;
	}

	
	/**
	 * De-serialize object from given file.
	 * @param fileName File name, but not absolute path
	 * @return A serializable object
	 */
	public Serializable deSerialize(final String fileName) {
		String path = this.getAbsolutePath(fileName);
		File file = new File(path);
		if (!file.exists()) {
			throw new WebGenomeSystemException("Cannot find file '"
                    + file + "'");
        }
		Serializable serializable = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			serializable = (Serializable) in.readObject();
		} catch (Exception e) {
			throw new WebGenomeSystemException(
					"Error de-serializing object from file '"
                    + fileName + "'", e);
		} finally {
			IOUtils.close(in);
		}
		return serializable;
	}
	

	/**
	 * Decommission all objects managed by serializer.
	 * After object has been decommissioned, it can
	 * no longer be de-serialized.
	 */
	public void decommissionAllObjects() {
		File[] files = this.directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].delete()) {
				LOGGER.warn("Could not decomission file '"
                        + files[i].getAbsolutePath()
                        + "'");
            }
        }
	}
	
	
	/**
	 * Decommission object that is serialized in given file.
	 * After object has been decommissioned, it can
	 * no longer be de-serialized.
	 * @param fileName Name of file (not absolute path)
	 * containing serialized object.
	 */
	public void decommissionObject(final String fileName) {
		String path = this.getAbsolutePath(fileName);
		File file = new File(path);
		if (!file.exists()) {
			throw new IllegalArgumentException("Cannot find object with id '"
                    + fileName + "'");
        }
		if (!file.delete()) {
			LOGGER.warn("Could not decomission file '"
                    + file.getAbsolutePath() + "'");
        }
	}
	
	
	
	/**
	 * An absolute path.
	 * @param fileName File name
	 * @return Absolute path
	 */
	private String getAbsolutePath(final String fileName) {
		return this.directory.getAbsolutePath() + DIRECTORY_DELIMITER 
		    + fileName;
	}
}
