package patternDetection;

import java.util.ArrayList;
import java.util.HashMap;

import tests.Pnt;
import behaviorClassification.Chunk;
import behaviorClassification.RawTimeSeriesTable;



public class SimpleTokenStream {
	public HashMap<String, TokenList> stream;
	private double[] partition;				//beginning and end times for each token
	private int timeOfEarliest;				//The time of the earliest token (in chunkWidth units)
	private int timeOfLatest;				//The time of the latest token (in chunkWidth units)

	


	public SimpleTokenStream(double[] partition){
		stream = new HashMap<String, TokenList>();
		this.partition = partition;
		
		timeOfEarliest = -1;
		timeOfLatest = -1;
	}
	
	public SimpleTokenStream(){
		this(null);
	}
	
	
	public void setPartition(double[] partition) {
		this.partition = partition;
	}
	
	
//	/**
//	 * Reconstructs the time series for a given quantity
//	 * @param quant
//	 * @return
//	 */
//	public double[][] reconstruct(String quant){
//		TokenList list = stream.get(quant);
//		if (list == null)
//			return null;
//		
//	}
	
	public RawTimeSeriesTable reconstruct(){
		ArrayList<String> quants = quantities();
		double[][] entries = new double[quants.size()+1][];
		for (int i=0; i<quants.size(); i++){
			double[][] timeVals = (stream.get(quants.get(i))).reconstruct();
			entries[i] = timeVals[1];
			entries[quants.size()] = timeVals[0];
		}
		String[] headers = quants.toArray(new String[quants.size()]);
		return new RawTimeSeriesTable(entries, headers, quants.size());
	}
	
	
	public SimpleTokenStream subStream(String[] quants){
		SimpleTokenStream out = new SimpleTokenStream();
		for (String q : quants)
			out.add(q, this.getQuant(q));
		out.partition = this.partition;
		return out;
	}
	
	/**  //TOOD: refactor all
	 * Constructs a SimpleTokenStream that contains tokens from index firstInd
	 *  to lastInd (inclusive)
	 * @param firstInd
	 * @param lastInd
	 * @return
	 */
	public SimpleTokenStream subStream(int firstInd, int lastInd){	
		
		if (firstInd > lastInd){
			int temp = firstInd;
			firstInd = lastInd;
			lastInd = temp;
		}
		if (lastInd >= this.length())
			lastInd = this.length()-1;
		if (firstInd < 0)
			firstInd = 0;
		
		SimpleTokenStream out = new SimpleTokenStream();

		boolean makePart = this.partition != null;
		
		double[] newPartition = new double[lastInd - firstInd + 2];

		for (String q : quantities()){
			TokenList oldList = stream.get(q);
			ArrayList<SimpleToken> list = new ArrayList<SimpleToken>();

			SimpleToken st=null;
			int partInd = 0;		//Redoes a lot of work
			for (int i=firstInd; i<=lastInd; i++){
				st = oldList.getTokens().get(i);
				list.add( st );
				if (makePart)
					newPartition[partInd++] = st.getChunk().getStart();
			}
			if (makePart)
				newPartition[partInd] = st.getChunk().getEnd();
			
			out.add(q, list);
			
			if (!list.isEmpty()){
				out.timeOfEarliest = list.get(0).getTime();
				out.timeOfLatest = list.get(list.size()-1).getTime();
			}
		}
		
		if (makePart)
			out.setPartition(newPartition);
		
		//out.timeOfEarliest = 
		return out;
	}
	
	//@require m.getType == MATCH
	//and that the match originates in this stream
	public SimpleTokenStream subStream(Match m){
		SimpleToken pre = m.getPrecursor();
		SimpleToken suc = m.getSuccessor();
		SimpleTokenStream quantTrunc = this.subStream(
				new String[]{pre.getQuantIdentity(), suc.getQuantIdentity()});
		SimpleTokenStream out = quantTrunc.subStream(pre.getTime(), suc.getTime());
		return out;
	}
	
	
	public static SimpleTokenStream mergeStreams( 
			SimpleTokenStream stream1, SimpleTokenStream stream2 ){
		SimpleTokenStream out = new SimpleTokenStream();
		ArrayList<String> quants1 = stream1.quantities();
		ArrayList<String> quants2 = stream2.quantities();
		for (String q : quants1)
			if (quants2.contains(q))
				throw new Error("Quantities intersect");
		for (String q : quants1)
			out.add(q, stream1.getQuant(q));
		for (String q : quants2)
			out.add(q, stream2.getQuant(q));
		
		return out;
	}
	
	
	public double[] getPartition() {
		return partition;
	}
	

	public int getTimeOfEarliest() {
		return timeOfEarliest;
	}

	public int getTimeOfLatest() {
		return timeOfLatest;
	}
	
	public boolean isEmpty(){
		return stream.isEmpty();
	}

	
	//TODO check consistency of times
	public void add( String quantID, ArrayList<SimpleToken> tokens ){
//		if (this.isEmpty()){
//			int n = tokens.size();
//			if (n != 0){
//				timeOfEarliest = tokens.get(0).time;
//				timeOfLatest = tokens.get(n-1).time;
//			}
//		}else{
//			int n = tokens.size();
//			
//		}
		
		if (this.isEmpty()){
			int n = tokens.size();
			if (n != 0){
				timeOfEarliest = tokens.get(0).time;
				timeOfLatest = tokens.get(n-1).time;
			}
		}
		stream.put(quantID, new TokenList(tokens));
	}
	
	public ArrayList<String> quantities(){
		return new ArrayList<String>(stream.keySet());
	}
	
	public ArrayList<SimpleToken> filter(Behavior b, String quantID){
		return stream.get(quantID).filter(b);
	}
	
	public ArrayList<SimpleToken> filter(SimpleClause c){
		return stream.get(c.quantID).filter(c.behavior);
	}
	
	/**
	 * Returns the UNK tokens of type quant
	 * @param quant
	 * @return
	 */
	public ArrayList<SimpleToken> filterUnk(String quant){
		ArrayList<SimpleToken> out =  stream.get(quant).filter(Behavior.UNK);
		return out;
	}
	
	public ArrayList<SimpleToken> getQuant(String quantID){
		if (!stream.containsKey(quantID))
			throw new Error("Invalid quantityID: "+quantID);
		return stream.get(quantID).tokens;
	}
	
	public String toString(){
		ArrayList<String> quantIDs = quantities();
		int len = length();
		String out = "	";
		for (String q : quantIDs){
			out += q + " 	";
		}
		out += "\n";
		for (int i=0; i<len; i++){
			String layer = "(   ";
			for (String q : quantIDs){
				SimpleToken temp = getQuant(q).get(i);
				layer += temp.behavior.toString() + "   ";
				//out += getQuant(q).get(i);
			}
			layer += ")" + (this.getTimeOfEarliest()+i) + "\n";
			out += layer;
			//out += "\n";
		}
		return out;
	}
	
	/**
	 * Returns the number of tokens in the stream
	 * @return
	 */
	public int length(){
		return stream.entrySet().iterator().next().getValue().tokens.size();
	}
	
	public static class TokenList {
		public ArrayList<SimpleToken> tokens;
		
		public ArrayList<SimpleToken> getTokens() {
			return tokens;
		}

		public TokenList(ArrayList<SimpleToken> tokens) {
			this.tokens = tokens;
		}

		public double[][] reconstruct(){
			ArrayList<double[]> times = new ArrayList<double[]>();
			ArrayList<double[]> vals = new ArrayList<double[]>();
			int totalTimes = 0;
			int totalVals = 0;
			for (int i=0; i<tokens.size(); i++){
				Chunk c = tokens.get(i).chunk;
				double[] subTimes = c.getTimes();
				double[] subVals = c.getVals();
				totalTimes += subTimes.length;
				totalVals += subVals.length;
				times.add(subTimes);
				vals.add(subVals);
			}
			if (totalTimes != totalVals)
				throw new Error("This shouldn't happen");
			int n = totalTimes;
			double[] conTimes = new double[n];
			double[] conVals = new double[n];
			int ind = 0;
			for (int i=0; i<times.size(); i++){
				double[] time = times.get(i);
				double[] val = vals.get(i);
				for (int j=0; j<time.length; j++){
					conTimes[ind] = time[j];
					conVals[ind++] = val[j];
				}
			}
			return new double[][]{conTimes, conVals};
		}
		
		public ArrayList<SimpleToken> filter(Behavior b){
			ArrayList<SimpleToken> out = new ArrayList<SimpleToken>();
			for (SimpleToken t : tokens){
				if (t.behavior == b) out.add(t); 
			}
			return out;
		}
		
		
		
		
	}
}
