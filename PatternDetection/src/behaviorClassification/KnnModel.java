package behaviorClassification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A model that uses K-Nearest Neighbor algorithm to classify feature vectors
 * @author nathandunn
 *
 */
public class KnnModel implements Serializable {



	private FeatureTable trainingData;
	private double[] meanVec;
	private double[] stdVec;
	
	private String[] classes;
	
	//TODO: make Settings object
	private int k = 10;				//number of neighbors
	private double gamma = 1;		//exponent for weighting distances
	private double maxWeight = 100;	//maximum weight of a single vote
	
	private static final long serialVersionUID = 4L;
	
	
	public KnnModel(FeatureTable trainingData){
		this.k = 10;
		this.gamma = 1;
		this.maxWeight = 100;
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
	
	public KnnModel(ClassifiedChunkList ccl){
		this( new FeatureTable(ccl) );
	}
	
	public KnnModel(ClassifiedChunkList ccl, FeatureExtractor fe){
		this( new FeatureTable(ccl, fe) );
	}


	public void train(FeatureTable trainingData){
		if (trainingData.isEmpty())
			throw new Error("Empty Training Data");

		this.meanVec = trainingData.getMeanVec();
		this.stdVec = trainingData.getStdVec();
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
	
	
//	public String[] classify(FeatureTable newData){
//		if ( trainingData == null ) 
//			throw new Error("Model hasn't been trained yet");
//		//Also check that the DataTables are compatible
//		String[] out = new String[newData.getNumRows()];
//		for (int i=0; i<out.length; i++){
//			out[i] = classify(newData.getRow(i));
//		}
//		return out;
//	}
	
	
	public String classify(double[] featureVec){
		return classify(featureVec, false);
	}
	
	/**
	 * @param featureVec
	 * @param isScaled
	 * @return
	 */
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
	
	/**
	 * Returns the classification with the highest vote from the neighbors
	 *  with the specified indices
	 *  (Note: no tie-breaking device, but ties should be rare)
	 * @param featureVec
	 * @param indsOfClosest
	 * @return
	 */
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
	/**
	 * Uses Leave-one-out cross validation to assess the accuracy of the
	 *  classifier with respect to its own training data
	 * @return proportion of correct classifications
	 */
	public double evaluateAccuracy(){
		if (this.trainingData.getNumRows() <= 1)
			//Insufficient data for a meaningful answer
			return Double.NaN;

		//FeatureTable copy = trainingData;// trainingData.copy();
		int n = trainingData.getNumRows();
		int matches = 0;
		for (int i=0; i<n; i++){
			int[] inds = rangeExclude(i, n);
			FeatureTable sub = trainingData.subTable(inds);
			KnnModel model = 
					new KnnModel(sub, this.k, this.gamma, this.maxWeight);
			String prediction = model.classify( trainingData.getRow(i), true );
			if (prediction.equals( trainingData.getClassifications()[i]) )
				matches++;
		}
		return matches / (double)n;
	}
	
//	public KnnModel copy(){
//		
//	}
	
	/**
	 * Evaluates the accuracy for each combination of parameter pairs
	 * @param gammas	array of values for gamma
	 * @param ks		array of values for k
	 * @return a gammas.length by ks.length array of doubles
	 */
	public double[][] accuracyParameterSweep(double[] gammas, int[] ks){
		double[][] out = new double[gammas.length][ks.length];
		KnnModel testModel = new KnnModel(this.trainingData);
		for (int i=0; i<gammas.length; i++){
			for (int j=0; j<ks.length; j++){
				testModel.setGamma(gammas[i]);
				testModel.setK(ks[j]);
				out[i][j] = testModel.evaluateAccuracy();
			}
		}
		return out;
	}
	
	/**
	 * Returns a 4 column table of gamma, k, maxWeight, accuracy
	 * @param gammas
	 * @param ks
	 * @param maxWeights
	 * @return
	 */
	public DataTable accuracyParameterSweep(double[] gammas,
			int[] ks, double[] maxWeights){
		double[][] transposedEntries = 
				new double[gammas.length*ks.length*maxWeights.length][];
		KnnModel copy = this.copy();
		int i=0;
		for (double g : gammas){
			System.out.println(g);
			for (int k : ks)
				for (double mw : maxWeights){
					copy.setGamma(g);
					copy.setK(k);
					copy.setMaxWeight(maxWeight);
					transposedEntries[i++] = new double[]{
							g, k, mw, copy.evaluateAccuracy()
					};
				}
		}
		
		double[][] entries = MyMath.transpose(transposedEntries);
		String[] headers = new String[]{"Gamma", "K", "Max", "Acc"};
		return new DataTable(entries, headers);
	}
	
	/**
	 * Chooses the parameers that maximize the accuracy
	 */
	public void configureParameters(){
		
	}

	
	private KnnModel copy() {
		return new KnnModel(trainingData, k, gamma, maxWeight);
	}


	/**
	 * Returns an array containing values 0,1,..,i-1,i+1,..,n-1
	 * That is, it contains the natural numbers less than n, excluding i
	 * (Values will likely be out of order)
	 * @param i		value to be excluded
	 * @param n		max possible value + 1
	 * @return
	 */
	private static int[] rangeExclude(int i, int n){
		int[] out = new int[n-1];
		for (int j=0; j<n-1; j++)
			out[j] = j;
		if (i!=n-1)
			out[i] = n-1;
		return out;
	}
	
	
	public FeatureTable getTrainingData() {
		return trainingData;
	}
}
