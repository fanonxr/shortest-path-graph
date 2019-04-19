import java.util.ArrayList;
import java.util.List;

public class Node {
    /*
    * A node will consist of a Character which will be the name of the node
    * an ArrayList that will of Edges that will show all the nodes it is connected to
    * weight is the current node to the distance
    * nodeVisited - keep track of if the node has been connected or not
    * inShortest path - if we don't need to back track we know its in the shortest path
    * */

    private Character name;
    private List<Edge> neighbors;
    private int distanceToZ;
    private boolean nodeVisited;
    private boolean inShortestPath;

    public Node(Character name) {
        this.name = name;
        this.neighbors = new ArrayList<Edge>();
        this.nodeVisited = false;
        this.inShortestPath = false;
    }

    public void addEdge(Node node, int weight) {
        // create a new edge
        Edge edge = new Edge(this, node, weight);
        neighbors.add(edge);
    }

    public Character getName() {
        return name;
    }

    public int getDistanceToZ() {
        return distanceToZ;
    }

    public boolean isNodeVisited() {
        return nodeVisited;
    }

    public boolean isInShortestPath() {
        return inShortestPath;
    }

    // set distance for node

    public void setDistanceToZ(int distanceToZ) {
        this.distanceToZ = distanceToZ;
    }

    public void setInShortestPath(boolean inShortestPath) {
        this.inShortestPath = inShortestPath;
    }

    public void setNodeVisited(boolean nodeVisited) {
        this.nodeVisited = nodeVisited;
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }

    // calculate weight
    public int findWeight(Node currentNode) {
        for (Edge edge: neighbors) {
            if(edge.destination == currentNode) {
                return edge.weight;
            }
        }
        return -1;
    }

    public void nodeReset() {
        nodeVisited = false;
        inShortestPath = false;
    }

    // print out the contents of an edge
    @Override
    public String toString() {
        String edgeString = "";

        for (Edge edge: neighbors) {
            Character nodeName = edge.destination.name;
            int nodeWeight = edge.weight;
            edgeString += "Edge: " + nodeName + " weight: " + nodeWeight + "\n";

        }
        edgeString += "Distance to z: " + distanceToZ + "\n";

        return edgeString;
    }
}
