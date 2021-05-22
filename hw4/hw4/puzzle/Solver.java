package hw4.puzzle;
import java.util.HashMap;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private int move;
    private HashMap<WorldState, Integer> hm = new HashMap<>();
    private Stack<WorldState> path = new Stack<>();

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState ws;
        private int move;
        private SearchNode prev;
        private int estimated;

        SearchNode(WorldState ws, int moves, SearchNode prev) {
            this.ws = ws;
            this.move = moves;
            this.prev = prev;
            if (hm.containsKey(ws)) {
                this.estimated = hm.get(ws);
            } else {
                this.estimated = ws.estimatedDistanceToGoal();
                hm.put(ws, this.estimated);
            }
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.move + this.estimated) - (o.move + o.estimated);
        }
    }

    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode curr = new SearchNode(initial, 0, null);
        while (!curr.ws.isGoal()) {
            for (WorldState neighbor : curr.ws.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.ws)) {
                    pq.insert(new SearchNode(neighbor, curr.move + 1, curr));
                }
            }
            curr = pq.delMin();
        }
        move = curr.move;
        while (curr != null) {
            path.push(curr.ws);
            curr = curr.prev;
        }
    }

    public int moves() {
        return move;
    }

    public Iterable<WorldState> solution() {
        return path;
    }
}
