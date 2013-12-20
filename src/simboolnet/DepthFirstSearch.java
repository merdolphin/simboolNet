package simboolnet;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch {
	
	Set<Edge> edges;
	Set<Vertice> vertices;
	
	private Stack<Vertice> st;
	private Set<String> visitedNodes;
	private Vertice Vstart;
	
	public DepthFirstSearch(Set<Vertice> vertices, Set<Edge> edges, Vertice Vstart){
		this.edges = edges;
		this.vertices = vertices;
		this.Vstart = Vstart;
		
	}
	
	public void dfs(){
		
		st = new Stack<Vertice>();
		visitedNodes = new HashSet<String>();

		st.push(Vstart);
		visitedNodes.add(Vstart.getName());
		
		while( ! st.isEmpty()){
			Vertice currentV = st.peek();
			
			Integer flage = 1;
		
			for(String adjacentnode : currentV.getAdjacentV()){
				
				if(! visitedNodes.contains(adjacentnode) ){
					flage = 0;
					for(Vertice v : vertices){
						if(v.getName().equals(adjacentnode)){
							st.push(v);
							visitedNodes.add(adjacentnode);
							break;
						}
					}
				}
			}
				
			
			if(flage == 1){
				st.pop();
			}
			
		}
		
		System.out.println(visitedNodes);
	}

}
