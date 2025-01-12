package behaviorClassification;

import java.io.Serializable;

/**
 * A class to find the relevant features of a chunk of time series data
 * @author nathandunn
 *
 */
public abstract class FeatureExtractor implements Serializable {

	private static final long serialVersionUID = 1360310641613878068L;

	public abstract double[] extractFeatures(double[] values, double[] times);
	
	public double[] extractFeatures(Chunk chunk){
		return extractFeatures(chunk.getVals(), chunk.getTimes());
	}
	
	public int numFeatures(){
		return featureNames().length;
	}

	public abstract String[] featureNames();
	
	/**
	 * Determines if the specified chunk has enough information in it for
	 *  features to be meaningful
	 * @param c		a cleaned Chunk
	 * @return
	 */
	public static boolean isFeaturizable(Chunk c){
		final int minLength = 5;
		return c.getLength() >= minLength;
	}
	
}
