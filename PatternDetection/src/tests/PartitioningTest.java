package tests;

import behaviorClassification.Partitioner;

public class PartitioningTest {

	public static void main(String[] args) {
		
		double[] vals = Partitioner.seq(0, 10, 1.0);
		Pnt.pntArr(vals);
		Pnt.pnt();
		vals = Partitioner.seq(0, 100, 10);
		Pnt.pntArr(vals);
		Pnt.pnt();
		
		double[] times = new double[100];
		for (int i=0; i<times.length; i++)
			times[i] = i;
		int[][]chunks = Partitioner.partitionTimes(times, vals);
		Pnt.pntArr(chunks);
		Pnt.pnt();
		
		vals = Partitioner.seq(0.0001, 100.0001, 10);
		Pnt.pntArr(vals);
		Pnt.pnt();
		
		chunks = Partitioner.partitionTimes(times, vals);
		Pnt.pntArr(chunks);
		Pnt.pnt();
		
		times = new double[]{0.0};
		double[] partition = new double[]{-1,1};
		chunks = Partitioner.partitionTimes(times, partition);
		Pnt.pntArr(chunks);
		Pnt.pnt();
		
		partition = new double[]{1,2};
		chunks = Partitioner.partitionTimes(times, partition);
		Pnt.pntArr(chunks);
		Pnt.pnt();
		
		partition = new double[]{0, 0.1, 0.9, 1.0};
		times = new double[100];
		for (int i=0; i<times.length; i++)
			times[i] = (double)i/100;
		chunks = Partitioner.partitionTimes(times, partition);
		Pnt.pntArr(chunks);
		Pnt.pnt();
		
		double[][] values = Partitioner.indexVals(chunks, times);
		Pnt.pntArr(values);
		Pnt.pnt();
		
	}

}
