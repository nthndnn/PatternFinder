package patternDetection;

import behaviorClassification.ChunkList;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;

import java.util.ArrayList;

import behaviorClassification.Chunk;

public class Tokenizer {
	
	private ModelClassifier model;

	public Tokenizer(ModelClassifier model) {
		this.model = model;
	}
	
	
	private ArrayList<Token> tokenize(ArrayList<Chunk> chunks, String quant){
		ArrayList<Token> out = new ArrayList<Token>();
		for (int i=0; i<chunks.size(); i++){
			Chunk chunk = chunks.get(i);
			Behavior b = model.classifyChunkBehavior(chunk);
			Token t = new Token(quant, b, i);
			out.add(t);
		}
		return out;
	}
	
	public TokenStream tokenize(ChunkList chunkList){
		TokenStream out = new TokenStream();
		for (String quant : chunkList.getQuantities())
			out.add(quant, tokenize(chunkList.getChunks(quant), quant));
		return out;
	}
	
//	TODO at some point will have to specify chunkWidth or numChunks
	
	public TokenStream tokenize(RawTimeSeriesTable table, double chunkWidth){
		ChunkList cl = new ChunkList(table, chunkWidth);
		return tokenize(cl);
	}
	
	public TokenStream tokenize(RawTimeSeriesTable table, int numChunks){
		double chunkWidth = table.timeSpan()/numChunks;
		return tokenize(table, chunkWidth);
	}
	
	
//	
//	/**
//	 * Tokenizes the contents of the specified csv file
//	 * @param fileName
//	 * @return
//	 */
//	public TokenStream tokenize(String fileName){
//		
//	}

}