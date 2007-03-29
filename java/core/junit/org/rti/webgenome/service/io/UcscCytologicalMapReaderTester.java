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

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.io.UcscCytologicalMapReader;
import org.rti.webgenome.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>UcscCytologicalMapReader</code>.
 * @author dhall
 *
 */
public final class UcscCytologicalMapReaderTester extends TestCase {
	
	/**
	 * Classpath-relative path to directory containing
	 * test files.
	 */
	private static final String TEST_DIR_PATH =
		"org/rti/webcgh/service/io/unit_test/"
		+ "ucsc_cytological_map_reader_test_files";

	/**
	 * Test read() method.
	 * @throws Exception if anything bad happens.
	 */
	public void testRead() throws Exception {
		File file = FileUtils.getFile(TEST_DIR_PATH, "small.txt");
		Reader in = new FileReader(file);
		UcscCytologicalMapReader r = new UcscCytologicalMapReader(
				Organism.UNKNOWN_ORGANISM);
		Collection<CytologicalMap> maps = r.read(in);
		assertNotNull(maps);
		assertEquals(2, maps.size());
		Iterator<CytologicalMap> it = maps.iterator();
		CytologicalMap map = it.next();
		assertEquals(23, map.getChromosome());
		assertEquals(56600000, map.getCentromereStart());
		assertEquals(65000000, map.getCentromereEnd());
		assertEquals(19, map.getCytobands().size());
		map = it.next();
		assertEquals(10, map.getChromosome());
	}
}
