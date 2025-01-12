package patternLanguage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * @author nathandunn
 * Provides static methods for transforming the intermediary text file of tokens
 *  into a token stream
 */
public class FileTranslator {

	public static CompositeTokenStream translateFile(String fileName) {
		CompositeTokenStream out = new CompositeTokenStream();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();		//Labels
			
			String[] labels = line.split(" +");
						
			while ((line = br.readLine()) != null) {
				String[] values = line.split(" +");
				out.addToken(createCompToken(values, labels));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}
	
	public static CompositeTokenStream translateFile(){
		return translateFile("TokenStream.txt");
	}
	
	
	private static CompositeToken createCompToken(String[] values, String[] labels){
		TimeAttribute time = new TimeAttribute(
				Double.parseDouble(values[0]),
				Double.parseDouble(values[1]),
				null
				);
		
		ArrayList<QuantityToken> qTokens = new ArrayList<QuantityToken>();
		for (int i=2; i<labels.length; i++){
			String name = labels[i];
			BehaviorToken bt = stringToToken(values[i]);
			QuantityToken qt = new QuantityToken( name, bt );
			qTokens.add(qt);
		}
		
		return new CompositeToken(time, qTokens);
	}

	/*
	private static void printArray(String[] s){
		System.out.println("length ="+s.length);
		for (String s2 : s){
			System.out.println(s2);
		}
		System.out.println();
	}*/
	
	private static BehaviorToken stringToToken(String s){
		if (s.equals("-2")){
			return new ExtremeBehavior(-4);
		}else if (s.equals("2")){
			return new ExtremeBehavior(4);
		}else if(s.equals("-1")){
			return new SteadyBehavior(-0.5);
		}else if(s.equals("1")){
			return new SteadyBehavior(0.5);
		}else{
			return new FlatBehavior();
		}
	}
}
