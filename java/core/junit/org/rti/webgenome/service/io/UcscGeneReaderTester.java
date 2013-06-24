/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:41 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.SortedSet;

import junit.framework.TestCase;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.util.FileUtils;

/**
 * Tester for <code>UcscGeneReader</code>.
 * @author dhall
 *
 */
public final class UcscGeneReaderTester extends TestCase {
	
	/**
	 * Classpath-relative path to directory containing
	 * test files.
	 */
	private static final String TEST_DIR_PATH =
		"org/rti/webgenome/service/io/"
		+ "ucsc_gene_reader_test_files";

	
	/**
	 * Test all methods.
	 * @throws Exception if something bad happens
	 */
	public void testAllMethods() throws Exception {
		
		// Setup
		File file = FileUtils.getFile(TEST_DIR_PATH, "small.txt");
		Reader reader = new FileReader(file);
		Organism org = new Organism("Homo", "sapiens");
		UcscGeneReader geneReader = new UcscGeneReader(reader, org);
		
		// Test first record
		assertTrue(geneReader.hasNext());
		AnnotatedGenomeFeature feat = geneReader.next();
		assertEquals("BC073913", feat.getName());
		assertEquals((short) 1, feat.getChromosome());
		assertEquals(4268, feat.getStartLocation());
		assertEquals(7438, feat.getEndLocation());
		SortedSet<AnnotatedGenomeFeature> childFeatures =
			feat.getChildFeatures();
		assertNotNull(childFeatures);
		assertEquals(6, childFeatures.size());
		AnnotatedGenomeFeature exon = childFeatures.first();
		assertEquals(4268, exon.getStartLocation());
		assertEquals(4692, exon.getEndLocation());
		
		// Test second record
		assertTrue(geneReader.hasNext());
		feat = geneReader.next();
		assertEquals("NM_001018113", feat.getName());
		assertEquals((short) 23, feat.getChromosome());
		assertEquals(14771449, feat.getStartLocation());
		assertEquals(14801105, feat.getEndLocation());
		childFeatures = feat.getChildFeatures();
		assertNotNull(childFeatures);
		assertEquals(10, childFeatures.size());
		exon = childFeatures.first();
		assertEquals(14771449, exon.getStartLocation());
		assertEquals(14772024, exon.getEndLocation());
		
		// Make sure there are no more records
		assertFalse(geneReader.hasNext());
		assertNull(geneReader.next());
		
		// Clean up
		reader.close();
	}
}
