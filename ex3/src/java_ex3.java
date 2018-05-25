import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * read the points and the wanted alg,
 * and write to file the division to clustrering by the alg.
 */
public class java_ex3 {

        public static void main(String[] args) throws IOException {
            BufferedReader in;
            // open and read from the input file
          in = new BufferedReader(new FileReader("input.txt"));
            // read from the input file the algName
            String algName = in.readLine();
            // read the asked num of clusters
            String numOdClusteringStr = in.readLine();
            int wantedNumOfClusters = Integer.parseInt(numOdClusteringStr);
            List<Cluster> allClusters = new ArrayList<Cluster>();
            List<Point> allPoints = new ArrayList<Point>();
            String str = in.readLine();
            while (!(str == null)) {
                Point first = new Point(str);
                allPoints.add(first);
                Cluster newCluster = new Cluster();
                newCluster.addPointToCluster(first);
                allClusters.add(newCluster);
                str = in.readLine();

            }
            Alg alg;
            if (algName.equals("single link"))
            {
                alg = new SingleLink();
            }
            else
            {
                alg = new AverageLink();
            }
            int i, j;
            int size = allClusters.size();
            for (i=0; i<= size- wantedNumOfClusters; i++)
            {
                if(allClusters.size()>wantedNumOfClusters)
                {
                    alg.merge(allClusters);
                }
            }
            int numOfPoints = allPoints.size();
            List<String> answer = new ArrayList<String>();
            // for each point find the cluster it belongs to
            for (i=0; i< numOfPoints; i++)
            {
                for (j=0; j< allClusters.size(); j++)
                {
                    if (allClusters.get(j).getPoints().contains(allPoints.get(i))) {
                        answer.add(Integer.toString(j+1));
                    }
                }
            }
            try {
                Files.write(Paths.get("output.txt"), answer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

}
