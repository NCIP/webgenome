/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
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

/**
 * Implementation of <code>Serializer</code> interface that
 * serialized to a file
 *
 */
public class FileSerializer implements Serializer {
	
	// Logger
	private static final Logger LOGGER = Logger.getLogger(FileSerializer.class);
	
	// File extension
	private static final String FILE_EXTENSION = ".obj";
	
	// ============================
	//       Attributes
	// ============================
	
	// Directory containing serialized files
	private final File directory;
	
	private final OidSequence oidSequence = new OidSequence();
	

	// ==============================
	//       Constructors
	// ==============================

	/**
	 * Constructor
	 * @param directoryPath Directory to serialize files into
	 */
	public FileSerializer(String directoryPath) {
		this.directory = new File(directoryPath);
		
		// Make sure directory exists
		if (! this.directory.exists() || ! this.directory.isDirectory())
			throw new IllegalArgumentException("Directory '" + directory + "' does not exist");
		
		// Try to clean out directory
		if (! this.cleanDirectory())
			LOGGER.warn("Could not clean out directory '" + 
					this.directory.getAbsolutePath() + "'");
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// Try to clean out directory
		if (! this.cleanDirectory())
			LOGGER.warn("Could not clean out directory '" + 
					this.directory.getAbsolutePath() + "'");
	}



	// ===============================
	//      Serializer interface
	// ===============================
	
	
	/**
	 * Serialize given serializable object and return an object
	 * ID that can be used to de-serialize object at a later time
	 * @param serializable A serializable object
	 * @return An object identifier
	 */
	public long serialize(Serializable serializable) {
		long oid = this.oidSequence.next();
		String fname = this.getFileName(oid);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname));
			out.writeObject(serializable);
		} catch (Exception e) {
			throw new WebcghSystemException("Error serializing object", e);
		}
		return oid;
	}

	
	/**
	 * De-serialize object with given object id
	 * @param objectId An object identifier
	 * @return A serializable object
	 */
	public Serializable deSerialize(long objectId) {
		String fname = this.getFileName(objectId);
		Serializable serializable = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname));
			serializable = (Serializable)in.readObject();
		} catch (Exception e) {
			throw new WebcghSystemException(
					"Error de-serializing object with id '" + objectId + "'", e);
		}
		return serializable;
	}
	
	// ===============================
	//       Private methods
	// ===============================
	
	/**
	 * Clean out directory of any files
	 * @return Returns whether or not directory
	 * was successfully cleaned
	 */
	private boolean cleanDirectory() {
		boolean cleanedAll = true;
		File[] files = this.directory.listFiles();
		for (int i = 0; i < files.length; i++)
			if (! files[i].delete())
				cleanedAll = false;
		return cleanedAll;
	}
	
	
	/**
	 * Return a file name corresponding to given object id
	 * @param oid Object id
	 * @return A file name
	 */
	private String getFileName(long oid) {
		return this.directory.getAbsolutePath() + "/" + oid + FILE_EXTENSION;
	}
	
	// ===============================
	//     Inner classes
	// ===============================
	
	/**
	 * Helper class to generate a sequence of object identifiers
	 */
	static class OidSequence {
		
		// Next object id in the sequence
		private long nextInSequence = (long)0;
		
		/**
		 * Constructor
		 */
		public OidSequence(){}
		
		/**
		 * Return next object id in the sequence
		 * @return An object id
		 */
		public long next() {
			long value = this.nextInSequence;
			if (this.nextInSequence == Long.MAX_VALUE)
				this.nextInSequence = (long)0;
			else
				this.nextInSequence++;
			return value;
		}
	}

}
