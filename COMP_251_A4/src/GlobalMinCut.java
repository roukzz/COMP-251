import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/****************************
 *
 * COMP251 template file
 *
 * Assignment 4
 *
 *****************************/

public class GlobalMinCut {

    static Random random;

    /**
     * One run of the contraction algorithm
     * Returns the min cut found
     *
     * @param   graph - the graph to find min cut of
     * @return  two ArrayLists of characters (placed together in another ArrayList)
     *          representing the partitions of the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut(Graph graph) {
        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();

        // For each node v, we will record
        // the list S(v) of nodes that have been contracted into v
        Map<Character, ArrayList<Character>> S = new HashMap<Character, ArrayList<Character>>();

        ArrayList<Character> nodes = graph.getNodes();
        
        // TODO: Initialize S(v) = {v} for each v
        for(int i = 0; i < nodes.size(); i++) {
          ArrayList<Character> node_list = new ArrayList<Character>(); //arraylist to hold the node character
          node_list.add(nodes.get(i));
          S.put(nodes.get(i), node_list);
        }
        
        while (graph.getNbNodes() > 2) {

            // select an edge randomly (DO NOT CHANGE THIS LINE)
            Edge edge = graph.getEdge(random.nextInt(graph.getNbEdges()));

            boolean setter = false; 

            // TODO: fill in the rest
            char u = edge.node_1; 
            char v = edge.node_2;
            int count = 0; //counter to check if there are no edges related to u except u-v or v-u
            for(Edge e : graph.getEdges()) { //for each edge
              count++;
            //get the list of nodes that have been contracted into v
              char node_1 = e.node_1; //get node_1
              char node_2 = e.node_2; //get node_2
              ArrayList<Character> array = S.get(v);  
              ArrayList<Character> list2 = new ArrayList<Character>();
              
              if(node_1 == u && node_2 != v && node_2 != u) { //if there is an edge adjcent to u, but thats not u-v
                list2 = S.get(node_1); // fill the list by the node_1=u
              //update the list S(v)
                for(int i = 0; i < list2.size(); i++) { 
                  array.add(list2.get(i));
                }
                S.put(v, array); 
                graph.contractEdge(edge);//contract the edge
                break; 
              }
              if(node_2 == u && node_1 != v && node_1 != u) { //check edge adjacent to u, but is not edge u-v
                list2 = S.get(node_2); //set the list of node_2 = u
                for(int i = 0; i < list2.size(); i++) { 
                  array.add(list2.get(i));
                }
                S.put(v, array);
                graph.contractEdge(edge); //contract the edge
                break; 
              }
            }
            if(count == graph.getNbEdges()) { //if there is no edge adjacent to u, then we remove u-v and v-u
              ArrayList<Edge> edges = graph.getEdges(); 
              ArrayList<Character> array2 = S.get(v); // list of nodes that have been contracted into v 
              ArrayList<Character> list3 = new ArrayList<Character>();
              for(int i = 0; i < edges.size(); i++) { // for each  edge
                if(i >= 0 && edges.get(i).node_1 == u && edges.get(i).node_2 == v) { //if its the edge u-v
                  
                  if(setter == false) { //check if S(v) has been updated
                  list3 = S.get(edges.get(i).node_1); //get S(u)
                  for(int j = 0; j < list3.size(); j++) { //update the list S(v)
                    array2.add(list3.get(j));
                    }
                  
                  S.put(v, array2); //update S(v)
                  setter = true; 
                  }
                  edges.remove(i);
                  i--; 
                  graph.removeNode(u); 
                }
                
                if(i >= 0 && edges.get(i).node_1 == v && edges.get(i).node_2 == u) { // check if its the edge v-u
                  
                  if(setter == false) {//check if S(v) has been updated
                  list3 = S.get(edges.get(i).node_2);
                  for(int j = 0; j < list3.size(); j++) { //update the list S(v)
                    array2.add(list3.get(j));
                    }
                  S.put(v, array2);//update S(v)
                  setter = true;
                  }
                  edges.remove(i); 
                  i--;
                  graph.removeNode(u); 
                }
              }
            }
        }        
        // TODO: assemble the output object
        for(int i = 0; i < graph.getNbNodes(); i++) {
          cut.add(S.get(nodes.get(i)));
        }
        return cut;
    }


    /**
     * repeatedly calls global_min_cut until finds min cut or exceeds the max allowed number of iterations
     *
     * @param graph         the graph to find min cut of
     * @param r             a Random object, don't worry about this, we only use it for grading so we can use seeds
     * @param maxIterations some large number of iterations, you expect the algorithm to have found the min cut
     *                      before then (with high probability), used as a sanity check / to avoid infinite loop
     * @return              two lists of nodes representing the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut_repeated(Graph graph, Random r, int maxIterations) {
        random = (r != null) ? r : new Random();

        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();
        int count = 0;
        do  {
            
            // TODO: use global_min_cut()
            Graph copy_graph = new Graph(graph);// copy
            cut = global_min_cut(copy_graph); //call the global min cut method to return the cut

            ++ count;


        } while (get_cut_size(graph, cut) > graph.getExpectedMinCutSize() && count < maxIterations);

        if (count >= maxIterations) System.out.println("Forced stop after " + maxIterations + " iterations... did something go wrong?");
        return cut;

        
    }

    /**
    * @param graph  the original unchanged graph
    * @param cut    the partition of graph into two lists of nodes
    * @return       the number of edges between the partitions
    */
    public static int get_cut_size(Graph graph, ArrayList<ArrayList<Character>> cut) {
        ArrayList<Edge> edges = graph.getEdges();
        int cut_size = 0;
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            if (cut.get(0).contains(edge.node_1) && cut.get(1).contains(edge.node_2) ||
                    cut.get(0).contains(edge.node_2) && cut.get(1).contains(edge.node_1)) {
                ++cut_size;
            }
        }
        return cut_size;
    }
}
