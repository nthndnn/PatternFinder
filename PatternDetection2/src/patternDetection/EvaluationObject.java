package patternDetection;

public class EvaluationObject implements Comparable<EvaluationObject>{
	private Pattern p;
	private double accuracy;
	private double rarity;
	private double power;
	private MatchDataObject data;
	
	public EvaluationObject( MatchDataObject mdo ){
		final double alpha = 2;
		final double beta = 0.25;
		
		this.data = mdo;
		p = mdo.getPattern();
		int numMatches = mdo.getMatches().size();
		int numAntiMatches = mdo.getAntiMatches().size();
		
		//Uses Agresti-Caull method
		this.accuracy = ((double)numMatches + 2) / (numMatches + numAntiMatches + 4);
		
		this.rarity = mdo.getLags().calculateRarity(p.time.getWidth());
		if (this.rarity == 0.0)
			this.power = -1;
		else
			this.power = Math.pow(accuracy, alpha) / Math.pow(rarity, beta);
	}
	
	
	@Override
	public int compareTo(EvaluationObject arg0) {
		return Double.compare(this.power, arg0.power);
	}
	
	@Override
	public String toString(){
		return String.format("%s (%d, %d, %d, %d)(%f, %f, %f)", 
				p, getNumMatches(), getNumAntiMatches(), data.getPrecursors().size(),
				data.getSuccessors().size(), accuracy, rarity, power);
	}
	
	public Pattern getPattern() {
		return p;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public double getRarity() {
		return rarity;
	}
	public double getPower() {
		return power;
	}
	
	public int getNumMatches(){
		return data.getMatches().size();
	}
	
	public int getNumAntiMatches(){
		return data.getAntiMatches().size();
	}
}