package tests;

import java.util.ArrayList;

import patternDetection.EvaluationObject;
import patternDetection.Match;
import patternDetection.PatternExtractor;
import patternDetection.SimpleTokenStream;
import patternDetection.Tokenizer;
import ui.CompositeTokenStreamDisplayer;
import ui.PatternCommandGuiApp;
import behaviorClassification.ChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

public class MatchDisplayerTest {

	public static void main(String[] args){
		SimpleTokenStream sts;
//		SimpleTokenStream sts2 = getTambora();
		
		RawTimeSeriesTable table = CsvToTable.readCsv("/Users/nathandunn/Desktop/tamboraTable.csv");
//		Pnt.pnt(table);
//		Pnt.pnt(table.getForwardIndicator());
//		Pnt.pnt(table.getMostRecentTime());
//		Pnt.pnt(table.getLeastRecentTime());
//		ChunkList cl = new ChunkList(table, 0.5);
//		System.out.println(cl);
		
		
//		PatternCommandGuiApp app = new PatternCommandGuiApp();
//		
//		sts = getSeasonal();
//		app.displayStream(sts);
//		Pnt.pause();
		sts = getTambora();
//		app.displayStream(sts);
		
	}
	
	
	public static SimpleTokenStream getTambora(){
		double start = 1717.545 - 2;
		double end = 1917.545;
		String so4file = "/Users/nathandunn/Desktop/tamboraTable.csv";
		
		return Tokenizer.defaultTokenize(so4file, 10.0, start, end);
//		return Tokenizer.defaultTokenize(so4file, 5, start, end);

	}

	public static SimpleTokenStream getSeasonal(){
		final double start = 1910.0;
		final double end = 1970.0;
		String fileName = "../../DataSets_R/US_ITASE-00-3_2013_filtered.csv";
		return Tokenizer.defaultTokenize(fileName, 0.5, start, end);
	}
	
	
	
}






