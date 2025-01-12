package behaviorClassification;

public class StandardFeatureExtractor extends FeatureExtractor {

	private static final long serialVersionUID = -3950662327345673055L;
	
	
	
	private static final int MIN_LENGTH = 5;
	
	@Override
	public double[] extractFeatures(double[] values, double[] times) {
		return new double[]{
				MyMath.minZ(values),
				MyMath.maxZ(values),
				MyMath.coefVar(values),
				fisherZ(values, times),
				MyMath.skewness(values)				
		};
	}

//	@Override
//	public int numFeatures() {
//		return 5;
//	}

	@Override
	public String[] featureNames() {
		return new String[]{
				"MinZ",
				"MaxZ",
				"CV",
				"FisherZ",
				"skew"
		};
	}
	
	//TODO: make private
	public static double fisherZ(double[] values, double[] times){
		if (values.length <= 3)
			throw new Error("Fisher Transformation needs n >= 4");
		double w = MyMath.fisherTrans( MyMath.corrCoef(values, times) );
		double std = 1/Math.sqrt(values.length - 3);
		
		return w/std;
	}

//	@Override
//	public boolean isFeaturizable(Chunk c) {
//		return c.getLength() >= MIN_LENGTH;
//	}
	
//	public static boolean isFeaturizable(Chunk c){
//		return true;
//	}
}
