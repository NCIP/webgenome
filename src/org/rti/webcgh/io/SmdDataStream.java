/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/io/SmdDataStream.java,v $
$Revision: 1.1 $
$Date: 2006-04-25 13:32:51 $

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.core.WebcghSystemException;


/**
 * Convert stream of SMD data into data objects.
 * The following describes the SMD data file format:
 * 
 * (1) Data are delimited text (e.g., comma-separated values)
 * (2) Each column, with the exception of special columns,
 *     corresponds to a separate bioassay (i.e., physical array).
 * (3) Each row, with the exception of the first (i.e., column headings),
 *     corresponds to a reporter (i.e., probe).
 * (4) The following are special columns:
 *         i.  The first column contains reporter names
 *         ii. OPTIONAL: The file may contain columns with
 *             headings named 'CHROMOSOME' and 'POSITION.'
 *             These contain the chromosome number and physical
 *             position (i.e., base pair), respectively,
 *             of the corresponding reporter.
 *             The column names may be lower case or upper case.
 * (5) The heading (i.e., first row) of each column gives the identifier
 *     of the corresponding bioassay.
 * (6) Values in bioassay columns give the measurements of the corresponding
 *     reporters.
 *     
 * Example:
 * 
 * NAME		CHROMOSOME		POSITION		BIOASSAY1		BIOASSAY2
 * RP-1		1				15000			0.3				-0.1
 * RP-2		1				34000			-0.2			0.5
 *
 */
public class SmdDataStream {
	
	private static final String CHROMOSOME = "CHROMOSOME";
	private static final String POSITION = "POSITION";
		
	
	// ==============================
	//        Constructors
	// ==============================
	
	public SmdDataStream() {}
	
	// ==============================
	//         Public methods
	// ==============================
	
	/**
	 * Get next experiment from stream
	 */
	public Experiment loadExperiment(InputStream in) throws SmdFormatException {
		Experiment exp = new Experiment();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			
			// Read headings and create bioassays
			String line = reader.readLine();
			if (line == null)
				throw new SmdFormatException("File is empty");
			BioAssay[] bioAssays = this.createBioAssays(line);
			exp.add(bioAssays);
			boolean havePositions = this.havePositions(line);
			
		} catch (IOException e) {
			throw new WebcghSystemException("Error reading data stream", e);
		}
		return exp;
	}
	
	
	// ===============================
	//         Private methods
	// ===============================
	
	private BioAssay[] createBioAssays(String line) {
		return null;
	}
	
	
	private boolean havePositions(String line) {
		String uc = line.toUpperCase();
		return uc.indexOf(CHROMOSOME) >= 0 && uc.indexOf(POSITION) >= 0;
	}
}
