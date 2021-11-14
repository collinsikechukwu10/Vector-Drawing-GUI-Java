package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PrimitiveIterator;

public class MurrayPolygonCalculations {
    int noOfXRadices;
    int noOfYRadices;
    int[] xRadices;
    int[] yRadices;
    int width;
    int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Point2D> getNextSegment() {
        return nextSegment;
    }

    private final ArrayList<Point2D> nextSegment = new ArrayList<Point2D>();

    public MurrayPolygonCalculations(int noOfXRadices, int noOfYRadices, int[] xRadices, int[] yRadices) {
        this.noOfXRadices = noOfXRadices;
        this.noOfYRadices = noOfYRadices;
        this.xRadices = xRadices;
        this.yRadices = yRadices;
        int maximumRadix = Math.max(noOfXRadices, noOfYRadices);
        int complexity = 2 * maximumRadix;
        int[] digits = new int[complexity + 1];
        int[] radices = new int[complexity + 1];
        boolean[] parities = new boolean[complexity + 1];
        Arrays.fill(digits, 0);
        Arrays.fill(radices, 1);
        Arrays.fill(parities, true);
        getRadices(radices, noOfXRadices, noOfYRadices);
        width = numberOfPoints(radices,0,2);
        height = numberOfPoints(radices,1,2);
        int noOfPoints = numberOfPoints(radices, 0, 1);
        int x2 = 0, y2 = 0;
        for (int j = 0; j < noOfPoints; j++) {
            int i = increment(digits, radices, 0);
            changeParities(parities, i);
            int inc = (parities[i + 1]) ? 1 : -1;
            if (i % 2 == 1) {
                x2 += inc;
            } else {
                y2 += inc;
            }
            Point2D nextPoint = new Point2D.Double(x2, y2);
            nextSegment.add(nextPoint);
        }


    }

    public static ArrayList<Point2D> generatePoints() {
        int[] xRadices = new int[]{3, 5};
        int[] yRadices = new int[]{5, 5};
        return new MurrayPolygonCalculations(2, 2, xRadices, yRadices).getNextSegment();
    }

    int numberOfPoints(int[] radices, int start, int increment) {
        int res = 1;
        for (int i = start; i < radices.length; i += increment) {
            res *= radices[i];
        }
        return res;
    }

    void getRadices(int[] radices, int xRadix, int yRadix) {
        PrimitiveIterator.OfInt xRadixIterator = Arrays.stream(xRadices).iterator();
        for (int i = 0; i < (2 * xRadix); i += 2) {
            radices[i] = xRadixIterator.nextInt();
        }

        PrimitiveIterator.OfInt yRadixIterator = Arrays.stream(yRadices).iterator();
        for (int i = 1; i < (2 * yRadix); i += 2) {
            radices[i] = yRadixIterator.nextInt();
        }
    }

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

    void changeParities(boolean[] p, int start) {
        for (int i = start; i >= 0; i = i - 2) {
            p[i] = !p[i];
        }
    }

    public static void main(String[] args) {
        MurrayPolygonCalculations f = new MurrayPolygonCalculations(
                2,
                2,
                new int[]{3, 5},
                new int[]{5, 5});
        f.getNextSegment().forEach(x -> System.out.println("x: " + x.getX() + ",y: " + x.getY()));
    }

}
