import java.util.ArrayList;
import java.util.List;

/* status class - get list of chars to build the board, and char to determine the player
*  represent the current status of the board */
public class Status {
    private List<StringBuilder> board;
    private char player;
    // constructor
    public Status (List<StringBuilder> board, char player)
    {
        this.board = board;
        this.player = player;
    }
    // constructor - duplicate the board
    public Status (Status curStatus)
    {
        this.board = new ArrayList<>();
        for(StringBuilder sb : curStatus.getBoard())
        {
            this.board.add(new StringBuilder(sb));
        }
       this.player = curStatus.player;
    }

    // get the board
    List<StringBuilder> getBoard()
    {
        return this.board;
    }

    // get the player
    char getPlayer()
    {
        return this.player;
    }

    // get yhe opponent
    char getOpponent()
    {
        if (this.getPlayer() == 'W')
        {
            return 'B';
        }
        else {
            return 'W';
        }
    }

    // set cell in specific position accordinate to the type of player
    void setCellInBoard(int currentRow, int currentCol, char playerType)
    {
        board.get(currentRow).setCharAt(currentCol, playerType);
    }

    // change the player type to be the opponent - in the opponent turn
    void ChangePlayerType(Status curStatus)
    {
        this.player = curStatus.getOpponent();

    }

    // rotate the board to right
    void rotate()
    {
        List<StringBuilder> newBoard = new ArrayList<>();
        int i, j;
        for (i = 0; i < 5; i++)
        {
            StringBuilder newSb = new StringBuilder();
            for (j = 0; j < 5; j++)
            {
                newSb.append(this.getBoard().get(j).charAt(i));
            }
            newSb = newSb.reverse();
            newBoard.add(newSb);
        }
        this.board = newBoard;
    }

}
