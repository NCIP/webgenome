/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:42 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.analysis.MinimumCommonAlteredRegionOperation;
import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ArrayDatumGenerator;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.QuantitationType;

import junit.framework.TestCase;

/**
 * Tester for <code>MinimumCommonAlteredRegionOperation</code>.
 * @author dhall
 *
 */
public final class MinimumCommonAlteredRegionOperationTester
extends TestCase {

	
	/**
	 * Test perform() method.
	 * @throws Exception if something bad happens
	 */
	public void testPerform() throws Exception {
		
		// Create test data
		long[] locations = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
		float[][] values = {
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{1, 1, 1, 1, 0, 0, 0, 1, 1, 0},
				{0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
				{0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 1, 1, 0, 0, 0}};
		ArrayDatum[][] data = ArrayDatumGenerator.newArrayData(
				(short) 1, locations, values);
		List<ChromosomeArrayData> cads =
			new ArrayList<ChromosomeArrayData>();
		for (int i = 0; i < data.length; i++) {
			ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
			cads.add(cad);
			for (int j = 0; j < data[i].length; j++) {
				cad.add(data[i][j]);
			}
		}
		
		// Instantiate operation
		MinimumCommonAlteredRegionOperation op =
			new MinimumCommonAlteredRegionOperation();
		op.setProperty("interpolate", "NO");
		op.setProperty("threshold", "0.5");
		op.setProperty("minPercent", "50");
		op.setQuantitationType(QuantitationType.LOH);
		
		// Run test
		List<ChromosomeArrayData> output = op.perform(cads);
		assertNotNull(output);
		assertEquals(1, output.size());
		ChromosomeArrayData cad = output.get(0);
		List<AnnotatedGenomeFeature> alts =
			cad.getChromosomeAlterations();
		assertNotNull(alts);
		assertEquals(2, alts.size());
		AnnotatedGenomeFeature f = alts.get(0);
		assertEquals(20, f.getStartLocation());
		assertEquals(40, f.getEndLocation());
		assertTrue(AnnotationType.LOH_SEGMENT == f.getAnnotationType());
		assertEquals((float) 1.0, f.getQuantitation());
		f = alts.get(1);
		assertEquals(80, f.getStartLocation());
		assertEquals(90, f.getEndLocation());
		assertTrue(AnnotationType.LOH_SEGMENT == f.getAnnotationType());
		assertEquals((float) 1.0, f.getQuantitation());
	}
}
