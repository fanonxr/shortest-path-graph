public class Edge {
    /*
    * Edge consist of a node source (current place)
    * a destination node (a place you can travel to from the current node
    * a weight representing the distance between the current node and the source node)
    * */

    public Node source;
    public Node destination;
    public int weight;

    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
