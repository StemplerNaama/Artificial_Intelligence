import java.util.ArrayList;
import java.util.List;

/*
 * GameLogic class - managment the game
 * will start the game and manage it.
 */
public class GameLogic {

    private Status status;
    private int mem;
    //constructor
    public GameLogic ()
    {
        this.status = null;
        this.mem = 0;
    }

    //CellExistInBoard - determine if cell is in limits of the board - turn true if yes
    boolean CellExistInBoard(int currentRow, int currentCol)
    {
        if (((currentRow >= 0) && (currentRow < 5)) && ((currentCol >= 0) && (currentCol < 5)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }



     //ValidationAccordingGameRolls: check if cell has neighbor
    boolean ValidationAccordingGameRolls(Status curStatus ,int currentRow, int currentCol)
    {

        int tempRow = 0;
        int i, j;
        boolean allowedFlag = false;
        for (i=currentRow-1; i<=currentRow+1; i++)
        {
            for (j=currentCol-1; j<=currentCol+1; j++)
            {
                if (((i >= 0) && (i<5)) && ((j>=0) && (j<5)))
                {
                    // there is cell is not empty- there is neighbor
                    if (curStatus.getBoard().get(i).charAt(j) != 'E')
                    {
                        allowedFlag = true;
                    }
                }
            }
        }
        return allowedFlag;
    }

     // IfCellIsValid: determine if cell is valid by all of the parameters (- limits, game rolls, neighbor)
     boolean IfCellIsValid(Status curStatus, int currentRow, int currentCol)
    {
        //in limits of board
        if (!CellExistInBoard(currentRow, currentCol))
        {
            return false;
        }
        //emptyCell
        if (curStatus.getBoard().get(currentRow).charAt(currentCol) !=  'E')
        {
            return false;
        }
        //by rools of game
        if (!ValidationAccordingGameRolls(curStatus, currentRow, currentCol))
        {
            return false;
        }
        // check if we have direction that in it we can eat
        return true;
    }


    // eating what need in the right side
    void EatingToRightSide(Status curStatus, int currentRow, int currentCol)
    {
        if(currentCol+1 < 5) {
            char cellFromRight = curStatus.getBoard().get(currentRow).charAt(currentCol + 1);
            if ((cellFromRight == 'E') || (cellFromRight == curStatus.getPlayer())) {
                return;
            }
            int limitsEating = currentCol + 1;
            while ((limitsEating < 5) && (curStatus.getBoard().get(currentRow).charAt(limitsEating) == curStatus.getOpponent())) {
                limitsEating++;
            }
            if ((limitsEating < 5) && (curStatus.getBoard().get(currentRow).charAt(limitsEating) == curStatus.getPlayer())) {
                int i;
                for (i = currentCol; i < limitsEating; i++) {
                    curStatus.setCellInBoard(currentRow, i, curStatus.getPlayer());
                }
            }
        }
        return;
    }

    // eating what need to eat to down direction
    void EatingInDiagoalRD(Status curStatus, int currentRow, int currentCol)
    {
        if ((currentCol+1 < 5) && (currentRow+1 < 5)) {
            char cellFromRightDown = curStatus.getBoard().get(currentRow + 1).charAt(currentCol + 1);
            if ((cellFromRightDown == 'E') || (cellFromRightDown == curStatus.getPlayer())) {
                return;
            }
            int limitsEatingCol = currentCol + 1;
            int limitsEatingRow = currentRow + 1;

            while ( ((limitsEatingCol < 5) && (limitsEatingRow < 5)) && (curStatus.getBoard().get(limitsEatingRow).charAt(limitsEatingCol) == curStatus.getOpponent())) {
                limitsEatingCol++;
                limitsEatingRow++;
            }
            if (((limitsEatingCol < 5) && (limitsEatingRow < 5)) && (curStatus.getBoard().get(limitsEatingRow).charAt(limitsEatingCol) == curStatus.getPlayer())) {
                int i, j;
                for (i = currentCol, j = currentRow; ((i < limitsEatingCol) && (j < limitsEatingRow)); i++ , j++) {
                    curStatus.setCellInBoard(j, i, curStatus.getPlayer());
                }
            }
        }
        return;
    }

    // rotate the board 4 times- every time eat o right direction and to down direction
    Status ProcessingBoard(Status curStatus, int row, int col)
    {
        int counter = 0;
        Status cpCurStatus = new Status(curStatus);
        while (counter < 4) {
            EatingToRightSide(cpCurStatus, row, col);
            EatingInDiagoalRD(cpCurStatus, row, col);
            cpCurStatus.setCellInBoard(row, col, cpCurStatus.getPlayer());
            cpCurStatus.rotate();
            int temp = row;
            row = col;
            col = 4 - temp;
            counter++;
        }
        cpCurStatus.ChangePlayerType(curStatus);
        return cpCurStatus;
    }

    //generate all the possible moves
    List<Status> GenerateAllMoves(Status curStatus) {
        int i,j;
        List<Status> listOfMoves = new ArrayList<>();
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if ((curStatus.getBoard().get(i).charAt(j) == 'E') && (IfCellIsValid(curStatus, i,j)))
                {
                    Status newStat = ProcessingBoard(curStatus, i, j);
                    listOfMoves.add(newStat);
                }
            }
        }
        return listOfMoves;
    }

    // return who is the winner when board is full
    char WhoIsTheWinner(List<StringBuilder> board)
    {
        int i, j, blackCounter=0, whiteCounter=0;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (board.get(i).charAt(j) == 'B') {
                    blackCounter++;
                }
                else{
                    whiteCounter++;
                }
            }
        }
        if(blackCounter>whiteCounter)
        {
            return 'B';
        }
        else return 'W';
    }

    // check if board is full
    boolean BoardIsNotFull(List<StringBuilder> board)
    {
        int i, j;
        boolean findEmptyCell =false;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (board.get(i).charAt(j) == 'E') {
                    findEmptyCell = true;
                    break;
                }
            }
        }
        return findEmptyCell;
    }

    // compute the cost by the heuristic
    double ComputeCost(Status curStatus)
    {
        char winner;
        int i, j, blackCounter=0, whiteCounter=0, blackOnLimits = 0, whiteOnLimits = 0;
        if (!BoardIsNotFull(curStatus.getBoard()))
        {
            winner = WhoIsTheWinner(curStatus.getBoard());
            if(winner == 'W') {
                return Double.NEGATIVE_INFINITY;
            }
            else if (winner == 'B')
            {
                return Double.POSITIVE_INFINITY;
            }
        }
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (curStatus.getBoard().get(i).charAt(j) == 'B') {
                    blackCounter++;
                }
                else if (curStatus.getBoard().get(i).charAt(j) == 'W'){
                    whiteCounter++;
                }
            }
        }
        for (i = 0; i < 5; i++) {
            if (curStatus.getBoard().get(0).charAt(i) == 'B') {
                blackOnLimits++;
            }
            else if (curStatus.getBoard().get(0).charAt(i) == 'W') {
                whiteOnLimits++;
            }
            if (curStatus.getBoard().get(4).charAt(i) == 'B') {
                blackOnLimits++;
            }
            else if (curStatus.getBoard().get(4).charAt(i) == 'W') {
                whiteOnLimits++;
            }
        }
        for (i = 1; i < 4; i++) {
            if (curStatus.getBoard().get(i).charAt(0) == 'B') {
                blackOnLimits++;
            }
            else if (curStatus.getBoard().get(i).charAt(0) == 'W') {
                whiteOnLimits++;
            }
            if (curStatus.getBoard().get(i).charAt(4) == 'B') {
                blackOnLimits++;
            }
            else if (curStatus.getBoard().get(i).charAt(4) == 'W') {
                whiteOnLimits++;
            }
        }
        int compute = (blackCounter - whiteCounter) + (blackOnLimits - whiteOnLimits);
        return compute;
    }

    // opperate the alg of minimax
    double MinimaxAlg (Status curStatus, int depth)
    {
        double bestValue,v ;

        if ((depth == 0) || (!BoardIsNotFull(curStatus.getBoard())))
        {
            return ComputeCost(curStatus);
        }
        List<Status> optionalMoves = GenerateAllMoves(curStatus);
        GameLogic game = new GameLogic();
        if (curStatus.getPlayer() == 'B')
        {
            bestValue = Double.NEGATIVE_INFINITY;
            for(Status curChild : optionalMoves)
            {
                v = game.MinimaxAlg(curChild, depth-1);
                if (v >= bestValue) {
                    bestValue =  v;
                    this.status = curChild;
                    this.mem = depth;
                }

            }
            return bestValue;
        }
        // cur status is W
        else {
            bestValue = Double.POSITIVE_INFINITY;
            for(Status curChild : optionalMoves)
            {
                v = game.MinimaxAlg(curChild, depth-1);
                if (v <= bestValue) {
                    bestValue = v;
                    this.status = curChild;
                    this.mem = depth;
                }
            }
            return bestValue;
        }
    }

    // make one move
    Status MakeMove (Status curStatus)
    {
        MinimaxAlg(curStatus, 3);

        return this.status;
    }

    // opperate the game and return who is the winner
    char PlayGame(List<StringBuilder> board)
    {
        Status curStatus = new Status(board, 'B');
        this.status = curStatus; /*************/
        while (BoardIsNotFull(curStatus.getBoard()))
        {
            curStatus = MakeMove(curStatus);
        }
        return WhoIsTheWinner(curStatus.getBoard());
    }


    // print the member board
    void PrintBoard() {
        int i, j;
        for (i = 0; i < 5; i++) {
            System.out.println(this.status.getBoard().get(i));
        }
    }

    // print given board
    void PrintBoardCur(Status curStatus) {
        int i, j;
        for (i = 0; i < 5; i++) {
            System.out.println(curStatus.getBoard().get(i));
        }
    }
}
