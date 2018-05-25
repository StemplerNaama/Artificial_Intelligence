import java.util.ArrayList;
import java.util.List;

/*
 * Alg abstract Class - extends by SingleLink and AverageLInk.
 * compute distance between clusters and merge the closest clusters
 */
public abstract class Alg {

    //merge function- get all clusters and merge the two that most simmilar clusters
    void merge(List<Cluster> allClusters) {
        String clustersToMerge = minDistanceOfAllClusters(allClusters);
        String[] parts = clustersToMerge.split(",");
        String part1 = parts[0];
        String part2 = parts[1];
        int firstIndex = Integer.parseInt(part1);
        int secondIndex = Integer.parseInt(part2);
        Cluster newCluster = new Cluster();
        newCluster.mergeClusters(allClusters.get(firstIndex));
        newCluster.mergeClusters(allClusters.get(secondIndex));

        allClusters.set(firstIndex, newCluster);
        allClusters.remove(secondIndex);
    }

    //minDistanceOfAllClusters function - find the most closest clusters
    String minDistanceOfAllClusters(List<Cluster> allClusters) {
        int i, j;
        double minDistance = Double.POSITIVE_INFINITY, dist;
        Cluster keepFirst, keepSecond;
        String retClusters = new String();
        int numOfClusters = allClusters.size();
        for (i = 0; i < numOfClusters; i++) {
            for (j = i + 1; j < numOfClusters; j++) {
                dist = distanceBetweenTwoClusters(allClusters.get(i), allClusters.get(j));
                if (dist < minDistance) {
                    minDistance = dist;
                    keepFirst = allClusters.get(i);
                    keepSecond = allClusters.get(j);
                    retClusters = i + "," + j;
                }
            }
        }
        return retClusters;
    }

    // calculate the distance between two clusters
    abstract double distanceBetweenTwoClusters(Cluster first, Cluster second);
}
