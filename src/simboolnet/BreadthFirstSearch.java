package simboolnet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch {

	
	Set<Edge> edges;
	Set<Vertice> vertices;
	
	private Queue<Vertice> qe;
	private Set<String> visitedNodes;
	private Vertice Vstart;
	
	Map<String, Vertice> nodeVertices = new HashMap<String, Vertice>();
	
	public BreadthFirstSearch(Set<Vertice>vertices, Set<Edge>edges, Vertice Vstart){
		this.edges = edges;
		this.vertices = vertices;
		this.Vstart = Vstart;
	}
	
	public void bfs(){
		
		for(Vertice v : vertices){
			nodeVertices.put(v.getName(), v);
		}
		
		qe = new LinkedList<Vertice>();
		visitedNodes = new HashSet<String>();
		
		visitedNodes.add(Vstart.getName());
		
		for(String s : Vstart.getAdjacentV()){
			qe.add(nodeVertices.get(s));
		}
		
		
		while( ! qe.isEmpty()){
			System.out.println(qe.size());
			qe.poll();
		}
		
	}

}
