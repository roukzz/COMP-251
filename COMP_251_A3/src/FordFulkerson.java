import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        // initialize ArrayLists
        ArrayList<Integer> Stack = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();

        // add source at the begging of the stack
        visited.add(source);
        path.add(source);

        // Using a stack, run the algorithm while there are still valid paths
        while (!path.isEmpty()) {

            Integer a = path.get(path.size() - 1);
            path.remove(path.size() - 1);// remove last element in the path list
            visited.add(a);// add the last element in the path list to the visited list

            // when no path found, Get the last element of Stack, and get the edge, remove
            // top if edge is null or weight is 0
            while (!Stack.isEmpty()) {
                Integer topElement = Stack.get(Stack.size() - 1);
                Edge edge = graph.getEdge(topElement, a);
                if (edge == null || edge.weight == 0) {
                    Stack.remove(Stack.size() - 1);
                } else {
                    break;
                }
            }

            // add a to Stack list
            Stack.add(a);

            // get all edges
            for (Edge edge : graph.getEdges()) {

                if ((edge.nodes[0] == a && edge.weight > 0) && (!visited.contains(edge.nodes[1]))) {
                    // If the end node is destination, clear the path so we can break
                    if (edge.nodes[1] == destination) {
                        Stack.add(destination);
                        path.clear();
                    } else {
                        path.add(edge.nodes[1]);
                    }
                }
            }
        }

        return Stack;
    }
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
	    String answer="";
        String myMcGillID = "260806502"; //Please initialize this variable with your McGill ID
        int maxFlow = 0;
        
        // Create a residual and capacity graph with initial values
        WGraph residualGraph = new WGraph(graph);
        WGraph capacityGraph = new WGraph(graph);
        
        // Set all graph's edge weights to 0 
        for(Edge edge: graph.getEdges()){
            graph.setEdge(edge.nodes[0], edge.nodes[1], 0);
        }
        
        // If source and destination are both in pathDFS, then it is successful
   while(pathDFS(source, destination, residualGraph).contains(source) && pathDFS(source, residualGraph.getDestination(), residualGraph).contains(destination)){    
      ArrayList<Integer> dfs = pathDFS(source, destination, residualGraph);
      int min_value = Integer.MAX_VALUE;
            
            
     // Find the bottleneck flow
      for(int i = 0; i < dfs.size()-1; i++){
           Edge edge = residualGraph.getEdge(dfs.get(i), dfs.get(i+1));
             if(edge != null && edge.weight < min_value){
                min_value = edge.weight;
                }
            }

            // update max flow
            for(int i = 0; i < dfs.size() - 1; i++){
                Integer node1 = dfs.get(i);
                Integer node2 = dfs.get(i + 1);
                Edge edge = graph.getEdge(node1, node2);
                if(edge != null){
                    graph.setEdge(node1, node2, edge.weight + min_value);
                }
                else{
                    graph.setEdge(node1, node2, edge.weight - min_value);
                }
            }
            
            // updte residual graph
            for(int i=0; i<dfs.size()-1; i++){
                Integer n1 = dfs.get(i);
                Integer n2 = dfs.get(i + 1);
                Edge edge = graph.getEdge(n1, n2);
                Edge capEdge = capacityGraph.getEdge(n1, n2);
               
                if(edge.weight <= capEdge.weight){
                    residualGraph.setEdge(n1, n2, capEdge.weight - edge.weight);// update forward edge in the residual graph
                } else if (edge.weight > 0) {
                    Edge residualEdge = residualGraph.getEdge(n1, n2);
                    if(residualEdge == null){
                        Edge backEdge = new Edge(n1, n2, edge.weight);
                        residualGraph.addEdge(backEdge);
                    }
                    else{
                        residualGraph.setEdge(n2, n1, edge.weight);// backward edge
                    }
                }
                
            }
            // update the maximum flow
            maxFlow += min_value;
          
        }
        
        
        answer += maxFlow + "\n" + graph.toString();    
        writeAnswer(filePath+myMcGillID+".txt",answer);
        System.out.println(answer);
    }
	

	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
	    
	    
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
