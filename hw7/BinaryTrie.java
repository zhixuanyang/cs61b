import java.io.Serializable;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import edu.princeton.cs.algs4.MinPQ;
public class BinaryTrie implements Serializable {
    private static int count;
    private class Node implements Comparable {
        private char ch;
        private int freq;
        private Node left, right;
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }
        public int compareTo(Object o) {
            Node that = (Node) o;
            return this.freq - that.freq;
        }
    }
    private Node buildTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Character c : frequencyTable.keySet()) {
            pq.insert(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        Node root = buildTrie(frequencyTable);
        String[] stringtable = new String[frequencyTable.size()];
        count = frequencyTable.size() - 1;
        buildCode(stringtable, root, "");
        Map<Character, Integer> sorted = sortByValue(frequencyTable);
        System.out.print(sorted);
        Map<Character, Character> codingtable = new HashMap<>();
    }

    private Map<Character, Integer> sortByValue(Map<Character, Integer> frequencyTable) {
        List<Entry<Character, Integer>> list = new LinkedList<>(frequencyTable.entrySet());
        Collections.sort(list, new Comparator<Entry<Character, Integer>>() {
            @Override
            public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map<Character, Integer> sorted = new LinkedHashMap<Character, Integer>();
        for (Entry<Character, Integer> entry : list) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + "0");
            buildCode(st, x.right, s + "1");
        } else {
            st[count] = s;
            count -= 1;
        }
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        return null;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return null;
    }
}
