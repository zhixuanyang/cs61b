public class ArrayDeque<T> {

    private T[] item;
    private int size;
    private int initialsize;
    private int firstsize;
    private int lastsize;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 8;
        initialsize = 4;
        size = 0;
    }

    public T get(int index) {
        return item[firstsize + index + 1];
    }

    public T removeFirst() {
        T temp = item[firstsize];
        item[firstsize + 1] = null;
        firstsize += 1;
        size -= 1;
        return temp;
    }

    public T removeLast() {
        T temp = item[lastsize];
        item[lastsize - 1] = null;
        lastsize -= 1;
        size -= 1;
        return temp;
    }

    private void firstindexChecker() {
        if (firstsize <= -1 & item[item.length - 1] == null) {
            firstsize = item.length - 1;
        }
    }

    private void lastindexChecker() {
        if (lastsize >= item.length & item[0] == null) {
            lastsize = 0;
        }
    }

    public void addFirst(T x) {
        if (item[initialsize] == null) {
            item[initialsize] = x;
            firstsize = initialsize - 1;
            lastsize = initialsize + 1;
            size += 1;
        } else {
            item[firstsize] = x;
            firstsize -= 1;
            size += 1;
            firstindexChecker();
        }
    }
    public void addLast(T x) {
        if (item[initialsize] == null) {
            item[initialsize] = x;
            firstsize = initialsize - 1;
            lastsize = initialsize + 1;
            size += 1;
        } else {
            item[lastsize] = x;
            lastsize += 1;
            size += 1;
            lastindexChecker();
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = firstsize + 1; i < lastsize; i++) {
            System.out.print(item[i]);
            System.out.print(' ');
        }
        System.out.println();
    }
}
