package patternDetection;

import java.util.ArrayList;

//TODO make more flexible
//TODO make into a class

public enum Behavior {
	SPI,				//Spike
	INC,				//Increasing Trend
	FLA,				//Flat (no trend)
	DEC,				//Decreasing Trend
	DIP,				//Dip
	UNK;				//Unknown
	

	
	/**
	 * Converts the enum to a character showing it's behavior
	 */
	public char toChar(){
		switch(this){
		case SPI: return '^';
		case INC: return '/';
		case FLA: return '-';
		case DEC: return '\\';
		case DIP: return 'v';
		case UNK: return '?';
		//default: return null;
		}
		throw new Error("Unrecognized enum value");	//This shouldn't happen
	}


//	public String toString(){
//		switch(this){
//		case SPI: return "SPI";
//		case INC: return "INC";
//		case FLA: return "FLA";
//		case DEC: return "DEC";
//		case DIP: return "DIP";
//		case UNK: return "UNK";
//		//default: return null;
//		}
//		throw new Error("Unrecognized enum value");	//This shouldn't happen
//	}
	
	public static Behavior translate(String s){
		if (s.equals("SPI"))
			return SPI;
		if (s.equals("INC"))
			return INC;
		if (s.equals("FLA"))
			return FLA;
		if (s.equals("DEC"))
			return DEC;
		if (s.equals("DIP"))
			return DIP;
		if (s.equals("UNK"))
			return UNK;
//		throw new Error("Unrecognized string value");
		return null;
	}
	
	public static Behavior translate(char c){
		if (c == '^')
			return SPI;
		if (c == '/')
			return INC;
		if (c == '-')
			return FLA;
		if (c == '\\')
			return DEC;
		if (c == 'v')
			return DIP;
		if (c == '?')
			return UNK;
//		throw new Error("Unrecognized char value");
		return null;
	}
	
	public static String getAllChars(){
		return getKnownChars() + UNK.toChar();
	}
	
	public static String getKnownChars(){
		String out = "";
		for (Behavior b : getKnownBehaviors())
			out += b.toChar();
		return out;
	}
	
	
//	public Behavior translate(int i){
//		switch(i){
//		case 2: return SPI;
//		case 1: return INC;
//		case 0: return FLA;
//		case -1: return DEC;
//		case -2: return DIP;
//		case -10: return UNK;
//		default: return null;
//		}
//	}
	
	public static ArrayList<String> getValidStrings(){
		ArrayList<String> out = new ArrayList<String>();
		for (Behavior b : Behavior.class.getEnumConstants())
			out.add(b.toString());
		return out;
	}
	
	//Returns all behaviors except UNK
	public static Behavior[] getKnownBehaviors(){
		ArrayList<Behavior> out = new ArrayList<Behavior>();
		for (Behavior b : Behavior.class.getEnumConstants())
			if (b != Behavior.UNK)
				out.add(b);
		return out.toArray(new Behavior[out.size()]);
	}
}








