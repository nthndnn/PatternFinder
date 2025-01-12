package patternDetection;

public class EvaluationObject implements Comparable<EvaluationObject>{
	private Pattern p;
	private double accuracy;
	private double ubiquity;
	private double power;
	private MatchDataObject data;
	
	private EvaluationSettings settings;
	
	
	public EvaluationObject( MatchDataObject mdo ){
		this(mdo, EvaluationSettings.DEFAULT);
	}
	
	
	public EvaluationObject( MatchDataObject mdo, double alpha, double beta, int padding ){
		this(mdo, new EvaluationSettings(alpha, beta, padding));
	}
	
	
	public static EvaluationObject evaluate(SimpleTokenStream ts, Pattern p, EvaluationSettings settings){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo, settings);
	}
	
	public static EvaluationObject evaluate(SimpleTokenStream ts, Pattern p){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo);
	}
	
	
	public static double evaluatePower(SimpleTokenStream ts, Pattern p, EvaluationSettings settings){
		return evaluate(ts, p, settings).getPower();
	}
	
	public static double evaluatePower(SimpleTokenStream ts, Pattern p){
		return evaluate(ts, p).getPower();
	}
	
	
	public MatchDataObject getMdo(){
		return data;
	}
	
	
	
	public EvaluationObject( MatchDataObject mdo, EvaluationSettings settings){
		
		this.settings = settings;
		this.data = mdo;
		p = mdo.getPattern();

		calculatePower();
	}
	
	private void calculatePower(){
		boolean flag = false;
		if (data.getPrecursors().size() == 0){
			flag = true;
			this.accuracy = Double.NaN;
		}
		if (data.getSuccessors().size() == 0){
			this.ubiquity = 0;
			flag = true;
		}
		if (data.getMatches().size() == 0){
			flag = true;
			calculateUbiquity();
			if (data.getAntiMatches().size() == 0){
				this.accuracy = Double.NaN;
				this.power = -1;
			}else{
				//TODO allow usual accuracy?
				this.accuracy = 0;
				this.power = 0;
			}
			
		}
		if (!flag){
			calculateAccuracy();
			calculateUbiquity();
			this.power = Math.pow(accuracy, settings.getAlpha()) /
					Math.pow(ubiquity, settings.getBeta());
		}else{
			this.power = -1;
		}
	}
	
	/**
	 * Uses Agrest-Coull method
	 */
	private void calculateAccuracy(){
		int numMatches = data.getMatches().size();
		int numAntiMatches = data.getAntiMatches().size();
		
		this.accuracy = ((double)numMatches + settings.padding) / 
				(numMatches + numAntiMatches + 2*settings.padding);
	}
	
	
	
	/**
	 * Uses a binomial model to compute how likely it is that there 
	 *  would be at least one occurrence of the successor clause 
	 *  within the time window of the string
	 */
	private void calculateUbiquity(){		
		int streamLen = nonUnk();	//data.getTokenStream().length();// - data;
		int intervalWidth = this.p.time.getWidth() + 1;		//add 1 because [0,0] covers one unit
		double p = (double)data.getSuccessors().size() / streamLen;
		this.ubiquity = 1 - Math.pow( 1-p, intervalWidth);
	}
	
	public boolean powerIsValid(){
		return power != -1 && !Double.isNaN(power);
	}
	
	private int nonUnk(){
		SimpleTokenStream ts = data.getTokenStream();
		String quant = p.suc.quantID;
		int numUnk = ts.filter( new SimpleClause(quant, Behavior.UNK) ).size();
		return ts.length() - numUnk;
	}
	
	
	@Override
	public int compareTo(EvaluationObject arg0) {
		return Double.compare(this.power, arg0.power);
	}
	
	@Override
	public String toString(){
		return String.format("%s (%d, %d, %d, %d)(%f, %f, %f)", 
				p, getNumMatches(), getNumAntiMatches(), data.getPrecursors().size(),
				data.getSuccessors().size(), accuracy, ubiquity, power);
	}
	
	public Pattern getPattern() {
		return p;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public double getUbiquity() {
		return ubiquity;
	}
	
	public double getRarity(){
		if (ubiquity == 0)
			return Double.NaN;
		return 1/ubiquity;
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
	
	
	public static class EvaluationSettings{
		
		private double alpha;
		private double beta;
		private double padding;
		
		public static final double DEFAULT_ALPHA = 1;
		public static final double DEFAULT_BETA = 0.5;
		public static final double DEFAULT_PADDING = 4;
		
		//TODO: make final
		public static /*final*/ EvaluationSettings DEFAULT = new EvaluationSettings();
		
		
		public static EvaluationSettings getEvaluationSettings(){
			return DEFAULT;
		}
		
		public static EvaluationSettings getEvaluationSettings(double alpha,
				double beta, double padding){
			return new EvaluationSettings(alpha, beta, padding);
		}
		
		private EvaluationSettings(){
			this.alpha = DEFAULT_ALPHA;
			this.beta = DEFAULT_BETA;
			this.padding = DEFAULT_PADDING;
		}
		
		private EvaluationSettings(double alpha, double beta, double padding) {
			this.alpha = alpha;
			this.beta = beta;
			this.padding = padding;
		}
		
		public double getAlpha() {
			return alpha;
		}
		public double getBeta() {
			return beta;
		}
		public double getPadding() {
			return padding;
		}
		
	}
	
}
