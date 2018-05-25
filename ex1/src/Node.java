import java.lang.Math;
import java.sql.Timestamp;

// Node class, responsible about the node (cells) on map
public class Node {
    //members
    private Node parent;
    private int cost;
    private char type;
    private int row;
    private int col;
    private int ground;
    private int timeStemp;
    //constructor
    public Node(int row, int col, char type)
    {
        this.parent = null;
        // fit the cost by the node type
        if (type == 'R')
        {
            this.cost = 1;
        }
        else if (type == 'D')
        {
            this.cost = 3;
        }
        else if (type == 'H')
        {
            this.cost = 10;
        }
        else if (type == 'W')
        {
            this.cost = 100;
        }
        else if (type == 'S')
        {
            this.cost = 0;
        }
        else if (type == 'G')
        {
            this.cost = 0;
        }
        this.timeStemp = 0;
        this.type = type;
        this.row = row;
        this.col = col;
        if(parent!=null)
            this.ground = (this.parent.ground)+this.cost;
        else this.ground = this.cost;
    }

    // convert from object to node and compare between the nodes
    @Override
    public boolean equals(Object other)
    {
        if((other== null)|| !(other.getClass().equals(this.getClass())))
        {
            return false;
        }
        Node otherNode = (Node)other;
        return isEqual(otherNode);
    }

    // compare betwen nodes
    public boolean isEqual(Node otherNode)
    {

        if (this.row != otherNode.row)
        {
            return false;
        }
        if (this.col != otherNode.col)
        {
            return false;
        }
        return true;
    }

    // make a hashCode
    public int hashCode()
    {
        return (this.row + "," + this.col).hashCode();
    }

    // get row of node on map
    public int getRow()
    {
        return this.row;
    }
    // get col of node on map
    public int getCol()
    {
        return this.col;
    }
    // get parent of node
    public Node CameFrom()
    {
        return this.parent;
    }
    // set parent of node
    public void SetCameFrom(Node parent)
    {
        this.parent = parent;
    }
    // get cost of node
    public int getCost()
    {
        return this.cost;
    }
    // get the in actual fact cost from the startNode to curNode
    public int getGround()
    {
        return this.ground;
    }
    // set the in actual fact cost from the startNode to curNode
    public void setGround(int newValue)
    {
        this.ground = newValue;
    }
    // set the timeStamp of the node, to know the order of creation
    public void setTime(int value)
    {
        this.timeStemp = value;
    }
    // get the timeStamp of the node, to know the order of creation
    public int getTime()
    {
       return this.timeStemp;
    }
}
