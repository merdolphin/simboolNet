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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeingInfluencedNode other = (BeingInfluencedNode) obj;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nodeName + "\t"
				+ mutationGeneChangesRatio;
	}
	
	
	
}
