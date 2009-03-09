/*
$Revision: 1.4 $
$Date: 2007-08-24 21:51:58 $

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

package org.rti.webgenome.service.dao.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.analysis.SimpleExperimentNormalizer;
import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.EjbDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.MultiAnalysisDataSourceProperties;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.SimulatedDataSourceProperties;
import org.rti.webgenome.domain.SingleAnalysisDataSourceProperties;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.units.BpUnits;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.
 * HibernateDataSourcePropertiesDao}
 * @author dhall
 *
 */
public class HibernateDataSourcePropertiesDaoTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get beans
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateDataSourcePropertiesDao dao =
			(HibernateDataSourcePropertiesDao)
			ctx.getBean("dataSourcePropertiesDao");
		HibernateExperimentDao expDao =
			(HibernateExperimentDao) ctx.getBean("experimentDao");
		HibernateOrganismDao orgDao =
			(HibernateOrganismDao) ctx.getBean("organismDao");
		
		// Instantiate test objects
		SimulatedDataSourceProperties p1 =
			new SimulatedDataSourceProperties();
		EjbDataSourceProperties p2 =
			new EjbDataSourceProperties("jndiName1",
					"jndiProvider1", "client2");
		UploadDataSourceProperties p3 =
			new UploadDataSourceProperties();
		p3.setChromosomeColumnName("chrom");
		p3.setExperimentName("exp");
		p3.setOrganism(orgDao.loadDefault());
		p3.setPositionColumnName("pos");
		p3.setPositionUnits(BpUnits.BP);
		p3.setReporterFile(RectangularTextFileFormat.CSV, "localfile",
				"remotefile", "reportername");
		DataFileMetaData meta = new DataFileMetaData();
		p3.add(meta);
		meta.setFormat(RectangularTextFileFormat.CSV);
		meta.setLocalFileName("localfile");
		meta.setRemoteFileName("remotefile");
		meta.add(new DataColumnMetaData("col1", "ba1"));
		meta.add(new DataColumnMetaData("col2", "ba2"));
		Experiment exp1 = new Experiment("exp1");
		Experiment exp2 = new Experiment("exp2");
		Experiment exp3 = new Experiment("exp3");
		exp1.setId(new Long(1));
		exp2.setId(new Long(2));
		exp3.setId(new Long(3));
		expDao.save(exp1);
		expDao.save(exp2);
		expDao.save(exp3);
		SingleAnalysisDataSourceProperties p4 =
			new SingleAnalysisDataSourceProperties(exp1,
					new SlidingWindowSmoother());
		Set<Experiment> experiments = new HashSet<Experiment>();
		experiments.add(exp2);
		experiments.add(exp3);
		MultiAnalysisDataSourceProperties p5 =
			new MultiAnalysisDataSourceProperties(experiments,
					new SimpleExperimentNormalizer());
		
		dao.save(p1);
		dao.save(p2);
		dao.save(p3);
		dao.save(p4);
		dao.save(p5);
		dao.delete(p1);
		dao.delete(p2);
		dao.delete(p3);
		dao.delete(p4);
		dao.delete(p5);
		expDao.delete(exp1);
		expDao.delete(exp2);
		expDao.delete(exp3);
	}
}
