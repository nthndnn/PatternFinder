package patternDetection;

import java.util.ArrayList;

import tests.Pnt;

public class MatchDataObject {

	private Pattern p;		//Pattern this object tries to match
	private SimpleTokenStream ts;

	private ArrayList<SimpleToken> precursors;
	private ArrayList<SimpleToken> successors;
	private ArrayList<SimpleToken> unknowns;		//The tokens with UNK type
	private MatchDataObject.DifferenceTable lags;
	
	private ArrayList<Match> matches;
	private ArrayList<Match> antiMatches;
	private ArrayList<Match> indMatches;
	
	public MatchDataObject(Pattern p, SimpleTokenStream ts){
		this.p = p;
		this.ts = ts;
		precursors = ts.filter(p.pre);
		successors = ts.filter(p.suc);
		unknowns = ts.filterUnk(p.suc.quantID);

		
//		lags = new DifferenceTable(precursors, successors, ts.length(), unknowns);
		lags = new DifferenceTable(precursors, successors, ts.getTimeOfLatest(), unknowns);
		matches 	= new ArrayList<Match>();
		antiMatches = new ArrayList<Match>();
		indMatches 	= new ArrayList<Match>();

		findMatches();
	}
	
	private void findMatches(){
		for (int i=0; i<precursors.size(); i++){
			Match match;
			SimpleToken pre = precursors.get(i);

			if (lags.isDeterminate(i, p.time)){

				SimpleToken possibleSuccessor = lags.firstBetween(i, p.time);
				if (possibleSuccessor == null)
					match = Match.createAntiMatch(pre);
				else
					match = Match.createMatch(pre, possibleSuccessor);
			}else{
				match = Match.createIndMatch(pre);
			}
			this.addMatch(match);
		}
	}
	
	private void addMatch( Match m ){
		if (m.type == MatchType.MATCH)
			matches.add(m);
		else if (m.type == MatchType.ANTI)
			antiMatches.add(m);
		else// if (m.type == MatchType.IND)
			indMatches.add(m);
	}
	
	
	
	//Getters
	public Pattern getPattern() {
		return p;
	}

	public SimpleTokenStream getTokenStream() {
		return ts;
	}

	public ArrayList<SimpleToken> getPrecursors() {
		return precursors;
	}

	public ArrayList<SimpleToken> getSuccessors() {
		return successors;
	}

	public DifferenceTable getLags() {
		return lags;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

	public ArrayList<Match> getAntiMatches() {
		return antiMatches;
	}

	public ArrayList<Match> getIndMatches() {
		return indMatches;
	}

	

	private static class DifferenceTable {

		private ArrayList<SimpleToken> precursors;
		private ArrayList<SimpleToken> successors;
		private ArrayList<SimpleToken> unknowns;
		
		private int latestTime;
		private int[][] diff;


		public DifferenceTable(ArrayList<SimpleToken> precursors, 
				ArrayList<SimpleToken>successors, int latestTime, ArrayList<SimpleToken> unknowns ){
			
			this.precursors = precursors;
			this.successors = successors;
			this.unknowns = unknowns;
			this.diff = new int[precursors.size()][successors.size()];
			this.latestTime = latestTime;
			populateTable();

		}

		private void populateTable(){
			for (int i = 0; i < precursors.size(); i++)
				for (int j = 0; j < successors.size(); j++)
					diff[i][j] = successors.get(j).time - precursors.get(i).time;
		}


		//TODO rename
		/**
		 * Returns the first Successor whose lag is within the range of the
		 *  specified Precursor
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		public SimpleToken firstBetween(int precursorIndex, Interval interval){
			int[] lags = diff[precursorIndex];
			for (int i=0; i<lags.length; i++){
				if (interval.contains(lags[i]))
					return successors.get(i);
			}
			return null;
		}

		/**
		 * Checks if the values are applicable, given the length of the stream
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		public boolean isDeterminate(int precursorIndex, Interval interval){
			return isLengthDeterminate(precursorIndex, interval) &&
					isUnkDeterminate(precursorIndex, interval);
		}
		
		/**
		 * Returns true if there are no UNK tokens (of the successor's quantity type)
		 *  in the interval with respect to the precursorIndex
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		private boolean isUnkDeterminate(int precursorIndex, Interval interval){
			if (unknowns.isEmpty())
				return true;
			int preTime = precursors.get(precursorIndex).time;
			int low = preTime + interval.low;
			int high = preTime + interval.high;
			for (SimpleToken t : unknowns)
				if ( low <= t.getTime() && t.getTime() <= high)
					return false;
			return true;
		}
		
		/**
		 * Returns true if the intervl with respect to the precursorIndex is fully
		 *  contained in the token stream
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		private boolean isLengthDeterminate(int precursorIndex, Interval interval){
			int preTime = precursors.get(precursorIndex).time;
			return preTime + interval.high <= latestTime;
		}


//		public ArrayList<Token> getPrecursors() {
//			return precursors;
//		}
//
//		public ArrayList<Token> getSuccessors() {
//			return successors;
//		}
//
//		public int getStreamLen() {
//			return streamLen;
//		}
	}

	
}
