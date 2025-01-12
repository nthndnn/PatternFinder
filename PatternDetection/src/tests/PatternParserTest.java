package tests;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import ui.PatternParser;

public class PatternParserTest {

	public static void main(String[] args){
		Pnt.pnt("Hello, World!".indexOf(","));
		Pnt.pnt("Hello, World!".indexOf("golf"));
		
//		String regex = "[.*]";
		
		String test = "[Hello!]";
		String test2 = "[Bye";
		
//		Pattern p = Pattern.compile(regex);
		String regex = Pattern.quote("[") + ".*" + Pattern.quote("]");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(test);
		Pnt.pnt(m.find());
		
		m = p.matcher(test2);
		Pnt.pnt(m.find());
		
		regex = "[" + Pattern.quote("\\") + "]";//"[\\^?]";
		p = Pattern.compile(regex);
		
		m = p.matcher("n\\,");
		Pnt.pnt(m.find());
		
		p = Pattern.compile("~");
		m = p.matcher("sd~asdf");
		Pnt.pnt(m.find());
		
		String pattern = "A\\~B/: [ -1 ,2]";
		
		Pnt.pnt(PatternParser.parse(pattern));
		
//		Pnt.pnt(PatternParser.parse(new Scanner(System.in).nextLine()));
		
		
		
		//Pnt.pntArr("hello, how are you   ? ".split(""));
		
//		m = p.matcher("$v");
		check("$v");
		check("%6");
		check("$789");
		check("$_87");
		check("$hello");
		check("$bdh528");
	
	}
	
	private static void check(String varName){
		Pattern varFormat = Pattern.compile("\\$\\w[\\w\\d]*");
		Matcher m = varFormat.matcher(varName);
		if (m.find())
			Pnt.pnt(varName + " is a valid name");
		else
			Pnt.pnt(varName + " is invalid");
	}
	
}




