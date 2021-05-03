public class OffByN implements CharacterComparator {

    private int number;

    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == number) {
            return true;
        }
        return false;
    }

    public void offByN(int N) {
        number = N;
    }

}
