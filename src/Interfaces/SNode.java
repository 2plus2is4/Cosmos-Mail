package Interfaces;

public class SNode {
    private Object element;
    private SNode next;

    public SNode() {
    }

    public SNode(Object obj, SNode n) {
        this.element = obj;
        this.next = n;
    }

    public Object getElement() {
        return this.element;
    }

    public SNode getNext() {
        return this.next;
    }

    public void setElement(Object newEle) {
        this.element = newEle;
    }

    public void setNext(SNode newNxt) {
        this.next = newNxt;
    }
}

