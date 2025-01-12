package tests;

import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import ui.TokenStreamDisplayer;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

/**
 * Tests Pattern Extraction on seasonal data
 * @author nathandunn
 *
 */
public class ItaseSeasonal {

	public static void main(String[] args) {
		
		Pnt.pnt("\n\nTruncated ITASE:");
		shortItase();
		Pnt.pause();
		
		Pnt.pnt("Long ITASE:");
		longItase();
		Pnt.pause();

		Pnt.pnt("\n\nSynthetic Seasonal");
		syntheticSeasonal();
		
	}

	
	public static void longItase(){
		final double start = 1910.0;
		final double end = 1970.0;
		RawTimeSeriesTable table = CsvToTable.
				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered.csv");
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, 0.5, start, end);
		table = table.subTable(start, end);
		showTokensAndPatterns(sts, table, null);
	}
	
	private static void shortItase(){
		RawTimeSeriesTable table = CsvToTable.
				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered_truncated.csv");
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, 0.5);
		showTokensAndPatterns(sts, table, null);
	}
	
	public static void syntheticSeasonal(){
		SimpleTokenStream sts = perfectSeasonal();
		showTokensAndPatterns(sts, null, null);
		
	}

	
	//will only show the first group of data
	/*public static void showStuff(SimpleTokenStream sts, RawTimeSeriesTable table){
		double[] partition = sts.getPartition() == null? new double[0] : sts.getPartition();
		
		TokenStreamDisplayer tsd = table == null?
				new TokenStreamDisplayer(sts.getQuant("SO4")) :
				new TokenStreamDisplayer(
				sts.getQuant(sts.quantities().get(0)),
				new double[][]{
					table.getTimes(),
					table.getCol(0)
				},
				"SO4", 
				partition
				);
				
		JFrame win = new JFrame();
		win.add(tsd);
		win.pack();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);		
		
		ArrayList<EvaluationObject> patterns = PatternExtractor.defaultExtract(sts);
		for (EvaluationObject eo : patterns)
			System.out.println(eo);
	}*/
	
	public static void showTokensAndPatterns(SimpleTokenStream sts, RawTimeSeriesTable table,
			EvaluationSettings settings){
		double[] partition = sts.getPartition() == null ? new double[0] : sts.getPartition();
		
		TokenStreamDisplayer tsd = table == null?
				new TokenStreamDisplayer(sts.getQuant("SO4")) :
				new TokenStreamDisplayer(
				sts.getQuant("SO4"),
				new double[][]{
					table.getTimes(),
					table.getCol("SO4")
				},
				"SO4", 
				partition
				);

		JFrame win = new JFrame();
		win.add(tsd);
		win.pack();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);		

		ArrayList<EvaluationObject> patterns = settings == null?
				PatternExtractor.defaultExtract(sts) : 
				new PatternExtractor(settings).extract(sts);
		
		for (EvaluationObject eo : patterns)
			System.out.println(eo);
	}
	
	
	public static SimpleTokenStream perfectSeasonal(){
		SimpleTokenStream out = new SimpleTokenStream();
		ArrayList<SimpleToken> sulfate = new ArrayList<SimpleToken>();
		//double[] partition = new double[226];
//		int num = 226;
		int num = 20;
		for (int i=0; i<num; i++){
			SimpleToken t = new SimpleToken("SO4", Behavior.UNK, i);
			t.behavior = i%2==0? Behavior.INC: Behavior.DEC;
			sulfate.add(t);
		}
		out.add("SO4", sulfate);
		return out;
	}
	
	
	
	
	
	

}
