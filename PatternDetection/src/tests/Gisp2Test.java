package tests;

import java.awt.GridLayout;

import javax.swing.JFrame;

import patternDetection.SimpleTokenStream;
import ui.TokenStreamDisplayer;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

public class Gisp2Test {

	public static void main(String[] args){
		String[] quants = new String[]{
				"Ca","Cl"//,"NO3",//"Na"
		};
		
		RawTimeSeriesTable table = CsvToTable.readCsv(Objs.DATA_PATH+"nfdunn_GISP2_formatted.csv");
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, 30);

		sts = sts.subStream( quants );
		
		JFrame win = new JFrame();
		win.setLayout(new GridLayout(quants.length, 1));
		for (String q : quants){
			TokenStreamDisplayer tsd = new TokenStreamDisplayer(sts, table, q);
			win.add(tsd);
		}
		win.pack();
		win.setVisible(true);
		
//		table = table.subTable(start, end);
//		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, numChunks, start, end);
//		return new Object[]{table, sts};
		ResultsDemo.displayPatterns(sts);
	}
	
	
}
