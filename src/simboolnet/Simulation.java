package simboolnet;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Simulation {
	
	Integer interationTimes = 100;
	
	static Set <Edge> edges;
	static Set <Vertice> vertices;
	static Set <String> receptors;
	
	static Double nodeInitSignal = 0.0; 
	Double edgeInitWeight = 0.8;
	
	static Map<String, Vertice> nodeVertices = new HashMap<String, Vertice>();
	
	static Map<Vertice, Double> nodeSignal = new HashMap<Vertice, Double>();
	
		
	public static void main(String[] args) throws Exception {
	
		
		/// get the receptors, vertices and edges
		edges = new HashSet<Edge>();
		vertices = new HashSet<Vertice>();
		receptors = new HashSet<String>();
		
		ProcessingPrimaryData processing = new ProcessingPrimaryData("tmp/data_sample");
	
		edges = processing.getEdges();
		vertices = processing.getVertices();
		
		//File receptorFile = new File("tmp/combined_receptors");
		File receptorFile = new File("tmp/receptor_sample");
		FileReader fr = new FileReader(receptorFile);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while( (line = br.readLine()) != null){
			receptors.add(line);
		}
		
		
		/// initiation 
		
		for(Vertice v : vertices){		
			nodeVertices.put(v.getName(), v);
			nodeSignal.put(v, nodeInitSignal);
		}
		
		
		for(String startnode : receptors){
		
			DepthFirstSearch search = new DepthFirstSearch(vertices, edges, nodeVertices.get(startnode));
			search.dfs();
		
			BreadthFirstSearch bfSearch = new BreadthFirstSearch(vertices, edges, nodeVertices.get(startnode));
			bfSearch.bfs();
		}
		
	}	
	
	public Double updateVertice(Vertice v){
		return nodeSignal.get(v) ;
	}

}
