import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;


/*
 * 'searcher' class. an abstract class.
 * implemented by algorithem class like ids and A*
 */
public abstract class searcher {
    //members
    private List<Node> solution;
    private char[][] map;
    private int size;
    // constructor
    searcher (char[][] map, int size) {
        this.solution = new ArrayList<Node>();
        this.map = map;
        this.size = size;
    }

    // convert the sulotion to the wanted view
    public String solutionTranslator() {
        int i;
        boolean ifDown = false, ifUp = false, ifLeft = false, ifRight = false;
        int curRow, curCol, nextRow, nextCol;
        String strSol = "";
        int strSize = solution.size();
        for (i = 0; i < strSize - 1; i++) {
            ifDown = false;
            ifUp = false;
            ifLeft = false;
            ifRight = false;
            curRow = solution.get(i).getRow();
            curCol = solution.get(i).getCol();
            nextRow = solution.get(i + 1).getRow();
            nextCol = solution.get(i + 1).getCol();
            if (curRow > nextRow)
                ifUp = true;
            else if (curRow < nextRow)
                ifDown = true;
            if (curCol > nextCol)
                ifLeft = true;
            else if (curCol < nextCol)
                ifRight = true;
            if ((ifDown) && (ifRight)) {
                strSol += "RD-";
            } else if ((ifDown) && (ifLeft)) {
                strSol += "LD-";
            } else if ((ifUp) && (ifRight)) {
                strSol += "RU-";
            } else if ((ifUp) && (ifLeft)) {
                strSol += "LU-";
            } else if (ifDown) {
                strSol += "D-";
            } else if (ifUp) {
                strSol += "U-";
            } else if (ifRight) {
                strSol += "R-";
            } else if (ifLeft) {
                strSol += "L-";
            }
        }
        strSol = strSol.substring(0, strSol.length() - 1);

        return strSol;
    }

    // evaluted the cost of the node
    public int evaluateCost()
    {
        int i;
        int solCost = 0;
        int strSize = solution.size();
        for (i=0;i<strSize; i++) {
            solCost += solution.get(i).getCost();
        }
        return solCost;
    }

    // retore the track from the goalNode to the startNode
    public void restoreTrack(Node goal)
    {
        while(goal != null)
        {
            solution.add(goal);
            goal = goal.CameFrom();
        }
    }

    // print the solution to file
    public void printSolution()
    {
        int i;
        int cost;
        int solSize = solution.size();
        if(solution.isEmpty())
        {
            try {
                Files.write(Paths.get("output.txt"),"no path".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("no path");
            return;
        }
        Collections.reverse(solution);
        cost = evaluateCost();
        String solution = solutionTranslator() +" "+cost;
        try {
            Files.write(Paths.get("output.txt"),solution.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //check the node's type in diagonals to curNode is not water
    public boolean ifWaterDoesntBarrierInDiagonalDirect(int currentRow, int currentCol ,int prevRow, int prevCol) {
        if((map[currentRow][prevCol] == 'W') || (map[prevRow][currentCol] == 'W'))
        {
            return false;
        }
        return (ifNodeIsNotWater(currentRow, currentCol));
    }

    //check to node's type we step to is not water
    public boolean ifNodeIsNotWater(int currentRow, int currentCol) {
        if (map[currentRow][currentCol] == 'W')
        {
            return false;
        }
        return true;
    }

    // get all the relevant successors
    public List<Node> getAllSuccessor(Node parentNode) {
        int i, curRow, curCol;
        List<Node> possibleStates = canBeSuccsesor(parentNode);
        List<Node> finalListStates = new ArrayList<Node>();
        for (Node possibleS : possibleStates)
        {
            curRow = possibleS.getRow();
            curCol = possibleS.getCol();
            if (ifWaterDoesntBarrierInDiagonalDirect(curRow, curCol, parentNode.getRow(), parentNode.getCol()))
            {
                finalListStates.add(possibleS);
            }
        }
        return finalListStates;
    }


    // check if the node can be succsessor
    public List<Node> canBeSuccsesor(Node parentNode)
    {
        int row = parentNode.getRow();
        int col = parentNode.getCol();
        List<Node> possibleStates = new ArrayList<Node>();
        if (inLimits(row,col+1)) {
            possibleStates.add(new Node(row, col + 1, map[row][col + 1])); //right
        }
        if(inLimits(row + 1, col + 1)) {
            possibleStates.add(new Node(row + 1, col + 1, map[row + 1][col + 1])); //rightDown
        }
        if(inLimits(row + 1, col))
        {
            possibleStates.add(new Node(row + 1, col, map[row+1][col])); //down
        }
        if(inLimits(row + 1, col - 1))
        {
            possibleStates.add(new Node(row + 1, col - 1, map[row+1][col-1])); //leftDown
        }
        if(inLimits(row, col - 1))
        {
            possibleStates.add(new Node(row, col - 1, map[row][col-1])); //left
        }
        if(inLimits(row - 1, col - 1))
        {
            possibleStates.add(new Node(row - 1, col - 1, map[row-1][col-1])); //leftUp
        }
        if(inLimits(row - 1, col))
        {
            possibleStates.add(new Node(row - 1, col, map[row-1][col])); //up
        }
        if(inLimits(row - 1, col + 1))
        {
            possibleStates.add(new Node(row - 1, col + 1, map[row-1][col+1])); //rightUp
        }
        for (Node s: possibleStates)
        {
            if (parentNode.CameFrom() != null) {
                if ((s.getRow() == parentNode.CameFrom().getRow()) && (s.getCol() == parentNode.CameFrom().getCol())) {
                    possibleStates.remove(s);
                    break;
                }
            }
        }
        return possibleStates;
    }

    // check if node is in limits of map
    public boolean inLimits(int curRow, int curCol) {
        int i;
        if(curRow >= 0 && curRow< this.size && curCol>=0 &&curCol<this.size ) {
            return true;
        }
        else return false;
    }

    // get the solution
    public List<Node> getSolution() {
        return  this.solution;
    }

    // get the size of map
    public int getSize() {
        return  this.size;
    }

    // get the map[row][col]
    public int getMap(int row, int col) {
        return this.map[row][col];
    }

    // the algorithem of the class implemented searcher
    public abstract void search();
}
