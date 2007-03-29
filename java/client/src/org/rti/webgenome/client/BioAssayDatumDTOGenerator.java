package org.rti.webgenome.client;


import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.core.WebcghSystemException;

/**
 * This class generates <code>BioAssayDatumDTO</code>
 * objects for testing.
 * @author dhall
 *
 */
public class BioAssayDatumDTOGenerator {
	
	/** Probability a reporter will have annotation. */
	private static final double PROBABILITY_ANNOTATION = 0.5;
	
	/** Probability a reporter will have an associated gene. */
	private static final float PROBABILITY_GENE = (float) 0.3;
	
	/**
	 * Probability a reporter marks an start point of an
	 * altered genome region.
	 */
	private static final float PROBABILITY_ALTERATION_STARTPOINT =
		(float) 0.01;
	
	/**
	 * Probability a reporter marks an end point of an
	 * altered genome region.
	 */
	private static final float PROBABILITY_ALTERATION_ENDPOINT =
		(float) 0.05;
	
	/** Value generators keyed on quantitation type. */
	private static final Map<String, ValueGenerator> GENERATORS =
		new HashMap<String, ValueGenerator>();
	
	static {
		GENERATORS.put(QuantitationTypes.COPY_NUMBER,
				new ValueGenerator((float) 20.0, (float) 30.0,
						(float) 0.0, (float) 10.0,
						PROBABILITY_ALTERATION_STARTPOINT,
						PROBABILITY_ALTERATION_ENDPOINT));
		GENERATORS.put(QuantitationTypes.COPY_NUMBER_LOG2_RATION,
				new ValueGenerator((float) 2.0, (float) 3.0,
						(float) 0.0, (float) 0.6,
						PROBABILITY_ALTERATION_STARTPOINT,
						PROBABILITY_ALTERATION_ENDPOINT));
		GENERATORS.put(QuantitationTypes.FOLD_CHANGE,
				new ValueGenerator((float) 20.0, (float) 30.0,
						(float) 0.0, (float) 10.0,
						PROBABILITY_ALTERATION_STARTPOINT,
						PROBABILITY_ALTERATION_ENDPOINT));
		GENERATORS.put(QuantitationTypes.FOLD_CHANGE_LOG2_RATIO,
				new ValueGenerator((float) 2.0, (float) 3.0,
						(float) 0.0, (float) 0.6,
						PROBABILITY_ALTERATION_STARTPOINT,
						PROBABILITY_ALTERATION_ENDPOINT));
		GENERATORS.put(QuantitationTypes.LOH,
				new ValueGenerator((float) 0.8, (float) 1.0,
						(float) 0.0, (float) 0.3,
						PROBABILITY_ALTERATION_STARTPOINT,
						PROBABILITY_ALTERATION_ENDPOINT));
	}

	/** Gap in base pairs between generated reporters. */
	private final long gap;
	
	/** Probability that a data point is selected. */
	private float probabilitySelected = (float) 0.1;
	
	/**
	 * Constructor.
	 * @param gap Gap between generated reporter in
	 * base pairs.
	 */
	public BioAssayDatumDTOGenerator(final long gap) {
		this.gap = gap;
	}
	
	/**
	 * Generate new bioassay datum data transfer objects.
	 * @param constraints Constraints
	 * @return Bioassay datum data transfer objects
	 */
	public final BioAssayDatumDTO[] newBioAssayDatumDTOs(
			final BioAssayDataConstraints[] constraints) {
		int totalNum = 0;
		for (int i = 0; i < constraints.length; i++) {
			totalNum += (int) ((constraints[i].getEndPosition()
				- constraints[i].getStartPosition()) / this.gap);
		}
		BioAssayDatumDTO[] dtos = new BioAssayDatumDTO[totalNum];
		int p = 0;
		for (int i = 0; i < constraints.length; i++) {
			BioAssayDataConstraints constraint = constraints[i];
			int num = (int) ((constraints[i].getEndPosition()
					- constraints[i].getStartPosition()) / this.gap);
			ValueGenerator gen =
				GENERATORS.get(constraint.getQuantitationType());
			if (gen == null) {
				throw new WebcghSystemException("Unknown quantitation type: '"
						+ constraint.getQuantitationType() + "'");
			}
			for (int j = 0; j < num && p < totalNum; j++) {
				long pos = (long) j * this.gap + constraint.getStartPosition();
				boolean selected = Math.random() < this.probabilitySelected;
				DefReporterDTOImpl r = new DefReporterDTOImpl(
						String.valueOf(j), constraint.getChromosome(), pos,
						selected);
				if (Math.random() < PROBABILITY_ANNOTATION) {
					r.addAnnotation("An annotation");
				}
				if (Math.random() < PROBABILITY_GENE) {
					r.addAssociatedGene("Gene A");
				}
				double value = gen.nextValue();
				BioAssayDatumDTO dto = new DefBioAssayDatumDTOImpl(value,
						constraint.getQuantitationType(), r);
				dtos[p++] = dto;
			}
		}
		return dtos;
	}
	
	
	/**
	 * Generates values.
	 * @author dhall
	 *
	 */
	static class ValueGenerator {
		
		/** Minimim value in an altered region. */
		private final float minAltValue;
		
		/** Maximum value in an altered region. */
		private final float maxAltValue;
		
		/** Minimum value in a non-altered region. */
		private final float minBackgroundValue;
		
		/** Maximum value in a non-altered region. */
		private final float maxBackgroundValue;
		
		/** Is generator currently in an altered region? */
		private boolean inAlteration = false;
		
		/**
		 * Probability that a given values represents
		 * the start point of an altered region.
		 */
		private final float probAltStartPoint;
		
		/**
		 * Probability that a given value represents
		 * the end point of an altered region.
		 */
		private final float probAltEndPoint;
		
		/**
		 * Constructor.
		 * @param minAltValue Minimim value in an altered region
		 * @param maxAltValue Maximum value in an altered region
		 * @param minBackgroundValue Minimum value in a non-altered region
		 * @param maxBackgroundValue Maximum value in a non-altered region
		 * @param probAltStartPoint Probability that a given values represents
		 * the start point of an altered region
		 * @param probAltEndPoint Probability that a given values represents
		 * the end point of an altered region
		 */
		ValueGenerator(final float minAltValue, final float maxAltValue,
				final float minBackgroundValue,
				final float maxBackgroundValue,
				final float probAltStartPoint,
				final float probAltEndPoint) {
			this.minAltValue = minAltValue;
			this.maxAltValue = maxAltValue;
			this.minBackgroundValue = minBackgroundValue;
			this.maxBackgroundValue = maxBackgroundValue;
			this.probAltStartPoint = probAltStartPoint;
			this.probAltEndPoint = probAltEndPoint;
		}
		
		
		/**
		 * Generate next value.
		 * @return Value
		 */
		public float nextValue() {
			float value = Float.NaN;
			if (!this.inAlteration) {
				this.inAlteration = Math.random() < this.probAltStartPoint;
			}
			if (!this.inAlteration) {
				value = this.generateValue(this.minBackgroundValue,
						this.maxBackgroundValue);
			} else {
				value = this.generateValue(this.minAltValue,
						this.maxAltValue);
				this.inAlteration = Math.random()
					< (float) 1.0 - this.probAltEndPoint;
			}
			return value;
		}
		
		/**
		 * Generate a value between <code>min</code> and
		 * <code>max</code>.
		 * @param min Minimum
		 * @param max Maximum
		 * @return A value
		 */
		private float generateValue(final float min, final float max) {
			float range = max - min;
			return (float) Math.random() * range + min;
		}
	}
}
