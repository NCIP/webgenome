/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/GenomeIntervalDto.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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

package org.rti.webcgh.array;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.rti.webcgh.graph.Units;
import org.rti.webgenome.client.BioAssayDataConstraints;

/**
 * DTO for transferring genome interval information
 */
public class GenomeIntervalDto implements Serializable{
	
	// =======================
	//    Constants
	// =======================
	
	private static final String DELIMITER = ",";
	
	
	// ===================================
	//       Attributes
	// ===================================
	
	private int chromosome = -1;
	private double start = -1;
	private double end = -1;
	

	/**
	 * @return Returns the chromNum.
	 */
	public int getChromosome() {
		return chromosome;
	}
	
	
	/**
	 * @return Returns the end.
	 */
	public double getEnd() {
		return end;
	}
	
	
	/**
	 * @return Returns the start.
	 */
	public double getStart() {
		return start;
	}
	
	
	/**
	 * @param chromosome The chromosome to set.
	 */
	public void setChromosome(int chromosome) {
		this.chromosome = chromosome;
	}
	
	
	/**
	 * @param end The end to set.
	 */
	public void setEnd(double end) {
		this.end = end;
	}
	
	
	/**
	 * @param start The start to set.
	 */
	public void setStart(double start) {
		this.start = start;
	}
	
	
	// ======================================
	//    Constructors
	// ======================================
	
	/**
	 * Constructor
	 */
	public GenomeIntervalDto() {}
	
	
	/**
	 * Constructor
	 * @param chromosome Chromosome number
	 * @param start Start point
	 * @param end End point
	 */
	public GenomeIntervalDto(int chromosome, double start, double end) {
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
	}
	
	
	/**
	 * Does this DTO have position information?
	 * @return T/F
	 */
	public boolean hasPositionInfo() {
		return this.start > 0.0 && this.end > 0.0;
	}
	
	
	// =====================================
	//       Overrides from Object
	// =====================================
	
	/**
	 * Equals
	 * @param obj An object
	 * @return T/F
	 */
	public boolean equals(Object obj) {
		if (! (obj instanceof GenomeIntervalDto))
			return false;
		GenomeIntervalDto dto = (GenomeIntervalDto)obj;
		return
			this.chromosome == dto.chromosome &&
			this.start == dto.start &&
			this.end == dto.end;
	}
		
	
	// ===============================
	//      Private methods
	// ===============================
	
	private boolean isValid() {
		boolean valid = true;
		if (this.chromosome < 1)
			valid = false;
		else if ((Double.isNaN(this.start) && ! Double.isNaN(this.end)) ||
				 (! Double.isNaN(this.start) && Double.isNaN(this.end)))
			valid = false;
		return valid;
	}
	
	
	// =====================================
	//       Static methods
	// =====================================
	
	/**
	 * Parse genome interval DTOs from given string
	 * @param str A string
	 * @param units Units
	 * @return genome interval DTOs
	 * @throws GenomeIntervalFormatException
	 */
	public static GenomeIntervalDto[] parse(String str, Units units) throws GenomeIntervalFormatException {
		if (str == null)
			return null;
		if (str.trim().length() < 1)
			return new GenomeIntervalDto[0];
		List dtoList = new ArrayList();
		for (StringTokenizer tok = new StringTokenizer(str, DELIMITER); tok.hasMoreTokens();)
			dtoList.add(GenomeIntervalDto.decode(tok.nextToken().trim(), units));
		GenomeIntervalDto[] dtos = new GenomeIntervalDto[0];
		dtos = (GenomeIntervalDto[])dtoList.toArray(dtos);
		return dtos;
	}
	
	
	public static String encode(BioAssayDataConstraints[] constraints) {
		StringBuffer encoding = new StringBuffer();
		for (int i = 0; i < constraints.length; i++) {
			BioAssayDataConstraints con = constraints[i];
			if (i > 0)
				encoding.append(DELIMITER);
			encoding.append(con.getChromosome() + ":" + 
					con.getStartPosition() + "-" + con.getEndPosition());
		}
		return encoding.toString();
	}
	
	
	/**
	 * Equals method
	 * @param d1 A genome interval DTO
	 * @param d2 Another genome interval DTO
	 * @return T/F
	 */
	public static boolean equal(GenomeIntervalDto[] d1, GenomeIntervalDto[] d2) {
		if (d1 == null || d2 == null || d1.length != d2.length)
			return false;
		boolean equal = true;
		for (int i = 0; i < d1.length && i < d2.length && equal; i++) {
			if (! d1[i].equals(d2[i]))
				equal = false;
		}
		return equal;
	}
	
	
	private static GenomeIntervalDto decode(String encoding, Units units) throws GenomeIntervalFormatException {
		int chromosome = -1;
		double start = Double.NaN;
		double end = Double.NaN;
		try {
			chromosome = GenomeIntervalDto.parseChromosome(encoding);
			start = (double)units.toBp(GenomeIntervalDto.parseStart(encoding));
			end = (double)units.toBp(GenomeIntervalDto.parseEnd(encoding));
		} catch (NumberFormatException e) {
			throw new GenomeIntervalFormatException("Bad genome interval format");
		}
		GenomeIntervalDto dto = new GenomeIntervalDto(chromosome, start, end);
		if (! dto.isValid())
			throw new GenomeIntervalFormatException("Bad genome interval format");
		return dto;
	}
	
	
	private static int parseChromosome(String encoding) {
		int chrom = -1;
		int p = 0;
		int q = encoding.indexOf(":");
		if (q < 0)
			q = encoding.length();
		if (p < q)
			chrom = Integer.parseInt(encoding.substring(p, q).trim());
		return chrom;
	}
	
	private static double parseStart(String encoding) throws GenomeIntervalFormatException {
		double start = Double.NaN;
		int p = encoding.indexOf(":");
		int q = encoding.indexOf("-");
		if (p >= 0 && q < 0)
		    throw new GenomeIntervalFormatException("Bad genome interval format");
		if (p >= 0 && q >= 0 && p + 1 < q)
			start = Double.parseDouble(encoding.substring(p + 1, q).trim());
		return start;
	}
	
	private static double parseEnd(String encoding) {
		double end = Double.NaN;
		int p = encoding.indexOf("-");
		int q = encoding.length();
		if (p >= 0 && p + 1 < q)
			end = Double.parseDouble(encoding.substring(p + 1, q).trim());
		return end;
	}
}
