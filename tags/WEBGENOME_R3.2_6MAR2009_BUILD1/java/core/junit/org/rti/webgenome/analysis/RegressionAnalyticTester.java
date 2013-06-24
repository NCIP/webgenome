/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/analysis/RegressionAnalyticTester.java,v $
$Revision: 1.3 $
$Date: 2007-04-13 02:52:13 $



*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.List;

//import org.rti.webcgh.analytic.AcghData;
//import org.rti.webcgh.analysis.AcghAnalyticTransformer;
//import org.rti.webcgh.analysis.AcghAnalyticOperation;
import org.rti.webgenome.analysis.LinearRegressionAnalyticOperation;
import org.rti.webgenome.analysis.LoessRegressionAnalyticOperation;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.service.analysis.RegressionService;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>AcghAnalyticTransformer, AcghAnalyticOperation</code>.
 * @author kungyen
 *
 */
public final class RegressionAnalyticTester extends TestCase {
	
	/**
	 * Pause duration in milliseconds of test thread
	 * after RServe started to
	 * give it time to initialize.
	 */
	private static final long PAUSE = 10000;
	
	/** RServe process required to run analytic operation. */
	private Process rServeProcess = null;
	
	/**
	 *  {@inheritDoc}
	 */
	public void setUp() throws Exception {
		String rServePath = UnitTestUtils.getUnitTestProperty("rserve.path");
		this.rServeProcess = Runtime.getRuntime().exec(rServePath);
		Thread.sleep(PAUSE);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void tearDown() {
		this.rServeProcess.destroy();
	}
    

    /**
     * Test perform() method.
     * @throws Exception if an error occurs
     */
    public void testPerform() throws Exception {
        
        // Setup input data set
        ChromosomeArrayData in = new ChromosomeArrayData((short) 21);
        Reporter r = new Reporter("RP11-82D16", (short) 21, (long) 2009);
        in.add(new ArrayDatum((float) 0.202059711 , r));
        in.add(new ArrayDatum((float) 0.173037143,
                new Reporter("RP11-62M23", (short) 21, (long) 3368)));
        in.add(new ArrayDatum((float) 0.121655619,
                new Reporter("RP11-111O5", (short) 21, (long) 4262)));
        in.add(new ArrayDatum((float) 0.090844669,
                new Reporter("RP11-51B4" , (short) 21, (long) 6069)));
        in.add(new ArrayDatum((float) 0.170214084,
                new Reporter("RP11-60J11", (short) 21, (long) 6817)));
        in.add(new ArrayDatum((float) -0.025518441,
                new Reporter("RP11-813J5", (short) 21, (long) 9498)));
        in.add(new ArrayDatum((float) 0.046834206,
                new Reporter("RP11-199O1", (short) 21, (long) 10284)));
        
        // Perform operation
        /*
        AcghAnalyticTransformer transformer = new AcghAnalyticTransformer();
        AcghData acghData = transformer.transform(in);
        double[] smoothedRatios = 
                {100.1, 200.1, 300.1, 400.1, 500.1, 600.1, 700.1};
        acghData.setSmoothedRatios(smoothedRatios);
        ChromosomeArrayData out = transformer.transform(acghData, in);
        */
        LinearRegressionAnalyticOperation operation = new LinearRegressionAnalyticOperation();
        //LoessRegressionAnalyticOperation operation = new LoessRegressionAnalyticOperation();
        ChromosomeArrayData out = operation.perform(in);
        
        // Check output
        List<ArrayDatum> data = new ArrayList<ArrayDatum>(out.getArrayData());
        assertEquals(in.getArrayData().size(), data.size());
        assertEquals(r, data.get(0).getReporter());
        //assertEquals((float) 100.1, data.get(0).getValue());
        //assertEquals((float) 0.0, data.get(0).getError());
        //assertEquals((float) 400.1, data.get(3).getValue());
        //assertEquals((float) 22.0 / (float) 4.0, data.get(5).getValue());
        List<Reporter> inreporters = new ArrayList<Reporter>(in.getReporters());
        List<Reporter> outreporters = new ArrayList<Reporter>(out.getReporters());
		for (int i = 0; i < data.size(); i++) {
			System.out.print(String.valueOf(inreporters.get(i).getChromosome()) + ", ");
			System.out.print(String.valueOf(inreporters.get(i).getName()) + ", ");
			System.out.print(String.valueOf(data.get(i).getValue()) + ", ");
			System.out.print(String.valueOf(outreporters.get(i).getChromosome()) + ", ");
			System.out.println(String.valueOf(inreporters.get(i).getName()) + ".");
		}
    }

}
