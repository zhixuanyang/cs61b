public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        Object first = or.readObject();
        BinaryTrie trie = (BinaryTrie) first;
        Object second = or.readObject();
        int length = (int) second;
        Object third = or.readObject();
        BitSequence input = (BitSequence) third;
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            Match match = trie.longestPrefixMatch(input);
            result[i] = match.getSymbol();
            input = input.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], result);
    }
}
