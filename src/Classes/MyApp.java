package Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import Interfaces.*;

public class MyApp implements IApp {
    public MyLinkedList accounts = new MyLinkedList();
    public IFolder allMails = new IFolder();
    public IFolder trash = new IFolder();
    public IFolder inbox = new IFolder();
    public IFolder sent = new IFolder();
    public IFolder draft = new IFolder();
    public MyDLinkedList contacts = new MyDLinkedList();
    public IFolder star = new IFolder();
    public MyDLinkedList folders = new MyDLinkedList();
    public MyDLinkedList folderMails = new MyDLinkedList();
    public MyDLinkedList fromF = new MyDLinkedList();
    public MyDLinkedList subjectF = new MyDLinkedList();
    public String accountName = new String();
    public String accountEmail = new String();
    public String accountPass = new String();
    public Store s = new Store();
    public Access a = new Access();
    private IFolder c;

    public MyApp() {
        this.accountFiles();
        this.accounts = this.s.readAccounts("accounts/accounts.json");
        this.s.writeAccounts(this.accounts);
    }

    public void accountFiles() {
        new IFolder("accounts");
    }

    public boolean CreatNewFolder(String folderName) {
        new MyDLinkedList();
        MyDLinkedList folders = this.s.foldersRead("accounts/" + this.accountName + "/" + this.accountEmail + "/folders//index.json");

        for(int i = 0; i < folders.size(); ++i) {
            if (folderName.equals(folders.get(i))) {
                return false;
            }
        }

        new IFolder("accounts/" + this.accountName + "/" + this.accountEmail + "/folders/" + folderName);
        this.s.makeMailindex("accounts/" + this.accountName + "/" + this.accountEmail + "/folders/" + folderName);
        folders.add(folderName);
        this.s.writeFolders(folders, "accounts/" + this.accountName + "/" + this.accountEmail + "/folders/index.json");
        this.signin(this.accountName, this.accountPass);
        return true;
    }

    public void creatIContactFolder(String accountName, String emailName) {
        new IFolder("accounts/" + accountName);
        new IFolder("accounts/" + accountName + "/" + emailName);
        new IFolder("accounts/" + accountName + "/" + emailName + "/all mails");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/all mails");
        new IFolder("accounts/" + accountName + "/" + emailName + "/inbox");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/inbox");
        new IFolder("accounts/" + accountName + "/" + emailName + "/sent");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/sent");
        new IFolder("accounts/" + accountName + "/" + emailName + "/trash");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/trash");
        new IFolder("accounts/" + accountName + "/" + emailName + "/draft");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/draft");
        new IFolder("accounts/" + accountName + "/" + emailName + "/contacts");
        this.s.makeContactsIndex("accounts/" + accountName + "/" + emailName + "/contacts");
        new IFolder("accounts/" + accountName + "/" + emailName + "/star");
        this.s.makeMailindex("accounts/" + accountName + "/" + emailName + "/star");
        new IFolder("accounts/" + accountName + "/" + emailName + "/folders");
        this.s.makeFoldersIndex("accounts/" + accountName + "/" + emailName + "/folders");
        IFolder filters = new IFolder("accounts/" + accountName + "/" + emailName + "/filters");
        filters.creatfolder("accounts/" + accountName + "/" + emailName + "/filters/from");
        filters.creatfolder("accounts/" + accountName + "/" + emailName + "/filters/subject");
        this.s.makeFromIndex("accounts/" + accountName + "/" + emailName + "/filters/from");
        this.s.makeSubjectIndex("accounts/" + accountName + "/" + emailName + "/filters/subject");
    }

    public boolean signin(String email, String password) {
        for(int i = 0; i < this.accounts.size(); ++i) {
            new IContact();
            IContact user = (IContact)this.accounts.get(i);
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                this.accountName = user.getName();
                this.accountEmail = user.getEmail();
                this.accountPass = password;
                this.allMails.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "all mails"));
                this.inbox.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "inbox"));
                this.sent.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "sent"));
                this.trash.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "trash"));
                this.draft.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "draft"));
                this.star.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "star"));
                this.contacts = this.a.getAcessContacts(email, user.getName(), "contacts");
                this.folders = new MyDLinkedList();
                this.subjectF = new MyDLinkedList();
                this.fromF = new MyDLinkedList();
                MyDLinkedList folderas = this.a.getAcessFolders(email, user.getName(), "folders");
                if (folderas.size() != 0) {
                    for(int j = 0; j < folderas.getSize(); ++j) {
                        IFolder f = new IFolder();
                        f.setFolderName(folderas.get(j).toString());
                        f.setFolderList(this.a.getAcessMailFolders(email, user.getName(), "folders/" + folderas.get(j).toString()));
                        this.folders.add(f);
                    }
                }

                new MyDLinkedList();
                new MyDLinkedList();
                MyDLinkedList fromlist = this.s.readFrom("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/index.json");
                MyDLinkedList sublist = this.s.readSubject("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/index.json");

                int j;
                IFolder f;
                for(j = 0; j < fromlist.size(); ++j) {
                    f = new IFolder();
                    f.setFolderName(fromlist.get(j).toString());
                    f.setFolderList(this.makeFromFilter(this.inbox.getFolderList(), fromlist.get(j).toString()));
                    this.fromF.add(f);
                }

                for(j = 0; j < sublist.size(); ++j) {
                    f = new IFolder();
                    f.setFolderName(sublist.get(j).toString());
                    f.setFolderList(this.makeSubFilter(this.inbox.getFolderList(), sublist.get(j).toString()));
                    this.subjectF.add(f);
                }

                return true;
            }
        }

        return false;
    }

    public boolean signup(IContact contact) {
        int i;
        for(i = 0; i < this.accounts.size(); ++i) {
            new IContact();
            IContact user = (IContact)this.accounts.get(i);
            if (user.getEmail().equals(contact.getEmail())) {
                return false;
            }
        }

        char character;
        for(i = 0; i < contact.getName().length(); ++i) {
            character = contact.getName().charAt(i);
            if (character > 'z' || character < '0' || character > '9' && character < 'A' && character > 'Z' && character < 'a' && character != '_' && character != '.') {
                return false;
            }
        }

        for(i = 0; i < contact.getEmail().length(); ++i) {
            character = contact.getEmail().charAt(i);
            if (character > 'z' || character < '0' || character > '9' && character < 'A' && character > 'Z' && character < 'a' && character != '_' && character != '.') {
                return false;
            }
        }

        this.creatIContactFolder(contact.getName(), contact.getEmail());
        this.accountName = contact.getName();
        this.accountEmail = contact.getEmail();
        this.accountPass = contact.getPassword();
        this.accounts.add(contact);
        this.s.writeAccounts(this.accounts);
        return true;
    }

    public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
        if (folder != null) {
            if (filter == null) {
                sort.setMails(folder.getFolderList());
                sort.sort();
            } else {
                filter.setMails(folder.getFolderList());
                filter.filter();
                sort.setMails(filter.getMails());
                sort.sort();
            }
        }

    }

    public IMail[] listEmails(int page) {
        MyDLinkedList allMail = this.c.getFolderList();
        int ii = 0;
        int begin;
        if (allMail != null) {
            for(begin = allMail.size(); begin > 1; --begin) {
                allMail.add(ii++, allMail.get(ii));
                allMail.remove(allMail.size()-1);
            }
        }

        begin = (page - 1) * 10;
        IMail[] m;
        int i;
        if (allMail.size() <= 10 && page == 1) {
            m = new IMail[allMail.size()];

            for(i = 0; i < allMail.size(); ++i) {
                new IMail();
                IMail mail = (IMail)allMail.get(i);
                int index = mail.getIndex();
                String path = mail.getPath();

                try {
                    m[i] = this.s.readMail(index, path);
                } catch (ParseException var11) {
                    var11.printStackTrace();
                }
            }

            return m;
        } else if (allMail.size() <= 10) {
            m = new IMail[10];
            return m;
        } else if (allMail.size() - begin < 10) {
            m = new IMail[allMail.size() - begin];

            for(i = begin; i < allMail.size(); ++i) {
                m[i] = (IMail)allMail.get(i);
            }

            return m;
        } else {
            m = new IMail[10];

            for(i = begin; i < begin + 10; ++i) {
                m[i] = (IMail)allMail.get(i);
            }

            return m;
        }
    }

    public void deleteEmails(ILinkedList mails) {
        Store s = new Store();
        IFolder trashF = new IFolder();

        MyDLinkedList all;
        for(int i = 0; i < mails.size(); ++i) {
            new MyDLinkedList();
            new IMail();
            IMail mail = (IMail)mails.get(i);
            String pathToDelete = mail.getPath();
            int indexTodelete = mail.getIndex();
            mail.setFolderName("trash");
            s.setFileIndex("accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");
            s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/trash");
            all = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");
            all.add(mail);
            s.writemails(all, "accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");
            trashF.deleteDirectory(pathToDelete + "/" + indexTodelete);
            new MyDLinkedList();
            MyDLinkedList pathMails = s.mailsRead(pathToDelete + "/index.json");

            for(int j = 0; j < pathMails.size(); ++j) {
                new IMail();
                IMail current = (IMail)pathMails.get(j);
                if (current.getIndex() == indexTodelete) {
                    pathMails.remove(j);
                    --j;
                }
            }

            s.writemails(pathMails, pathToDelete + "/index.json");
        }

        new MyDLinkedList();
        MyDLinkedList trashMails = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");
        new MyDLinkedList();
        all = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/all mails/index.json");

        for(int i = 0; i < all.size(); ++i) {
            for(int j = 0; j < trashMails.size(); ++j) {
                new IMail();
                new IMail();
                IMail mail = (IMail)all.get(i);
                IMail mail2 = (IMail)trashMails.get(j);

                try {
                    mail = s.readMail(mail.getIndex(), mail.getPath());
                    mail2 = s.readMail(mail2.getIndex(), mail2.getPath());
                } catch (ParseException var13) {
                    var13.printStackTrace();
                }

                if (mail.mailEqual(mail2)) {
                    try {
                        FileWriter index = new FileWriter("accounts/" + this.accountName + "/" + this.accountEmail + "/all mails/" + mail.getIndex() + "/" + mail.getIndex() + ".json");
                        index.write("");
                        index.flush();
                        index.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                    File index = new File("accounts/" + this.accountName + "/" + this.accountEmail + "/all mails/" + mail.getIndex() + "/" + mail.getIndex() + ".json");
                    index.delete();
                    trashF.deleteDirectory("accounts/" + this.accountName + "/" + this.accountEmail + "/all mails/" + mail.getIndex());
                    all.remove(i);
                    --i;
                }
            }
        }

        s.writemails(all, "accounts/" + this.accountName + "/" + this.accountEmail + "/all mails/index.json");
    }

    public void moveEmails(ILinkedList mails, IFolder des) {
        Store s = new Store();
        new MyDLinkedList();
        MyDLinkedList temp = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/folders/" + des.getFolderName() + "/index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mail = (IMail)mails.get(i);
            mail.setFolderName(des.getFolderName());
            s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/folders/" + des.getFolderName());
            temp.add(mail);
        }

        s.writemails(temp, "accounts/" + this.accountName + "/" + this.accountEmail + "/folders/" + des.getFolderName() + "/index.json");
    }

    @Override
    public boolean compose(IMail email) {
        if (email.getSubject() != null && email.getText() != null && email.getReciever().size() != 0) {
            email.setStar(false);
            new IMail();
            new IMail();
            new IMail();
            new IMail();
            IMail inboxMail = email;
            IMail allmails2 = email;
            email.setPriority(1);
            Store s = new Store();
            new MyDLinkedList();
            email.setFolderName("sent");
            email.setFolderName("sent");
            s.setFileIndex("accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/sent/index.json");
            s.storeMail(email, "accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/sent/");
            MyDLinkedList temp = s.mailsRead("accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/sent/index.json");
            temp.add(email);
            s.writemails(temp, "accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/sent/index.json");
            if (email.getSender().equals(this.accountName)) {
                this.sent.setFolderList(temp);
            }

            s.setFileIndex("accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/all mails/index.json");
            s.storeMail(email, "accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/all mails/");
            temp = s.mailsRead("accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/all mails/index.json");
            temp.add(email);
            s.writemails(temp, "accounts/" + s.getEmailUser(email.getSender(), this.accounts) + "/" + email.getSender() + "/all mails/index.json");
            if (email.getSender().equals(this.accountName)) {
                this.allMails.setFolderList(temp);
            }

            for(int i = 0; i < email.getReciever().size(); ++i) {
                s = new Store();
                new String();
                String store = email.getReciever().get(i).toString();
                if (s.getEmailUser(store, this.accounts) != null) {
                    inboxMail.setFolderName("inbox");
                    allmails2.setFolderName("inbox");
                    s.setFileIndex("accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/inbox/index.json");
                    s.storeMail(inboxMail, "accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/inbox/");
                    temp = s.mailsRead("accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/inbox/index.json");
                    temp.add(inboxMail);
                    s.writemails(temp, "accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/inbox/index.json");
                    s.setFileIndex("accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/all mails/index.json");
                    s.storeMail(allmails2, "accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/all mails/");
                    temp = s.mailsRead("accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/all mails/index.json");
                    temp.add(allmails2);
                    s.writemails(temp, "accounts/" + s.getEmailUser(store, this.accounts) + "/" + store + "/all mails/index.json");
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private void despose() {
        new MyDLinkedList();
        MyDLinkedList trashMails = this.trash.getFolderList();

        for(int i = 0; i < trashMails.size(); ++i) {
            if (!((IMail)trashMails.get(i)).dated()) {
                trashMails.remove(i);
                --i;
            }
        }

        this.trash.setFolderList(trashMails);
    }

    public void saveToDraft(IMail email) {
        email.setStar(false);
        Store s = new Store();
        new MyDLinkedList();
        new IMail();
        email.setFolderName("draft");
        s.setFileIndex("accounts/" + this.accountName + "/" + this.accountEmail + "/draft/index.json");
        s.storeMail(email, "accounts/" + this.accountName + "/" + this.accountEmail + "/draft/");
        MyDLinkedList temp = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/draft/index.json");
        temp.add(email);
        s.writemails(temp, "accounts/" + this.accountName + "/" + this.accountEmail + "/draft/index.json");
        this.draft.setFolderList(temp);
    }

    public void editePriority(IMail mail, int newP) {
        mail.setPriority(newP);
        Store s = new Store();
        new MyDLinkedList();
        MyDLinkedList mails = s.mailsRead(mail.getPath() + "index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)mails.get(i);
            if (mailt.getIndex() == mail.getIndex()) {
                mails.set(i, mail);
            }
        }

        s.writemails(mails, mail.getPath() + "index.json");
        s.storeMailAfterPr(mail, mail.getPath());
    }

    public void deletFromTrash(int index) {
        IFolder f = new IFolder();
        new MyDLinkedList();
        MyDLinkedList mails = this.s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)mails.get(i);
            if (mailt.getIndex() == index) {
                mails.remove(i);
                break;
            }
        }

        this.s.writemails(mails, "accounts/" + this.accountName + "/" + this.accountEmail + "/trash/index.json");
        f.deleteDirectory("accounts/" + this.accountName + "/" + this.accountEmail + "/trash/" + index);
    }

    public boolean addContact(String name, String email) {
        if (email.equals(this.accountEmail)) {
            return false;
        } else {
            new MyDLinkedList();
            IFolder contact = new IFolder("accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/" + name);
            MyDLinkedList con = this.s.readContacts("accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/contacts.json");

            for(int i = 0; i < con.size(); ++i) {
                new IContact();
                IContact test = (IContact)con.get(i);
                if (email.equals(test.getEmail())) {
                    return false;
                }
            }

            IContact contact1 = new IContact();
            contact1.setName(name);
            contact1.setEmail(email);
            con.add(contact1);
            this.s.writeContacts(con, "accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/contacts.json");
            this.contacts = this.s.readContacts("accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/contacts.json");
            contact.creatfolder("accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/" + name + "/" + email);
            this.s.makeMailindex("accounts/" + this.accountName + "/" + this.accountEmail + "/contacts/" + name + "/" + email);
            return true;
        }
    }

    public void setMailStar(IMail mail) {
        mail.setStar(true);
        Store s = new Store();
        new MyDLinkedList();
        MyDLinkedList mails = s.mailsRead(mail.getPath() + "index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)mails.get(i);
            if (mailt.getIndex() == mail.getIndex()) {
                mails.set(i, mail);
            }
        }

        s.writemails(mails, mail.getPath() + "index.json");
        s.storeMailAfterPr(mail, mail.getPath());
        new IMail();
        new MyDLinkedList();
        mail.setFolderName("star");
        s.setFileIndex("accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/star/");
        MyDLinkedList temp = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        temp.add(mail);
        s.writemails(temp, "accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        this.star.setFolderList(temp);
    }

    public void setNotMailStar(IMail mail) {
        IFolder f = new IFolder();
        mail.setStar(true);
        Store s = new Store();
        new MyDLinkedList();
        MyDLinkedList mails = s.mailsRead(mail.getPath() + "index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)mails.get(i);
            if (mailt.getIndex() == mail.getIndex()) {
                mails.set(i, mail);
            }
        }

        s.writemails(mails, mail.getPath() + "index.json");
        s.storeMailAfterPr(mail, mail.getPath());
        new IMail();
        IMail unStaredMail = mail;
        new MyDLinkedList();
        mail.setFolderName("star");
        f.deleteDirectory("accounts/" + this.accountName + "/" + this.accountEmail + "/star/" + mail.getIndex());
        MyDLinkedList temp = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");

        for(int i = 0; i < temp.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)temp.get(i);
            if (mailt.getIndex() == unStaredMail.getIndex()) {
                temp.remove(i);
            }
        }

        s.writemails(temp, "accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        this.star.setFolderList(temp);
    }

    public IMail[] page(IFolder folder, int page) {
        this.c = folder;
        return this.listEmails(page);
    }

    public void setMailNotStar(IMail mail) {
        mail.setStar(false);
        Store s = new Store();
        new MyDLinkedList();
        MyDLinkedList mails = s.mailsRead(mail.getPath() + "index.json");

        for(int i = 0; i < mails.size(); ++i) {
            new IMail();
            IMail mailt = (IMail)mails.get(i);
            if (mailt.getIndex() == mail.getIndex()) {
                mails.set(i, mail);
            }
        }

        s.writemails(mails, mail.getPath() + "index.json");
        s.storeMailAfterPr(mail, mail.getPath());
        new IMail();
        new MyDLinkedList();
        mail.setFolderName("star");
        s.setFileIndex("accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/star/");
        MyDLinkedList temp = s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        temp.add(mail);
        s.writemails(temp, "accounts/" + this.accountName + "/" + this.accountEmail + "/star/index.json");
        this.star.setFolderList(temp);
    }

    public boolean renameFolder(String folderName, String newName) {
        new MyDLinkedList();
        MyDLinkedList folderss = this.s.foldersRead("accounts/" + this.accountName + "/" + this.accountEmail + "folders/");
        boolean check = false;

        for(int i = 0; i < folderss.size(); ++i) {
            new String();
            String folderssName = folderss.get(i).toString();
            if (folderssName.equals(folderName)) {
                check = true;
                break;
            }
        }

        if (!check) {
            return false;
        } else {
            new MyDLinkedList();
            MyDLinkedList mails = this.s.mailsRead("accounts/" + this.accountName + "/" + this.accountEmail + "folders/");
            new MyDLinkedList();

            for(int i = 0; i < mails.size(); ++i) {
                new IMail();
                IMail mail = (IMail)mails.get(i);
                if (mail.getFolderName().equals(folderName)) {
                    mail.setFolderName(newName);
                }
            }

            IFolder f = new IFolder();
            f.rename(folderName, newName, "accounts/" + this.accountName + "/" + this.accountEmail + "folders");
            return true;
        }
    }

    public MyDLinkedList makeFromFilter(MyDLinkedList mails, String fil) {
        new MyDLinkedList();
        MyDLinkedList from = this.s.readFrom("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/index.json");
        boolean check = false;

        for(int i = 0; i < from.size(); ++i) {
            new String();
            String test = from.get(i).toString();
            if (test.equals(fil)) {
                check = true;
                break;
            }
        }

        if (!check) {
            from.add(fil);
        }

        this.s.writeFrom(from, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/index.json");
        IFilter f = new IFilter("from", fil);
        new MyDLinkedList();
        new MyDLinkedList();
        MyDLinkedList all = this.a.getAcessMailFolders(this.accountEmail, this.accountName, "inbox");
        System.out.println();
        f.setMails(all);
        MyDLinkedList listMails = f.filterL();
        IFolder fs = new IFolder();
        File file = new File("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil);
        if (file.exists()) {
            fs.deleteDirectory("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil);
        }

        fs.creatfolder("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil);
        this.s.makeMailindex("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil);
        this.s.writemails(listMails, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil + "/index.json");

        for(int i = 0; i < listMails.size(); ++i) {
            new IMail();
            IMail mail = (IMail)listMails.get(i);

            try {
                mail = this.s.readMail(mail.getIndex(), mail.getPath());
            } catch (ParseException var13) {
                var13.printStackTrace();
            }

            mail.setIndex(1);
            this.s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/from/" + fil);
        }

        return listMails;
    }

    public MyDLinkedList makeSubFilter(MyDLinkedList mails, String fil) {
        new MyDLinkedList();
        MyDLinkedList sub = this.s.readSubject("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/index.json");
        boolean check = false;

        for(int i = 0; i < sub.size(); ++i) {
            new String();
            String test = sub.get(i).toString();
            if (test.equals(fil)) {
                check = true;
                break;
            }
        }

        if (!check) {
            sub.add(fil);
        }

        this.s.writeSubject(sub, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/index.json");
        IFilter f = new IFilter("subject", fil);
        new MyDLinkedList();
        new MyDLinkedList();
        MyDLinkedList all = this.a.getAcessMailFolders(this.accountEmail, this.accountName, "inbox");
        f.setMails(all);
        MyDLinkedList listMails = f.filterL();
        IFolder fs = new IFolder();
        File file = new File("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil);
        if (file.exists()) {
            fs.deleteDirectory("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil);
        }

        fs.creatfolder("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil);
        this.s.makeMailindex("accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil);
        this.s.writemails(listMails, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil + "/index.json");

        for(int i = 0; i < listMails.size(); ++i) {
            new IMail();
            IMail mail = (IMail)listMails.get(i);

            try {
                mail = this.s.readMail(mail.getIndex(), mail.getPath());
            } catch (ParseException var13) {
                var13.printStackTrace();
            }

            mail.setIndex(0);
            this.s.storeMail(mail, "accounts/" + this.accountName + "/" + this.accountEmail + "/filters/subject/" + fil);
        }

        return listMails;
    }
}
