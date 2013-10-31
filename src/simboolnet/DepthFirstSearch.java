package simboolnet;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch {
	
	Set<Edge> edges;
	Set<Vertice> vertices;
	
	private Stack<Vertice> st;
	private Set<String> visitedNodes;
	
	public DepthFirstSearch(Set<Vertice> vertices, Set<Edge> edges){
		this.edges = edges;
		this.vertices = vertices;
		
	}
	
	public void dfs(){
		
		Vertice Vstart = vertices.iterator().next();
		
		st = new Stack<Vertice>();
		visitedNodes = new HashSet<String>();

		st.push(Vstart);
		visitedNodes.add(Vstart.getName());
		
		while( ! st.isEmpty()){
			Vertice currentV = st.peek();
			//System.out.println(currentV);
			
			Integer flage = 1;
		
			for(String adjacentnode : currentV.getAdjacentV()){
				
				if(! visitedNodes.contains(adjacentnode) ){
					flage = 0;
					for(Vertice v : vertices){
						if(v.getName().equals(adjacentnode)){
							st.push(v);
							visitedNodes.add(adjacentnode);
							System.out.println(visitedNodes);
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
