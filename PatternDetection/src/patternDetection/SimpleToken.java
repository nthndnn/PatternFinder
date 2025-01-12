package patternDetection;

import behaviorClassification.Chunk;

public class SimpleToken {
	public String getQuantIdentity() {
		return quantIdentity;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public int getTime() {
		return time;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public String quantIdentity;
	public Behavior behavior;
	public int time;
	
	public Chunk chunk;
	
	//TODO implement fully qualified model
	
	public SimpleToken(String quantIdentity, Behavior behavior, int time) {
		this.quantIdentity = quantIdentity;
		this.behavior = behavior;
		this.time = time;
	}
	
	//TODO remove this:
	public SimpleToken(String quantIdentity, Behavior behavior, int time, Chunk chunk) {
		this.quantIdentity = quantIdentity;
		this.behavior = behavior;
		this.time = time;
		this.chunk = chunk;
	}
	
	public String toString(){
		return "("+quantIdentity + behavior.toChar() + ", t="+time+")";
	}
	
}
