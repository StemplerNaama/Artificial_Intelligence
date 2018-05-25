import java.io.*;
import java.lang.*;

public class java_ex1 {
    public static void main(String[] args) throws IOException {
        BufferedReader in;
        // open and read from the input file
        in = new BufferedReader(new FileReader("input.txt"));
        // read from the input file the algName
        String algName = in.readLine();
        // read from the input file the gridSize
        String gridSizeStr = in.readLine();
        int gridSize = Integer.parseInt(gridSizeStr);
        char[][] mapStr = new char[gridSize][gridSize];
        int i;
        // read the map from the input file
        for (i = 0; i < gridSize; i++) {
            String str = in.readLine();
            mapStr[i] = str.toCharArray();
        }
        // create a searcher
        searcher alg;
        // find if IDS or A*
        if (algName.equals("IDS"))
        {
            alg = new Ids(mapStr, gridSize);
        }
        else
        {
            alg = new Astar(mapStr, gridSize);
        }
        // operate the searchFunc
        alg.search();
    }
}
