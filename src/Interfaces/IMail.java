package Interfaces;

import java.util.Date;

public class IMail {
    private String sender;
    private Date date;
    private MyLinkedList reciever;
    private String subject;
    private String text;
    private MyLinkedList attach;
    private int index;
    private int priority;
    private String folderName;
    private boolean star;
    private String path;
    private boolean read = false;

    public IMail() {
    }

    public boolean isStar() {
        return this.star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public MyLinkedList getReciever() {
        return this.reciever;
    }

    public void setReciever(MyLinkedList reciever) {
        this.reciever = reciever;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAttach(MyLinkedList attach) {
        this.attach = attach;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MyLinkedList getAttach() {
        return this.attach;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean dated() {
        long x = 2592000000L;
        Date date = new Date();
        return date.getTime() - this.getDate().getTime() >= x;
    }

    public boolean mailEqual(IMail mail) {
        new String();
        String tex = mail.getText();
        String sende = mail.getSender();
        MyLinkedList rec = new MyLinkedList();

        int p;
        for(p = 0; p < mail.getReciever().size(); ++p) {
            new String();
            String rec1 = mail.getReciever().get(p).toString();
            rec.add(rec1);
        }

        p = mail.getPriority();
        Date dat = mail.getDate();
        String sub = mail.getSubject();
        if (this.getText().equals(tex) && sende.equals(this.getSender()) && p == this.getPriority() && dat.equals(this.getDate()) && sub.equals(this.getSubject())) {
            boolean check = false;
            int i = 0;
            if (i < mail.getReciever().size()) {
                if (mail.getReciever().get(i).equals(this.getReciever().get(i))) {
                    if (check) {
                        return false;
                    }

                    return true;
                }

                check = true;
            }

            return true;
        } else {
            return false;
        }
    }
}

