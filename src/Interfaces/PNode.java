package Interfaces;

public class PNode {
    private Object element;
    private int key;
    private PNode next;

    public PNode(Object ele, PNode n, int k) {
        this.element = ele;
        this.next = n;
        this.key = k;
    }

    void setEle(Object newEle) {
        this.element = newEle;
    }

    void setNext(PNode newNxt) {
        this.next = newNxt;
    }

    Object getELe() {
        return this.element;
    }

    PNode getNxt() {
        return this.next;
    }

    int getKey() {
        return this.key;
    }

    void setKey(int k) {
        this.key = k;
    }
}

