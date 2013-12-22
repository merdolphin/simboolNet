package simboolnet;

import java.util.HashMap;
import java.util.Map;

public class MutatedEvolutionNodesSignal {
	
	String mutatedGene;
	Integer iterationTime;	
	Map<String, Double> nodeSignal = new HashMap<String, Double>();

	public MutatedEvolutionNodesSignal(String mutatedGene,
			Integer iterationTime, Map<String, Double> nodeSignal) {
		super();
		this.mutatedGene = mutatedGene;
		this.iterationTime = iterationTime;
		this.nodeSignal = nodeSignal;
	}

	public String getMutatedGene() {
		return mutatedGene;
	}

	public void setMutatedGene(String mutatedGene) {
		this.mutatedGene = mutatedGene;
	}

	public Integer getIterationTime() {
		return iterationTime;
	}

	public void setIterationTime(Integer iterationTime) {
		this.iterationTime = iterationTime;
	}

	public Map<String, Double> getNodeSignal() {
		return nodeSignal;
	}

	public void setNodeSignal(Map<String, Double> nodeSignal) {
		this.nodeSignal = nodeSignal;
	}
	
	
	
}
