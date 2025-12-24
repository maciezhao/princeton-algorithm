import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }

        List<LineSegment> segmentList = new ArrayList<>();

        for (Point p : sortedPoints) {
            Point[] otherPoints = sortedPoints.clone();
            Arrays.sort(otherPoints, p.slopeOrder());
            int j = 1;
            while (j < otherPoints.length) {
                List<Point> collinearPoints = new ArrayList<>();
                double slope = p.slopeTo(otherPoints[j]);
                while (j < otherPoints.length && p.slopeTo(otherPoints[j]) == slope) {
                    collinearPoints.add(otherPoints[j]);
                    j++;
                }


                if (collinearPoints.size() >= 3) {
                    collinearPoints.add(p);
                    Collections.sort(collinearPoints);
                    if (collinearPoints.get(0).equals(p)) {
                        Point min = collinearPoints.get(0);
                        Point max = collinearPoints.get(collinearPoints.size() - 1);
                        segmentList.add(new LineSegment(min, max));
                    }
                }
            }

        }
        segments = segmentList.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
