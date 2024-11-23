
// this class for helping us with list 
public class LinkedList<T> {

   
    @SuppressWarnings("hiding")
    class Node<T> {
        public T data;
        public Node<T> next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(T val) {
            data = val;
            next = null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    // LinkedList class fields
    private Node<T> head;
    private Node<T> current;
    int size;

    public LinkedList() {
        head = current = null;
        size = 0;
    }

    public boolean empty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public boolean last() {
        return current.next == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    public T retrieve() {
        return current != null ? current.data : null;
    }

    public void update(T val) {
        if (current != null) {
            current.data = val;
        }
    }

    public void insert(T val) {
        Node<T> tmp;
        if (empty()) {
            current = head = new Node<>(val);
        } else {
            tmp = current.next;
            current.next = new Node<>(val);
            current = current.next;
            current.next = tmp;
        }
        size++;
    }

    public void remove() {
        if (current == head) {
            head = head.next;
        } else {
            Node<T> tmp = head;
            while (tmp.next != current) {
                tmp = tmp.next;
            }
            tmp.next = current.next;
        }

        if (current.next == null) {
            current = head;
        } else {
            current = current.next;
        }
        size--;
    }
}
