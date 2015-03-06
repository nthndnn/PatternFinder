package tests;

import behaviorClassification.FeatureTable;
import behaviorClassification.KnnModel;

public class KnnTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KnnModel model = new KnnModel();
		
		//String[] headers = new String[]{"V1","V2"};//,"V2"};
		double[][] train = new double[][]{
			new double[]{5,  6,  4,  6,  7,  -5,  -3,  -2,  -1 },	//V1 features
			new double[]{20, 30, 40, 50, 10, -15, -20, -30, -20},	//V2 features
			//new double[]{}			//V3 features
		};
		String[] classifications = new String[]
						{"++","++","++","++","++","--","--","--","--"};
		
		double[] test1 = new double[]{-6, 0};	//should be --
		double[] test2 = new double[]{4, 40};	//should be ++
		
		FeatureTable trainingData = new FeatureTable(train);
		model.train(trainingData, classifications);
		//model.setGamma(1);
		model.setK(5);
		System.out.println(model.classify(test1));
		System.out.println(model.classify(test2));
		//pntArr(model.classes);
	}
	


}
