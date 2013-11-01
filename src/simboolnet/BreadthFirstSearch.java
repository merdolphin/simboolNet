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
		
		visitingMarkedQueue(Vstart);
		
		
		while( ! qe.isEmpty()){
			
			Vertice currentV = qe.remove();
			visitingMarkedQueue(currentV);
		}
				
	}
	
	public void visitingMarkedQueue (Vertice V){
		
		for(String s : V.getAdjacentV()){
			if( ! visitedNodes.contains(s) ){
				qe.add(nodeVertices.get(s));
				visitedNodes.add(s);
			}
		}
	}
	
}
