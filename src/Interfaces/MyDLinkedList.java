package Interfaces;

public class MyDLinkedList implements ILinkedList {
    private DNode head = new DNode((Object)null, (DNode)null, (DNode)null);
    private DNode tail;
    private int size;

    public MyDLinkedList() {
        this.tail = new DNode((Object)null, this.head, (DNode)null);
        this.head.setnext(this.tail);
        this.setSize(0);
    }

    public void add(int index, Object element) {
        if (index <= this.size && index >= 0) {
            DNode temp;
            if (index == 0) {
                temp = new DNode(element, (DNode)null, this.head.getnext());
                temp.setpre(this.head);
                this.head.getnext().setpre(temp);
                this.head.setnext(temp);
                this.setSize(this.getSize() + 1);
            } else {
                temp = this.head.getnext();
                DNode add = new DNode(element, (DNode)null, (DNode)null);

                for(int i = 0; i < index; ++i) {
                    temp = temp.getnext();
                }

                add.setnext(temp);
                temp.getpre().setnext(add);
                add.setpre(temp.getpre());
                temp.setpre(add);
                this.setSize(this.getSize() + 1);
            }

        } else {
            throw new RuntimeException();
        }
    }

    public void add(Object element) {
        DNode temp = this.tail.getpre();
        DNode add = new DNode(element, (DNode)null, (DNode)null);
        add.setnext(this.tail);
        temp.setnext(add);
        add.setpre(temp);
        this.tail.setpre(add);
        this.setSize(this.getSize() + 1);
    }

    public Object get(int index) {
        if (index < this.size && index >= 0) {
            DNode temp = this.head.getnext();

            for(int i = 0; i < index; ++i) {
                temp = temp.getnext();
            }

            return temp.getElement();
        } else {
            throw new RuntimeException();
        }
    }

    public void set(int index, Object element) {
        if (index < this.size && index >= 0) {
            DNode temp = this.head.getnext();

            for(int i = 0; i < index; ++i) {
                temp = temp.getnext();
            }

            temp.setElement(element);
        } else {
            throw new RuntimeException();
        }
    }

    public void clear() {
        this.head.setnext(this.tail);
        this.tail.setpre(this.head);
        this.setSize(0);
    }

    public boolean isEmpty() {
        return this.head.getnext() == this.tail;
    }

    public void remove(int index) {
        if (index < this.size && index >= 0) {
            DNode temp = this.head.getnext();

            for(int i = 0; i < index; ++i) {
                temp = temp.getnext();
            }

            DNode p = temp.getpre();
            DNode n = temp.getnext();
            n.setpre(p);
            p.setnext(n);
            temp.setpre((DNode)null);
            temp.setnext((DNode)null);
            this.setSize(this.getSize() - 1);
        } else {
            throw new RuntimeException();
        }
    }

    public int size() {
        return this.size;
    }

    public ILinkedList sublist(int fromIndex, int toIndex) {
        if (fromIndex < this.size && toIndex < this.size && fromIndex >= 0 && toIndex >= 0) {
            MyDLinkedList list = new MyDLinkedList();
            DNode temp = this.head.getnext();

            int i;
            for(i = 0; i < fromIndex; ++i) {
                temp = temp.getnext();
            }

            for(i = fromIndex; i <= toIndex; ++i) {
                list.add(temp.getElement());
                temp = temp.getnext();
            }

            return list;
        } else {
            throw new RuntimeException();
        }
    }

    public boolean contains(Object o) {
        DNode temp = this.head.getnext();
        boolean x = false;

        for(int i = 0; i < this.size; ++i) {
            if (temp.getElement().equals(o)) {
                x = true;
                break;
            }

            temp = temp.getnext();
        }

        return x;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size1) {
        this.size = size1;
    }
}

