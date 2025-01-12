package patternDetection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;




public class Main {

	static Behavior[] behaviors = Behavior.class.getEnumConstants();
	static Random rng = new Random();
	
	public static void main(String[] args) throws FileNotFoundException {
		
	/*	for (int i=0; i<100; i++){
			String vals = "";
			TokenStream ts = randData();
			ArrayList<Clause> allClauses = PatternExtractor.allClauses(ts);
			for (Clause pre:allClauses){
				for (Clause suc:allClauses){
					for (int start=0; start<ts.length(); start++){
						for (int end=start; end<ts.length(); end++){
							Pattern p = new Pattern(pre, suc, new Interval(start, end));
							double power = PatternEvaluator.evaluatePower(ts, p);
							vals += power + "\n";
						}
					}
				}
				//System.out.println("asd");
			}
			String fileName = "Eval"+i+".txt";
			PrintWriter writer = new PrintWriter(fileName);
			writer.println(vals);
			writer.close();
		}
		*/
		
		//*
		SimpleTokenStream ts = randData();
		ArrayList<EvaluationObject> patterns = new PatternExtractor().extract(ts, 100);
		
		for (int i=0; i<patterns.size(); i++){
			System.out.println(patterns.get(i));
		}
		
		System.out.println("\n\n" + ts);
		//*/
	}
	
	
	private static SimpleTokenStream randData(){
		SimpleTokenStream out = new SimpleTokenStream();
		int len = 100;
		String[] quantities = {"Fe+", "Na+", "Ca+", "CO2", "O18", "D20"};
		for (String q : quantities){
			ArrayList<SimpleToken> tokens = new ArrayList<SimpleToken>();
			for (int i = 0; i < len; i++){
				tokens.add( new SimpleToken(q, randBehavior(), i) );
			}
			out.add(q, tokens);
		}
		return out;
	}
	
	private static Behavior randBehavior(){
		return behaviors[ rng.nextInt(behaviors.length) ];
	}
	

}
