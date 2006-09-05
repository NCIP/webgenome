/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ArrayDatumFactory.java,v $
$Revision: 1.4 $
$Date: 2006-09-05 14:06:45 $

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

import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.deprecated.DomainObjectFactory;

public class ArrayDatumFactory {
	
	private QuantitationType quantType;
	private GenomeAssembly genomeAssembly;
	private Map<String, Reporter> reporterCache = new HashMap<String, Reporter>();
	private Map<Short, Chromosome> chromosomeCache = new HashMap<Short, Chromosome>();
	
	
	/**
	 * Constructor
	 *
	 */
	public ArrayDatumFactory() {
		this("hg18", QuantitationType.LOG_2_RATIO, "Homo", "sapiens");
	}
	
	/**
	 * 
	 * @param genomeAssemblyName GenomeAssembly name
	 * @param quantType QuantitationType enum
	 * @param genus Genus
	 * @param species Species
	 */
	public ArrayDatumFactory(String genomeAssemblyName, QuantitationType quantType, String genus, 
			String species) {
		
		this.quantType = quantType;
		Organism organism = new Organism(genus, species);
		this.genomeAssembly = new GenomeAssembly(genomeAssemblyName, organism);
	}
	
	/**
	 * Generates a new ArrayDatum object from the given parameters
	 * @param reporterName Reporter name
	 * @param chromNum Choromsome number
	 * @param location Location on chromosome
	 * @param quantValue Quantitation number
	 * @return A new array datum
	 */
	public ArrayDatum newArrayDatum(String reporterName, short chromNum, 
			long location, float quantValue)  {
		
		// get Chromosome from cache if it exists; else, instantiate new one	
		Chromosome chromosome = null;
		if (chromosomeCache.containsKey(chromNum))
			chromosome = this.chromosomeCache.get(chromNum);
		else {
			chromosome = new Chromosome(this.genomeAssembly, chromNum);
			this.chromosomeCache.put(chromNum, chromosome);
		}
		
		GenomeLocation genomeLocation = new GenomeLocation(chromosome, location);
		genomeLocation.setChromosome(chromosome);
		
		// get Reporter from cache if it exists; else, instantiate new one
		Reporter reporter = null;
		if (this.reporterCache.containsKey(reporterName))
			reporter = this.reporterCache.get(reporterName);
		else {
			reporter = new Reporter(reporterName);
			ReporterMapping reporterMapping = new ReporterMapping(reporter, genomeLocation);
			reporter.setReporterMapping(reporterMapping);
			this.reporterCache.put(reporterName, reporter);
		}
		
		Quantitation quantitation = new Quantitation(quantValue, quantType);
		ArrayDatum arrayDatum = new ArrayDatum(reporter, quantitation);
		
		return arrayDatum;
		
	}
	
	
	/**
	 * Generates a new ArrayDatum object from the given parameters
	 * @param reporterName Reporter name
	 * @param chromNum Choromsome number
	 * @param location Location on chromosome
	 * @param quantValue Quantitation number
	 * @param error Error value
	 * @return A new array datum
	 */
	public ArrayDatum newArrayDatum(String reporterName, short chromNum, 
			long location, float quantValue, float error)  {
		ArrayDatum arrayDatum = this.newArrayDatum(reporterName, chromNum, location, quantValue);
		arrayDatum.getQuantitation().setError(error);
		return arrayDatum;
		
	}

}
