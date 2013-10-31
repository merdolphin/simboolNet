package simboolnet;

import java.util.Set;

public class Vertice {
	String name;
	Set <String> adjacentV;

	public Vertice(String name, Set<String> adjacentV){
		this.name = name;
		this.adjacentV = adjacentV;
	}
	
	public Set<String> getAdjacentV() {
		return adjacentV;
	}

	public void setAdjacentV(Set<String> adjacentV) {
		this.adjacentV = adjacentV;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Vertice [name=" + name + ", adjacentV=" + adjacentV + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adjacentV == null) ? 0 : adjacentV.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
