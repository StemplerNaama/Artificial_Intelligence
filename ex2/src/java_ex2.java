import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class java_ex2 {
    public static void main(String[] args) throws IOException {
        BufferedReader in;
        // open and read from the input file
        in = new BufferedReader(new FileReader("input.txt"));
        int gridSize = 5;
        List<StringBuilder> board = new ArrayList<>();
        int i;
        // read the board from the input file
        for (i = 0; i < gridSize; i++) {
            String str = in.readLine();
            StringBuilder sb = new StringBuilder(str);
            board.add(sb);
        }
        // create a GameLogic
        GameLogic game = new GameLogic();
        // operate the searchFunc
        char winner = game.PlayGame(board);
        String winnerstr = ""+winner;
        try {
            Files.write(Paths.get("output.txt"), winnerstr.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}