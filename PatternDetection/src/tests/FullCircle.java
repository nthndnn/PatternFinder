package tests;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.FeatureExtractor;
import behaviorClassification.FeatureTable;
import behaviorClassification.InputSimulator;
import behaviorClassification.KnnModel;
import behaviorClassification.ManualInputReader;
import behaviorClassification.RawDataTable;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserClassifier;
import behaviorClassification.Chunk;

public class FullCircle {

	public static void main(String[] args) {
		
		RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
		ChunkList cl = new ChunkList(rdt, 30);
		ClassifiedChunkList ccl = new UserClassifier(
				new ManualInputReader()
				//new InputSimulator()
		).classify(cl);

		FeatureTable table = new FeatureTable(ccl, new StandardFeatureExtractor());
		KnnModel model = new KnnModel(table);
		
//		Pnt.pnt(table);
//		Pnt.pnt(table.getScaledTable());
//		table.shuffle();
//		Pnt.pnt(table);

		model = new KnnModel(table);
		Pnt.pnt(model.evaluateAccuracy());
		//int i=1/0;
		
		FeatureExtractor fe = new StandardFeatureExtractor();
		for (String q : cl.getQuantities()){
			for (Chunk c : cl.getChunks(q)){
				if (FeatureExtractor.isFeaturizable(c)){
					double[] vec = fe.extractFeatures(c);
					Pnt.pnt(model.classify(vec));
				}
			}
		}
		
		//Pnt.pnt(model);
		
		
	}

}
