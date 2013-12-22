package simboolnet;

import java.util.Map;
import java.util.Set;

public class BeingInfluencedNode {

	String nodeName;
	
	Set<Map<String,Double>> mutationGeneChangesRatio; // mutation <node,loss/gain>

	public BeingInfluencedNode(String nodeName,
			Set<Map<String, Double>> mutationGeneChangesRatio) {
		super();
		this.nodeName = nodeName;
		this.mutationGeneChangesRatio = mutationGeneChangesRatio;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Set<Map<String, Double>> getMutationGeneChangesRatio() {
		return mutationGeneChangesRatio;
	}

	public void setMutationGeneChangesRatio(
			Set<Map<String, Double>> mutationGeneChangesRatio) {
		this.mutationGeneChangesRatio = mutationGeneChangesRatio;
	}
	
	
	
}
