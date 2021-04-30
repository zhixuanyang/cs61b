//import java.util.Deque;

public class LinkedListDeque<T> {
    public static class DequeNode<T> {
        private T item;
        private DequeNode prev;
        private DequeNode next;
        public DequeNode(DequeNode p, T i, DequeNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private DequeNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DequeNode(null, 321, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(T x) {
        sentinel.next = new DequeNode(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    public void addLast(T x) {
        sentinel.prev = new DequeNode(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }
    public int size() {
        return size;
    }

    public void printDeque() {
        DequeNode temp = sentinel;
        while (temp.next != sentinel) {
            System.out.print(temp.next.item);
            System.out.print(' ');
            temp = temp.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = (T) sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = null;
        size -= 1;
        if (size == 0 || size == 1) {
            sentinel.next.prev = sentinel;
        }
        return result;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = (T) sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = null;
        size -= 1;
        if (size == 0) {
            sentinel.next = sentinel;
        } else if (size == 1) {
            sentinel.prev.next = sentinel;
        }
        return result;
    }

    public T get(int index) {
        DequeNode temp = sentinel;
        for (int i = 0; i < index + 1; i++) {
            temp = temp.next;
        }
        if (temp == null || temp.next == null) {
            return null;
        }
        return (T) temp.item;
    }
    public T getRecursive(int index) {
        return recursiveHelper(sentinel, index, 0);
    }

    private T recursiveHelper(DequeNode node, int index, int i) {
        if (i == index + 1) {
            return (T) node.item;
        }
        return recursiveHelper(node.next, index, i + 1);
    }
}
