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

	static Double CNVlossInit = 0.0;
	static Double CNVgainInit = 0.0;
	static Double CNVlossEdgeWeight = 0.4;
	static Double CNVgainEdgeWeight = 1.0;

	static Double edgeInitWeight = 0.8;

	static Set<Edge> edges;
	static Set<Vertice> vertices;
	static Set<String> receptors;
	static Map<String, String> mutatedGenes;

	static Map<String, Vertice> nodeToVertice = new HashMap<String, Vertice>();
	static Map<String, Edge> nodeToEdge = new HashMap<String, Edge>();

	static Map<String, Double> nodeSignal = new HashMap<String, Double>();

	private static BufferedReader breader;
	private static BufferedReader mutationbr;

	public static void main(String[] args) throws Exception {

		// / get the receptors, vertices and edges
		edges = new HashSet<Edge>();
		vertices = new HashSet<Vertice>();
		receptors = new HashSet<String>();
		mutatedGenes = new HashMap<String, String>();

		String dataFileName = "tmp/data_sample";
		String recepterFileName = "tmp/receptor_sample";
		String mutationFileName = "tmp/mutation_sample";

		// String dataFileName = "tmp/combined.txt";
		// String recepterFileName = "tmp/combined_receptors";

		ProcessingPrimaryData processing = new ProcessingPrimaryData(
				dataFileName);

		edges = processing.getEdges();
		vertices = processing.getVertices();

		for (Vertice v : vertices)
			nodeToVertice.put(v.getName(), v);

		for (Edge e : edges)
			nodeToEdge.put(e.getSource(), e);

		File receptorFile = new File(recepterFileName);
		FileReader fr = new FileReader(receptorFile);
		breader = new BufferedReader(fr);
		String receptorline;
		while ((receptorline = breader.readLine()) != null) {
			receptors.add(receptorline);
		}
		fr.close();

		File mutationFile = new File(mutationFileName);
		FileReader mutationfr = new FileReader(mutationFile);
		mutationbr = new BufferedReader(mutationfr);
		String mutationline;
		while ((mutationline = mutationbr.readLine()) != null) {
			String[] temps = mutationline.split(";");
			mutatedGenes.put(temps[0], temps[1]);
		}
		mutationfr.close();
		
		// run simulation for each single mutation

		for (String mNode : mutatedGenes.keySet()) {

			System.out.println("Mutation on: " + mNode);

			for (Integer i = 1; i < iterationTimes; i++) {

				initiateNodeStat();

				if (mutatedGenes.get(mNode).equals("+")) {
					nodeToEdge.get(mNode).setWeight(CNVgainEdgeWeight);
				} else {
					nodeToEdge.get(mNode).setWeight(CNVlossEdgeWeight);
				}
				run_simulation();

				System.out.println("Iteration Times: " + i);
				for (Vertice v : vertices) {
					System.out.println(v.getName() + "\t"
							+ nodeSignal.get(v.getName()));
				}
			}
		}
	}


	public static void initiateNodeStat() {

		for (Vertice v : vertices)
			nodeSignal.put(v.getName(), nodeInitSignal);

		for (String s : receptors)
			nodeSignal.put(s, receptorInitSignal);

		for (Edge e : edges)
			e.setWeight(edgeInitWeight);
	}

	public static void updateVerticeSignal(Vertice v) {

		Double AProduct = 1.0;
		Double BProduct = 1.0;

		if (v.getUpstreamV() != null) {
			for (String upstreamNode : v.getUpstreamV()) {
				if (nodeToEdge.get(upstreamNode).getAttr().equals("+")) {
					AProduct *= (1.0 - nodeSignal.get(upstreamNode)
							* nodeToEdge.get(upstreamNode).getWeight());
				} else {
					BProduct *= (1 - nodeSignal.get(upstreamNode)
							* nodeToEdge.get(upstreamNode).getWeight());
				}
			}
		}
	
		nodeSignal
				.put(v.getName(), nodeSignal.get(v.getName()) + (1 - AProduct)
						* BProduct * (1 - nodeSignal.get(v.getName())));
	}

	public static void run_simulation() {

		for (String startnode : receptors) {

			// DepthFirstSearch search = new DepthFirstSearch(vertices,
			// edges, nodeVertices.get(startnode));
			// search.dfs();
			if (nodeToVertice.get(startnode) != null) {
				BreadthFirstSearch bfSearch = new BreadthFirstSearch(vertices,
						edges, nodeToVertice.get(startnode));
				List<String> bfsVisitedNodesList = new ArrayList<String>();
				bfsVisitedNodesList = bfSearch.getBfsVisitedNodes();
				for (String s : bfsVisitedNodesList)
					updateVerticeSignal(nodeToVertice.get(s));
			} else {
				System.out.println("can't translate the receptor " + startnode
						+ " to the Vertice.");
			}
		}

	}

}
