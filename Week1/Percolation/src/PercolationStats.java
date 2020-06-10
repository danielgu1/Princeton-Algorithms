import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
	
	private int trials;
	private double[] thresholdList;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	
    	this.trials = trials;
    	this.thresholdList = new double[trials];
    	if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }

        if (trials < 1) {
            throw new IllegalArgumentException("You must run percolation at least once");
        }
    	
    	for(int i = 0; i<trials; i++) {
    		Percolation percolation = new Percolation(n);
    		
    		while(!percolation.percolates()) {
    			int row = StdRandom.uniform(1, n+1);
    			int col = StdRandom.uniform(1, n+1);
    			
    			percolation.open(row, col);
    		}
    		
    		thresholdList[i] = (double) percolation.numberOfOpenSites() / (n * n);
    	}
    	
    }

    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(thresholdList);

    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(thresholdList);
    }

    // Low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // Test client
    public static void main(String[] args)
    {
    	System.out.println("Input size of grid:");
        int N = StdIn.readInt();
        System.out.println("Input number of experiments:");
        int T = StdIn.readInt();
        PercolationStats percstats = new PercolationStats(N, T);
        System.out.println("mean = " + percstats.mean());
        System.out.println("stddev = " + percstats.stddev());
        System.out.println("95% confidence interval = [" + percstats.confidenceLo() + ", " + percstats.confidenceHi() + "]" );
}
}
