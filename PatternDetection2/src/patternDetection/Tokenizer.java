package patternDetection;

import org.rosuda.JRI.Rengine;


public class Tokenizer {
	
	public static Rengine re;
	
	public static void initialize(){
		//System.loadLibrary("jri");
		re = new Rengine(new String[]{"--no-save"}, false, new TextConsole2());
		re.eval("source(\"sourcesSetup.r\")");
	}

	
	public static void tokenize(String csvFileName, int timeIndex){
		String cmd = String.format("tokenize(\"%s\",%d)", csvFileName, timeIndex);
		re.eval(cmd);
	}
	
	
//	public static void tokenize(){
//
//	}


}
