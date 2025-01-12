package behaviorClassification;

import java.util.ArrayList;

import tests.Pnt;

/**
 * Holds data representing a table of raw time-series values
 * @author nathandunn
 *
 */
public class RawTimeSeriesTable extends DataTable{


	private static final long serialVersionUID = 6L;
	private int timeInd;			//Index of the time data
	
	private boolean before;			//Indicates if the times represent time
									// before some event (e.g. Years Before 
									// Present vs. Years AD)
	
	public boolean getForwardIndicator(){
		return before;
	}
	
//	public RawDataTable(double[][] entries) {
//		super(entries);
//	}
	
	//Headers required
	//Entries are in column-major order, with earliest times with lower indices
	public RawTimeSeriesTable(double[][] entries, String[] headers, int timeInd) {
		super(entries, headers);
		this.timeInd = timeInd;
//		swapTimeCol(this.entries, timeInd);
//		this.headers = swapAndCopy(headers, timeInd);
		
//		double[] times = entries[timeInd];
		double[] times = getTimes();
		if (times.length != 0)
			this.before = times[0] < times[ times.length-1 ];
	}
	
	private void swapTimeCol(double[][] entries, int timeInd){
		double[] temp = entries[timeInd];
		entries[timeInd] = entries[0];
		entries[0] = temp;
	}
	
	private String[] swapAndCopy(String[] headers, int timeInd){
		String[] out = new String[headers.length];
		for (int i=0; i<out.length; i++)
			out[i] = headers[i];
		String temp = out[timeInd];
		out[timeInd] = out[0];
		out[0] = temp;
		
		return out;
	}
	
	
	/**
	 * @return the amount of time this record spans
	 */
	public double timeSpan(){
		double out = before?
				getLeastRecentTime() - getMostRecentTime():
				getMostRecentTime() - getLeastRecentTime();

		if (out < 0)
			throw new Error("Times are reversed");
		return out;
	}
	
	//TODO make more sophisticated
	public double getMostRecentTime(){
		return getTimes()[0];
	}
	
	public double getLeastRecentTime(){
		double[] times = getTimes();
		return times[times.length-1];
	}

	public double[] getTimes(){
//		return entries[0];
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
	
	//TODO refactor
	/**
	 * Return a table whose time entries are only within the specified times
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public RawTimeSeriesTable subTable(double startTime, double endTime){
		ArrayList<Integer> inds = new ArrayList<Integer>();
		double[] times = this.getTimes();

		for (int i=0; i<this.getNumRows(); i++){
			if ( startTime <= times[i] && times[i] < endTime)
				inds.add(i);
		}
		
		double[][] emptyEntries = new double[this.getNumCols()][inds.size()];
		RawTimeSeriesTable out = new RawTimeSeriesTable(emptyEntries, 
				this.getHeaders(), this.timeInd);
		
		for (int i =0; i<inds.size(); i++)
			out.setRow(this.getRow( inds.get(i)), i);
		
		out.before = this.before;
		return out;
	}
	
	public RawTimeSeriesTable subTable(String[] headers){
		double[][] entries = new double[headers.length][];
		int timeInd = -1;
		for (int i=0; i<headers.length; i++){
			if (this.getTimeHeader().equals(headers[i]))
				timeInd = i;
			entries[i] = this.getCol(headers[i]);
		}
		return new RawTimeSeriesTable(entries, headers, timeInd );
	}
	
	public void rename(String current, String renamed){
		for (int i=0; i <headers.length; i++)
			if (headers[i].equals(current))
				headers[i] = renamed;
	}
	
	public String toString(){
		String header = getTimeHeader() + ":"+timeInd+"\n";
		return header + super.toString();
	}
	
	
//	public RawDataTable subTable(String[] desiredQuantities){
//		double[][] newEntries = new double[desiredQuantities.length][];
//		//for ()
//	}
	
	
}
