/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.List;
import org.apache.log4j.Logger;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;


/**
 * Performs the mapping between ChromosomeArrayData object and AcghData.
 * @author Kungyen
 */
public class AcghAnalyticTransformer {

	/** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(AcghAnalyticTransformer.class);


	/**
	 * Transforms data from ChromosomeArrayData object to AcghData object.
	 * @param chrData The data given as a ChromosomeArrayData object
	 * @return AcghData
	 */
    public AcghData transform(ChromosomeArrayData chrData) {
		List<ArrayDatum> expValuesByChr = chrData.getArrayData();
		int size = expValuesByChr.size();       //# of clones, e.g. # of rows
		double[] log2Ratios = new double[size]; //log2 ratios of copy # changes
		String[] clones = new String[size];     //clone name
		String[] targets = new String[size];    //unique ID, e.g. Well ID
		int[] chromosomes = new int[size];      //chromosome number
		int[] positions = new int[size];        //kb position on the chromosome

		for (int i = 0; i < expValuesByChr.size(); i++) {
			ArrayDatum arrayDatum = expValuesByChr.get(i);
			log2Ratios[i] = (double) arrayDatum.getValue();
			clones[i] = arrayDatum.getReporter().getName();
			targets[i] = arrayDatum.getReporter().getName();
			chromosomes[i] = 1; // set chr# = 1 to avoid problem in R
			//chromosomes[i] = (int) chrData.getChromosome();
			positions[i] = (int) arrayDatum.getReporter().getLocation();
		}

		AcghData acghData = new AcghData();
		acghData.setLog2Ratios(log2Ratios);
		acghData.setClones(clones);
		acghData.setTargets(targets);
		acghData.setChromosomes(chromosomes);
		acghData.setPositions(positions);
		acghData.setSize(size);

		return acghData;
	}


	/**
	 * Transforms AcghData object into ChromosomeArrayData object.
	 * @param acghData The AcghData object after R smoothing
	 * @param origChrData The data of the original ChromosomeArrayData object
	 * @return ChromosomeArrayData
	 */
	public ChromosomeArrayData transform(AcghData acghData, ChromosomeArrayData oriChrData) {
		ChromosomeArrayData newChrData =
			new ChromosomeArrayData(oriChrData.getChromosome());

		double[] smoothedRatios = acghData.getSmoothedRatios(); // smoothed log2 ratio
		List<ArrayDatum> expValuesByChr = oriChrData.getArrayData();
		for (int i = 0; i < expValuesByChr.size(); i++) {
			ArrayDatum arrayDatum = expValuesByChr.get(i);
			ArrayDatum newDatum = new ArrayDatum();
			newDatum.setReporter(arrayDatum.getReporter());
			newDatum.setValue((float) smoothedRatios[i]);
			newChrData.add(newDatum);
		}
		return newChrData;
	}


}
