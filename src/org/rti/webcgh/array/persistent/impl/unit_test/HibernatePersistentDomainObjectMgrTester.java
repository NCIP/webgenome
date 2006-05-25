/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/unit_test/HibernatePersistentDomainObjectMgrTester.java,v $
$Revision: 1.2 $
$Date: 2006-05-25 19:41:30 $

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
package org.rti.webcgh.array.persistent.impl.unit_test;

import java.util.Date;

import junit.framework.TestCase;

import org.rti.webcgh.array.persistent.PersistentArray;
import org.rti.webcgh.array.persistent.PersistentArrayDatum;
import org.rti.webcgh.array.persistent.PersistentArrayMapping;
import org.rti.webcgh.array.persistent.PersistentBinnedData;
import org.rti.webcgh.array.persistent.PersistentBioAssay;
import org.rti.webcgh.array.persistent.PersistentBioAssayData;
import org.rti.webcgh.array.persistent.PersistentChromosome;
import org.rti.webcgh.array.persistent.PersistentChromosomeBin;
import org.rti.webcgh.array.persistent.PersistentCytoband;
import org.rti.webcgh.array.persistent.PersistentCytologicalMap;
import org.rti.webcgh.array.persistent.PersistentCytologicalMapSet;
import org.rti.webcgh.array.persistent.PersistentExon;
import org.rti.webcgh.array.persistent.PersistentExperiment;
import org.rti.webcgh.array.persistent.PersistentGenomeAssembly;
import org.rti.webcgh.array.persistent.PersistentGenomeFeature;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureDataSet;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureType;
import org.rti.webcgh.array.persistent.PersistentGenomeLocation;
import org.rti.webcgh.array.persistent.PersistentOrganism;
import org.rti.webcgh.array.persistent.PersistentQuantitation;
import org.rti.webcgh.array.persistent.PersistentQuantitationType;
import org.rti.webcgh.array.persistent.PersistentReporter;
import org.rti.webcgh.array.persistent.PersistentReporterMapping;
import org.rti.webcgh.array.persistent.impl.HibernatePersistentDomainObjectMgr;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tester for HibernatePersistentDomainObjectMgr
 */
public class HibernatePersistentDomainObjectMgrTester extends TestCase {
    
    static HibernatePersistentDomainObjectMgr OBJ_MGR = null;
    
    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-beans.xml");
        OBJ_MGR = (HibernatePersistentDomainObjectMgr)ctx.getBean("hibernatePersistentDomainObjectMgr");
    }
    
    
    /**
     * 
     *
     */
    public void testOrganism() {
        String genus = "Homo";
        String species = "sapiens";
        PersistentOrganism org1 = OBJ_MGR.getPersistentOrganism(genus, species, true);
        assertNotNull(org1);
        PersistentOrganism org2 = OBJ_MGR.getPersistentOrganism(org1.getId());
        assertNotNull(org2);
        org1.delete();
        PersistentOrganism org3 = OBJ_MGR.getPersistentOrganism(genus, species, false);
        assertNull(org3);
    }
    
    
    /**
     * 
     *
     */
    public void testGenomeAssembly() {
        String name = "hg17";
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly a1 = OBJ_MGR.getPersistentGenomeAssembly(name, org, true);
        assertNotNull(a1);
        PersistentGenomeAssembly a2 = OBJ_MGR.getPersistentGenomeAssembly(a1.getId());
        assertNotNull(a2);
        a1.delete();
        PersistentGenomeAssembly a3 = OBJ_MGR.getPersistentGenomeAssembly(name, org, false);
        assertNull(a3);
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testChromosome() {
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentChromosome c1 = OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
        assertNotNull(c1);
        c1.delete();
        PersistentChromosome c2 = OBJ_MGR.getPersistentChromosome(asm, (short)1, false);
        assertNull(c2);
        asm.delete();
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testQuantitationType() {
        String name = "ratio";
        PersistentQuantitationType t1 = OBJ_MGR.getPersistentQuantitationType(name, true);
        assertNotNull(t1);
        t1.delete();
        PersistentQuantitationType t2 = OBJ_MGR.getPersistentQuantitationType(name, false);
        assertNull(t2);
    }
    
    
    /**
     * 
     *
     */
    public void testReporter() {
        String name = "rep1";
        PersistentReporter r1 = OBJ_MGR.getPersistentReporter(name, true);
        assertNotNull(r1);
        r1.delete();
        PersistentReporter r2 = OBJ_MGR.getPersistentReporter(name, false);
        assertNull(r2);
    }
    
    
    /**
     * 
     *
     */
    public void testQuantitation() {
       PersistentQuantitationType type = OBJ_MGR.getPersistentQuantitationType("ratio", true);
       PersistentQuantitation q1 = OBJ_MGR.newPersistentQuantitation((float)1.0, type);
       assertNotNull(q1);
       q1.delete();
       type.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testGenomeLocation() {
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentChromosome c1 = OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
        PersistentGenomeLocation l1 = OBJ_MGR.newPersistentGenomeLocation(c1, 10);
        assertNotNull(l1);
        l1.delete();
        c1.delete();
        asm.delete();
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testReporterMapping() {
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentChromosome c1 = OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
        PersistentGenomeLocation l1 = OBJ_MGR.newPersistentGenomeLocation(c1, 10);
        PersistentReporter r1 = OBJ_MGR.getPersistentReporter("r1", true);
        PersistentReporterMapping m1 = OBJ_MGR.newPersistentReporterMapping(r1, l1);
        assertNotNull(m1);
        m1.delete();
        r1.delete();
        c1.delete();
        asm.delete();
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testGenomeFeatureType() {
    	String name = "gene";
    	boolean representsGene = true;
    	PersistentGenomeFeatureType t1 = OBJ_MGR.getPersistentGenomeFeatureType(name, representsGene, true);
    	assertNotNull(t1);
    	t1.delete();
    	PersistentGenomeFeatureType t2 = OBJ_MGR.getPersistentGenomeFeatureType(name, representsGene, false);
    	assertNull(t2);
    }
    
    
    /**
     * 
     */
    public void testGenomeFeatureDataSet() {
        PersistentOrganism org = 
        	OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = 
        	OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
    	PersistentGenomeFeatureType t1 = 
    		OBJ_MGR.getPersistentGenomeFeatureType("gene", true, true);
    	PersistentGenomeFeatureDataSet d1 =
    		OBJ_MGR.newPersistentGenomeFeatureDataSet(asm, new Date(), t1);
    	assertNotNull(d1);
    	PersistentGenomeFeatureDataSet d2 =
    		OBJ_MGR.getPersistentGenomeFeatureDataSet(asm, t1);
    	assertNotNull(d2);
    	d1.delete();
    	PersistentGenomeFeatureDataSet d3 =
    		OBJ_MGR.getPersistentGenomeFeatureDataSet(asm, t1);
    	assertNull(d3);
    	t1.delete();
    	asm.delete();
    	org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testGenomeFeature() {
        PersistentOrganism org = 
        	OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = 
        	OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
    	PersistentGenomeFeatureType t1 = 
    		OBJ_MGR.getPersistentGenomeFeatureType("gene", true, true);
    	PersistentGenomeFeatureDataSet d1 =
    		OBJ_MGR.newPersistentGenomeFeatureDataSet(asm, new Date(), t1);
    	PersistentChromosome c1 = OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
    	PersistentGenomeFeature f1 = OBJ_MGR.newPersistentGenomeFeature("name", 
    			(long)1, (long)10, c1, d1);
    	PersistentExon e1 = OBJ_MGR.newPersistentExon((long)2, (long)5);
    	PersistentExon e2 = OBJ_MGR.newPersistentExon((long)6, (long)9);
    	f1.addExon(e1);
    	f1.addExon(e2);
    	f1.update();
    	f1.delete();
    	c1.delete();
    	d1.delete();
    	t1.delete();
    	asm.delete();
    	org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testCytoband() {
    	PersistentCytoband c1 = 
    		OBJ_MGR.newPersistentCytoband("c1", (long)1, (long)10, "green");
    	assertNotNull(c1);
    	assertNotNull(c1.getId());
    	c1.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testCytologicalMap() {
        PersistentOrganism org = 
        	OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = 
        	OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentChromosome chrom = 
        	OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
    	PersistentCytoband c1 = 
    		OBJ_MGR.newPersistentCytoband("c1", (long)1, (long)10, "green");
    	PersistentCytologicalMap m1 =
    		OBJ_MGR.newPersistentCytologicalMap(chrom);
    	m1.addCytoband(c1);
    	m1.update();
    	assertNotNull(m1);
    	m1.delete();
        chrom.delete();
        asm.delete();
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testCytologicalMapSet() {
        PersistentOrganism org = 
        	OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        PersistentGenomeAssembly asm = 
        	OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentChromosome chrom = 
        	OBJ_MGR.getPersistentChromosome(asm, (short)1, true);
    	PersistentCytoband c1 = 
    		OBJ_MGR.newPersistentCytoband("c1", (long)1, (long)10, "green");
    	PersistentCytologicalMap m1 =
    		OBJ_MGR.newPersistentCytologicalMap(chrom);
    	m1.addCytoband(c1);
    	m1.update();
    	PersistentCytologicalMapSet s1 =
    		OBJ_MGR.newPersistentCytologicalMapSet(asm, new Date());
    	s1.add(m1);
    	s1.update();
    	PersistentCytologicalMapSet s2 =
    		OBJ_MGR.getPersistentCytologicalMapSet(asm);
    	assertNotNull(s2);
    	assertEquals(s2.numCytologicalMaps(), 1);
    	s1.delete();
    	PersistentCytologicalMapSet s3 =
    		OBJ_MGR.getPersistentCytologicalMapSet(asm);
    	assertNull(s3);
        chrom.delete();
        asm.delete();
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testChromosomeBin() {
    	PersistentChromosomeBin cBin = 
    		OBJ_MGR.newPersistentChromosomeBin(1, (float)1.0, 1);
    	cBin.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testBinnedData() {
        PersistentQuantitationType type = 
        	OBJ_MGR.getPersistentQuantitationType("ratio", true);
    	PersistentChromosomeBin b1 = 
    		OBJ_MGR.newPersistentChromosomeBin(1, (float)1.0, 1);
    	PersistentChromosomeBin b2 = 
    		OBJ_MGR.newPersistentChromosomeBin(2, (float)1.0, 1);
    	PersistentBinnedData d1 =
    		OBJ_MGR.newPersistentBinnedData(type);
    	d1.add(b1);
    	d1.add(b2);
    	d1.update();
    	PersistentBinnedData d2 =
    		OBJ_MGR.getPersistentBinnedData(d1.getId());
    	assertNotNull(d2);
    	assertEquals(d2.numBins(), 2);
    	d1.delete();
    	type.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testArray() {
        PersistentReporter r1 = OBJ_MGR.getPersistentReporter("r1", true);
        PersistentReporter r2 = OBJ_MGR.getPersistentReporter("r2", true);
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        String vendor = "Affymetrix";
        String name = "HG100";
        PersistentArray a1 = OBJ_MGR.getPersistentArray(vendor, name, org, true);
        a1.add(r1);
        a1.add(r2);
        a1.update();
        PersistentArray a2 = OBJ_MGR.getPersistentArray(a1.getId());
        assertNotNull(a2);
        assertEquals(a2.numReporters(), 2);
        a1.delete();
        PersistentArray a3 = OBJ_MGR.getPersistentArray(vendor, name, org, false);
        assertNull(a3);
        org.delete();
    }
    
    
    /**
     * 
     *
     */
    public void testArrayMapping() {
        PersistentReporter r1 = OBJ_MGR.getPersistentReporter("r1", true);
        PersistentReporter r2 = OBJ_MGR.getPersistentReporter("r2", true);
        PersistentOrganism org = OBJ_MGR.getPersistentOrganism("Homo", "sapiens", true);
        String vendor = "Affymetrix";
        String name = "HG100";
        PersistentArray a1 = OBJ_MGR.getPersistentArray(vendor, name, org, true);
        a1.add(r1);
        a1.add(r2);
        a1.update();
        PersistentGenomeAssembly asm = OBJ_MGR.getPersistentGenomeAssembly("hg17", org, true);
        PersistentArrayMapping m1 =
            OBJ_MGR.getPersistentArrayMapping(a1, asm, true);
        PersistentArrayMapping m2 =
            OBJ_MGR.getPersistentArrayMapping(a1, asm, false);
        assertNotNull(m2);
        m1.delete();
        PersistentArrayMapping m3 =
            OBJ_MGR.getPersistentArrayMapping(a1, asm, false);
        assertNull(m3);
        asm.delete();
        a1.delete();
        org.delete();
    }
    
    
    /**
     * 
     */
    public void testArrayDatum() {
        PersistentQuantitationType type = OBJ_MGR.getPersistentQuantitationType("ratio", true);
        PersistentQuantitation q1 = OBJ_MGR.newPersistentQuantitation((float)1.0, type);
        PersistentReporter r1 = OBJ_MGR.getPersistentReporter("r1", true);
        PersistentArrayDatum d1 = OBJ_MGR.newPersistentArrayDatum(r1, q1);
        d1.delete();
        r1.delete();
        type.delete();
    }
    
    
    /**
     * 
     */
    public void testBioAssayData() throws Exception {
    	PersistentQuantitationType type = OBJ_MGR.getPersistentQuantitationType("ratio", true);
	    PersistentQuantitation q1 = OBJ_MGR.newPersistentQuantitation((float)1.0, type);
	    PersistentQuantitation q2 = OBJ_MGR.newPersistentQuantitation((float)2.0, type);
	    PersistentReporter r1 = OBJ_MGR.getPersistentReporter("r1", true);
	    PersistentReporter r2 = OBJ_MGR.getPersistentReporter("r2", true);
	    PersistentArrayDatum d1 = OBJ_MGR.newPersistentArrayDatum(r1, q1);
	    PersistentArrayDatum d2 = OBJ_MGR.newPersistentArrayDatum(r2, q2);
	    PersistentBioAssayData bad1 = OBJ_MGR.newPersistentBioAssayData();
	    bad1.add(d1);
	    bad1.add(d2);
	    bad1.saveOrUpdate();
	    PersistentBioAssayData bad2 = OBJ_MGR.getPersistentBioAssayData(bad1.getId());
	    assertNotNull(bad2);
	    assertEquals(bad2.numArrayDatum(), 2);
	    bad1.delete();
	    r2.delete();
	    r1.delete();
	    type.delete();
    }
    
    
    /**
     * 
     */
    public void testBioAssay() {
        PersistentBioAssay b1 = OBJ_MGR.newPersistentBioAssay("b1", "description", "dbName");
        PersistentBioAssay b2 = OBJ_MGR.getPersistentBioAssay(b1.getId());
        assertNotNull(b2);
        b1.delete();
    }
    
    
    /**
     * 
     */
    public void testExperiment() {
        PersistentBioAssay b1 = OBJ_MGR.newPersistentBioAssay("b1", "description", "dbName");
        PersistentBioAssay b2 = OBJ_MGR.newPersistentBioAssay("b2", "description", "dbName");
        PersistentBioAssay b3 = OBJ_MGR.newPersistentBioAssay("b1", "description", "dbName");
        PersistentBioAssay b4 = OBJ_MGR.newPersistentBioAssay("b2", "description", "dbName");
        PersistentBioAssay b5 = OBJ_MGR.newPersistentBioAssay("b1", "description", "dbName");
        PersistentBioAssay b6 = OBJ_MGR.newPersistentBioAssay("b2", "description", "dbName");
        PersistentExperiment e1 = OBJ_MGR.newPersistentExperiment("exp", "description", "dbName");
        PersistentExperiment e2 = OBJ_MGR.newPersistentExperiment("exp", "description", "dbName");
        PersistentExperiment e3 = OBJ_MGR.newPersistentExperiment("exp", "description", "dbName");
        e1.add(b1);
        e1.add(b2);
        e1.add(b3);
        e2.add(b4);
        e2.add(b5);
        e2.add(b6);
        e1.update();
        e2.update();
        e3.update();
        PersistentExperiment e4 = OBJ_MGR.getPersistentExperiment(e1.getId());
        assertNotNull(e4);
        assertEquals(e4.numBioAssays(), 3);
        PersistentExperiment[] exps1 = OBJ_MGR.getAllPersistentPublicExperiments();
        assertNotNull(exps1);
        assertEquals(exps1.length, 3);
        e1.delete();
        PersistentExperiment[] exps2 = OBJ_MGR.getAllPersistentPublicExperiments();
        assertNotNull(exps2);
        assertEquals(exps2.length, 2);
        OBJ_MGR.deleteAllPersistentPublicExperiments();
        PersistentExperiment[] exps3 = OBJ_MGR.getAllPersistentPublicExperiments();
        assertNotNull(exps3);
        assertEquals(exps3.length, 0);
    }
}
