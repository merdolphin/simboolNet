package simboolnet;

import java.util.Set;

public class Vertice {
	String name;
	Set <String> adjacentV;
	Set <String> downstreamV;
	Set <String> upstreamV;

	public Vertice(String name, Set<String> adjacentV, Set<String> downstreamV, Set<String> upstreamV){
		this.name = name;
		this.adjacentV = adjacentV;
		this.downstreamV = downstreamV;
		this.upstreamV = upstreamV;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getAdjacentV() {
		return adjacentV;
	}

	public void setAdjacentV(Set<String> adjacentV) {
		this.adjacentV = adjacentV;
	}

	public Set<String> getDownstreamV() {
		return downstreamV;
	}

	public void setDownstreamV(Set<String> downstreamV) {
		this.downstreamV = downstreamV;
	}

	public Set<String> getUpstreamV() {
		return upstreamV;
	}

	public void setUpstreamV(Set<String> upstreamV) {
		this.upstreamV = upstreamV;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adjacentV == null) ? 0 : adjacentV.hashCode());
		result = prime * result
				+ ((downstreamV == null) ? 0 : downstreamV.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((upstreamV == null) ? 0 : upstreamV.hashCode());
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
		Vertice other = (Vertice) obj;
		if (adjacentV == null) {
			if (other.adjacentV != null)
				return false;
		} else if (!adjacentV.equals(other.adjacentV))
			return false;
		if (downstreamV == null) {
			if (other.downstreamV != null)
				return false;
		} else if (!downstreamV.equals(other.downstreamV))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (upstreamV == null) {
			if (other.upstreamV != null)
				return false;
		} else if (!upstreamV.equals(other.upstreamV))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return   name + "\t" + adjacentV
				+ "\t" + downstreamV + "\t" + upstreamV;
	}


	

}
