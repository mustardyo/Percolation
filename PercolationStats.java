public class PercolationStats {
    public double runCount;
    public int randomRow;
    public int randomCol;
    public double gridSize;
    public double openSquares;
    public double[] thresholdArray;
    public double sumOfDifferenceSquared;
        
    public PercolationStats(int N, int T) 
    {
        gridSize = N*N;
        thresholdArray = new double[T];
        for(int i=0; i<T; i++){
            Percolation percolation = new Percolation(N);
            openSquares = 0.0;
            while(!percolation.percolates()) 
            {
                randomRow = StdRandom.uniform(0,N);
                randomCol = StdRandom.uniform(0,N);
                if(!percolation.isOpen(randomRow,randomCol))
                {
                    percolation.open(randomRow,randomCol);
                    openSquares++;
                }
            }
            thresholdArray[i] = openSquares/gridSize;
            runCount++; 
        }
    };
    
    public double runCount()
    {
         return runCount;
    }
    
    public double mean()
    {
        double sum = 0.0;
        for(int i=0; i < runCount; i++)
        {
            sum += thresholdArray[i];
        }
        return sum/runCount;
    }
    
    public double stddev() 
    {
        for(int i= 0; i< runCount; i++)
        {
           double difference = thresholdArray[i] - mean(); 
           sumOfDifferenceSquared += difference*difference;  
        }
        double variance = sumOfDifferenceSquared/(runCount-1);
        double stdDev = Math.sqrt(variance);
        return stdDev;
    }
    
    public double confidenceLo() 
    {
        return mean() - ((1.96*stddev())/Math.sqrt(runCount));    
    }             

    public double confidenceHi() 
    {
        return mean() + ((1.96*stddev())/Math.sqrt(runCount));
    }
    
    public static void main(String[] args) 
    {
        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(row, col);

        System.out.println("mean:" + percolationStats.mean());
        System.out.println("standard deviation:" + percolationStats.stddev());
        System.out.println("95% confience interval:" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());
        
    }
   
}