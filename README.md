# shortest-path-graph
algorithm to select the shortest path from a starting point to an destination


Documentation: Pseudocode 

Note: In my program, the algorithms are identical except for when to choose what the next node will be. Originally, I had a large amount of duplicate code that did the same exact thing for each algorithm. In this case, I pass in the an int value of 1 or 2 to determine which will be the next node. I created 1 wrapper static class that would contain the node and the weight that will be tied to it. I then created two methods to update the node depending on either algorithm. This documentation states when to choose the nextNode depending on the algorithms criteria. 

Algorithm: shortestPath(Graph graph, Character startNode, int algorithm) 

currentNode = graph.getNode(startNode) {Get the starting node that the user will pass in}

List nodesToVisit = new ArrayList() {create an array list that will keep track of nodes visited equating to the path}

nodesToVisit.add(currentNode) {add the current node to the arrayList since it’s the first node we start at}

Stack visitingNodes = new Stack() {keep track of the nodes, but specifically used for backtracking}

While (currentNode != Node Z) {Loop until we have reached Node Z in the path}
	visitingNodes.add(currentNode) {add to the stack}
	currentNode.isVisited = true {We now know this node has been visited}
	currentNode.setIsInshortestPath = true {We now know it is in the shortest path}

List<Edge> adjacentNodes = currentNode.getNeighbors() {create an array list of all the nodes that are connected to this graph}

Node nextNode = null {Temp node variable so we can choose the next node based on direct distance to Z}

int weightCounter = Integer.MAX_VALUE {will use to keep track of the max weight + distance to z (this is used for algorithm 2)}

for (Edge i: adjacentNods) {Loop over all the edges that are connected to that current node}
	if (i.destination.nodeVisited == true) {check to see if we have visited that node}
		continue {pass through}

	if (nextNode == null) {assign the next node to the destination node}
		nextNode = i.destination
	

if (algorithm == 1) {based on which algorithm to run}
	nextNode = updateNextNodeAlgo1(nextNode, i.destination) {passing in the next node and the destination of that node}
	method: updateNextNodeAlgo1(Node nextNode, Node destination)
			if (nextNode.toZ > destination.toZ) {return the smallest distance to z}
				return destination

			return nextNode {otherwise return the same node we are on}

else {this is algorithm 2}
		if (edge.weight + edge.getDestination.toZ < weightCounter) {if the added weight + distance to z for that edge is less than the counter }	
			weightCounter = edge.weight + edge.getDestination.toZ 

if (nextNode == null) {next has no other edges to choose from}
	nextNode = visitingNodes {backtrack once}
	nextNode.setShortestPath(False) {this node is not in the shortest path}
	nextNode = visitngNodes.pop() {get the previous node}

nodesToVisit.add(nextNode.name) {keep track of the nodes we have visited}

currentNode = nextNode {progress to the nextNode}

if (currentNode.getName == ‘Z’) {check to see if we are at Z}
	currentNode.setInShortestPath(true) {Z is the current node so we have to set in path to true}

int pathCounter = 0 {keep of the total weights given the path}

List<Node> shortestPath = new ArrayList {array list containing nodes in the shortest path}

for (Char nodeShortPath: nodesToVisit) {Loop through the nodes we have visited}
	Node node = graph.getNode(nodeShortPath) {get the node}
	
	If (node.inShortestPath() && !shortestPath.contains(node)) 
ShortestPath.add(node) {add to the list if the node inshortPath is true and is not already in the list}


displayPathDetails() – print statements displaying the algorithm, sequence of nodes, shortest path,  and shortest path length

graph.graphReset() – reset all the nodes in the graph toZ and inShortestPath back to false
	



Data Structures:

Graph – hashMap: I created a graph class that only contains a hash map data structure. The key for the hash map is going to be a character and the value for the hash map is going to be a Node. 

Node – ArrayList: In the node class, each node object will hold a list of edge objects. This helps and see what nodes are connected with one another based on its weight. 

nodesVisited – ArrayList: created an array list of Character objects to help keep track of the entire path from the starting node to the destination node z. I add to this array list every time next node is updated to the destination. Once Z is the next node, I print out the entire contents of the array displaying the entire bath from start node to Z depending on the algorithm. 

visitingNodes – Stack: I used the stack data structure to help with backtracking. Once it visits the node itself it also adds it to the nodesVisited Stack, I than have a Boolean variable that turns to true once it visits that node. So, the next node has to back track then I use the pop method twice to pop the current node I’m at, and pop again to get the previous node visited.

adjacentNodes – ArrayList: A list of edges. I use this to help choose what the next node to visit will be. 

shortestPath – ArrayList: A list that contains the shortest path of the nodes. It loops over the nodesVisited array List and checks the Boolean inShortestPath and if the node is not already in the list, then it will add it to the shortest path list, which is then used for the shortest path output. 
