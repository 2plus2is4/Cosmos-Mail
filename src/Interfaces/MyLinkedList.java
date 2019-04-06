package Interfaces;

public class MyLinkedList implements ILinkedList {
    private Node head = new Node((Object)null, (Node)null);
    private long size;

    public MyLinkedList() {
        this.setSize(0L);
    }

    public void add(int index, Object element) {
        Node temp = this.head;
        Node added = new Node(element, (Node)null);
        int index1 = index;
        if ((long)index <= this.size && index >= 0) {
            if (index == 0) {
                if (this.head.getElement() == null) {
                    this.head.setElement(element);
                    this.head.setNext((Node)null);
                } else {
                    added.setNext(this.head);
                    this.head = added;
                }

                this.setSize(this.getSize() + 1L);
            } else if ((long)index >= this.size) {
                if ((long)index == this.size) {
                    while(temp.getNext() != null) {
                        temp = temp.getNext();
                    }

                    temp.setNext(added);
                    this.setSize(this.getSize() + 1L);
                }
            } else {
                while(index1 > 1) {
                    temp = temp.getNext();
                    --index1;
                }

                added.setNext(temp.getNext());
                temp.setNext(added);
                this.setSize(this.getSize() + 1L);
            }

        } else {
            throw new RuntimeException();
        }
    }

    public void add(Object element) {
        Node added = new Node(element, (Node)null);
        Node temp = this.head;
        if (this.head.getElement() == null) {
            this.head.setElement(element);
            this.setSize(this.getSize() + 1L);
        } else {
            while(true) {
                if (temp.getNext() == null) {
                    temp.setNext(added);
                    this.setSize(this.getSize() + 1L);
                    break;
                }

                temp = temp.getNext();
            }
        }

    }

    public Object get(int index) {
        boolean check = false;
        Node temp = this.head;
        if ((long)index <= this.size && index >= 0) {
            for(int i = 0; i < index; ++i) {
                if (temp.getNext() == null) {
                    check = true;
                    break;
                }

                temp = temp.getNext();
            }

            return !check ? temp.getElement() : null;
        } else {
            throw new RuntimeException();
        }
    }

    public void set(int index, Object element) {
        Node temp = this.head;
        if ((long)index <= this.size && index >= 0) {
            if ((long)index < this.size) {
                for(int i = 0; i < index; ++i) {
                    temp = temp.getNext();
                }
            }

            temp.setElement(element);
        } else {
            throw new RuntimeException();
        }
    }

    public void clear() {
        this.head.setNext((Node)null);
        this.head.setElement((Object)null);
        this.setSize(0L);
    }

    public boolean isEmpty() {
        return this.head.getElement() == null;
    }

    public void remove(int index) {
        Node temp = this.head;
        if ((long)index < this.size && index >= 0) {
            if (index == 0) {
                this.head = temp.getNext();
                temp.setNext((Node)null);
            } else {
                for(int i = 0; i < index - 1; ++i) {
                    temp = temp.getNext();
                }

                Node temp2 = temp.getNext();
                temp.setNext(temp2.getNext());
                temp2.setNext((Node)null);
            }

            this.setSize(this.getSize() - 1L);
        } else {
            throw new RuntimeException();
        }
    }

    public int size() {
        return (int)this.size;
    }

    public ILinkedList sublist(int fromIndex, int toIndex) {
        MyLinkedList list = new MyLinkedList();
        if ((long)fromIndex < this.size && (long)toIndex < this.size && fromIndex >= 0 && toIndex >= 0) {
            Node temp = this.head;

            int i;
            for(i = 0; i < fromIndex; ++i) {
                temp = temp.getNext();
            }

            for(i = fromIndex; i <= toIndex; ++i) {
                list.add(temp.getElement());
                temp = temp.getNext();
            }

            return list;
        } else {
            throw new RuntimeException();
        }
    }

    public boolean contains(Object o) {
        boolean check = false;
        Node temp = this.head;
        if (temp.getElement().equals(o)) {
            check = true;
        }

        while(temp.getNext() != null) {
            if (temp.getElement().equals(o)) {
                check = true;
                break;
            }

            temp = temp.getNext();
        }

        if (!check) {
            return temp.getElement().equals(o);
        } else {
            return true;
        }
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size1) {
        this.size = size1;
    }
}

