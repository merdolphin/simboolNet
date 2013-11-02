package simboolnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch {

	Set<Edge> edges;
	Set<Vertice> vertices;
	List<String> bfsVisitedNodes;

	private Queue<Vertice> qe;
	private Vertice Vstart;

	Map<String, Vertice> nodeVertices = new HashMap<String, Vertice>();

	public BreadthFirstSearch(Set<Vertice> vertices, Set<Edge> edges,
			Vertice Vstart) {
		this.edges = edges;
		this.vertices = vertices;
		this.Vstart = Vstart;
		bfs();
	}

	public void bfs() {

		for (Vertice v : vertices) {
			nodeVertices.put(v.getName(), v);
		}

		qe = new LinkedList<Vertice>();
		bfsVisitedNodes = new ArrayList<String>();

		bfsVisitedNodes.add(Vstart.getName());

		visitingMarkedQueue(Vstart);

		while (!qe.isEmpty()) {

			Vertice currentV = qe.remove();
			visitingMarkedQueue(currentV);
		}
	}

	public void visitingMarkedQueue(Vertice V) {

		for (String s : V.getAdjacentV()) {
			if (!bfsVisitedNodes.contains(s)) {
				qe.add(nodeVertices.get(s));
				bfsVisitedNodes.add(s);
			}
		}
	}

	public List<String> getBfsVisitedNodes() {
		return bfsVisitedNodes;
	}
}
