package behaviorClassification;

/**
 * Holds data representing a table of raw time-series values
 * @author nathandunn
 *
 */
public class RawDataTable extends DataTable{

	private int timeInd;			//Index of the time data
	
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
		return times[ times.length-1 ] - times[0];
	}

	public double[] getTimes(){
		return entries[timeInd];
	}
	
	
	
	
//	public RawDataTable subTable(String[] desiredQuantities){
//		double[][] newEntries = new double[desiredQuantities.length][];
//		//for ()
//	}
	
	
}
