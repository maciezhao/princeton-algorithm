import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
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

        for (int i = 0; i < sortedPoints.length; i++) {
            for (int j = i + 1; j < sortedPoints.length; j++) {
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    for (int l = k + 1; l < sortedPoints.length; l++) {
                        if (sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[k]) 
                            && sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[l])) {
                                segmentList.add(new LineSegment(sortedPoints[i], sortedPoints[l]));
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }


    public LineSegment[] segments() {
        return segments.clone();
    }
}

