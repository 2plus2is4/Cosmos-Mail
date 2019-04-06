package Classes;

import java.util.Date;

public class MailListMail {
    private String sender;
    private String subject;
    private Date date;
    private boolean attach;
    private boolean flag;

    public MailListMail(String sender, String subject, Date date, boolean attach, boolean flag) {
        this.sender = sender;
        this.attach = attach;
        this.date = date;
        this.subject = subject;
        this.flag = flag;
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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAttach() {
        return this.attach;
    }

    public void setAttach(boolean attach) {
        this.attach = attach;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
