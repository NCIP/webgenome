/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:41 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
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
		"org/rti/webgenome/service/io/"
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
