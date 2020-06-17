import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastCollinearPoints {
	
	private LineSegment[] lineSegmentsArr;
	
	public FastCollinearPoints(Point[] points) {
	   // finds all line segments containing 4 or more points
		
		//14-30 checks for corner cases
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
		//lineSegments is for temporarily storing linesegments that will be added to linesegment array
		ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
		
		Point[] sortedPoints = points.clone();
		Arrays.sort(sortedPoints);
		
		Point[] slopeArray;
		
		//sets p as origin
		for(Point p: sortedPoints) {
			slopeArray = sortedPoints.clone();
			Arrays.sort(slopeArray, p.slopeOrder());
			//[1,p,2,3,3,4,55,6,6]
			//if you sort by p it becomes first element
			ArrayList<Point> tempPoints;
			
			//lines 52-79 checks if the next two elements have potential to create a line segment with p
			//if they do, it continues to check after those two elements and adds them to a arraylist contianing possible linesegment points
			//(this array is initialized with a new object every iteration of the while loop 
			//i.e every time we cycle through elements
			//if it is detected there is linesegment potential, we keep the object and continue to add elements until the
			//next element is not part of the line segment
			int j = 1;
			while(j<slopeArray.length-1) {
				tempPoints = new ArrayList<Point>();
				
				if(Math.abs(p.slopeTo(slopeArray[j])) == Math.abs(p.slopeTo(slopeArray[j+1]))) {
					tempPoints.add(p);
					tempPoints.add(slopeArray[j]);
					tempPoints.add(slopeArray[j+1]);
					//absolute value is used to check points both before and after p. (relative on graph location)
					if(j+2>slopeArray.length-1) {
						break;
					}
					j = j+2;
					
					while(Math.abs(p.slopeTo(slopeArray[j]))== Math.abs(p.slopeTo(slopeArray[j-1]))){
						tempPoints.add(slopeArray[j]);
						j++;
						
						if(j==slopeArray.length) {
							break;
							//if statements placed near every increment of j to prevent indexout of bounds 
						}
					}
					
					
					Point[] segmentPoints = tempPoints.toArray(new Point[0]);
					Arrays.sort(segmentPoints);
					
					//checks if the points in the line segment statisfy requirements of:
					//1: linesegment is at least length 4
					//2: p is the smallest point (this ensures no duplicates or subsegments are inserted
					
					if(segmentPoints[0]==p && segmentPoints.length>=4) {
						//if p is the smallest point, add p and its line segment.
						lineSegments.add(new LineSegment(p, segmentPoints[segmentPoints.length-1]));
					}
					
				}else {
					//j incremented if no line segment potential is detected in the next two elements
					// by incrementing j, we can continue to check for line segment potentials and enter the previous if statement if 
					//potential is detected/
					j++;
					
				}
				
			}
			//convert linesegment arraylist to array for return value, as API requests return values of array[LineSegment] 
			//and not ArrayList
			this.lineSegmentsArr = lineSegments.toArray(new LineSegment[0]);
		}

   }
	//returns number of maximal linesegments by calling length of linesgments array
   public int numberOfSegments() {
	   return lineSegmentsArr.length;
   }
   //returns entire linesegment array
   public LineSegment[] segments() {
	   // returns copy of linesegment array to avoid immutability issues 
	   return Arrays.copyOf(lineSegmentsArr, lineSegmentsArr.length);
   }
   
   public static void main(String[] args) {
	   Point point1 = new Point(10616,3168);
		Point point2 = new Point(10616,4849);
		Point point3 = new Point(10616,5231);
		Point point4 = new Point(10616,5805);
		Point point5 = new Point(10000,100);
		Point point6 = new Point(3000,4000);
		Point point7 = new Point(4000,4000);
		Point point8 = new Point(5000,4000);
		Point point9 = new Point(6000,4000);
		
		Point point10 = new Point(5000,5000);
		Point point11 = new Point(7000,3000);
		
		Point[] points = new Point[11];
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
		points[3] = point4;
		points[4] = point5;
		points[5] = point6;
		points[6] = point7;
		points[7] = point8;
		points[8] = point9;
		points[9] = point10;
		points[10] = point11;
		
		FastCollinearPoints test1 = new FastCollinearPoints(points);
		BruteCollinearPoints test = new BruteCollinearPoints(points);
		System.out.println(test1.numberOfSegments());
		System.out.println(test.numberOfSegments());
		System.out.println(test1.segments());
		
   }
}