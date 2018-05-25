import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;


/*
 *Ids class - an algorithm for graph. extends searcher
 * find a track from start node to the goal node
 */
public class Ids extends searcher{
    // members
    private Node startNode;
    private List<Integer> dupPruning;

    // constructor. use super constructor of searcher class
    Ids (char[][] map, int size)
    {
        super(map, size);
        this.startNode = new Node(0,0,'S');
        int i ,j;
        this.dupPruning = new ArrayList<Integer>();
    }

    // activate the func of the alg and print the solution to file
    @Override
    public void search()
    {
        Node s = IDDFS();
        if (s != null) {
            restoreTrack(s);
        }
        printSolution();

    }

    // activate the 'depthLimitedSearch' func with max depth to search on graph
    public Node IDDFS() {
        int depth;
        double maxDepth = this.getSize()*this.getSize();
        for (depth = 0; depth<maxDepth; depth++) {
            Node found = depthLimitedSearch(this.startNode, depth);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    // activate dfs with limit (max depth)
    public Node depthLimitedSearch(Node node, int depth) {
        int uniqueNum = -1;
        Integer uninqueNumInteger = new Integer(uniqueNum);
        // if get to the goalNode we will return it
        if ((depth == 0) && ((node.getRow() == this.getSize()-1) && (node.getCol() == this.getSize()-1)))
        {
            return node;
        }
        // if didnt get goalNode and cureentDepth > 0 continue search on graph
        if (depth > 0)
        {
            // get all the possible successors to develop
            List<Node> successors = getAllSuccessor(node);
            for (Node s : successors)
            {
                uniqueNum = (s.getRow()*this.getSize()) +s.getCol();
                uninqueNumInteger = new Integer(uniqueNum);
                // if we still operating the recursion on one of the successors. if true - continue to next successor
                if (this.dupPruning.contains(uninqueNumInteger))
                {
                    continue;
                }
                // adding the successor to the dupPruning list - after that, start recursion with it
                this.dupPruning.add(uninqueNumInteger);
                // set the cameFrom node
                s.SetCameFrom(node);
                Node found = depthLimitedSearch(s,depth-1);
                this.dupPruning.remove(uninqueNumInteger);
                // found the goalNode
                if (found != null)
                {
                    return found;
                }
            }
        }
        // if didnt get goalNode and cureentDepth equal to 0 - return null
        return null;
    }
}
