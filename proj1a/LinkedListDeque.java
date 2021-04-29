//import java.util.Deque;

public class LinkedListDeque<Any> {
    public static class DequeNode<Any> {
        private Any item;
        private DequeNode prev;
        private DequeNode next;
        public DequeNode(DequeNode p, Any i, DequeNode n) {
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
    public void addFirst(Any x) {
        sentinel.next = new DequeNode(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    public void addLast(Any x) {
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

    public Any removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        Any result = (Any) sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel;
        size -= 1;
        return result;
    }

    public Any removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        Any result = (Any) sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel;
        size -= 1;
        return result;
    }

    public Any get(int index) {
        DequeNode temp = sentinel;
        for (int i = 0; i < index + 1; i++) {
            temp = temp.next;
        }
        if (temp == null || temp.next == null) {
            return null;
        }
        return (Any) temp.item;
    }
    public Any getRecursive(int index) {
        return recursiveHelper(sentinel, index, 0);
    }
    private Any recursiveHelper(DequeNode node, int index, int i) {
        if (i == index + 1) {
            return (Any) node.item;
        }
        return recursiveHelper(node.next, index, i + 1);
    }
}
