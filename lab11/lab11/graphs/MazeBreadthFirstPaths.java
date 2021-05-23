package lab11.graphs;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new ArrayDeque<Integer>();
        q.add(source);
        marked[source] = true;
        announce();
        while (!q.isEmpty()) {
            int tempSource = q.remove();
            for (int w : maze.adj(tempSource)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = tempSource;
                    distTo[w] = distTo[tempSource] + 1;
                    announce();
                    if (w == target) {
                        return;
                    }
                    q.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

