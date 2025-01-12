package tests;

import java.awt.GridLayout;

import javax.swing.JFrame;

import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;
import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.MatchDataObject;
import patternDetection.Pattern;
import patternDetection.SimpleClause;
import patternDetection.SimpleTokenStream;
import patternDetection.Tokenizer;
import ui.TokenStreamDisplayer;

public class MatchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleClause pre = new SimpleClause("Cl", Behavior.FLA);
		SimpleClause suc = new SimpleClause("Ca", Behavior.SPI);
		Interval t = new Interval(27, 27);
		Pattern p = new Pattern(pre, suc, t);
		
		String[] quants = new String[]{
				"Ca","Cl"//,"NO3",//"Na"
		};
		

		SimpleTokenStream sts = getSeasonal();
		sts = sts.subStream( quants );
		
		MatchDataObject mdo = new MatchDataObject(p, sts);
		
		EvaluationObject eo = new EvaluationObject(mdo);
		
		Pnt.pnt(mdo);
		Pnt.pnt(eo);
		
		
//		JFrame win = new JFrame();
//		win.setLayout(new GridLayout(quants.length, 1));
//		for (String q : quants){
//			TokenStreamDisplayer tsd = new TokenStreamDisplayer(sts, table, q);
//			win.add(tsd);
//		}
//		win.pack();
//		win.setVisible(true);
	}

	
	public static SimpleTokenStream getTambora(){
		double start = 1717.545 - 2;
		double end = 1917.545;
		String so4file = "/Users/nathandunn/Desktop/tamboraTable.csv";
		
		return Tokenizer.defaultTokenize(so4file, 10.0, start, end);

	}

	public static SimpleTokenStream getSeasonal(){
		final double start = 1910.0;
		final double end = 1970.0;
		String fileName = "../../DataSets_R/US_ITASE-00-3_2013_filtered.csv";
		return Tokenizer.defaultTokenize(fileName, 0.5, start, end);
	}
	
}





