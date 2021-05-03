//import java.util.Deque;

public class LinkedListDeque<T> implements Deque<T> {
    private static class DequeNode<T> {
        private T item;
        private DequeNode prev;
        private DequeNode next;
        DequeNode(DequeNode p, T i, DequeNode n) {
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
    @Override
    public void addFirst(T x) {
        sentinel.next = new DequeNode(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    @Override
    public void addLast(T x) {
        sentinel.prev = new DequeNode(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }
    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        DequeNode temp = sentinel;
        while (temp.next != sentinel) {
            System.out.print(temp.next.item);
            System.out.print(' ');
            temp = temp.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = (T) sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return result;
    }
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = (T) sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return result;
    }
    @Override
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
