package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;

import tests.Pnt;


/**
 * Provides a class to store chunks of data grouped by the quantity that
 *  is represented
 * @author nathandunn
 *
 */
public class ChunkList {

	private HashMap<String, ArrayList<Chunk>> chunkList;
	private String[] quantities;
	private int numChunks;			
	private double[] partition;

	/**
	 * Breaks the information in the table into numChunks equi-time-spaced
	 *  chunks
	 * @param table
	 * @param numChunks
	 */
	public ChunkList(RawTimeSeriesTable table, int numChunks){
		this(table, table.timeSpan()/numChunks);
	}
	
	//Used to ensure no values are left out in a partition
	// starting at the earliest time and ending at the latest
	private static final double EPS = 0.0000001;

	/**
	 * Breaks the information in the table into Chunks whose time spans
	 *  are all equal to chunkWidth time units
	 * @param table
	 * @param chunkWidth
	 */
	public ChunkList(RawTimeSeriesTable table, double chunkWidth){
		this(table, chunkWidth, table.getMostRecentTime() - EPS, table.getLeastRecentTime() + EPS);
	}
	
	
	
	
	
	
	//TODO support for reversed times
	/**
	 * Breaks the information in the table from start to end into Chunks whose time spans
	 *  are all equal to chunkWidth time units
	 * @param table
	 * @param chunkWidth
	 * @param start
	 * @param end
	 */
	public ChunkList(RawTimeSeriesTable table, double chunkWidth, double start, double end){
		chunkList = new HashMap<String, ArrayList<Chunk>>();
		double[] times = table.getTimes();
//		Pnt.pntArr(times);
		partition = Partitioner.seq(start, end, chunkWidth);
//		Pnt.pntArr(partition);
		int[][] inds = Partitioner.partitionTimes(times, partition);
//		Pnt.pntArr(inds);
		double[][] timeChunks = Partitioner.indexVals(inds, times);
//		Pnt.pntArr(timeChunks);

		numChunks = inds.length;
		
		quantities = table.getNonTimeHeaders();
		for (String quant : quantities){
			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
			double[][] vals = Partitioner.indexVals(inds, table.getCol(quant));
			for (int i=0; i<vals.length; i++){
				chunks.add(new Chunk(vals[i], timeChunks[i], 
						partition[i], partition[i+1]));
			}
			chunkList.put(quant, chunks);
		}
	}
	
	public double[] getPartition() {
		return partition;
	}

	/**
	 * Returns a list of Chunks associated with the specified quantity 
	 *  (SO4, etc.)
	 * @param quant
	 * @return
	 */
	public ArrayList<Chunk> getChunks(String quant){
		return chunkList.get(quant);
	}
	
	/**
	 * @return an array of Strings describing the quantities whose values
	 *  are stored in the chunks
	 */
	public String[] getQuantities() {
		return quantities;
	}
	
	public int getNumChunks() {
		return numChunks;
	}
	
	public String toString(){
		String out = "";
		for (String s : quantities){
			out += s + "\n";
			out += this.getChunks(s);
		}
		return out;
	}

}
