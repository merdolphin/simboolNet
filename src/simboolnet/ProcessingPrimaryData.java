package simboolnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProcessingPrimaryData {

	private static Set<Edge> edges = new HashSet<Edge>();
	private static Set<Vertice> vertices = new HashSet<Vertice>();
	static Set<String> receptors = new HashSet<String>();
	static Double virtualEdgeWeight = 1.0;
	
	public ProcessingPrimaryData(String filename) throws IOException {

		File infile = new File(filename);
		FileReader fr = new FileReader(infile);
		BufferedReader br = new BufferedReader(fr);

		String line;

		while ((line = br.readLine()) != null) {
			Edge edge = new Edge(null, null, 0.0, null);
			String[] temp = line.split(";");
	
			edge.setSource(temp[0]);
			edge.setTarget(temp[1]);
			edge.setAttr(temp[2]);
			
			edges.add(edge);
		}

		for (Edge e : edges) {
			Vertice vs = new Vertice(null, null, null, null);
			vs.setName(e.getSource());
			Set<String> tempDSV = new HashSet<String>();
			Set<String> tempADV = new HashSet<String>();
			tempDSV.add(e.getTarget());
			tempADV.add(e.getTarget());
			vs.setDownstreamV(tempDSV);
			vs.setAdjacentV(tempADV);
			vertices.add(vs);

			Vertice vt = new Vertice(null, null, null, null);
			vt.setName(e.getTarget());
			Set<String> tempUSV = new HashSet<String>();
			Set<String> tempADVt = new HashSet<String>();
			tempUSV.add(e.getSource());
			tempADVt.add(e.getSource());
			vt.setUpstreamV(tempUSV);
			vt.setAdjacentV(tempADVt);
			vertices.add(vt);
		}
		fr.close();
		
		String recepterFileName = "tmp/combined_receptors";
		File receptorFile = new File(recepterFileName);
		FileReader frr = new FileReader(receptorFile);
		BufferedReader breader = new BufferedReader(frr);
		String receptorline;
		while ((receptorline = breader.readLine()) != null) {
			receptors.add(receptorline);
		}
		frr.close();
		
		add_virtual_node();

		mergeV(vertices);
	}


	public static void add_virtual_node (){
		
		Vertice virtualV = new Vertice(null, null, null, null);
		virtualV.setName("virtualNode");
		Set<String> adjacentVirtualV = new HashSet<String>();
		Set<String> downstreamVirtualV = new HashSet<String>();
		for(String receptor : receptors){
			Edge tempEdge = new Edge("virtualNode", receptor, virtualEdgeWeight, "+");
			adjacentVirtualV.add(receptor);
			downstreamVirtualV.add(receptor);
			edges.add(tempEdge);
		}
		virtualV.setAdjacentV(adjacentVirtualV);
		virtualV.setDownstreamV(downstreamVirtualV);
		vertices.add(virtualV);		
	};
	
	public void mergeV(Set<Vertice> v) {
		Set<String> nodes = new HashSet<String>();
		Set<Vertice> removeV = new HashSet<Vertice>();

		for (Vertice v1 : v) {
			if (!nodes.contains(v1.getName())) {
				for (Vertice v2 : v) {
					if (v1.getName().equals(v2.getName()) && (!v1.equals(v2))) {
						for (String s21 : v2.getAdjacentV())
							v1.getAdjacentV().add(s21);

						if (v2.getDownstreamV() != null) {
							if (v1.getDownstreamV() != null) {
								for (String s22 : v2.getDownstreamV())
									v1.getDownstreamV().add(s22);
							} else {
								Set<String> v2downV = new HashSet<String>();
								for (String s23 : v2.getDownstreamV()) {
									v2downV.add(s23);
								}
								v1.setDownstreamV(v2downV);
							}
						}

						if (v2.getUpstreamV() != null)
							if (v1.getUpstreamV() != null) {
								for (String s23 : v2.getUpstreamV())
									v1.getUpstreamV().add(s23);
							} else {
								Set<String> v2upV = new HashSet<String>();
								for (String sv2 : v2.getUpstreamV()) {
									v2upV.add(sv2);
								}
								v1.setUpstreamV(v2upV);
							}

						nodes.add(v1.getName());
						removeV.add(v2);
					}
				}
			}
		}

		for (Vertice v1 : removeV) {
			vertices.remove(v1);
		}

	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public Set<Vertice> getVertices() {
		return vertices;
	}
	public Set<String> getReceptors() {
		return receptors;
	}

}
