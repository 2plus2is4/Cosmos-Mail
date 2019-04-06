package Interfaces;

public class ISort {
    private MyDLinkedList mails;
    private String type;

    public ISort() {
    }

    public MyDLinkedList getMails() {
        return this.mails;
    }

    public void setMails(MyDLinkedList mails) {
        this.mails = mails;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ISort(MyDLinkedList mails, String type) {
        this.mails = mails;
        this.type = type;
    }

    public void sort() {
        if (this.type == "name") {
            this.qsname(this.mails);
        } else if (this.type == "date") {
            this.qstime(this.mails);
        }

    }

    public void qsint(int[] mails) {
        MyStack s = new MyStack();
        int right = mails.length - 1;
        int left = 0;
        s.push(right);
        s.push(Integer.valueOf(left));

        while(!s.isEmpty()) {
            int l = (Integer)s.pop();
            int r = (Integer)s.pop();
            if (l < r) {
                int index = this.partition(mails, r, l);
                if (index <= mails.length) {
                    s.push(r);
                    s.push(index);
                }

                if (index - 1 >= 0) {
                    s.push(index - 1);
                    s.push(l);
                }
            }
        }

    }

    private int partition(int[] mails, int right, int left) {
        int pivot = right;

        while(left <= right) {
            while(mails[left] < mails[pivot]) {
                ++left;
            }

            if (left <= right) {
                this.swapp(mails, left, pivot);
                --right;
                pivot = left;
            }

            while(mails[right] > mails[pivot]) {
                --right;
            }

            if (left <= right) {
                this.swapp(mails, right, pivot);
                ++left;
                pivot = right;
            }
        }

        return left;
    }

    public void qstime(MyDLinkedList mails) {
        MyStack s = new MyStack();
        int right = mails.size() - 1;
        int left = 0;
        s.push(right);
        s.push(Integer.valueOf(left));

        while(!s.isEmpty()) {
            int l = (Integer)s.pop();
            int r = (Integer)s.pop();
            if (l < r) {
                int index = this.partitiont(mails, r, l);
                if (index <= mails.size()) {
                    s.push(r);
                    s.push(index);
                }

                if (index - 1 >= 0) {
                    s.push(index - 1);
                    s.push(l);
                }
            }
        }

    }

    public void qsname(MyDLinkedList mails) {
        MyStack s = new MyStack();
        int right = mails.size() - 1;
        int left = 0;
        s.push(right);
        s.push(Integer.valueOf(left));

        while(!s.isEmpty()) {
            int l = (Integer)s.pop();
            int r = (Integer)s.pop();
            if (l < r) {
                int index = this.partitionn(mails, r, l);
                if (index <= mails.size()) {
                    s.push(r);
                    s.push(index);
                }

                if (index - 1 >= 0) {
                    s.push(index - 1);
                    s.push(l);
                }
            }
        }

    }

    private int partitiont(MyDLinkedList mails, int left, int right) {
        int pivot = right;

        while(left <= right) {
            while(((IMail)mails.get(left)).getDate().before(((IMail)mails.get(pivot)).getDate())) {
                ++left;
            }

            if (left <= right) {
                this.swap(mails, left, pivot);
                --right;
                pivot = left;
            }

            while(((IMail)mails.get(right)).getDate().after(((IMail)mails.get(pivot)).getDate())) {
                --right;
            }

            if (left <= right) {
                this.swap(mails, right, pivot);
                ++left;
                pivot = right;
            }
        }

        return left;
    }

    private int partitionn(MyDLinkedList mails, int left, int right) {
        int pivot = right;

        while(left <= right) {
            while(((IMail)mails.get(left)).getSender().charAt(0) < ((IMail)mails.get(pivot)).getSender().charAt(0)) {
                ++left;
            }

            if (left <= right) {
                this.swap(mails, left, pivot);
                --right;
                pivot = left;
            }

            while(((IMail)mails.get(right)).getSender().charAt(0) > ((IMail)mails.get(pivot)).getSender().charAt(0)) {
                --right;
            }

            if (left <= right) {
                this.swap(mails, right, pivot);
                ++left;
                pivot = right;
            }
        }

        return left;
    }

    private void swap(MyDLinkedList mails, int left, int right) {
        IMail dum = (IMail)mails.get(left);
        mails.set(left, mails.get(right));
        mails.set(right, dum);
    }

    private void swapp(int[] mils, int lft, int rght) {
        int dum = mils[lft];
        mils[lft] = mils[rght];
        mils[rght] = dum;
    }
}

