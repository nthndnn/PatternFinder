package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;

import tests.Pnt;

public class Partitioner {	
	
//	public//private 
//	static int[][] createPartition(double[] times, int numChunks){
//		double[] partition = seq(times[0], times[times.length-1], numChunks);
//		return partitionTimes(times, partition);
//	}
	
	
	//TODO add offset parameter
	public //private 
	static int[][] createIndexPartition(double[] times, double chunkWidth){
		double[] partition = seq(times[0], times[times.length-1], chunkWidth);
		return partitionTimes(times, partition);
	}
	
	//TODO: could be made more efficient
	public //
	//private 
	static int[][] partitionTimes(double[] times, double[] partition){
		//@require partition[0] < times[0] && times[times.length-1] < partition[partitoin.length-1]
		int[][] out = new int[partition.length-1][];
		for (int i=0; i<partition.length-1; i++){
			ArrayList<Integer> chunkInds = new ArrayList<Integer>();
			double low = partition[i];
			double high = partition[i+1];
			for (int j=0; j<times.length; j++){
				if (low <= times[j] && times[j] < high)
					chunkInds.add(j);
			}
			int[] chunkInds2 = new int[chunkInds.size()];
			for (int j=0; j<chunkInds2.length; j++)
				chunkInds2[j] = chunkInds.get(j);
			out[i] = chunkInds2;
		}	
		return out;		
	}
	
	public //private
	static double[][] indexVals(int[][] indChunks, double[] vals){
		double[][] out = new double[indChunks.length][];
		for (int i=0; i<out.length; i++){
			double[] chunk = new double[ indChunks[i].length ];
			for (int j=0; j<chunk.length; j++){
				chunk[j] = vals[ indChunks[i][j] ];
			}
			out[i] = chunk;
		}
		return out;
	}
	
	/**
	 * Creates a 
	 * @param start
	 * @param end
	 * @param chunkWidth
	 * @return
	 */
	public //private 
	static double[] seq(double start, double end, double chunkWidth){
		int numChunks = (int)Math.floor((end-start)/chunkWidth);
//		int numChunks = (int)Math.floor(Math.abs(end-start)/chunkWidth);

		double[] out = new double[numChunks+1];
		out[0] = start;
		for (int i=1; i<numChunks; i++){
			out[i] = out[i-1] + chunkWidth;
		}
		out[numChunks] = end;
		return out;
	}
	
//	public //private 
//	static double[] seq(double start, double end, int numChunks){
//		double chunkWidth = (end-start)/numChunks;
//		return seq(start, end, chunkWidth);
//	}

}
