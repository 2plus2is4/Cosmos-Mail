package Interfaces;

public class IFilter {
    private MyDLinkedList mails;
    private String type;
    private String comparable;

    public String getComparable() {
        return this.comparable;
    }

    public void setComparable(String comparable) {
        this.comparable = comparable;
    }

    public IFilter(String type, String comparable) {
        this.type = type;
        this.comparable = comparable;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyDLinkedList getMails() {
        return this.mails;
    }

    public void setMails(MyDLinkedList mails) {
        this.mails = mails;
    }

    public IFilter(MyDLinkedList mails) {
        this.mails = mails;
    }

    public IMail[] subject(String sub) {
        MyDLinkedList filtered = new MyDLinkedList();

        for(int i = 0; i < this.mails.size(); ++i) {
            IMail test = (IMail)this.mails.get(i);
            if (test.getSubject().contains(sub)) {
                filtered.add(test);
            }
        }

        IMail[] filter = new IMail[filtered.size()];

        for(int i = 0; i < filtered.size(); ++i) {
            filter[i] = (IMail)filtered.get(i);
        }

        return filter;
    }

    public IMail[] from(String from) {
        MyDLinkedList filtered = new MyDLinkedList();

        for(int i = 0; i < this.mails.size(); ++i) {
            IMail test = (IMail)this.mails.get(i);
            if (test.getSender().equals(from)) {
                filtered.add(test);
            }
        }

        IMail[] filter = new IMail[filtered.size()];

        for(int i = 0; i < filtered.size(); ++i) {
            filter[i] = (IMail)filtered.get(i);
        }

        return filter;
    }

    public IMail[] haveAttach() {
        MyDLinkedList filtered = new MyDLinkedList();

        for(int i = 0; i < this.mails.size(); ++i) {
            IMail test = (IMail)this.mails.get(i);
            if (test.getAttach().size() != 0) {
                filtered.add(test);
            }
        }

        IMail[] filter = new IMail[filtered.size()];

        for(int i = 0; i < filtered.size(); ++i) {
            filter[i] = (IMail)filtered.get(i);
        }

        return filter;
    }

    public IMail[] nomAttach(int num) {
        MyDLinkedList filtered = new MyDLinkedList();

        for(int i = 0; i < this.mails.size(); ++i) {
            IMail test = (IMail)this.mails.get(i);
            if (test.getAttach().size() == num) {
                filtered.add(test);
            }
        }

        IMail[] filter = new IMail[filtered.size()];

        for(int i = 0; i < filtered.size(); ++i) {
            filter[i] = (IMail)filtered.get(i);
        }

        return filter;
    }

    public MyDLinkedList priority() {
        MyDLinkedList lpr = new MyDLinkedList();
        MyPriorityQueue pr = new MyPriorityQueue();

        int i;
        for(i = 0; i < this.mails.size(); ++i) {
            new IMail();
            IMail mail = (IMail)this.mails.get(i);
            pr.insert(mail, mail.getPriority());
        }

        for(i = 0; i < pr.size(); ++i) {
            lpr.add(pr.removeMin());
        }

        return lpr;
    }

    public MyDLinkedList specificPriority(int p) {
        MyDLinkedList lpr = new MyDLinkedList();

        for(int i = 0; i < this.mails.size(); ++i) {
            new IMail();
            IMail mail = (IMail)this.mails.get(i);
            if (mail.getPriority() == p) {
                lpr.add(mail);
            }
        }

        return lpr;
    }

    public void filter() {
        if (this.getType() == "subject") {
            this.subject(this.comparable);
        } else if (this.getType() == "from") {
            this.from(this.comparable);
        } else if (this.getType() == "attach number") {
            this.nomAttach(Integer.parseInt(this.comparable));
        } else if (this.getType() == "have attach") {
            this.haveAttach();
        }

    }

    public MyDLinkedList filterL() {
        IMail[] mails = new IMail[0];
        MyDLinkedList mailsf = new MyDLinkedList();
        if (this.getType() == "subject") {
            mails = new IMail[this.subject(this.comparable).length];
            mails = this.subject(this.comparable);
        } else if (this.getType() == "from") {
            mails = new IMail[this.from(this.comparable).length];
            mails = this.from(this.comparable);
        }

        for(int i = 0; i < mails.length; ++i) {
            mailsf.add(mails[i]);
        }

        return mailsf;
    }
}