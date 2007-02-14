/*
$Revision: 1.3 $
$Date: 2007-02-14 17:47:48 $

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

package org.rti.webcgh.service.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.util.ChromosomeCoder;
import org.rti.webcgh.util.StringTokenizerUtils;

/**
 * <p>
 * Class to read gene data from from a text input stream
 * derived from a file downloaded from the UCSC Genome Browser
 * FTP site.  This file is from a table dump from the Genome Browser
 * annotation database.  The input is rectangular with fields
 * corresponding directly to the database table structure
 * shown in the SQL code snipped below.  This snipped was
 * also downloaded from the UCSC FTP site.
 * <p>
 * 
 * <pre>
 * CREATE TABLE knownGene (
 *   name varchar(255) NOT NULL default '',
 *   chrom varchar(255) NOT NULL default '',
 *   strand char(1) NOT NULL default '',
 *   txStart int(10) unsigned NOT NULL default '0',
 *   txEnd int(10) unsigned NOT NULL default '0',
 *   cdsStart int(10) unsigned NOT NULL default '0',
 *   cdsEnd int(10) unsigned NOT NULL default '0',
 *   exonCount int(10) unsigned NOT NULL default '0',
 *   exonStarts longblob NOT NULL,
 *   exonEnds longblob NOT NULL,
 *   proteinID varchar(40) NOT NULL default '',
 *   alignID varchar(8) NOT NULL default '',
 *   KEY name (name(16)),KEY chrom (chrom(16),txStart),
 *   KEY chrom_2 (chrom(16),txEnd),
 *   KEY protein (proteinID(16)),
 *   KEY align (alignID)
 * ) TYPE=MyISAM;
 * </pre>
 * 
 * <p>
 * The following fields are parsed out:
 * </p>
 * 
 * <ul>
 *   <li>name</li>
 *   <li>chrom</li>
 *   <li>txStart</li>
 *   <li>txEnd</li>
 *   <li>exonStarts</li>
 *   <li>exonEnds</li>
 * </ul>
 * @author dhall
 *
 */
public class UcscGeneReader {

	//
	//     ATTRIBUTES
	//
	
	/** Organism associated with data being read. */
	private final Organism organism;
	
	/**
	 * Next record that will be parsed upon a 
	 * call to the next() method.
	 */
	private String nextRecord = null;
	
	/** Buffered reader for reading input line-by-line. */
	private final BufferedReader in;
	
	/** Index of next record to parse. */
	private int recordNumber = 0;
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param input Data input
	 * @param organism Organism associated with data
	 * being read.
	 * @throws IOException if a basic read problem
	 * is encountered
	 */
	public UcscGeneReader(final Reader input,
			final Organism organism)
	throws IOException {
		this.in = new BufferedReader(input);
		this.organism = organism;
		this.advance();
	}
	
	
	/**
	 * Advance input stream to next record, if any.
	 * @throws IOException if there is a basic read
	 * problem.
	 */
	private void advance() throws IOException {
		this.nextRecord = in.readLine();
		this.recordNumber++;
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Is there another record?
	 * @return T/F
	 */
	public final boolean hasNext() {
		return this.nextRecord != null;
	}
	
	
	/**
	 * Get the next gene feature in the stream.
	 * @return Next gene feature in the stream
	 * @throws IOException if there is a basic
	 * read error
	 * @throws UcscFileFormatException if the
	 * file format does not comply with the
	 * UCSC gene table dump format
	 */
	public final AnnotatedGenomeFeature next()
	throws IOException, UcscFileFormatException {
		AnnotatedGenomeFeature feat = null;
		if (this.hasNext()) {
			try {
				feat = this.parseNextRecord();
			} catch (UcscFileFormatException e) {
				throw e;
			} finally {
				this.advance();
			}
		}
		return feat;
	}
	
	
	/**
	 * Parse the <code>nextRecord</code> field and
	 * instantiate a new gene object.
	 * @return A new gene object
	 * @throws UcscFileFormatException if the proper
	 * fields are not found in the record being parsed
	 */
	private AnnotatedGenomeFeature parseNextRecord()
	throws UcscFileFormatException {
		assert this.nextRecord != null;
		AnnotatedGenomeFeature feat = new AnnotatedGenomeFeature();
		feat.setAnnotationType(AnnotationType.GENE);
		feat.setOrganism(this.organism);
		StringTokenizer tok =
			new StringTokenizer(this.nextRecord, "\t");
		try {
			
			// Field 0: Name
			String token = StringTokenizerUtils.skip(1, tok);
			feat.setName(token);
			
			// Field 1: Chromosome
			token = StringTokenizerUtils.skip(1, tok);
			feat.setChromosome(ChromosomeCoder.decodeUcscFormat(
					token, this.organism));
			
			// Field 3: txStart
			token = StringTokenizerUtils.skip(2, tok);
			feat.setStartLocation(Long.parseLong(token));
			
			// Field 4: txEnd
			token = StringTokenizerUtils.skip(1, tok);
			feat.setEndLocation(Long.parseLong(token));
			
			// Field 8: exonStarts
			String exonStarts = StringTokenizerUtils.skip(4, tok);
			
			// Field 9: exonEnds
			String exonEnds = StringTokenizerUtils.skip(1, tok);
			
			// Add exons
			List<AnnotatedGenomeFeature> exons =
				this.createExons(exonStarts, exonEnds);
			feat.addChildren(exons);
			
		} catch (Exception e) {
			throw new UcscFileFormatException(
					"Invalid format in record " + this.recordNumber, e);
		}
		
		return feat;
	}
	
	
	/**
	 * Instantiate exon features.
	 * @param exonStarts Comma-separated list of exon start positions
	 * @param exonEnds Comma-separated list of exon end locations
	 * @return Exon features
	 * @throws UcscFileFormatException if the exon starts and ends
	 * cannot be parsed or are of unequal number
	 */
	private List<AnnotatedGenomeFeature> createExons(
			final String exonStarts, final String exonEnds)
			throws UcscFileFormatException {
		assert exonStarts != null && exonEnds != null;
		List<AnnotatedGenomeFeature> exons =
			new ArrayList<AnnotatedGenomeFeature>();
		StringTokenizer startTok = new StringTokenizer(exonStarts, ",");
		StringTokenizer endTok = new StringTokenizer(exonEnds, ",");
		while (startTok.hasMoreTokens() && endTok.hasMoreTokens()) {
			long start = -1, end = -1;
			try {
				start = Long.parseLong(startTok.nextToken());
				end = Long.parseLong(endTok.nextToken());
			} catch (NumberFormatException e) {
				throw new UcscFileFormatException(
						"Unparseable exon end points in record "
						+ this.recordNumber);
			}
			AnnotatedGenomeFeature exon = new AnnotatedGenomeFeature();
			exon.setAnnotationType(AnnotationType.EXON);
			exon.setStartLocation(start);
			exon.setEndLocation(end);
			exons.add(exon);
		}
		if (startTok.hasMoreTokens() || endTok.hasMoreTokens()) {
			throw new UcscFileFormatException(
					"Unequal number of exon start and end points "
					+ "in record " + this.recordNumber);
		}
		return exons;
	}
}
