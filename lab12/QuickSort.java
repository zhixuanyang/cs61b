import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            if (pivot.compareTo(item) == 0) {
                equal.enqueue(item);
            } else if (pivot.compareTo(item) < 0) {
                greater.enqueue(item);
            } else if (pivot.compareTo(item) > 0) {
                less.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Item> result;
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Item pivot = getRandomItem(items);
        partition(items, pivot, less, equal, greater);
        result = catenate(less, equal);
        result = catenate(result, greater);
        return result;
    }

    public static void main(String[] args) {
        Queue<Integer> original = new Queue<>();
        original.enqueue(3);
        original.enqueue(2);
        original.enqueue(8);
        original.enqueue(10);
        original.enqueue(6);
        original.enqueue(5);
        original.enqueue(18);
        original.enqueue(7);
        original.enqueue(120);
        original.enqueue(0);
        original.enqueue(1);
        System.out.println(original);
        Queue<Integer> test = QuickSort.quickSort(original);
        System.out.println(original);
        System.out.println(test);
    }
}
