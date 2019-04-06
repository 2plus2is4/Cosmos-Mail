package Interfaces;

public class Node {
    private Object element;
    private Node next;

    public Node(Object o, Node n) {
        this.element = o;
        this.next = n;
    }

    public Object getElement() {
        return this.element;
    }

    public Node getNext() {
        return this.next;
    }

    public void setElement(Object newEle) {
        this.element = newEle;
    }

    public void setNext(Node newNode) {
        this.next = newNode;
    }
}

