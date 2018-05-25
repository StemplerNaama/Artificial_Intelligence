import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Cluster class - have list of points that make it up to a cluster
 */
public class Cluster {
    private List<Point> Points;

    public Cluster() {
        this.Points = new ArrayList<Point>();
    }
    // get all points in cluster
    List<Point> getPoints() {
        return this.Points;
    }
    //add point to cluster
    void addPointToCluster(Point point) {
        Points.add(point);
    }
    // merge current cluster with the received one
    void mergeClusters(Cluster cluster) {
        this.Points.addAll(cluster.getPoints());
    }
}
