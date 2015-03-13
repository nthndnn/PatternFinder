package tests;

import behaviorClassification.MyMath;
import behaviorClassification.StandardFeatureExtractor;

public class MyMathTest {

	public static void main(String[] args) {
		
		double[] zero = new double[]{0,0};
		double[] vec = new double[] {3,4};
		Pnt.pnt(MyMath.distance(zero, vec));
		Pnt.pnt(MyMath.distance(MyMath.add(vec, vec), vec));
		Pnt.pnt(MyMath.distance(MyMath.mult(vec, 2), vec));
		Pnt.pnt(MyMath.distance(MyMath.mult(vec, 3), zero));
		
		
		double[][] arr = new double[][]{
				new double[] {1,2,3,4},
				new double[] {5,6,7,8}
		};
		
		Pnt.pntArr(arr);
		Pnt.pnt();
		Pnt.pntArr(MyMath.transpose(arr));
		
		double[] arr2 = new double[]{1,5,67,6,4,3,6,6,4,-2,-8,-3,-100};
		double[] t = new double[]	{1,2,3, 4,5,6,7,8,9,10,11,12,14};
		Pnt.pnt(MyMath.getMax(arr2));
		Pnt.pnt(MyMath.getMin(arr2));
		Pnt.pnt();
		Pnt.pnt(MyMath.mean(arr2));
		Pnt.pnt(MyMath.std(arr2));
		Pnt.pnt();
		Pnt.pntArr((new StandardFeatureExtractor()).extractFeatures(arr2,t));
		Pnt.pntArr(new StandardFeatureExtractor().featureNames());
	}
	
	
	public static boolean doubEq(double d1, double d2){
		final double epsilon = 0.00000001;
		return Math.abs(d1-d2) < epsilon;
	}
}
