package behaviorClassification;

import java.io.Serializable;

/**
 * A class to hold a record-field table of real values. 
 *  Each column - or field - has a String "header" associated with it
 * @author nathandunn
 *
 */
public class DataTable implements Serializable {

	
	private static final long serialVersionUID = 5L;
	
	
	protected double[][] entries;		//entries stored in column-major order
	protected String[] headers;
	
	/**
	 * 
	 * @param entries
	 * @param headers
	 * @require entries.length == headers.length
	 */
	public DataTable(double[][] entries, String[] headers) {
		this.entries = entries;
		this.headers = headers;
	}
	
//	public DataTable(double[][] entries) {
//		this(entries, null);
//	}
	
	/**
	 * Returns the rowInd-th record/row of the table
	 * @param rowInd
	 * @return
	 */
	public double[] getRow(int rowInd){
		double[] out = new double[getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = entries[i][rowInd];
		return out;
	}
	
	/**
	 * Overwrites the rowInd-th row with the specified data
	 * @param data
	 * @param rowInd
	 */
	public void setRow(double[] data, int rowInd){
		for (int i=0; i<getNumCols(); i++)
			entries[i][rowInd] = data[i];
	}
	
	public double[][] getEntries() {
		//TODO return a copy instead?
		return entries;
	}
	
	/**
	 * @return true if either the number of rows or the number of columns is 0
	 */
	public boolean isEmpty(){
		return entries.length==0 || entries[0].length==0;
	}
	
	/**
	 * @return number of records in the table
	 */
	public int getNumRows(){
		if (entries.length != 0)
			return entries[0].length;
		return 0;
	}
	
	/**
	 * @return number of fields in the table
	 */
	public int getNumCols(){
		return entries.length;
	}
	
	
	public String[] getHeaders(){
//		if (headers==null) return null;		//TODO remove unnecessary condition?
		//Returns a copy to prevent alteration of table
		String[] out = new String[headers.length];
		for (int i=0; i<out.length; i++)
			out[i] = headers[i];
		
		return out;
	}
	
	/**
	 * Returns the index of the specified header value
	 * @param header
	 * @return
	 */
	private int headerInd(String header){
//		if (headers == null)		//TODO remove unnecessary condition?
//			throw new Error("Table doesn't have headers");
		for (int i=0; i<headers.length; i++)
			if (header.equals(headers[i]))
				return i;
		
		throw new Error("Not a valid column header");
	}
	
	/**
	 * Return an array of values in the column with the specified header
	 * @param header
	 * @return
	 */
	public double[] getCol(String header) {
		return getCol( headerInd(header) );
	}
	
	/**
	 * Return an array of values in the column with the specified index
	 * @param header
	 * @return
	 */
	public double[] getCol(int colInd) {
		return entries[colInd];
	}
	
	public String toString(){
		String out = "";
		if (headers != null)
			for (String s : headers)
				out += s + "	";
		
		out += "\n";
		for (int i=0; i<getNumRows(); i++){
			double[] row = this.getRow(i);
			for (int j=0; j<row.length; j++)
				out += row[j] +"	";
			out += "\n";
		}
		return out;
	}
	
	protected void swapRows( int ind1, int ind2 ){
		double[] temp = this.getRow(ind1);
		this.setRow( this.getRow(ind2), ind1);
		this.setRow( temp, ind2);
	}
	

//	/**
//	 * 
//	 * @param colInd
//	 * @param increasing
//	 */
//	public void sort(int colInd, boolean increasing){
//		
//	}
	
//	/**
//	 * Sorts based on the column entries with the specified index
//	 * @param colInd
//	 */
//	public void sort(int colInd){
//		this.sort(colInd, true);
//	}
	
}
