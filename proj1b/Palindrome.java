public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque temp = new LinkedListDeque();
        char[] wordlist = word.toCharArray();
        for (char w : wordlist) {
            temp.addLast(w);
        }
        return temp;
    }

    public boolean isPalindrome(String word) {
        Deque temp = wordToDeque(word);
        Deque temptest = wordToDeque(word);
        while (!temp.isEmpty()) {
            if (temp.removeFirst() != temptest.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int size = word.length();
        Deque<Character> temp = wordToDeque(word);
        if (size == 0 || size == 1) {
            return true;
        }
        if (size % 2 == 1) {
            for (int i = 0; i < (size - 1) / 2; i++) {
                if (!cc.equalChars(temp.removeFirst(), temp.removeLast())) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size / 2; i++) {
                if (!cc.equalChars(temp.removeFirst(), temp.removeLast())) {
                    return false;
                }
            }
        }
        return true;
    }
}
