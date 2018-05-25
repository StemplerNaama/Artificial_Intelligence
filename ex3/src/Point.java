
/*
 * point class
 */
public class Point {
    private double x;
    private double y;

    // construcor with numeric indexes recieved
    public Point(double xIndex, double yIndex) {
        this.x = xIndex;
        this.y = yIndex;
    }
    // constructor with indexes that recived in string format
    public Point(String str) {
        String[] parts = str.split(",");
        String part1 = parts[0];
        String part2 = parts[1];
        double row = Double.parseDouble(part1);
        double col = Double.parseDouble(part2);
        this.x = row;
        this.y = col;
    }
    // get x index
    double getX(){
        return this.x;
    }
    // get y index
    double getY(){
        return this.y;
    }
}
