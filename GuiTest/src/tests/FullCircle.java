
package tests;

import java.util.ArrayList;

import patternDetection.Behavior;
import patternDetection.Clause;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.Pattern;
import patternDetection.PatternEvaluator;
import patternDetection.PatternExtractor;
import patternDetection.TokenStream;
import patternDetection.Tokenizer;
import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.FeatureExtractor;
import behaviorClassification.FeatureTable;
import behaviorClassification.InputSimulator;
import behaviorClassification.KnnModel;
import behaviorClassification.ManualInputReader;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.Serializer;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserClassifier;
import behaviorClassification.Chunk;

public class FullCircle {

	public static void main(String[] args) {
		
		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
		Tokenizer tokenizer = new Tokenizer(new ModelClassifier(model));
		
		RawTimeSeriesTable itaseData = CsvToTable.
				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered.csv");
		
		
		TokenStream ts = tokenizer.tokenize(itaseData, 0.5);	//half a year chunk width
		System.out.println(ts);
		ArrayList<EvaluationObject> patterns = PatternExtractor.bruteForce(ts);
		
		for (EvaluationObject eo : patterns)
			System.out.println(eo);
		
		Clause pre = new Clause("SO4", Behavior.INC);
		Clause suc = new Clause("SO4", Behavior.DEC);
		Pattern p = new Pattern(pre, suc, new Interval(1,1));
		Pattern p2 = new Pattern(suc, pre, new Interval(1,1));
		
		
		System.out.println(PatternEvaluator.evaluatePower(ts, p));
		System.out.println(PatternEvaluator.evaluatePower(ts, p2));
		
		
		
//		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
//		Tokenizer tokenizer = new Tokenizer(new ModelClassifier(model));
//		
//		RawDataTable gispData = CsvToTable.readCsv("../../DataSets_R/nfdunn_GISP2.csv");
//		
//		TokenStream ts = tokenizer.tokenize(gispData, 100);
//		
//		ArrayList<EvaluationObject> patterns = PatternExtractor.bruteForce(ts);
//		
//		for (EvaluationObject eo : patterns)
//			System.out.println(eo);
		
//		RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
//		ChunkList cl = new ChunkList(rdt, 30);
//		ClassifiedChunkList ccl = new UserClassifier(
//				new ManualInputReader()
//				//new InputSimulator()
//		).classify(cl);
//
//		FeatureTable table = new FeatureTable(ccl, new StandardFeatureExtractor());
//		KnnModel model = new KnnModel(table);
//		
////		Pnt.pnt(table);
////		Pnt.pnt(table.getScaledTable());
////		table.shuffle();
////		Pnt.pnt(table);
//
//		model = new KnnModel(table);
//		Pnt.pnt(model.evaluateAccuracy());
//		//int i=1/0;
//		
//		FeatureExtractor fe = new StandardFeatureExtractor();
//		for (String q : cl.getQuantities()){
//			for (Chunk c : cl.getChunks(q)){
//				if (FeatureExtractor.isFeaturizable(c)){
//					double[] vec = fe.extractFeatures(c);
//					Pnt.pnt(model.classify(vec));
//				}
//			}
//		}
//		
//		//Pnt.pnt(model);
//		
		
	}

}