import java.io.File;
import java.io.IOException;
import java.util.*;

public class Project {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        char userStartNode;

        String graphPathName = "/Users/fanonxrogers/Documents/BUCourses/MET526/fanonx-MET-526-final-project/graph_input.txt";
        String directDistancePathName = "/Users/fanonxrogers/Documents/BUCourses/MET526/fanonx-MET-526-final-project/direct_distance.txt";

        // create a graph from the file
        Graph cityGraph = fileToGraph(graphPathName);
        // add the direct distances from Z to each of the Nodes
        fileToDirectDistance(directDistancePathName, cityGraph);

        // prompt the user for the input
        while (!flag) {
            try {
                System.out.println("Enter a start Character: ");
                System.out.println("---------------------------");
                System.out.println("Type exit to stop the program from running.");
                System.out.println("---------------------------");

                String userInput = scanner.nextLine();

                if (userInput.length() > 1) {
                    flag = true;
                } else {
                    userStartNode = userInput.toUpperCase().charAt(0);
                    shortestPath(cityGraph, userStartNode, 1);
                    shortestPath(cityGraph, userStartNode, 2);
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input");
            } catch (NullPointerException e) {
                System.out.println("Please enter a character that's in the graph");
            }
        }

        scanner.close();
    }

    private static Graph fileToGraph(String fileName) throws IOException {
        // create a new graph object
        Graph graph = new Graph();
        // line counter to help keep track of the first line in the array
        // we will add those characters to a arrayList of characters
        int lineCounter = 0;
        ArrayList<Character> nodeArray = new ArrayList<>();

        // check if its a existing file
        if (new File(fileName).isFile()) {
            Scanner scanner = new Scanner(new File(fileName));
            // loop over each line in the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] stringNums = line.trim().split("\\s+");
                // System.out.println(Arrays.toString(stringNums));
                if (lineCounter == 0) {
                    for (int i = 0; i < stringNums.length; i++) {
                        // add the character to the array list
                        nodeArray.add(stringNums[i].charAt(0));
                        // add the character to the graph
                        // creating a key based on the character
                        // and creating a node with the name of that same character
                        graph.addNode(stringNums[i].charAt(0));
                    }
                } else {
                    // the first index is always going to be a character
                    Character nodeName = stringNums[0].charAt(0);
                    // we will get the node based on what character we are at
                    Node currentNode = graph.getNode(nodeName);
                    // loop over the array
                    for (int i = 1; i < stringNums.length; i++) {
                        // convert to an int
                        int weight = Integer.parseInt(stringNums[i]);
                        // create node by getting
                        Node destination = graph.getNode(nodeArray.get(i - 1));
                        // connect the destination node and weight to current node that we are at
                        if (weight != 0) {
                            currentNode.addEdge(destination, weight);
                        }
                    }
                }
                lineCounter++;
            }
        }
        return graph;
    }

    private static void fileToDirectDistance(String fileName, Graph graph) throws IOException {
        // check to see if it is a file
        if (new File(fileName).isFile()) {
            Scanner scanner = new Scanner(new File(fileName));
            // loop over each line in the file
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] stringCharNumPair = line.split(" ");
                // convert to a character type and an int type
                Character sourceChar = stringCharNumPair[0].charAt(0);
                int numDirectDistance = Integer.parseInt(stringCharNumPair[1]);

                // add to node in graph
                graph.getNode(sourceChar).setDistanceToZ(numDirectDistance);
            }
        }
    }

    private static void shortestPath(Graph graph, Character startNode, int algorithm) {
        // check all adjacent nodes to the start node
        Node currentNode = graph.getNode(startNode);

        // keep track of the nodes visited
        List<Character> nodesVisited = new ArrayList<>();

        // add the current node
        nodesVisited.add(currentNode.getName());

        // breadth first search to help keep track of the nodes we have already visited
        // helps with backtracking
        Stack<Node> visitingNodes = new Stack<>();

        // loop through the graph
        while (currentNode != graph.getNode('Z')) {
            // we have visited a node
            // add it to the stack
            // set true to since its been visited and it will be in the shortest path
            visitingNodes.add(currentNode);
            currentNode.setNodeVisited(true);
            currentNode.setInShortestPath(true);

            // get all the edges that are connected to the current node we are on
            List<Edge> adjacentNodes = currentNode.getNeighbors();

            // temp for next node
            Node nextNode = null;
            int weightDistanceTotal = Integer.MAX_VALUE;

            for (Edge i: adjacentNodes) {
                // testing
                // System.out.println(i.destination.getName());
                // System.out.println(i.destination.getDistanceToZ());

                // 1. always check to see if we have visited the node
                if (i.destination.isNodeVisited()) {
                    // System.out.println(i.destination.getName() + " is already in the path");
                    continue;
                }

                // 2. assign the next node to the destination
                if (nextNode == null) {
                    nextNode = i.destination;
                }

                // compare distances
                if (algorithm == 1) {
                    nextNode = updateNextNodeAlgo1(nextNode, i.destination);
                } else {
                    NodeWithWeightDistanceTotal nodeWithWeightDistanceTotal = updateNextNodeAlgo2(nextNode, i, weightDistanceTotal);
                    nextNode = nodeWithWeightDistanceTotal.node;
                    weightDistanceTotal = nodeWithWeightDistanceTotal.weightDistanceTotal;
                }
                //if (nextNode.getDistanceToZ() > i.destination.getDistanceToZ()) {
                //    nextNode = i.destination;
                //}
            }

            // next has no other edges
            if (nextNode == null) {
                // System.out.println("There no place to go from " + currentNode.getName());
                // pop off the node we just visited
                nextNode = visitingNodes.pop();
                // its not in the shortest path to Z
                nextNode.setInShortestPath(false);
                // set the next node to the previous node
                nextNode = visitingNodes.pop();
            }

            // add the nodes we visit to keep track of the path
            nodesVisited.add(nextNode.getName());

            // System.out.println(Arrays.toString(nodesVisited.toArray()));
            // testing purposes to see if the node is on the shortest path

//            for (Character node: nodesVisited) {
//                Node boolVisit = graph.getNode(node);
//                System.out.println(boolVisit.isInShortestPath());
//            }

            // progress to the next node
            currentNode = nextNode;
            // when visiting the last node mark z in the shortest path
            if (currentNode.getName() == 'Z') {
                currentNode.setInShortestPath(true);
            }
            // testing
            // System.out.println("next node = " + currentNode.getName());
        }

        // keep track of the path visited and the total addition of weights
        int pathCounter = 0;

        // construct the shortest path
        List<Node> shortestPath = new ArrayList<>();

        for (Character nodeVisitor: nodesVisited) {
            // get the node
            Node node = graph.getNode(nodeVisitor);

            // add to the shortest path
            if (node.isInShortestPath() && !shortestPath.contains(node)) {
                shortestPath.add(node);
            }
        }

        // print the shortest path
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            currentNode = shortestPath.get(i);
            Node nextNode = shortestPath.get(i + 1);

            // find the weight of that node
            int weight = currentNode.findWeight(nextNode);
            pathCounter += weight;

            // System.out.println("weight " + weight);
            // System.out.println("path total " + pathCounter);
        }

        // final output
        String fullPathSequence = "";
        String shortestPathSequence = "";

        for (int i = 0; i < nodesVisited.size(); i++) {
            if (i != nodesVisited.size() - 1) {
                fullPathSequence += nodesVisited.get(i) + " -> ";
            }
        }

        fullPathSequence += nodesVisited.get(nodesVisited.size() - 1);

        for (int i = 0; i < shortestPath.size(); i++) {
            if (i != shortestPath.size() - 1) {
                shortestPathSequence += shortestPath.get(i).getName() + " -> ";
            }
        }

        if (currentNode.getName() == 'Z') {
            shortestPathSequence += "Z";
        } else {
            shortestPathSequence += shortestPath.get(shortestPath.size() - 1).getName();
        }

        System.out.println("Algorithm " + algorithm + " : ");
        System.out.println("Sequence of all nodes " + fullPathSequence);
        System.out.println("Shortest path: " + shortestPathSequence);
        System.out.println("Shortest path length: " + pathCounter);
        System.out.println("\n");

        // reset the graph
        graph.graphReset();

    }

    // method to update the next method in algorithm 1

    private static Node updateNextNodeAlgo1(Node nextNode, Node destination) {
        if (nextNode.getDistanceToZ() > destination.getDistanceToZ()) {
            return destination;
        }
        return nextNode;
    }

    // wrapper class to return a node and the weight tied to it

    private static class NodeWithWeightDistanceTotal {
        Node node;
        int weightDistanceTotal;

        public NodeWithWeightDistanceTotal(Node node, int weightDistanceTotal) {
            this.node = node;
            this.weightDistanceTotal = weightDistanceTotal;
        }
    }
    // method to update the node in algorithm 2

    private static NodeWithWeightDistanceTotal updateNextNodeAlgo2(Node nextNode, Edge edge, Integer weightDistanceTotal) {
        if (edge.weight + edge.getDestination().getDistanceToZ() < weightDistanceTotal) {
            weightDistanceTotal = (edge.weight + edge.getDestination().getDistanceToZ());
            return new NodeWithWeightDistanceTotal(edge.destination, weightDistanceTotal);
            // System.out.println(nextNode.getName());
        }
        return new NodeWithWeightDistanceTotal(nextNode, weightDistanceTotal);
    }
}

