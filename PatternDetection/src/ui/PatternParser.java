package ui;


import patternDetection.Behavior;
import patternDetection.Interval;
import patternDetection.Pattern;
import patternDetection.SimpleClause;

public class PatternParser {

	/**
	 * Returns true if string corresponds to a valid, 3 component Pattern string
	 * @param str
	 * @return
	 */
//	public static boolean isValid(String str){
////		int preSucSepInd = str.indexOf(Pattern.PRE_SUC_SEP);
////		if (preSucSepInd < 1)		//if not there or if first charactern or earlier
////			return false;
////		int winSepInd = str.indexOf(Pattern.SUC_WIN_SEP);
////		if (winSepInd < preSucSepInd+2)	//if not there or if less than 2 chars between ~ and :
////			return false;
////		int lBrackInd = str.indexOf(Interval.L_BRACK);
////		if (lBrackInd < winSepInd + 1 )
////			return false;
////		int staEndSep = str.index
////		int rBrackInd = str.indexOf(Interval.R_BRACK);
//		String clauseRegex = ".*[" +quo(Behavior.getAllChars()) +"]";
//		String timeRegex = "-?\\d+";
//		String regex = 
//				clauseRegex + Pattern.PRE_SUC_SEP + clauseRegex + Pattern.SUC_WIN_SEP + //pre~suc:
//				quo(Interval.L_BRACK) + timeRegex + Interval.STA_END_SEP + timeRegex + Interval.R_BRACK;
//				
//		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
//		
//		Matcher m = p.matcher(str);
//		return m.find();
//	}
	
	
	
	public static Pattern parse(String str){
		int preSucSepInd = str.indexOf(Pattern.PRE_SUC_SEP);
		if (preSucSepInd < 1)		//if not there or if first character or earlier
			return null;
		int winSepInd = str.indexOf(Pattern.SUC_WIN_SEP);
		if (winSepInd < preSucSepInd+2)	//if not there or if less than 2 chars between ~ and :
			return null;
		int lBrackInd = str.indexOf(Interval.L_BRACK);
		if (lBrackInd < winSepInd + 1 )
			return null;
		int staEndSep = str.indexOf(Interval.STA_END_SEP);
		if (staEndSep < lBrackInd + 1)
			return null;
		int rBrackInd = str.indexOf(Interval.R_BRACK);
		if (rBrackInd < staEndSep + 1)
			return null;
		
		String preString = str.substring(0, preSucSepInd);
		String sucString = str.substring(preSucSepInd+1, winSepInd);
		
		String t1String = str.substring(lBrackInd+1, staEndSep);
		String t2String = str.substring(staEndSep+1, rBrackInd);
		
		SimpleClause pre = parseClause(preString);
		SimpleClause suc = parseClause(sucString);
		if (pre == null || suc == null)
			return null;
		
		Integer t1 = parseInt(t1String);
		Integer t2 = parseInt(t2String);
		if (t1 == null || t2 == null)
			return null;
		
		return new Pattern(pre, suc, new Interval(t1, t2));
		
	}
	
	private static SimpleClause parseClause(String str){
		int lastInd = str.length()-1;
		char behaviorChar = str.charAt(lastInd);
		String quantID = str.substring(0, lastInd);
		
		Behavior behavior = Behavior.translate(behaviorChar);
		if (behavior == null || quantID.equals(""))
			return null;
		
		return new SimpleClause(quantID, behavior);
	}
	
	public static boolean isValid(String str){
		return parse(str) != null;
	}
	
//	private static String quo(String str){
//		return java.util.regex.Pattern.quote(str);
//	}
	
//	public static boolean isNumeric(String str){
//		try{
//			Integer.parseInt(str);
//		}catch(Exception e){
//			return false;
//		}
//		return true;
//	}
	
	public static Integer parseInt(String str){
		str = str.replaceAll("\\s", "");		//Strip whitespace
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			return null;
		}
//		return null;
	}
	
//	public static Pattern parsePattern(String str){
//		
//		String[] preRest = str.split(Pattern.PRE_SUC_SEP);
//		
//		
//	}
//	
//	public static Interval parseInterval(String str){
//		
//	}
//	
//	public static SimpleClause parseClause(String str){
//		
//	}
//	
//	public static Behavior parseBehavior(String str){
//		
//	}
}






