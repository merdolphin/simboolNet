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
		
		ProcessingPrimaryData processing = new ProcessingPrimaryData();
		 
		edges = processing.getEdges();
		vertices = processing.getVertices();
		
		DepthFirstSearch search = new DepthFirstSearch(vertices, edges);
		search.dfs();
		
		Map <Vertice, Double> nodeSignal = new HashMap<Vertice, Double>();
		
		File receptorFile = new File("tmp/combined_receptors");
		FileReader fr = new FileReader(receptorFile);
		BufferedReader br = new BufferedReader(fr);
		
		
		
		
	}

}
