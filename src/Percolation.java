import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int top;
	private int bottom;
	private int openSites = 0;
	
	private int gridDimensions;
	private WeightedQuickUnionUF ufForPercolation;
	private WeightedQuickUnionUF ufForFullness;
	
	private boolean[][] sites;
	
	

	
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n<=0) throw new IllegalArgumentException("ERROR");
    	
    	sites = new boolean[n][n];
    	ufForPercolation = new WeightedQuickUnionUF(n*n+2);
    	ufForFullness = new WeightedQuickUnionUF(n*n+1);
    	this.top = 0;
    	this.bottom = n*2+1;
    	this.gridDimensions = n;
    	
    	for (int col = 1; col <= gridDimensions; col++) {
            int rowTop = 1;
            int siteTopIndex = getIndexByRowAndColumn(rowTop, col);
            ufForPercolation.union(top, siteTopIndex);
            ufForFullness.union(top, siteTopIndex);

            int rowBottom = gridDimensions;
            int siteBottomIndex = getIndexByRowAndColumn(rowBottom, col);
            ufForPercolation.union(bottom, siteBottomIndex);
        }
    	
    	
    	
    }
    private int getIndexByRowAndColumn(int row, int col){

        return ((row - 1) * gridDimensions) + col;
    }
    //test method to see if elements must be set to true or false
    

    // opens the site (row, col) if it is not open already
    public void open(int i, int j) {
    	//checks if indices are in grid
    	if(checkIndex(i, j)) {
    		//checks if site is not open. If not open, assigns site true to open.
    		if(!this.isOpen(i, j)) {
    			sites[i-1][j-1] = true;
    			openSites++;
    			//obtains index of site that was opened
    			int siteIndex = getIndexByRowAndColumn(i,j);
	
    			//connect with left neighbor
    		
    			if(j>1 && this.isOpen(i, j-1)){
    				int siteLeftIndex = siteIndex-1;
    				ufForPercolation.union(siteIndex, siteLeftIndex);
    				ufForFullness.union(siteIndex, siteLeftIndex);
    			}
    			//connect with right neighbor
    			if(this.isOpen(i, j+1)&&j<gridDimensions){
    				int siteRightIndex = siteIndex+1;
    				ufForPercolation.union(siteIndex, siteRightIndex);
    				ufForFullness.union(siteIndex, siteRightIndex);
    			}
    			//connect with bottom neighbor
    			
    			if(this.isOpen(i+1, j)&& i<gridDimensions) {
    				
    				ufForPercolation.union(siteIndex, ((i)*this.gridDimensions) + j);
    				ufForFullness.union(siteIndex,((i)*this.gridDimensions) + j);
    			}
    			//connect with top neighbor
    			
    			if(this.isOpen(i-1, j)&&i>gridDimensions) {
    				ufForPercolation.union(siteIndex, ((i-2)*this.gridDimensions) + j);
    				ufForFullness.union(siteIndex, ((i-2)*this.gridDimensions) + j);
    			}
    		}
    	} else {
    		throw new IndexOutOfBoundsException();
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if(col>gridDimensions||col<1||row>gridDimensions||row<1) {
    		return false;
    	}
    	else if(sites[row-1][col-1] == true) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	int siteIndex = getIndexByRowAndColumn(row, col);
    	
    	return (isOpen(row, col) && ufForFullness.connected(top, siteIndex));
    	
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	return (this.ufForPercolation.connected(top, bottom));
    }
    
    private boolean checkIndex(int i, int j)
    {
        if (i < 1 || i > gridDimensions || j < 1 || j > gridDimensions) return false;
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
	    
    }
}
