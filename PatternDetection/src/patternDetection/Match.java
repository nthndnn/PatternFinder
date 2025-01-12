package patternDetection;

public class Match {
	public SimpleToken getPrecursor() {
		return precursor;
	}

	public SimpleToken getSuccessor() {
		return successor;
	}

	public MatchType getType() {
		return type;
	}

	public SimpleToken precursor;
	public SimpleToken successor;
	public MatchType type;
	
	
	public Match(SimpleToken precursor, SimpleToken successor, MatchType type) {
		super();
		this.precursor = precursor;
		this.successor = successor;
		this.type = type;
	}
	
	public static Match createMatch(SimpleToken pre, SimpleToken suc){
		return new Match(pre, suc, MatchType.MATCH);
	}
	
	public static Match createAntiMatch(SimpleToken pre){
		return new Match(pre, null, MatchType.ANTI);
	}
	
	public static Match createIndMatch(SimpleToken pre){
		return new Match(pre, null, MatchType.INDET);
	}

	public String toString(){
		return type + " : " +precursor + " " + successor;
	}
	
}
