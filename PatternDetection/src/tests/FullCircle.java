
package tests;

import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.Behavior;
import patternDetection.SimpleClause;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.Pattern;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import patternDetection.SimpleTokenStream.TokenList;
import patternDetection.Tokenizer;
import ui.InputSimulator;
import ui.ManualInputReadRecord;
import ui.TokenStreamDisplayer;
import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.FeatureExtractor;
import behaviorClassification.FeatureTable;
import behaviorClassification.KnnModel;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.Serializer;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserChunkClassifier;
import behaviorClassification.Chunk;


//TODO: create a coherent demonstration
public class FullCircle {

	public static void main(String[] args) {
		
		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
		Tokenizer tokenizer = new Tokenizer(new ModelClassifier(model));
		
		RawTimeSeriesTable itaseData = CsvToTable.
//				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered.csv");
				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered_truncated.csv");
		
		SimpleTokenStream ts = tokenizer.tokenize(itaseData, 0.5);	//half a year chunk width
		
//		TokenList tokens = ts.stream.get("SO4");
//		tokens.tokens.set(2, new Token("SO4",Behavior.SPI,2) );


		TokenStreamDisplayer tsd = new TokenStreamDisplayer(
				ts.getQuant(ts.quantities().get(0)),
				new double[][]{
					itaseData.getTimes(),
					itaseData.getCol(0)
				},
				"SO4", 
				ts.getPartition()
//				new double[0]
				);

		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.add(tsd);
		win.pack();
		win.setVisible(true);

//		Pnt.pnt(ts);
//		int i = 1/0;
		
//		int crash = 1/0;
		
		
		
		System.out.println(ts);
		ArrayList<EvaluationObject> patterns = PatternExtractor.defaultExtract(ts);
		
		for (EvaluationObject eo : patterns)
			System.out.println(eo);
		
		SimpleClause pre = new SimpleClause("SO4", Behavior.INC);
		SimpleClause suc = new SimpleClause("SO4", Behavior.DEC);
		Pattern p = new Pattern(pre, suc, new Interval(1,1));
		Pattern p2 = new Pattern(suc, pre, new Interval(1,1));
		
		
		System.out.println(EvaluationObject.evaluatePower(ts, p));
		System.out.println(EvaluationObject.evaluatePower(ts, p2));
		
	}

	
	
//	KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
//	Tokenizer tokenizer = new Tokenizer(new ModelClassifier(model));
//	
//	RawDataTable gispData = CsvToTable.readCsv("../../DataSets_R/nfdunn_GISP2.csv");
//	
//	TokenStream ts = tokenizer.tokenize(gispData, 100);
//	
//	ArrayList<EvaluationObject> patterns = PatternExtractor.bruteForce(ts);
//	
//	for (EvaluationObject eo : patterns) 
//		System.out.println(eo);
//
//	RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
//	ChunkList cl = new ChunkList(rdt, 30);
//	ClassifiedChunkList ccl = new UserClassifier(
//			new ManualInputReader()
//			//new InputSimulator()
//	).classify(cl);
//
//	FeatureTable table = new FeatureTable(ccl, new StandardFeatureExtractor());
//	KnnModel model = new KnnModel(table);
//	
////	Pnt.pnt(table);
////	Pnt.pnt(table.getScaledTable());
////	table.shuffle();
//	Pnt.pnt(table);
//
//	model = new KnnModel(table);
//	Pnt.pnt(model.evaluateAccuracy());
//	//int i=1/0;
//	
//	FeatureExtractor fe = new StandardFeatureExtractor();
//	for (String q : cl.getQuantities()){
//		for (Chunk c : cl.getChunks(q)){
//			if (FeatureExtractor.isFeaturizable(c)){
//				double[] vec = fe.extractFeatures(c);
//				Pnt.pnt(model.classify(vec));
//			}
//		}
//	}
//	
//	//Pnt.pnt(model);
//	
		
	private static void displayTokens(){
		
	}
	
}
