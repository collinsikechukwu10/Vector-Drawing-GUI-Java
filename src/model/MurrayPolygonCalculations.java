package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PrimitiveIterator;

/**
 * Murray Coordinate calculator by Jack Cole.
 * [https://alb.host.cs.st-andrews.ac.uk/cole/poster.html]
 * <p>
 * author 210032207
 */
public class MurrayPolygonCalculations {
    /**
     * Default x radices used in the poster.
     */
    public static final int[] DEFAULT_XRADICES = new int[]{3, 5};
    /**
     * Default y radices used in the poster.
     */
    public static final int[] DEFAULT_YRADICES = new int[]{5, 5};
    private final int width;
    private final int height;
    private final ArrayList<Point2D> path = new ArrayList<>();

    /**
     * Get murray polygon width.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets murray polygon height.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns coordinates for path to construct murray polygons.
     *
     * @return list of coordinates
     */
    public ArrayList<Point2D> getPath() {
        return path;
    }

    /**
     * Constructor initializing generating the path for the murray polygon.
     *
     * @param noOfXRadices number of x radices
     * @param noOfYRadices number of y radices
     * @param xRadices     x radices
     * @param yRadices     y radices
     */
    public MurrayPolygonCalculations(int noOfXRadices, int noOfYRadices, int[] xRadices, int[] yRadices) {
        int maximumRadix = Math.max(noOfXRadices, noOfYRadices);
        int complexity = 2 * maximumRadix;
        int[] digits = new int[complexity + 1];
        int[] radices = new int[complexity + 1];
        boolean[] parities = new boolean[complexity + 1];
        Arrays.fill(digits, 0);
        Arrays.fill(radices, 1);
        Arrays.fill(parities, true);
        getRadices(radices, noOfXRadices, noOfYRadices, xRadices, yRadices);
        width = numberOfPoints(radices, 0, 2);
        height = numberOfPoints(radices, 1, 2);
        generatePath(radices, digits, parities);
    }

    /**
     * Generates the coordinates that represent the path of the murray polygon.
     *
     * @param radices  radices
     * @param digits   digits
     * @param parities parities
     */
    public void generatePath(int[] radices, int[] digits, boolean[] parities) {
        int noOfPoints = numberOfPoints(radices, 0, 1);
        int x2 = 0, y2 = 0;
        Point2D nextPoint = new Point2D.Double(x2, y2);
        path.add(nextPoint);
        for (int j = 0; j < noOfPoints - 1; j++) {
            int i = increment(digits, radices, 0);
            changeParities(parities, i);
            int inc = (parities[i + 1]) ? 1 : -1;
            if (i % 2 == 1) {
                x2 += inc;
            } else {
                y2 += inc;
            }
            nextPoint = new Point2D.Double(x2, y2);
            path.add(nextPoint);
        }
    }

    /**
     * Generates the murray polygon for x radices:{3,5} and y radices:{5,5}.
     *
     * @return MurrayPolygonCalculations object
     */
    public static MurrayPolygonCalculations generateMurrayPolygon() {
        return new MurrayPolygonCalculations(2, 2, DEFAULT_XRADICES, DEFAULT_YRADICES);
    }

    /**
     * Get numbr of points for a set of radices.
     *
     * @param radices   radices
     * @param start     start value
     * @param increment increment
     * @return number of points
     */
    int numberOfPoints(int[] radices, int start, int increment) {
        int res = 1;
        for (int i = start; i < radices.length; i += increment) {
            res *= radices[i];
        }
        return res;
    }

    /**
     * Combines x and y radices.
     *
     * @param radices  radices array to fill with x and y radix
     * @param xRadix   number of x radix
     * @param yRadix   number of y radix
     * @param xRadices x radices
     * @param yRadices y radices
     */
    void getRadices(int[] radices, int xRadix, int yRadix, int[] xRadices, int[] yRadices) {
        PrimitiveIterator.OfInt xRadixIterator = Arrays.stream(xRadices).iterator();
        for (int i = 0; i < (2 * xRadix); i += 2) {
            radices[i] = xRadixIterator.nextInt();
        }

        PrimitiveIterator.OfInt yRadixIterator = Arrays.stream(yRadices).iterator();
        for (int i = 1; i < (2 * yRadix); i += 2) {
            radices[i] = yRadixIterator.nextInt();
        }
    }

    /**
     * fills the digits array and returns the parity index value to start the flip operation.
     *
     * @param d digits
     * @param r radices
     * @param i index
     * @return parity index value to start flipping from
     */
    int increment(int[] d, int[] r, int i) {
        try {
            if (d[i] < r[i] - 1) {
                d[i] = d[i] + 1;
                return i;
            } else {
                d[i] = 0;
                return increment(d, r, i + 1);
            }
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Flips the boolean values of the parities array.
     *
     * @param p     parities arrray
     * @param start start index
     */
    void changeParities(boolean[] p, int start) {
        for (int i = start; i >= 0; i = i - 2) {
            p[i] = !p[i];
        }
    }

}
