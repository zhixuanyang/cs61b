public class ArrayDeque<T> {

    private T[] item;
    private int size;
    private int initialindex;
    private int firstindex;
    private int lastindex;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        initialindex = 4;
        size = 0;
    }
    private void resize(int capacity) {
        if (capacity <= 0) {
            T[] temp = (T[]) new Object[8];
            item = temp;
        } else {
            T[] temp = (T[]) new Object[capacity];
            int midpoint = size / 2;
            int fromfirsttoend;
            int fromendtofirst;
            if (firstindex >= size) {
                fromfirsttoend = lastindex - 1 - firstindex;
                System.arraycopy(item, firstindex + 1, temp, midpoint - 1, fromfirsttoend);
            } else {
                fromfirsttoend = size - firstindex - 1;
                fromendtofirst = size - fromfirsttoend;
                System.arraycopy(item, firstindex + 1, temp, midpoint - 1, fromfirsttoend);
                System.arraycopy(item, 0, temp, midpoint + fromfirsttoend - 1, fromendtofirst);
            }
            item = temp;
            firstindex = midpoint - 2;
            lastindex = midpoint + size - 1;
        }
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        return item[(index + firstindex + 1) % item.length];
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else if (firstindex == item.length - 1) {
            T temp = item[0];
            item[0] = null;
            firstindex = 0;
            size -= 1;
            calculateUsageFactor();
            return temp;
        } else {
            T temp = item[firstindex + 1];
            item[firstindex + 1] = null;
            firstindex += 1;
            size -= 1;
            calculateUsageFactor();
            return temp;
        }
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else if (lastindex == 0) {
            T temp = item[item.length - 1];
            item[item.length - 1] = null;
            lastindex = item.length - 1;
            size -= 1;
            calculateUsageFactor();
            return temp;
        } else {
            T temp = item[lastindex - 1];
            item[lastindex - 1] = null;
            lastindex -= 1;
            size -= 1;
            calculateUsageFactor();
            return temp;
        }
    }

    private void firstindexChecker() {
        if (firstindex <= -1 & item[item.length - 1] == null) {
            firstindex = item.length - 1;
        }
    }

    private void lastindexChecker() {
        if (lastindex >= item.length & item[0] == null) {
            lastindex = 0;
        }
    }

    private void calculateUsageFactor() {
        double usagefactor = (double) size / (double) item.length;
        if (usagefactor < 0.25 & item.length >= 16) {
            resize(size * 2);
            firstindexChecker();
            lastindexChecker();
        }
    }

    public void addFirst(T x) {
        firstindexChecker();
        if (item[initialindex] == null & size == 0) {
            item[initialindex] = x;
            firstindex = initialindex - 1;
            lastindex = initialindex + 1;
            size += 1;
        } else {
            if (size == item.length) {
                resize(size * 2);
            }
            item[firstindex] = x;
            firstindex -= 1;
            size += 1;
            firstindexChecker();
        }
    }
    public void addLast(T x) {
        lastindexChecker();
        if (item[initialindex] == null & size == 0) {
            item[initialindex] = x;
            firstindex = initialindex - 1;
            lastindex = initialindex + 1;
            size += 1;
        } else {
            if (size == item.length) {
                resize(size * 2);
            }
            item[lastindex] = x;
            lastindex += 1;
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
        for (int i = firstindex + 1; i < lastindex; i++) {
            System.out.print(item[i]);
            System.out.print(' ');
        }
        System.out.println();
    }
}
