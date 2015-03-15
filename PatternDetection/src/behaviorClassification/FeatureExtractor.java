package behaviorClassification;

public abstract class FeatureExtractor {

	public abstract double[] extractFeatures(double[] values, double[] times);
	
	public double[] extractFeatures(Chunk chunk){
		return extractFeatures(chunk.getVals(), chunk.getTimes());
	}
	
	//TODO: make abstract?
	public int numFeatures(){
		return featureNames().length;
	}

	public abstract String[] featureNames();
	
	//TODO make non-static
	public final static boolean isFeaturizable(Chunk c){
		final int minLength = 5;
		return c.getLength() >= minLength;
	}
	
}
