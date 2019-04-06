package Interfaces;

public class DNode {
    DNode next;
    DNode pre;
    public Object element;

    public DNode(Object value, DNode p, DNode n) {
        this.element = value;
        this.pre = p;
        this.next = n;
    }

    public Object getElement() {
        return this.element;
    }

    public DNode getnext() {
        return this.next;
    }

    public DNode getpre() {
        return this.pre;
    }

    public void setnext(DNode n) {
        this.next = n;
    }

    public void setpre(DNode p) {
        this.pre = p;
    }

    public void setElement(Object e) {
        this.element = e;
    }
}

