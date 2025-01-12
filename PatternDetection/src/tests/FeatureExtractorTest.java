package tests;

import behaviorClassification.MyMath;
import behaviorClassification.StandardFeatureExtractor;

public class FeatureExtractorTest {

	public static void main(String[] args) {
		
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

}
