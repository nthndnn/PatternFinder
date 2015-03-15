package behaviorClassification;

import java.util.ArrayList;

/**
 * Holds data representing a table of raw time-series values
 * @author nathandunn
 *
 */
public class RawDataTable extends DataTable{


	private static final long serialVersionUID = 6L;
	private int timeInd;			//Index of the time data
	
	//private double 
	
//	public RawDataTable(double[][] entries) {
//		super(entries);
//	}
	
	//Headers required
	public RawDataTable(double[][] entries, String[] headers, int timeInd) {
		super(entries, headers);
		this.timeInd = timeInd;
	}
	
	
	/**
	 * @return the amount of time this record spans
	 */
	public double timeSpan(){
		double[] times = getTimes();
		double out = times[ times.length-1 ] - times[0];
		
		//TODO fix this
		if (out < 0)
			throw new Error("Times are reversed");
		return out;
	}

	public double[] getTimes(){
		return entries[timeInd];
	}
	
	public int getTimeInd(){
		return timeInd;
	}
	
	public String getTimeHeader(){
		return this.headers[timeInd];
	}
	
	public String[] getNonTimeHeaders(){
		String timeHeader = getTimeHeader();
		ArrayList<String> out = new ArrayList<String>();
		for (String s : this.headers)
			if ( !s.equals(timeHeader) )
				out.add(s);

		return out.toArray(new String[out.size()]);
	}
	
	
	
//	public RawDataTable subTable(String[] desiredQuantities){
//		double[][] newEntries = new double[desiredQuantities.length][];
//		//for ()
//	}
	
	
}
