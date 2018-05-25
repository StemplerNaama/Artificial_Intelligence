/*
 * AverageLink Class - extends Alg.
 * compute distance between clusters and merge the closest clusters
 * distance is compute by the average ditances of points from the first cluster to the second.
 */

public class AverageLink extends Alg {

    /*
    * distanceBetweenTwoClusters function - calculate distance between two clusters.
    * distance is compute by the average ditances of points from the first cluster to the second.
    */
    double distanceBetweenTwoClusters(Cluster first, Cluster second) {
        int sizeFirst = first.getPoints().size();
        int sizeSecond = second.getPoints().size();
        int i, j;
        double min= Double.POSITIVE_INFINITY, dist,computeSum= 0;
        for (i = 0; i < sizeFirst; i++) {
            for (j = 0; j < sizeSecond; j++) {
                computeSum += Math.sqrt(Math.pow(first.getPoints().get(i).getX() - second.getPoints().get(j).getX(), 2)+
                        Math.pow(first.getPoints().get(i).getY() - second.getPoints().get(j).getY(), 2));
            }
        }
        dist = computeSum /(sizeFirst*sizeSecond);
        return dist;
    }
}
