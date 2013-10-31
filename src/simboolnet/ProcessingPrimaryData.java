package simboolnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProcessingPrimaryData {
	
	private Set <Edge> edges = new HashSet<Edge>();
	private Set <Vertice> vertices = new HashSet<Vertice>();

	public ProcessingPrimaryData(String filename) throws IOException {
			
		File infile = new File(filename);
		FileReader fr = new FileReader(infile);
		BufferedReader br = new BufferedReader(fr);

		String line;
		
		while( (line = br.readLine()) != null){
			Edge edge = new Edge(null, null, 0, null);
			String [] temp = line.split(";");
			edge.setSource(temp[0]);
			edge.setTarget(temp[1]);
			edge.setAttr(temp[2]);
			edges.add(edge);
	
			Integer flage = 1;
			
			for(Vertice v : vertices ){
				if(v.getName().equals(temp[0])){
					flage = 0;
					if(! v.getAdjacentV().contains(temp[1])){
						v.getAdjacentV().add(temp[1]);
					}
					break;
				}		
			}
			if(flage == 1){
				Vertice node = new Vertice(null, null);
				node.setName(temp[0]);
				Set<String> tempv = new HashSet<String>();
				tempv.add(temp[1]);
				node.setAdjacentV(tempv);
				vertices.add(node);
				
				Vertice nodeT = new Vertice(null, null);
				nodeT.setName(temp[1]);
				Set<String> tempvT = new HashSet<String>();
				tempvT.add(temp[0]);
				nodeT.setAdjacentV(tempv);
				vertices.add(nodeT);
			}
			
		}
		fr.close();

	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public Set<Vertice> getVertices() {
		return vertices;
	}
}
