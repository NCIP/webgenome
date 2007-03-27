/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:11 $

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

package org.rti.webcgh.service.dao;

import java.util.List;

import org.rti.webcgh.domain.CytologicalMap;
import org.rti.webcgh.domain.Organism;

/**
 * Data access class for <code>CytologicalMap</code>.
 * @author dhall
 *
 */
public interface CytologicalMapDao {
	
	/**
	 * Save given map to persistent storage.
	 * @param cytologicalMap Cytological map.
	 */
	void save(CytologicalMap cytologicalMap);
	
	/**
	 * Load cytological map from persistent storage.
	 * @param id Primary key identifier of
	 * cytological map.
	 * @return Cytological map corresponding to
	 * given id.  Throws an exception if a cytological
	 * map is not available with given ID.
	 */
	CytologicalMap load(Long id);
	
	/**
	 * Load cytological map from persistent storage.
	 * @param organism An organism
	 * @param chromosome Chromosome number
	 * @return A cytological map or null if there
	 * is no map from given organism and chromosome.
	 */
	CytologicalMap load(Organism organism, short chromosome);

	/**
	 * Delete given cytological map from persistent storage.
	 * @param cytologicalMap Cytological map to delete.
	 */
	void delete(CytologicalMap cytologicalMap);
	
	/**
	 * Load all cytological maps.
	 * @return All cytological maps.
	 */
	List<CytologicalMap> loadAll();
	
	/**
	 * Delete all cytological maps from given organism.
	 * @param organism Organism.
	 */
	void deleteAll(Organism organism);
}
