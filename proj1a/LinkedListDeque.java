import java.util.Deque;

public class LinkedListDeque<Any>{
    public static class DequeNode<Any> {
        public Any item;
        public DequeNode prev;
        public DequeNode next;
        public DequeNode(DequeNode p, Any i, DequeNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private DequeNode front;
    private DequeNode last;
    private int size;

    public LinkedListDeque(){
        front = new DequeNode(null, 63, null);
        last = new DequeNode(null, 232,null);
        front.next = last;
        size = 0;
    }
    public void addFirst(Any x){
       front.next = new DequeNode(front, x, front.next);
       size += 1;
    }
    public void addLast(Any x){
        last.prev = new DequeNode(last.prev, x, last);
        size += 1;
    }
    public boolean isEmpty(){
        if (front.next == last){
            return true;
        }
        else{
            return false;
        }
    }
    public int size(){
        return size;
    }

    public void printDeque(){
        DequeNode temp = front;
        while(temp.next != last){
            System.out.print(temp.next.item);
            System.out.print(' ');
            temp = temp.next;
        }
        System.out.println();
    }

    /*public Any removeFirst(){
        if(this.front.next == null){
            return null;
        }
        Any temp = (Any) front.next.item;
        DequeNode test = front;
        front.next.item = null;


        return temp;
    }*/
    /*public Any removeLast(){
        if(this.)
    }*/

}
