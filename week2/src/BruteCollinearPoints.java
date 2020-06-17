import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
	
	private Point[] sortedPoints;
	private List<LineSegment> segments;
	private LineSegment[] lineSegments;
	
	public BruteCollinearPoints(Point[] points) {
		if(points == null) {
			throw new IllegalArgumentException();
		}
		
		
		
		for(int i = 0; i<points.length; i++) {
			if(points[i] == null) {
				throw new IllegalArgumentException("null element");
			}
		}
		
		for(int i = 0; i<points.length-1; i++) {
			for(int j = i+1; j<points.length; j++) {
				if(points[i].compareTo(points[j])==0){
					throw new IllegalArgumentException("repeated points");
				}
			}
		}
		this.sortedPoints = new Point[points.length];
		
		
		sortedPoints = Arrays.copyOf(points, points.length);
		//creates copy of points to avoid immutability problem
		Arrays.sort(sortedPoints);
		//sorts new copy using natural order established in Point class
		this.segments = new ArrayList<LineSegment>();
		//creates new ArrayList that will be used to initially store line segments
		
		for(int i = 0; i<sortedPoints.length-3; i++) {
			Point point1 = sortedPoints[i];
			
			for(int j = i+1; j<sortedPoints.length-2; j++) {
				Point point2 = sortedPoints[j];
				double slope1 = point1.slopeTo(point2);
				
				for(int k = j+1; k<sortedPoints.length-1; k++) {
					Point point3 = sortedPoints[k];
					double slope2 = point1.slopeTo(point3);
					
					for(int m = k+1; m<sortedPoints.length; m++) {
						Point point4 = sortedPoints[m];
						double slope3 = point1.slopeTo(point4);
						
						if(slope1 == slope2 && slope1 == slope3) {
							segments.add(new LineSegment(point1, point4));
							/*nested for loops check every single combination of 4-tuples
							 * by treating sortedPoints[i] as origin, and comparing 
							 * slopes of j k and m to i. If they have the same
							 * slope, they are collinear and a new linesegment containing the 
							 * end points is added to the segments ArrayList
							 */
						}
					}
				}
			}
		}
		
		lineSegments = segments.toArray(new LineSegment[0]);
		/*because API requires segments() method to return a LineSegment[] array,
		 * segments ArrayList is copied to lineSegments array
		 */
		
	}
	//returns number of linesegments
	public int numberOfSegments() {
		return lineSegments.length;
	}
	//returns copy of linesegments array 
	public LineSegment[] segments() {	
		
		return Arrays.copyOf(lineSegments, lineSegments.length); 
	}
	
	/*private static void main(String[] args) {
		// TODO Auto-generated method stub
		Point point1 = new Point(1,1);
		Point point2 = new Point(0,0);
		Point point3 = new Point(2,2);
		Point point4 = new Point(4,5);
		Point point5 = new Point(3,3);
		Point point6 = new Point(5,6);
		Point point7 = new Point(6,7);
		Point point8 = new Point(7,8);
		
		Point[] points = new Point[8];
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
		points[3] = point4;
		points[4] = point5;
		points[5] = point6;
		points[6] = point7;
		points[7] = point8;
		
		BruteCollinearPoints test = new BruteCollinearPoints(points);
		
		System.out.println(test.numberOfSegments());
		System.out.println(test.segments());
		
	}*/
}


