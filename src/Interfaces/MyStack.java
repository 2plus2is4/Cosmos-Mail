package Interfaces;

public class MyStack implements IStack {
    private SNode top = null;
    private int size = 0;

    public MyStack() {
    }

    public Object pop() {
        if (this.isEmpty()) {
            throw new RuntimeException();
        } else {
            Object temp = this.top.getElement();
            this.top = this.top.getNext();
            --this.size;
            return temp;
        }
    }

    public Object peek() {
        if (this.isEmpty()) {
            throw new RuntimeException();
        } else {
            return this.top.getElement();
        }
    }

    public void push(Object element) {
        SNode temp = new SNode(element, this.top);
        this.top = temp;
        ++this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }
}

