/*
$Revision: 1.7 $
$Date: 2007-10-10 17:47:01 $

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

package org.rti.webgenome.service.job;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.graphics.InterpolationType;
import org.rti.webgenome.service.dao.hibernate.HibernateExperimentDao;
import org.rti.webgenome.service.dao.hibernate.HibernateOrganismDao;
import org.rti.webgenome.service.plot.GenomeSnapshopPlotParameters;
import org.rti.webgenome.units.BpUnits;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for {@link HibernateJobDao}.
 * @author dhall
 *
 */
public final class HibernateJobDaoTester extends TestCase {

	
	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/job/beans.xml");
		HibernateJobDao dao = (HibernateJobDao) ctx.getBean("jobDao");
		HibernateOrganismDao oDao = (HibernateOrganismDao)
			ctx.getBean("organismDao");
		HibernateExperimentDao expDao = (HibernateExperimentDao)
			ctx.getBean("experimentDao");
		
		// Instantiate test objects
		Organism org = oDao.loadDefault();
		Experiment exp1 = new Experiment("exp1");
		Experiment exp2 = new Experiment("exp2");
		BioAssay ba1 = new DataSerializedBioAssay("ba1", org);
		BioAssay ba2 = new DataSerializedBioAssay("ba2", org);
		ba1.setColor(Color.BLACK);
		ba2.setColor(Color.BLUE);
		exp1.add(ba1);
		exp2.add(ba2);
		exp1.setId(new Long(1));
		exp2.setId(new Long(2));
		ba1.setId(new Long(1));
		ba2.setId(new Long(2));
		expDao.save(exp1);
		expDao.save(exp2);
		Set<Experiment> experiments = new HashSet<Experiment>();
		experiments.add(exp1);
		experiments.add(exp2);
		AnalyticOperation op = new SlidingWindowSmoother();
		Map<Long, String> outputBioAssayNames = new HashMap<Long, String>();
		outputBioAssayNames.put(new Long(1), "ba1-smoothed");
		outputBioAssayNames.put(new Long(2), "ba2-smoothed");
		Map<Long, String> outputExperimentNames =
			new HashMap<Long, String>();
		outputExperimentNames.put(new Long(1), "exp1-smoothed");
		outputExperimentNames.put(new Long(2), "exp2-smoothed");
//		ScatterPlotParameters params = new ScatterPlotParameters();
		AnalysisJob job1 = new AnalysisJob(experiments, op,
				outputBioAssayNames, outputExperimentNames, "user", "domain");
		ReRunAnalysisJob job2 = new ReRunAnalysisJob(exp1, op,
				"user", "domain");
//		ReRunAnalysisOnPlotExperimentsJob job3 =
//			new ReRunAnalysisOnPlotExperimentsJob(experiments,
//					(long) 1, "user");
		GenomeSnapshopPlotParameters params =
			new GenomeSnapshopPlotParameters();
		params.setInterpolationType(InterpolationType.SPLINE);
		params.setMaxY((float) 0.5);
		PlotJob job4 = new PlotJob(null, experiments, params, "user", "domain");
		UploadDataSourceProperties uProps =
			new UploadDataSourceProperties();
		uProps.setChromosomeColumnName("chrom");
		uProps.setExperimentName("exp");
		uProps.setOrganism(org);
		uProps.setPositionColumnName("pos");
		uProps.setPositionUnits(BpUnits.BP);
		uProps.setReporterFile(RectangularTextFileFormat.CSV, "localfile",
				"remotefile", "reportername");
		DataFileMetaData meta = new DataFileMetaData();
		uProps.add(meta);
		meta.setFormat(RectangularTextFileFormat.CSV);
		meta.setLocalFileName("localfile");
		meta.setRemoteFileName("remotefile");
		meta.add(new DataColumnMetaData("col1", "ba1"));
		meta.add(new DataColumnMetaData("col2", "ba2"));
		DataImportJob job5 = new DataImportJob(uProps, "user", "domain");
		
		// Perform tests
		dao.saveOrUpdate(job1);
		dao.saveOrUpdate(job2);
//		dao.saveOrUpdate(job3);
		dao.saveOrUpdate(job4);
		dao.saveOrUpdate(job5);
		dao.delete(job1);
		dao.delete(job2);
//		dao.delete(job3);
		dao.delete(job4);
		dao.delete(job5);
		
		// Clean up
		expDao.delete(exp1);
		expDao.delete(exp2);
	}
}
