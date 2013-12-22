package simboolnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimBoolNetMain extends Thread {

	static String dataFileName = "tmp/combined_after_silencing.txt";
	static String mutationFileName = "tmp/combined_cnv_tmp";

	static Integer iterationTimes = 1000;
	static Double nodeInitSignal = 0.0;
	static Double receptorInitSignal = 0.66;
	static Double edgeInitWeight = 0.8;

	static Double CNVlossInit = 0.0;
	static Double CNVgainInit = 0.0;
	static Double CNVlossEdgeWeight = 0.4;
	static Double CNVgainEdgeWeight = 1.0;

	static Set<Edge> edges;
	static Set<Vertice> vertices;
	static Set<String> receptors;
	static Map<String, String> mutatedGenes;

	static Map<String, Vertice> nodeToVertice = new HashMap<String, Vertice>();
	static Map<String, Edge> nodeToEdge = new HashMap<String, Edge>();

	static Map<String, Double> nodeSignal = new HashMap<String, Double>();

	static Map<String, Map<Integer, Map<String, Double>>> mutatedEvolve = new HashMap<String, Map<Integer, Map<String, Double>>>();
	static Map<Integer, Map<String, Double>> normalEvolve = new HashMap<Integer, Map<String, Double>>();
	static Map<String, Map<String, Double>> mutationCausedSignalChanges = new HashMap<String, Map<String, Double>>();

	static Set<BeingInfluencedNode> beingInfluencedNodesSet = new HashSet<BeingInfluencedNode>();
	
	public static void main(String[] args) throws Exception {

		edges = new HashSet<Edge>();
		vertices = new HashSet<Vertice>();
		receptors = new HashSet<String>();
		mutatedGenes = new HashMap<String, String>();

		ProcessingPrimaryData processing = new ProcessingPrimaryData(
				dataFileName);

		edges = processing.getEdges();
		vertices = processing.getVertices();
		receptors = processing.getReceptors();

		for (Vertice v : vertices)
			nodeToVertice.put(v.getName(), v);

		for (Edge e : edges)
			nodeToEdge.put(e.getSource(), e);

		for (Edge e : edges)
			if (nodeToEdge.get(e.getTarget()) == null)
				nodeToEdge.put(e.getTarget(), e);

		File mutationFile = new File(mutationFileName);
		FileReader mutationfr = new FileReader(mutationFile);
		BufferedReader mutationbr = new BufferedReader(mutationfr);
		String mutationline;
		while ((mutationline = mutationbr.readLine()) != null) {
			String[] temps = mutationline.split(";");
			mutatedGenes.put(temps[0], temps[1]);
		}
		mutationfr.close();

		for (String mNode : mutatedGenes.keySet()) {
			forEachMutatedRunAndWrite(mNode);
		}

		runNormalSimulation();

		compareNormalandMutatedFinalResultAndWriteToFile();
	}

	public static void compareNormalandMutatedFinalResultAndWriteToFile()
			throws IOException {
		Integer j = iterationTimes - 1;

		String outputChangesFileName = "tmp/random_mutation/changes.txt";
		File output = new File(outputChangesFileName);
		FileWriter wr;
		wr = new FileWriter(output);
		BufferedWriter br = new BufferedWriter(wr);

		for (String mutatedNode : mutatedGenes.keySet()) {

			System.out.println("mutation on: " + mutatedNode);
			br.write("mutation on: " + mutatedNode + "\n");

			Map<String, Double> nodeSingnalChanges = new HashMap<String, Double>();
			for (String node : mutatedEvolve.get(mutatedNode).get(j).keySet()) {
				
				Double changes = mutatedEvolve.get(mutatedNode).get(j)
						.get(node)
						- normalEvolve.get(j).get(node);
				
				nodeSingnalChanges.put(node, changes);

				if (changes != 0.0 && normalEvolve.get(j).get(node) * 100 != 0) {
					
					Map<String, String> tempMap = new HashMap<String, String>();
					Double changeRatio = changes/normalEvolve.get(j).get(node)*100;
					
					if( ! beingInfluencedNodesSet.contains(node)){
						Map<String,Double> mutatedGeneCausedChangeRatio = new HashMap<String, Double>();
						mutatedGeneCausedChangeRatio.put(mutatedNode, changeRatio);
						Set<Map<String,Double>> mutatedSetTemp = new HashSet<Map<String, Double>>();
						mutatedSetTemp.add(mutatedGeneCausedChangeRatio);
						BeingInfluencedNode influencedNode = new BeingInfluencedNode(node,mutatedSetTemp);
						beingInfluencedNodesSet.add(influencedNode);
					} else{
						
					}

					System.out.println(node + "\t"
							+ mutatedEvolve.get(mutatedNode).get(j).get(node)
							+ "\t" + normalEvolve.get(j).get(node) + "\t"
							+ +changes + "\t" + changes
							/ normalEvolve.get(j).get(node) * 100);
					br.write(node + "\t"
							+ mutatedEvolve.get(mutatedNode).get(j).get(node)
							+ "\t" + normalEvolve.get(j).get(node) + "\t"
							+ changes + "\t" + changes
							/ normalEvolve.get(j).get(node) * 100 + "\n");
				}
			}
			mutationCausedSignalChanges.put(mutatedNode, nodeSingnalChanges);
		}
		br.close();
	}

	public static void runNormalSimulation() throws Exception {
		initiateNodeStat();

		for (Integer i = 1; i < iterationTimes; i++) {
			run_simulation();
			Map<String, Double> tempNodeSignal = new HashMap<String, Double>();

			for (Vertice v : vertices) {
				tempNodeSignal.put(v.getName(), nodeSignal.get(v.getName()));
			}

			normalEvolve.put(i, tempNodeSignal);
			writeOutputFile(null, i);
		}
	}

	public static void forEachMutatedRunAndWrite(String mNode) throws Exception {

		initiateNodeStat();

		set_mutated_edge_weight(mNode);

		Map<Integer, Map<String, Double>> eachIterationNodeSignal = new HashMap<Integer, Map<String, Double>>();

		for (Integer i = 1; i < iterationTimes; i++) {

			run_simulation();

			Map<String, Double> tempNodeSignal = new HashMap<String, Double>();

			for (Vertice v : vertices) {
				tempNodeSignal.put(v.getName(), nodeSignal.get(v.getName()));
			}

			eachIterationNodeSignal.put(i, tempNodeSignal);

			if (i == 999) {
				writeOutputFile(mNode, i);
			}

		}
		mutatedEvolve.put(mNode, eachIterationNodeSignal);

	}

	public static void initiateNodeStat() {

		nodeSignal.clear();

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
					BProduct *= (1.0 - nodeSignal.get(upstreamNode)
							* nodeToEdge.get(upstreamNode).getWeight());
				}
			}
		}

		Double changes = (1.0 - AProduct) * BProduct
				* (1.0 - nodeSignal.get(v.getName()));

		nodeSignal.put(v.getName(), nodeSignal.get(v.getName()) + changes);
	}

	public static void set_mutated_edge_weight(String mNode) {

		if (nodeToEdge.get(mNode) != null) {

			if (mutatedGenes.get(mNode).equals("+")) {
				nodeToEdge.get(mNode).setWeight(CNVgainEdgeWeight);
			}

			if (mutatedGenes.get(mNode).equals("-")) {
				nodeToEdge.get(mNode).setWeight(CNVlossEdgeWeight);
			}
		} else {
			System.out.println("cannot find the edge of nodename: " + mNode);
		}
	}

	public static void run_simulation() {

		String startnode = "virtualNode";

		if (nodeToVertice.get(startnode) != null) {
			BreadthFirstSearch bfSearch = new BreadthFirstSearch(vertices,
					edges, nodeToVertice.get(startnode));
			List<String> bfsVisitedNodesList = new ArrayList<String>();
			bfsVisitedNodesList = bfSearch.getBfsVisitedNodes();

			for (String s : bfsVisitedNodesList)
				updateVerticeSignal(nodeToVertice.get(s));
		} else {
			System.out.println("cannot find receptor " + startnode
					+ " in the input data.");
		}
	}

	public static void writeOutputFile(String mNode, Integer i)
			throws Exception {

		String outputFileName = "tmp/random_mutation/output_normal" + "_" + i
				+ ".txt";

		if (mNode != null) {
			outputFileName = "tmp/random_mutation/output" + mNode + "_"
					+ nodeToEdge.get(mNode).getWeight() + "_" + i + ".txt";
		}

		File output = new File(outputFileName);
		FileWriter wr;

		wr = new FileWriter(output);

		BufferedWriter br = new BufferedWriter(wr);

		br.write("Mutation on: " + mNode + "\n");

		// System.out.println("Iteration Times: " + i);
		br.write("Iteration Times: " + i + "\n");

		for (Vertice v : vertices) {
			br.write(v.getName() + "\t" + nodeSignal.get(v.getName()) + "\n");
		}
		br.close();
	}
}