public class Percolation 
{
    private boolean[][] open;
    private int top;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF connections;

    public Percolation(int N) 
    {
    
        size = N;
        top = 0;
        bottom = size * size + 1;
        connections = new WeightedQuickUnionUF(size * size + 2);
        open = new boolean[size][size];
    }


    public void open(int i, int j) 
    {
        open[i - 1][j - 1] = true;
        
        if (i == 1) 
        {
            connections.union(getconnectionsIndex(i, j), top);
        }
        if (i == size)
        {
            connections.union(getconnectionsIndex(i, j), bottom);
        }
        if (j > 1 && isOpen(i, j - 1)) 
        {
            connections.union(getconnectionsIndex(i, j), getconnectionsIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) 
        {
            connections.union(getconnectionsIndex(i, j), getconnectionsIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j))
        {
            connections.union(getconnectionsIndex(i, j), getconnectionsIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) 
        {
            connections.union(getconnectionsIndex(i, j), getconnectionsIndex(i + 1, j));
        }
    }

    public boolean isOpen(int i, int j) 
    {
        return open[i-1][j-1];
    }


    public boolean isFull(int i, int j) 
    {
        if (0 < i && i <= size && 0 < j && j <= size) 
        {
            return connections.connected(top, getconnectionsIndex(i , j));
        } 
        else 
        {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean percolates() 
    {
        return connections.connected(top, bottom);
    }

    private int getconnectionsIndex(int i, int j) 
    {
        return size * (i - 1) + j;
    }
}