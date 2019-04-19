import java.util.HashMap;
import java.util.Map;

public class Graph {
    /*
    * Graph class will be a hashMap
    * with a character as the key and
    * a node
    * a node contains the characters as a name
    * a arrayList to contain the edges
    * a int distance to z
    * a boolean if it will be in the shortest path
    * a boolean if it will be visited
    * */

    private HashMap<Character, Node> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    // addNode method add a node to the graph
    // based on the character that inserted in
    // will create a new node
    public void addNode(Character name) {
        graph.put(name, new Node(name));
    }

    // will get the node based on the character that is passed in
    public Node getNode(Character name) {
        return graph.get(name);
    }

    @Override
    public String toString() {
        String nodeString = "";
        // map these functions to every object in the hashMap
        for (Map.Entry<Character, Node> entry: graph.entrySet()) {
            // get the key
            Character source = entry.getKey();
            // get the node value
            Node value = entry.getValue();
            // concatenate to a string
            nodeString +=  "source: " + source + "\n" + "value: " + value + "\n";
        }
        return nodeString;
    }

    // method to reset the graph
    public void graphReset() {
        for (Node node: graph.values()) {
            node.nodeReset();
        }
    }
}
