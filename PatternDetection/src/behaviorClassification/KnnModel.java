package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;

public class KnnModel {
	
//	private DataTable trainingData;
//	private String[] trainingClassifications;
//	private double[] meanVec;
//	private double[] stdVec;
//	
//	private String[] classes;
//	private int k = 10;
//	private double gamma = 0;
//	private double maxWeight = 10;
	
	public FeatureTable trainingData;
	//public String[] trainingClassifications;
	public double[] meanVec;
	public double[] stdVec;
	
	public String[] classes;
	public int k = 10;
	public double gamma = 1;		//Distance weight exponent
	public double maxWeight = 100;
	
	
//	public String toString(){
//		String out = "";
//		out += "Training Data Table:\n" + trainingData;
//		out += "Gamma: " + gamma + "\n";
//		
//	}


//	public KnnModel(FeatureTable trainingData, String[] classifications){
//		train(trainingData, classifications);
//	}
	
	//public KnnModel(FeatureTable trainingData, )
	
	public KnnModel(FeatureTable trainingData){
		train(trainingData);
	}
	
	
	public KnnModel(FeatureTable trainingData, int k, double gamma,
			double maxWeight) {
		this.trainingData = trainingData;
		this.k = k;
		this.gamma = gamma;
		this.maxWeight = maxWeight;
		train(trainingData);
	}


	public void train(FeatureTable trainingData){//, String[] classifications){
		if (trainingData.isEmpty())
			throw new Error("Empty Training Data");
//		if (trainingData.getNumRows() != classifications.length)
//			throw new Error("Incorrect number of classifications");
		
		this.meanVec = trainingData.getMeanVec();
		this.stdVec = trainingData.getStdVec();
		//this.trainingClassifications = classifications;
		this.classes = extractClasses( trainingData.getClassifications() );
		this.trainingData = trainingData.getScaledTable();
	}
	
	
	public void setK(int k) {
		this.k = k;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
	
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	//Returns an array containing only the unique strings in classifications
	private String[] extractClasses(String[] classifications){
		ArrayList<String> unique = new ArrayList<String>();
		for (String s : classifications){
			boolean contained = false;
			for (String s2 : unique)
				if (s.equals(s2)) contained = true;
			if (!contained)
				unique.add(s);
		}
		String[] out = new String[unique.size()];
		for (int i=0; i<out.length; i++)
			out[i] = unique.get(i);
		return out;
	}
	
	public String[] classify(FeatureTable newData){
		if ( trainingData == null ) 
			throw new Error("Model hasn't been trained yet");
		//Also check that the DataTables are compatible
		String[] out = new String[newData.getNumRows()];
		for (int i=0; i<out.length; i++){
			out[i] = classify(newData.getRow(i));
		}
		return out;
	}
	
	public String classify(double[] featureVec){
		return classify(featureVec, false);
//		double[] scaledVec = scaleFeatureVec(featureVec);
//		double[] distances = new double[trainingData.getNumRows()];
//		for (int i=0; i<distances.length; i++)
//			distances[i] = MyMath.distance(scaledVec, trainingData.getRow(i));
//		//KnnTest.pntArr(distances); 			//TODO
//		int[] inds = MyMath.indsOfLowest(distances, this.k);
//		return vote(scaledVec, inds);
	}
	
	public String classify(double[] featureVec, boolean isScaled){
		if (!isScaled)
			featureVec = scaleFeatureVec(featureVec);
		
		double[] distances = new double[trainingData.getNumRows()];
		for (int i=0; i<distances.length; i++)
			distances[i] = MyMath.distance(featureVec, trainingData.getRow(i));
		//KnnTest.pntArr(distances); 			//TODO
		int[] inds = MyMath.indsOfLowest(distances, this.k);
		return vote(featureVec, inds);
	}
	
	
	
	private String vote(double[] featureVec, int[] indsOfClosest){
		HashMap<String, Double> votes = new HashMap<String, Double>();
		for (String s : this.classes)
			votes.put(s, 0.0);
		
		for (int i : indsOfClosest){
			double distance = MyMath.distance(featureVec, trainingData.getRow(i));
			double weight = Math.pow(distance, -this.gamma);
			weight = Math.min(weight, this.maxWeight);
			String key = trainingData.getClassifications()[i];
			votes.put(key, votes.get(key) + weight);
		}
		//System.out.println(votes);		//TODO
		//Note: no tiebreaking device
		String keyOfHigh = null;
		double highest = Double.NEGATIVE_INFINITY;
		for (String s : this.classes){
			if (votes.get(s) > highest){
				highest = votes.get(s);
				keyOfHigh = s;
			}
		}
		return keyOfHigh;
	}

	
	private double[] scaleFeatureVec(double[] featureVec){
		return MyMath.scale(featureVec, meanVec, stdVec);
	}
	
	//Uses Leave-one-out cross validation (LOOCV)
	//TODO: use k-fold cross validation?
	public double evaluateAccuracy(){
//		final int clusterSize = 10;
		if (this.trainingData==null || this.trainingData.getNumRows() < 2)
			return Double.NaN;
//			throw new Error("Insufficient data for meaningful answer");
		FeatureTable copy = trainingData.copy();
//		copy.shuffle();
		int n = copy.getNumRows();
		int matches = 0;
		for (int i=0; i<n; i++){
			int[] inds = rangeExclude(i, n);
			FeatureTable sub = copy.subTable(inds);
			KnnModel model = 
					new KnnModel(sub, this.k, this.gamma, this.maxWeight);
			String prediction = model.classify( copy.getRow(i), true );
			if (prediction.equals(copy.getClassifications()[i]))
				matches++;
		}
		return matches / (double)n;
	}
	
	/**
	 * Returns an array containing values 0,1,..,i-1,i+1,..,n-1
	 * That is, it contains the natural numbers less than n, excluding i
	 * (Makes no claims about the order these values will appear in)
	 * @param i		value to be excluded
	 * @param n		max possible value + 1
	 * @return
	 */
	public 
	//private		//TODO make private
	static int[] rangeExclude(int i, int n){
		int[] arr = new int[n-1];
		for (int j=0; j<n-1; j++)
			arr[j] = j;
		if (i!=n-1)
			arr[i] = n-1;
		
		return arr;
	}
	
//	private FeatureTable[] cluster(FeatureTable table, int[] inds1, int[] inds2){
//		
//	}

	
	
}
