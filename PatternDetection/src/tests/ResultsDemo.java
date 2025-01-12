package tests;

import java.util.ArrayList;
import java.util.Random;

import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.Pattern;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import ui.CompositeTokenStreamDisplayer;
import ui.PatternParser;

public class ResultsDemo {

	public static void main(String[] args) {
//		examine();
		//*
		ItaseSeasonal.main(null);
		Pnt.pause();
//	Objs.setEvalSettings(2, 0.2, 2);

//		Pnt.pause();
//		Objs.setEvalSettings(0.5, 0, 0.2);		//Lagged seasonal
//		Object o = Objs.tokenizer;
//		Pnt.pnt(o);
////		randomPatterns();
//*
//		Objs.setEvalSettings(0.5, 0, 0.1);
//ClassifierTrainingTest.demoManual();
//		Pnt.pause();
		TamboraTest.main(null);

//		Gisp2Test.main(null);
//		Pnt.pause();

		ItaseSeasonal.main(null);
//		Pnt.pause();
//		Pnt.pnt();
//		Pnt.pause();
		Gisp2Test.main(null);
		Pnt.pause();
//		ItaseSeasonal.longItase();
		//*/
	}
	
	//Used to examine why there is an anti-match in perfect seasonal
	public static void examine(){
		SimpleTokenStream sts = ItaseSeasonal.perfectSeasonal();
		
		Pattern p = PatternParser.parse("SO4\\~SO4/:[1,1]");
		
		EvaluationObject o = EvaluationObject.evaluate(sts, p);
		Pnt.pnt(o);
		Pnt.pnt(o.getMdo().getAntiMatches());
		Pnt.pnt(o.getMdo().getIndMatches());
		Pnt.pnt(sts);
		
		sts = sts.subStream(5, 200);
		Pnt.pnt(sts);
	}
	
	public static void displayPatterns(SimpleTokenStream sts, EvaluationSettings settings){
		ArrayList<EvaluationObject> patterns = new PatternExtractor(settings).extract(sts);
		Pnt.pntArr(patterns);
	}
	public static void displayPatterns(SimpleTokenStream sts){
		ArrayList<EvaluationObject> patterns = new PatternExtractor().extract(sts);
		Pnt.pntArr(patterns);
	}
	
	public static void randomPatterns(){
		String[] quants = new String[]{
			"NO3", "K", "SO4", "Ca"	
		};
		int len = 100;
		SimpleTokenStream sts = new SimpleTokenStream();
		for (String q : quants){
			ArrayList<SimpleToken> list = new ArrayList<SimpleToken>();
			for (int i=0; i<len; i++){
				list.add(randToken(q, i));
			}
			sts.add(q, list);
		}
		
		new CompositeTokenStreamDisplayer(sts).display();
		displayPatterns(sts);
	}
	
	private static SimpleToken randToken(String quant, int i){
		int ind = new Random().nextInt(5);
		return new SimpleToken(quant, Behavior.getKnownBehaviors()[ind], i);
	}

}
