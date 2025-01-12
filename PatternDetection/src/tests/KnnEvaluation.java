package tests;

import behaviorClassification.DataTable;
import behaviorClassification.FeatureTable;
import behaviorClassification.KnnModel;
import behaviorClassification.Serializer;

public class KnnEvaluation {

	public static void main(String[] args) {
		evaluateGisp();
	
	}
	
	
	//Evaluates the GISP KnnModel
	private static void evaluateGisp(){
		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
		Pnt.pnt(model.evaluateAccuracy());
		
//		int[] ks = new int[30];
//		for (int i=0; i<ks.length; i++) ks[i] = i+1;
		
		double[] gammas = new double[31];
		for (int i=0; i<gammas.length; i++)
			gammas[i] = (double)i/10;
		int[] ks = new int[30];
		for (int i=0; i<ks.length; i++)
			ks[i] = i+1;
		double[] maxWeights = new double[]{
				0.001, 0.01, 0.1, 1.0, 10.0, 100.0, 1000.0, Double.POSITIVE_INFINITY	
		};
		
		DataTable accTable = model.accuracyParameterSweep(gammas, ks, maxWeights);
		Serializer.writeFile(accTable.toString(), "AccTable_g_k_mw4");
		
		//model.setMaxWeight(Double.POSITIVE_INFINITY);
//		DataTable accTable = new DataTable(model.accuracyParameterSweep(gammas, ks), null);
//		System.out.println(accTable);
		
		//Pnt.pntArr();
		
	
	}

}
