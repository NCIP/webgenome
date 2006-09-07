/*
$Revision$
$Date$

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

package org.rti.webcgh.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.IOUtils;

/**
 * Implementation of <code>Serializer</code> interface that
 * serialized to a file.
 *
 */
public final class FileSerializer implements Serializer {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(FileSerializer.class);
	
	/** File extension. */
	private static final String FILE_EXTENSION = ".obj";
	
	/** Directory name delimiter. */
	private static final String DIRECTORY_DELIMITER = "/";
	
	// ============================
	//       Attributes
	// ============================
	
	/** Directory containing serialized files. */
	private final File directory;
	
	/** Object ID sequence. */
	private final OidSequence oidSequence;
	

	// ==============================
	//       Constructors
	// ==============================

	/**
	 * Constructor.
	 * @param directoryPath Directory to serialize files into
	 */
	public FileSerializer(final String directoryPath) {
		this.directory = new File(directoryPath);
		
		// Make sure directory exists
		if (!this.directory.exists() || !this.directory.isDirectory()) {
			throw new IllegalArgumentException("Directory '"
                    + directory + "' does not exist");
        }
		
		// Set properties
		this.oidSequence = new OidSequence(this.directory);
	}


	// ===============================
	//      Serializer interface
	// ===============================
	
	
	/**
	 * Serialize given serializable object and return an object
	 * ID that can be used to de-serialize object at a later time.
	 * @param serializable A serializable object
	 * @return An object identifier
	 */
	public long serialize(final Serializable serializable) {
		long oid = this.oidSequence.next();
		String fname = this.getFileName(oid);
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(fname));
			out.writeObject(serializable);
		} catch (Exception e) {
			throw new WebcghSystemException("Error serializing object", e);
		} finally {
			IOUtils.close(out);
		}
		return oid;
	}

	
	/**
	 * De-serialize object with given object id.
	 * @param objectId An object identifier
	 * @return A serializable object
	 */
	public Serializable deSerialize(final long objectId) {
		String fname = this.getFileName(objectId);
		File file = new File(fname);
		if (!file.exists()) {
			throw new WebcghSystemException("Cannot find file '"
                    + file + "'");
        }
		Serializable serializable = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			serializable = (Serializable) in.readObject();
		} catch (Exception e) {
			throw new WebcghSystemException(
					"Error de-serializing object with id '"
                    + objectId + "'", e);
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
	 * Decommission object given by objectId.
	 * After object has been decommissioned, it can
	 * no longer be de-serialized.
	 * @param objectId An object identifier
	 */
	public void decommissionObject(final long objectId) {
		String fname = this.getFileName(objectId);
		File file = new File(fname);
		if (!file.exists()) {
			throw new IllegalArgumentException("Cannot find object with id '"
                    + objectId + "'");
        }
		if (!file.delete()) {
			LOGGER.warn("Could not decomission file '"
                    + file.getAbsolutePath() + "'");
        }
	}
    
	
	// ===============================
	//       Private methods
	// ===============================
	
	
	/**
	 * Return a file name corresponding to given object id.
	 * @param oid Object id
	 * @return A file name
	 */
	private String getFileName(final long oid) {
		return this.directory.getAbsolutePath() + DIRECTORY_DELIMITER 
		    + oid + FILE_EXTENSION;
	}
	
	
	/**
	 * Parse object id from file name.
	 * @param file A file
	 * @return Object id or -1 if an id cannot be parsed
	 */
	private long getObjectId(final File file) {
		long oid = (long) -1;
		String fname = file.getName();
		int p = fname.indexOf(FILE_EXTENSION);
		if (p >= 0) {
			try {
				oid = Long.parseLong(fname.substring(0, p));
			} catch (NumberFormatException e) {
	            LOGGER.warn("Data directory contains non-webGenonome file '"
	                    + file.getName() + "'");
	        }
		}
		return oid;
	}
	
	// ===============================
	//     Inner classes
	// ===============================
	
	/**
	 * Helper class to generate a sequence of object identifiers.
	 */
	class OidSequence {
		
		/** Next object id in the sequence. */
		private long nextInSequence = (long) 0;
		
		/**
		 * Constructor.
         * @param directory Directory containing files.
		 */
		public OidSequence(final File directory) {
			
			// Set next in sequence
			if (directory == null || !directory.isDirectory()) {
				throw new IllegalArgumentException("'"
                        + directory.getAbsolutePath()
                        + "' is not a valid directory");
            }
			File[] files = directory.listFiles();
			this.nextInSequence = (long) -1;
			for (int i = 0; i < files.length; i++) {
				long candidate = getObjectId(files[i]);
				if (candidate >= 0 && candidate > this.nextInSequence) {
					this.nextInSequence = candidate;
                }
			}
			this.nextInSequence++;
		}
		
		/**
		 * Return next object id in the sequence.
		 * @return An object id
		 */
		public long next() {
			long value = this.nextInSequence;
			if (this.nextInSequence == Long.MAX_VALUE) {
				this.nextInSequence = (long) 0;
            } else {
				this.nextInSequence++;
            }
			return value;
		}
	}

}
