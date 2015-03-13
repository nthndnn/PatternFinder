package mainFeatures;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.FeatureTable;
import behaviorClassification.InputSimulator;
import behaviorClassification.KnnModel;
import behaviorClassification.ManualInputReader;
import behaviorClassification.RawDataTable;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserClassifier;

public class TrainClassifier {

	public static void main(String[] args) {
		//RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
		RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_GISP2.csv");
		//System.out.println("table good");

		//System.out.println(rdt);
		
		ChunkList cl = new ChunkList(rdt, 100);
		System.out.println("chunking good");

		//ClassifiedChunkList ccl = new UserClassifier(new ManualInputReader()).classify(cl);
		ClassifiedChunkList ccl = new UserClassifier(
				//new InputSimulator()
				).classify(cl);

		System.out.println("classify good");

		
		FeatureTable table = new FeatureTable(ccl, new StandardFeatureExtractor());
		KnnModel model = new KnnModel(table);
		System.out.println(model.evaluateAccuracy());
	}

}
