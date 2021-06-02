import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> result = new HashMap<>();
        for (char c : inputSymbols) {
            if (result.containsKey(c)) {
                result.put(c, result.get(c) + 1);
            } else {
                result.put(c, 1);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        char[] inputfile = FileUtils.readFile(args[0]);
        Map<Character, Integer> freqTable = buildFrequencyTable(inputfile);
        BinaryTrie trie = new BinaryTrie(freqTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);
        ow.writeObject(inputfile.length);
        Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
        List<BitSequence> temp = new ArrayList<>();
        for (int i = 0; i < inputfile.length; i++) {
            temp.add(lookupTable.get(inputfile[i]));
        }
        BitSequence all = BitSequence.assemble(temp);
        ow.writeObject(all);
    }
}
