package clausesAndTokens;

import java.util.ArrayList;

/**
 * Used for both tokens and clauses
 * @author nathandunn
 *
 */
public class FeatureType {

	private final String name;
	
	private final ArrayList<FeatureType> children;
	
	
	private FeatureType(String name, ArrayList<FeatureType> children){
		this.name = name;
		this.children = children;
	}

	


	//TODO toString()?
	public String string(){
		return name;
	}
	
	public boolean equals(FeatureType type){
		return name.equals(type.string());
	}
	
}
