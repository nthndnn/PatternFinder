package tests;

import java.util.ArrayList;

import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.PatternExtractor;
import patternDetection.SimpleTokenStream;

public class ResultsDemo {

	public static void main(String[] args) {
		ItaseSeasonal.main(null);
		Pnt.pause();
		Pnt.pnt();
		TamboraTest.main(null);
		Pnt.pause();
		Gisp2Test.main(null);
		Pnt.pause();
		ItaseSeasonal.fullItase();
	}
	
	public static void displayPatterns(SimpleTokenStream sts, EvaluationSettings settings){
		ArrayList<EvaluationObject> patterns = new PatternExtractor(settings).extract(sts);
		Pnt.pntArr(patterns);
	}
	public static void displayPatterns(SimpleTokenStream sts){
		ArrayList<EvaluationObject> patterns = new PatternExtractor().extract(sts);
		Pnt.pntArr(patterns);
	}

}