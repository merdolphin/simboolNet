package simboolnet;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulation {
	
	static Integer iterationTimes = 5;
	static Double nodeInitSignal = 0.0; 
	static Double receptorInitSignal = 0.66;
	
	static Double edgeInitWeight = 0.8;
	
	static Set <Edge> edges;
	static Set <Vertice> vertices;
	static Set <String> receptors;
	
	static Map<String, Vertice> nodeToVertice = new HashMap<String, Vertice>();
	static Map<String, Edge> nodeToEdge = new HashMap<String, Edge>();
	
	static Map<String, Double> nodeSignal = new HashMap<String, Double>();

	private static BufferedReader breader;
	
		
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
		breader = new BufferedReader(fr);
		String line;
		while( (line = breader.readLine()) != null){
			receptors.add(line);
		}
		
		
		/////////////initiation ////////////////////////
		
		for(Vertice v : vertices){		
			nodeToVertice.put(v.getName(), v);
			nodeSignal.put(v.getName(), nodeInitSignal);
		}
		
		
		for(String s : receptors){
			nodeSignal.put(s,receptorInitSignal);
		}
		
		
		for(Edge e : edges){
			nodeToEdge.put(e.getSource(), e);
			e.setWeight(edgeInitWeight);
		}
		
		for(Integer i=1; i<iterationTimes; i++){
				
			for(String startnode : receptors){
		
//			DepthFirstSearch search = new DepthFirstSearch(vertices, edges, nodeVertices.get(startnode));
//			search.dfs();		
				BreadthFirstSearch bfSearch = new BreadthFirstSearch(vertices, edges, nodeToVertice.get(startnode));
				List<String> bfsVisitedNodesList = new ArrayList<String>();
				bfsVisitedNodesList = bfSearch.getBfsVisitedNodes();
				for(String s : bfsVisitedNodesList){
					updateVerticeSignal(nodeToVertice.get(s));
				}
			}
		System.out.println("Iteration Times: " + i);
		for(Vertice v : vertices){
			System.out.println(v.getName() + "\t" + nodeSignal.get(v.getName()));
			}
		}
	}
		
	
	public static void updateVerticeSignal(Vertice v){
						
		Double AProduct = 1.0;
		Double BProduct = 1.0;
		if(v.getUpstreamV() != null){
			for(String upstreamNode : v.getUpstreamV()){
				if(nodeToEdge.get(upstreamNode).getAttr().equals("+") ){
					//System.out.println("upstreamNodeSignal: " + nodeSignal.get(upstreamNode) + "\t" +nodeToEdge.get(upstreamNode).getWeight());
					AProduct *= (1.0 - nodeSignal.get(upstreamNode) * nodeToEdge.get(upstreamNode).getWeight() );
					//System.out.println("AProduct: " + AProduct);
				}else{
					BProduct *= (1 - nodeSignal.get(upstreamNode) * nodeToEdge.get(upstreamNode).getWeight() );
				}
			}
		}
		//System.out.println(AProduct + "\t" + BProduct);
		nodeSignal.put( v.getName(), nodeSignal.get(v.getName()) + (1-AProduct) * BProduct * (1-nodeSignal.get(v.getName())));	
	}

}
