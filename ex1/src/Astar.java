import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
* Astar class - an algorithm for graph. extends searcher
* finds the best track to the goal
*/
public class Astar extends searcher{
    // member
    private Node startNode;

    // constructor. use super constructor of searcher class
    Astar(char[][] map, int size) {
        super(map,size);
        this.startNode = new Node(0, 0, 'S');
    }

    // activate the func of the alg and print the solution to file
    @Override
    public void search() {
        Node s = AstarAlg();
        if (s != null) {
            restoreTrack(s);
        }
        printSolution();

    }

    // operate the alg of Astar
    public Node AstarAlg() {
        int stempCount = 1;
        // creates the nodes comperator
        finalCostComparator nodeComparator = new finalCostComparator();
        // creates the queue of nodes to develop
        PriorityQueue<Node> queue = new PriorityQueue<Node>(10, nodeComparator);
        // add the start node to the queue
        queue.add(this.startNode);
        // the max depth to develop nodes in
        int counter = this.getSize() * this.getSize();
        while ((counter > 0) && (!queue.isEmpty())) {
            counter--;
            Node node = queue.poll();
            // get to the goal node
            if ((node.getRow() == this.getSize() - 1) && (node.getCol() == this.getSize() - 1)) {
                return node;
            }
            // get all the possible successors to develop
            List<Node> successors = getAllSuccessorAndDetermineTime(node, stempCount);
            // if the successor not in queue - set its parent, and its g cost, than, add it to queue
            for (Node s : successors) {
                if (!queue.contains(s)) {
                    s.SetCameFrom(node);
                    if (s.CameFrom() != null) {
                        s.setGround(s.CameFrom().getGround() + s.getCost());
                    } else {
                        s.setGround(s.getCost());
                    }
                    queue.add(s);
                }

            }
        }
        // there is no path
        return null;
    }

    // get all the relevant successors and determine a time stamp
    public List<Node> getAllSuccessorAndDetermineTime(Node parentNode, int stempCount) {
        List<Node> finalListStates = this.getAllSuccessor(parentNode);
        for (Node nodeS : finalListStates) {
            nodeS.setTime(stempCount);
            stempCount++;
        }
        return finalListStates;
    }

    // compute the huristic
    public int computeHeuristic(Node node) {
        return this.getSize() - 1 - (Math.min(node.getRow(), node.getCol()));
    }


    /*
    * class 'finalCostComparator'
    * determine the way to compare between 2 nodes, used by the priority queue
    */
    // StringLengthComparator.java
    class finalCostComparator implements Comparator<Node> {
        // determine how to compare nodes
        @Override
        public int compare(Node node1, Node node2) {
            double node1finalCost = computeHeuristic(node1) + node1.getGround();
            double node2finalCost = computeHeuristic(node2) + node2.getGround();
            if (node1finalCost < node2finalCost) {
                return -1;
            }
            if (node1finalCost > node2finalCost) {
                return 1;
            }
            if (node1.getTime() < node2.getTime()) {
                return -1;
            }
            if (node1.getTime() > node2.getTime()) {
                return 1;
            }
            return 0;
        }
    }
}
