package ui;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CommandAppUtil {

	private static final Pattern varFormat = Pattern.compile("\\$\\w[\\w\\d]*");
	
	public static boolean isValidVarName(String varName){
		return varFormat.matcher(varName).find();
	}
	
	public static String argTypeCheckRepeat(ArrayList<AppVar<? extends Object>> args,
			AppVar<? extends Object> argType){
		ArrayList<AppVar<? extends Object>> argTypes = new ArrayList<AppVar<? extends Object>>();
		for (int i=0; i<args.size(); i++){
			argTypes.add(argType);			
		}
		return argTypeCheck(args, argTypes);
	}
	
	
	public static String argTypeCheck(ArrayList<AppVar<? extends Object>> args,
			ArrayList<AppVar<? extends Object>> argTypes){
		boolean flag = false;
		String out = "";
		if (args.size() != argTypes.size()){
			out = "Size mismatch: \n";
			flag = true;
		}else{
			for (int i=0; i<args.size(); i++){
				if (!argTypes.get(i).instance(args.get(i))){
					flag = true;
					out = "Type mismatch: \n";
				}
			}
		}
		if (flag){
			out += "Expected types: ";
			for (AppVar<? extends Object> expArg : argTypes){
				out += expArg.getType() + ", ";
			}
			out += "\nGiven types: ";
			for (AppVar<? extends Object> actArg : args){
				out += actArg.getType() + ", ";
			}
			return out;
		}
		
		return out;
	}
	
		
	/**
	 * Provides information on which argument types are incorrect. Returns "" if not problems
	 * @param args
	 * @param argTypes
	 * @return
	 */
	public static String argTypeCheck(ArrayList<AppVar<? extends Object>> args,
			AppVar<? extends Object>... argTypes){
		
		ArrayList<AppVar<? extends Object>> argTypes2 = new ArrayList<AppVar<? extends Object>>();
		for (int i=0; i<argTypes.length; i++){
			argTypes2.add(argTypes[i]);			
		}
		return argTypeCheck(args, argTypes2);
//		boolean flag = false;
//		String out = "";
//		if (args.size() != argTypes.length){
//			out = "Size mismatch: \n";
//			flag = true;
//		}else{
//			for (int i=0; i<args.size(); i++){
//				if (!argTypes[i].instance(args.get(i))){
//					flag = true;
//					out = "Type mismatch: \n";
//				}
//			}
//		}
//		if (flag){
//			out += "Expected types: ";
//			for (AppVar<? extends Object> expArg : argTypes){
//				out += expArg.getType() + ", ";
//			}
//			out += "\nGiven types: ";
//			for (AppVar<? extends Object> actArg : args){
//				out += actArg.getType() + ", ";
//			}
//			return out;
//		}
//		
//		return out;
	}
	
	
	/**
	 * Provides information on which argument types are incorrect. Returns "" if not problems
	 * @param args
	 * @param argTypes
	 * @return
	 */
	public static String argTypeCheck(AppVar<? extends Object>[] args,
			AppVar<? extends Object>... argTypes){
		boolean flag = false;
		String out = "";
		if (args.length != argTypes.length){
			out = "Size mismatch: \n";
			flag = true;
		}else{
			for (int i=0; i<args.length; i++){
				if ( !argTypes[i].instance(args[i]) ){
					flag = true;
					out = "Type mismatch: \n";
				}
			}
		}
		if (flag){
			out += "Expected types: ";
			for (AppVar<? extends Object> expArg : argTypes){
				out += expArg.getType() + ", ";
			}
			out += "Given types: ";
			for (AppVar<? extends Object> actArg : args){
				out += actArg.getType() + ", ";
			}
			return out;
		}
		
		return out;
	}
	
}






