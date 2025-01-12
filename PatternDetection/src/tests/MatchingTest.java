package tests;

import java.util.ArrayList;

import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.Pattern;
import patternDetection.PatternExtractor;
import patternDetection.SimpleClause;
import patternDetection.SimpleTokenStream;
import behaviorClassification.RawTimeSeriesTable;

public class MatchingTest {

	public static void main(String[] args) {
		Pnt.pnt("Tambora");
		double start = 1717.545 - 2;
		double end = 1917.545;
		String so4file = "/Users/nathandunn/Desktop/tamboraTable.csv";

		Object[] things = TamboraTest.getStreamAndTable(so4file, start, end, 10.0);
		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream sts = (SimpleTokenStream)things[1];
		
//		ArrayList<EvaluationObject> patterns = PatternExtractor.defaultExtract(sts);
//		for (EvaluationObject eo : patterns)
//			Pnt.pnt(eo);
		
		Pattern p = new Pattern(
				new SimpleClause("SO4", Behavior.INC), 
				new SimpleClause("SO4", Behavior.DEC),
				new Interval(5, 5));
		
		EvaluationObject eo = EvaluationObject.evaluate(sts, p);
		Pnt.pnt(eo);
		Pnt.pnt(eo);
		
		
	}

}
