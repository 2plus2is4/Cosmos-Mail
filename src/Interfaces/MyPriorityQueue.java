package Interfaces;

public class MyPriorityQueue implements IPriorityQueue {
    int size = 0;
    PNode min = null;
    PNode max = null;

    public MyPriorityQueue() {
    }

    public void insert(Object item, int key) {
        if (key <= 0) {
            throw new RuntimeException();
        } else {
            PNode temp;
            if (this.size == 0) {
                temp = new PNode(item, this.max, key);
                this.min = temp;
            } else if (this.size == 1) {
                temp = new PNode(item, (PNode)null, key);
                if (this.min.getKey() <= key) {
                    this.max = temp;
                    this.min.setNext(this.max);
                    if (this.min.getNxt() == null) {
                        throw new RuntimeException();
                    }

                    System.out.println("ssa:" + this.min.getNxt().getELe());
                } else {
                    this.max = new PNode(this.min.getELe(), (PNode)null, this.min.getKey());
                    this.min = temp;
                    this.min.setNext(this.max);
                    if (this.min.getNxt() == null) {
                        throw new RuntimeException();
                    }
                }
            } else if (this.max.getKey() <= key) {
                temp = new PNode(item, (PNode)null, key);
                this.max.setNext(temp);
                this.max = temp;
                System.out.println("ssa:" + this.min.getNxt().getELe());
            } else {
                temp = new PNode(item, (PNode)null, key);
                PNode temp2;
                if (this.min.getKey() > key) {
                    temp2 = new PNode(this.min.getELe(), this.min.getNxt(), this.min.getKey());
                    temp.setNext(temp2);
                    this.min = temp;
                } else {
                    temp = this.min;

                    for(temp2 = temp.getNxt(); temp2.getKey() <= key && temp2.getNxt() != null; temp2 = temp2.getNxt()) {
                        temp = temp.getNxt();
                    }

                    if (temp2.getNxt() == null && temp2.getKey() <= key) {
                        temp = temp.getNxt();
                        temp2 = temp2.getNxt();
                    }

                    PNode temp3 = new PNode(item, temp2, key);
                    temp.setNext(temp3);
                }
            }

            ++this.size;
        }
    }

    public Object removeMin() {
        if (this.isEmpty()) {
            throw new RuntimeException();
        } else {
            Object temp = this.min.getELe();
            this.min = this.min.getNxt();
            --this.size;
            return temp;
        }
    }

    public Object min() {
        if (this.isEmpty()) {
            throw new RuntimeException();
        } else {
            return this.min.getELe();
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }
}

