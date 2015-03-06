package behaviorClassification;


public class FeatureTable extends DataTable {
	
	public FeatureTable(double[][] entries, String[] headers){
		super(entries, headers);
	}

	public FeatureTable(double[][] entries) {
		super(entries);
	}

	//Returns a vector of means for each data column
	public double[] getMeanVec(){
		double[] out = new double[super.getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.mean(entries[i]);
		return out;
	}
	
	//Returns a vector of standard deviations for each data column
	public double[] getStdVec(){
		double[] out = new double[getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.std(entries[i]);
		return out;
	}
	
	//Creates a version of this table where each column is normalized
	public FeatureTable getScaledTable(){
		double[][] newEntries = new double[getNumCols()][getNumRows()];
		for (int i=0; i<getNumCols(); i++)
			newEntries[i] = MyMath.scale(this.entries[i]);
		
		return new FeatureTable(newEntries, this.getHeaders());
	}
	

	
}
