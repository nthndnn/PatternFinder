package rTest;

import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class Main {

	public static void main(String[] args) {
		Rengine re = new Rengine(args, false, new TextConsole());

//		RVector vector = (re.eval("data.frame(c(1:3),c(2:4))").asVector());
//		System.out.println(re.eval("getwd()"));
//		System.out.println(vector);
//		System.out.println(vector.at(0));
//		System.out.println("End?");
//		re.eval("col1=c(1,2,3,4)");
//		re.eval("col2=c(5,6,7,8)");
//		//RVector vector = 
//		
//		System.out.println(re.eval(
//				"k=data.frame(col1,col2)"
//				));
//		re.eval("g=list(1,2,3,4)");
		
		System.out.println(re.eval("k=c(T,F)"));
		System.out.println(re.eval("k"));
		System.out.println(re.eval("k").asString());
		System.out.println(re.eval("source('hello.r')"));
		//System.out.println(re.eval("k").asVector().at("col1"));
		
		
	}
	
}
