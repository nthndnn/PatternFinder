package behaviorClassification;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Provides an object to store a subset of time series data
 * @author nathandunn
 *
 */
public class Chunk implements Serializable {

	private static final long serialVersionUID = 3L;
	
	private double[] vals;
	private double[] times;
	
	private double start;	//The earliest possible time in times
							// (not necessarily equal to min(times))
	private double end;		// '' latest ''
							// '' max ''
	
	
	public Chunk(double[] vals, double[] times, double start, double end) {
		MyMath.checkDim(vals, times);		//ensure vals.length==start.length
		this.vals = vals;
		this.times = times;
		this.start = start;
		this.end = end;
	}
	
	
	public double[] getVals() {
		return vals;
	}
	public double[] getTimes() {
		return times;
	}
	public double getStart() {
		return start;
	}
	public double getEnd() {
		return end;
	}
	
	/**
	 * @return number of time-value pairs in this chunk
	 */
	public int getLength() {
		return vals.length;
	}
	
	public String toString(){
		String out = String.format("(%f, %f)\n", start, end);
		for (int i=0; i<vals.length; i++)
			out += String.format("%f,	%f\n", vals[i],times[i]);
		return out;
	}

	/**
	 * Returns a new Chunk identical to the specified chunk
	 *  whose NaN values have been stripped out
	 * @param chunk
	 * @return 
	 */
	public static Chunk cleanChunk(Chunk c){
		ArrayList<Double> outTimes = new ArrayList<Double>();
		ArrayList<Double> outVals = new ArrayList<Double>();
		
		double[] times = c.getTimes();
		double[] vals = c.getVals();
		
		for (int i=0; i<times.length; i++){
			if (!Double.isNaN(times[i]) && !Double.isNaN(vals[i])){
				outTimes.add(times[i]);
				outVals.add(vals[i]);
			}
		}
		
		double[] outTimes2 = new double[outTimes.size()];
		double[] outVals2 = new double[outVals.size()];
		for (int i=0; i<outTimes2.length; i++){
			outTimes2[i] = outTimes.get(i);
			outVals2[i] = outVals.get(i);
		}
		
		return new Chunk( outVals2, outTimes2, c.getStart(), c.getEnd() );
	}
	
}
