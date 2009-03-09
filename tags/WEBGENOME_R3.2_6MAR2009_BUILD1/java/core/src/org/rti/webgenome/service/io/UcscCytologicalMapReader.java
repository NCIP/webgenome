/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;

/**
 * Class to read cytoband data from from a text input stream
 * derived from a file downloaded from the UCSC Genome Browser
 * FTP site.  This file is from a table dump from the Genome Browser
 * annotation database.  Format of text input
 * stream is rectangular data with the following tab-delimited fields:
 * 
 * <pre>
 * CHROMOSOME, START_LOCATION, END_LOCATION, CYTOLOGICAL_LOCATION, STAIN
 * </pre>
 * 
 * Records from the same chromosome are clustered and ordered
 * by START_LOCATION ascending.
 * 
 * @author dhall
 *
 */
public class UcscCytologicalMapReader {

	/** Prefix before chromosome number. */
	private static final String CHROMOSOME_PREFIX = "chr";
	
	/** Organism. */
	private final Organism organism;
	
	/**
	 * Constructor.
	 * @param organism An organism.
	 */
	public UcscCytologicalMapReader(final Organism organism) {
		this.organism = organism;
	}
	
	/**
	 * Read in cytoband data from a stream
	 * originating from a UCSC Genome Browser
	 * annotation database table dump and create
	 * cytological maps.
	 * @param in Input stream.
	 * @return Cytological maps.
	 * @throws UcscFileFormatException if file format
	 * is bad.
	 * @throws IOException if there is a problem reading
	 * the stream.
	 */
	public final Collection<CytologicalMap> read(final InputStream in)
	throws UcscFileFormatException, IOException {
		Reader r = new InputStreamReader(in);
		return this.read(r);
	}
	
	
	/**
	 * Read in cytoband data from a reader
	 * originating from a UCSC Genome Browser
	 * annotation database table dump and create
	 * cytological maps.
	 * @param in A reader.
	 * @return Cytological maps.
	 * @throws UcscFileFormatException if file format
	 * is bad.
	 * @throws IOException if there is a problem reading
	 * the stream.
	 */
	public final Collection<CytologicalMap> read(final Reader in)
	throws UcscFileFormatException, IOException {
		Collection<CytologicalMap> maps =
			new ArrayList<CytologicalMap>();
		CytologicalMap map = null;
		BufferedReader reader = new BufferedReader(in);
		String line = reader.readLine();
		while (line != null) {
			StringTokenizer tok = new StringTokenizer(line, "\t");
			
			// Get data fields
			String chromosomeField = this.nextToken(tok);
			String startField = this.nextToken(tok);
			String endField = this.nextToken(tok);
			String nameField = this.nextToken(tok);
			String stainField = this.nextToken(tok);
			
			// Instantiate new cytoband
			Cytoband c = new Cytoband();
			c.setStart(this.parseLocation(startField));
			c.setEnd(this.parseLocation(endField));
			c.setName(nameField);
			c.setStain(stainField);
			
			// Add to cytological map
			short chromosome = this.parseChromosome(chromosomeField);
			if (map == null || chromosome != map.getChromosome()) {
				map = new CytologicalMap();
				map.setChromosome(chromosome);
				map.setOrganism(this.organism);
				maps.add(map);
			}
			if (c.inCentromere()) {
				if (map.getCentromereStart() < 0) {
					map.setCentromereStart(c.getStart());
				}
				map.setCentromereEnd(c.getEnd());
			}
			map.addCytoband(c);
			
			line = reader.readLine();
		}
		return maps;
	}
	
	
	/**
	 * Retrieve next token from given tokenizer
	 * throwing exception if there are no more tokens.
	 * @param tok String tokenizer.
	 * @return Next token.
	 * @throws UcscFileFormatException if there
	 * are no more tokens.
	 */
	private String nextToken(final StringTokenizer tok)
	throws UcscFileFormatException {
		if (!tok.hasMoreTokens()) {
			throw new UcscFileFormatException("Too few columns");
		}
		return tok.nextToken();
	}
	
	
	/**
	 * Parse chromosome number from given string and throw
	 * exception if it cannot be parsed.
	 * @param str A string encoding a chromosome number.
	 * @return Chromosome number.
	 * @throws UcscFileFormatException if chromosome
	 * number cannot be parsed from given string.
	 */
	private short parseChromosome(final String str)
	throws UcscFileFormatException {
		if (str.length() <= CHROMOSOME_PREFIX.length()
				|| str.indexOf(CHROMOSOME_PREFIX) != 0) {
			throw new UcscFileFormatException(
					"Invalid chromosome number '" + str + "'");
		}
		String chromosomeField = str.substring(
				CHROMOSOME_PREFIX.length());
		short chromosome = -1;
		if ("X".equalsIgnoreCase(chromosomeField)) {
			chromosome = 23;
		} else if ("Y".equalsIgnoreCase(chromosomeField)) {
			chromosome = 24;
		} else {
			try {
				chromosome = Short.parseShort(chromosomeField);
			} catch (NumberFormatException e) {
				throw new UcscFileFormatException(
						"Invalid chromosome number '" + str + "'");
			}
		}
		return chromosome;
	}
	
	
	/**
	 * Parse chromosome location from given string.
	 * @param str String encoding chromosome location.
	 * @return Chromosome location.
	 * @throws UcscFileFormatException if string
	 * does not encode a valid number.
	 */
	private long parseLocation(final String str)
	throws UcscFileFormatException {
		long loc = -1;
		try {
			loc = Long.parseLong(str);
		} catch (NumberFormatException e) {
			throw new UcscFileFormatException(
					"Invalid number '" + str + "'");
		}
		return loc;
	}
}
