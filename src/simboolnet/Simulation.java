package simboolnet;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Simulation {
	

	

	public static void main(String[] args) throws Exception {
		
		Set <Edge> edges = new HashSet<Edge>();
		Set <Vertice> vertices = new HashSet<Vertice>();
		Set <String> receptors = new HashSet<String>();
		String startnode = "I";
		Vertice Vstart = new Vertice(null, null);
		Integer interationTimes = 100;
		
		ProcessingPrimaryData processing = new ProcessingPrimaryData("tmp/data_sample");
		 
		edges = processing.getEdges();
		vertices = processing.getVertices();
		
		for(Vertice v : vertices){
			if(v.getName().equals(startnode)){
				Vstart = v;
				break;
			}
		}
		
		DepthFirstSearch search = new DepthFirstSearch(vertices, edges, Vstart);
		search.dfs();
		
		BreadthFirstSearch bfSearch = new BreadthFirstSearch(vertices, edges, Vstart);
		bfSearch.bfs();
		
		Map <Vertice, Double> nodeSignal = new HashMap<Vertice, Double>();
		
		File receptorFile = new File("tmp/combined_receptors");
		FileReader fr = new FileReader(receptorFile);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while( (line = br.readLine()) != null){
			receptors.add(line);
		}
		
		
	}

}
