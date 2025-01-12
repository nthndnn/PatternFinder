package behaviorClassification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Provides a class to store Chunks of data that have been classified
 * @author nathandunn
 *
 */
public class ClassifiedChunkList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ArrayList<ClassifiedChunk>> chunkList;
	private ArrayList<String> quantities;
	
	/**
	 * Creates an empty list. Quantities have to be added later
	 */
	public ClassifiedChunkList(){
		chunkList = new HashMap<String, ArrayList<ClassifiedChunk>>();
		quantities = new ArrayList<String>();
	}
	
	/**
	 * Adds a list of ClassifiedChunks of the indicated quantity
	 * @param quant
	 * @param list
	 */
	public void addList(String quant, ArrayList<ClassifiedChunk> list){
		chunkList.put(quant, list);
		quantities.add(quant);
	}

	/**
	 * @return an array of the names of the quantities this list's
	 *  chunks describe
	 */
	public String[] getQuantities(){
		return quantities.toArray( new String[quantities.size()] );
	}
	
	/**
	 * Returns a list of chunks that describe the specified quantity
	 * @param quant
	 * @return
	 */
	public ArrayList<ClassifiedChunk> getClassifiedChunks(String quant){
		return chunkList.get(quant);
	}

	public int getNumChunks() {
		int sum = 0;
		for (String s : quantities){
			sum += getClassifiedChunks(s).size();
		}
		return sum;
	}
	
	public String toString(){
		String out = "";
		for (String s : quantities){
			out += s + ":\n";
			out += this.getClassifiedChunks(s);
			out += "\n";
		}
		return out;
	}

}
