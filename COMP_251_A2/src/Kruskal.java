
public class Kruskal{

    public static WGraph kruskal(WGraph g) {
        
        /* Fill this method (The statement return null is here only to compile) */
        DisjointSets nodeSet = new DisjointSets(g.getNbNodes());
        WGraph t = new WGraph();
        // for each edge
        for (int index = 0; index < g.listOfEdgesSorted().size(); index++) {
            Edge edge = new Edge(g.listOfEdgesSorted().get(index).nodes[0], g.listOfEdgesSorted().get(index).nodes[1],
                    g.listOfEdgesSorted().get(index).weight);
            // verify for an edge if it is safe to add it to the MST
            if (IsSafe(nodeSet, edge)) {
                // construct the MST
                t.addEdge(edge);
                // ensure after adding an edge that the nodes are in the same set and have the
                // same parent
                nodeSet.union(edge.nodes[0], edge.nodes[1]);
            }
        }

        // return the mst
        return t;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e) {
        // when detecting a cycle
        if (p.find(e.nodes[0]) == p.find(e.nodes[1])) {
            return false;
        } else {
            return true;
        }

    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

    } 
}
