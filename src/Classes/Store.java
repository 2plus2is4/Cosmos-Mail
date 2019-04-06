package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Store {
    public Store() {
    }

    public void storeMail(IMail mail, String path) {
        JSONObject obj = new JSONObject();
        obj.put("subject", mail.getSubject());
        obj.put("sender", mail.getSender());
        String date = (new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")).format(mail.getDate());
        obj.put("date", date);
        obj.put("priority", mail.getPriority());
        obj.put("folder name", mail.getFolderName());
        obj.put("star", mail.isStar());
        obj.put("read", mail.isRead());
        JSONArray rec = new JSONArray();

        for (int i = 0; i < mail.getReciever().size(); ++i) {
            rec.add(mail.getReciever().get(i));
        }

        obj.put("reciever", rec);
        if (mail.getAttach() != null) {
            if (mail.getAttach().equals((Object) null)) {
                obj.put("attachments numbers", 0);
            } else {
                obj.put("attachments numbers", mail.getAttach().size());
            }
        } else {
            obj.put("attachments numbers", 0);
        }

        JSONArray att;
        if (mail.getAttach() != null) {
            att = new JSONArray();

            for (int i = 0; i < mail.getAttach().size(); ++i) {
                att.add(mail.getAttach().get(i));
            }
        }

        if (mail.getAttach() != null) {
            if (mail.getAttach().size() > 0) {
                att = new JSONArray();

                for (int i = 0; i < mail.getAttach().size(); ++i) {
                    att.add(mail.getAttach().get(i));
                }
            }
        } else {
            obj.put("attachments numbers", 0);
        }

        obj.put("text", mail.getText());
        mail.setIndex(this.setFileIndex(path + "/index.json"));

        for (File check = new File(path + "/" + Integer.toString(mail.getIndex()) + "/" + Integer.toString(mail.getIndex()) + ".json"); check.exists(); check = new File(path + "/" + Integer.toString(mail.getIndex()) + "/" + Integer.toString(mail.getIndex()) + ".json")) {
            mail.setIndex(mail.getIndex() + 1);
        }

        obj.put("index", mail.getIndex());
        File dir = new File(path + "/" + Integer.toString(mail.getIndex()));
        dir.mkdir();
        mail.setPath(path);
        obj.put("path", mail.getPath());

        try {
            FileWriter index = new FileWriter(path + "/" + Integer.toString(mail.getIndex()) + "/" + Integer.toString(mail.getIndex()) + ".json");
            Throwable var9 = null;
            try {
                index.write(obj.toString());
                index.flush();
                index.close();
            } catch (Throwable var21) {
                var9 = var21;
                throw var21;
            } finally {
                if (index != null) {
                    if (var9 != null) {
                        try {
                            index.close();
                        } catch (Throwable var20) {
                            var9.addSuppressed(var20);
                        }
                    } else {
                        index.close();
                    }
                }

            }
        } catch (Exception var23) {
            var23.printStackTrace();
        }

        if (mail.getAttach() != null) {
            if (mail.getAttach().size() > 0) {
                IFolder iFolder = new IFolder(path + "/" + Integer.toString(mail.getIndex()) + "/attachments");
                for (int j = 0; j < mail.getAttach().size(); ++j) {
                    File f = (File) mail.getAttach().get(j);
                    File des = new File(path + "/" + Integer.toString(mail.getIndex()) + "/attachments/" + f.getName());

                    try {
                        iFolder.copyFileUsingStream(f, des);
                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }
                }
            }
        } else {
            obj.put("attachments numbers", 0);
        }

    }

    public void writeAccounts(MyLinkedList accounts) {
        JSONArray allAccounts = new JSONArray();

        for (int i = 0; i < accounts.size(); ++i) {
            new IContact();
            IContact ac = (IContact) accounts.get(i);
            JSONObject obj = new JSONObject();
            obj.put("name", ac.getName());
            obj.put("password", ac.getPassword());
            obj.put("email", ac.getEmail());
            allAccounts.add(obj);
        }

        JSONObject accOb = new JSONObject();
        accOb.put("accounts", allAccounts);

        try {
            FileWriter jsonWriter = new FileWriter("accounts/accounts.json");
            Throwable var20 = null;

            try {
                jsonWriter.write(accOb.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var20 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var20 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var20.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public MyLinkedList readAccounts(String pathToRead) {
        MyLinkedList accounts = new MyLinkedList();
        JSONParser parser = new JSONParser();
        File f = new File(pathToRead);
        if (!f.exists()) {
            this.writeAccounts(accounts);
        }

        try {
            FileReader re = new FileReader(pathToRead);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("accounts");

            for (int i = 0; i < arr.size(); ++i) {
                JSONObject tr = (JSONObject) arr.get(i);
                IContact account = new IContact();
                account.setName(tr.get("name").toString());
                account.setEmail(tr.get("email").toString());
                account.setPassword(tr.get("password").toString());
                accounts.add(account);
            }

            re.close();
        } catch (FileNotFoundException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        } catch (ParseException var14) {
            var14.printStackTrace();
        }

        return accounts;
    }

    public void mailsIndex(MyDLinkedList mails, String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (int i = 0; i < mails.size(); ++i) {
            JSONObject st = new JSONObject();
            new IMail();
            IMail mail = (IMail) mails.get(i);
            st.put("subject", mail.getSubject());
            st.put("sender", mail.getSender());
            String date = (new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")).format(mail.getDate());
            st.put("date", date);
            st.put("index", mail.getIndex());
            st.put("priority", mail.getPriority());
            st.put("path", mail.getPath());
            st.put("folder name", mail.getFolderName());
            obj.put("star", mail.isStar());
            obj.put("read", mail.isRead());
            JSONArray rec = new JSONArray();

            for (int j = 0; j < mail.getReciever().size(); ++j) {
                rec.add(mail.getReciever().get(i));
            }

            st.put("reciever", rec);
            arr.add(st);
        }

        obj.put("mails", arr);
        new IMail();
        IMail mail = (IMail) mails.get(mails.size() - 1);
        obj.put("highest index", mail.getIndex());

        try {
            FileWriter jsonWriter = new FileWriter(path + ".json");
            Throwable var24 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.close();
            } catch (Throwable var19) {
                var24 = var19;
                throw var19;
            } finally {
                if (jsonWriter != null) {
                    if (var24 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var18) {
                            var24.addSuppressed(var18);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var21) {
            var21.printStackTrace();
        }

    }

    public MyDLinkedList mailsRead(String path) {
        MyDLinkedList mails = new MyDLinkedList();
        JSONParser parser = new JSONParser();

        try {
            FileReader re = new FileReader(path);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("mails");

            for (int i = 0; i < arr.size(); ++i) {
                JSONObject tr = (JSONObject) arr.get(i);
                IMail mail = new IMail();
                mail.setSubject(tr.get("subject").toString());
                mail.setSender(tr.get("sender").toString());
                mail.setIndex(Integer.parseInt(tr.get("index").toString()));
                mail.setPriority(Integer.parseInt(tr.get("priority").toString()));
                mail.setPath(tr.get("path").toString());
                mail.setFolderName(tr.get("folder name").toString());
                mail.setStar(Boolean.parseBoolean(tr.get("star").toString()));
                mail.setRead(Boolean.parseBoolean(tr.get("read").toString()));
                mails.add(mail);
            }

            re.close();
        } catch (FileNotFoundException var11) {
            var11.printStackTrace();
        } catch (IOException var12) {
            var12.printStackTrace();
        } catch (ParseException var13) {
            var13.printStackTrace();
        }

        return mails;
    }

    public int setFileIndex(String path) {
        JSONParser parser = new JSONParser();
        int high = 0;

        try {
            FileReader re = new FileReader(path);
            Object obj = parser.parse(re);
            JSONObject object = (JSONObject) obj;
            high = Integer.parseInt(object.get("highest index").toString());
            object.put("highest index", high);
            ++high;

            try {
                FileWriter jsonWriter = new FileWriter(path);
                Throwable var8 = null;

                try {
                    jsonWriter.write(object.toString());
                    jsonWriter.flush();
                    jsonWriter.close();
                } catch (Throwable var21) {
                    var8 = var21;
                    throw var21;
                } finally {
                    if (jsonWriter != null) {
                        if (var8 != null) {
                            try {
                                jsonWriter.close();
                            } catch (Throwable var20) {
                                var8.addSuppressed(var20);
                            }
                        } else {
                            jsonWriter.close();
                        }
                    }

                }
            } catch (Exception var23) {
                var23.printStackTrace();
            }

            re.close();
        } catch (FileNotFoundException var24) {
            var24.printStackTrace();
        } catch (IOException var25) {
            var25.printStackTrace();
        } catch (ParseException var26) {
            var26.printStackTrace();
        }

        return high;
    }

    public void makeMailindex(String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("mails", arr);
        obj.put("highest index", 0);

        try {
            FileWriter jsonWriter = new FileWriter(path + "/index.json");
            Throwable var5 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var5 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public void writemails(MyDLinkedList mails, String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (int i = 0; i < mails.size(); ++i) {
            JSONObject o = new JSONObject();
            new IMail();
            IMail mail = (IMail) mails.get(i);
            o.put("subject", mail.getSubject());
            o.put("sender", mail.getSender());
            o.put("index", mail.getIndex());
            o.put("priority", mail.getPriority());
            o.put("path", mail.getPath());
            o.put("folder name", mail.getFolderName());
            o.put("star", mail.isStar());
            o.put("read", mail.isRead());
            arr.add(o);
        }

        obj.put("mails", arr);
        new IMail();
        IMail mail;
        if (mails.size() == 0) {
            mail = null;
            obj.put("highest index", 0);
        } else {
            mail = (IMail) mails.get(mails.getSize() - 1);
            obj.put("highest index", mail.getIndex());
        }

        try {
            FileWriter jsonWriter = new FileWriter(path);
            Throwable var22 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var17) {
                var22 = var17;
                throw var17;
            } finally {
                if (jsonWriter != null) {
                    if (var22 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var16) {
                            var22.addSuppressed(var16);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
        }

    }

    public String getEmailUser(String mail, MyLinkedList acoounts) {
        new String();

        for (int i = 0; i < acoounts.size(); ++i) {
            new IContact();
            IContact user = (IContact) acoounts.get(i);
            if (mail.equals(user.getEmail())) {
                String name = user.getName();
                return name;
            }
        }

        return null;
    }

    public void storeMailAfterPr(IMail mail, String path) {
        JSONObject obj = new JSONObject();
        obj.put("subject", mail.getSubject());
        obj.put("sender", mail.getSender());
        String date = (new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")).format(mail.getDate());
        obj.put("date", date);
        obj.put("priority", mail.getPriority());
        obj.put("folder name", mail.getFolderName());
        obj.put("star", mail.isStar());
        obj.put("read", mail.isRead());
        JSONArray rec = new JSONArray();

        for (int i = 0; i < mail.getReciever().size(); ++i) {
            rec.add(mail.getReciever().get(i));
        }

        obj.put("reciever", rec);
        if (mail.getAttach().equals((Object) null)) {
            obj.put("attachments numbers", 0);
        } else {
            obj.put("attachments numbers", mail.getAttach().size());
        }

        if (mail.getAttach() != null) {
            JSONArray att = new JSONArray();

            for (int i = 0; i < mail.getAttach().size(); ++i) {
                att.add(mail.getAttach().get(i));
            }
        }

        obj.put("text", mail.getText());
        obj.put("index", mail.getIndex());
        obj.put("path", mail.getPath());

        try {
            FileWriter index = new FileWriter(path + "/" + Integer.toString(mail.getIndex()) + "/" + Integer.toString(mail.getIndex()) + ".json");
            Throwable var22 = null;

            try {
                index.write(obj.toString());
                index.flush();
                index.close();
            } catch (Throwable var17) {
                var22 = var17;
                throw var17;
            } finally {
                if (index != null) {
                    if (var22 != null) {
                        try {
                            index.close();
                        } catch (Throwable var16) {
                            var22.addSuppressed(var16);
                        }
                    } else {
                        index.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
        }

    }

    public void writeFolders(MyDLinkedList folders, String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (int i = 0; i < folders.size(); ++i) {
            arr.add(folders.get(i));
        }

        obj.put("folders", arr);

        try {
            FileWriter index = new FileWriter(path);
            Throwable var6 = null;

            try {
                index.write(obj.toString());
                index.flush();
                index.close();
            } catch (Throwable var16) {
                var6 = var16;
                throw var16;
            } finally {
                if (index != null) {
                    if (var6 != null) {
                        try {
                            index.close();
                        } catch (Throwable var15) {
                            var6.addSuppressed(var15);
                        }
                    } else {
                        index.close();
                    }
                }

            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

    }

    public void makeFoldersIndex(String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("folders", arr);

        try {
            FileWriter jsonWriter = new FileWriter(path + "/index.json");
            Throwable var5 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var5 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public void makeContactsIndex(String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("contacts", arr);

        try {
            FileWriter jsonWriter = new FileWriter(path + "/contacts.json");
            Throwable var5 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var5 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public MyDLinkedList foldersRead(String path) {
        MyDLinkedList folders = new MyDLinkedList();
        JSONParser parser = new JSONParser();

        try {
            FileReader re = new FileReader(path);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("folders");

            for (int i = 0; i < arr.size(); ++i) {
                String o = arr.get(i).toString();
                folders.add(o);
            }

            re.close();
        } catch (FileNotFoundException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        } catch (ParseException var12) {
            var12.printStackTrace();
        }

        return folders;
    }

    public MyDLinkedList readContacts(String pathToRead) {
        MyDLinkedList contacts = new MyDLinkedList();
        JSONParser parser = new JSONParser();
        File f = new File(pathToRead);
        if (!f.exists()) {
            this.writeContacts(contacts, pathToRead);
        }

        try {
            FileReader re = new FileReader(pathToRead);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("contacts");

            for (int i = 0; i < arr.size(); ++i) {
                JSONObject tr = (JSONObject) arr.get(i);
                IContact contact = new IContact();
                contact.setName(tr.get("name").toString());
                contact.setEmail(tr.get("email").toString());
                contacts.add(contact);
                re.close();
            }
        } catch (FileNotFoundException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        } catch (ParseException var14) {
            var14.printStackTrace();
        }

        return contacts;
    }

    public void writeContacts(MyDLinkedList contacts, String path) {
        JSONArray allContacts = new JSONArray();

        for (int i = 0; i < contacts.size(); ++i) {
            new IContact();
            IContact ac = (IContact) contacts.get(i);
            JSONObject obj = new JSONObject();
            obj.put("name", ac.getName());
            obj.put("email", ac.getEmail());
            allContacts.add(obj);
        }

        JSONObject accOb = new JSONObject();
        accOb.put("contacts", allContacts);

        try {
            FileWriter jsonWriter = new FileWriter(path);
            Throwable var21 = null;

            try {
                jsonWriter.write(accOb.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var16) {
                var21 = var16;
                throw var16;
            } finally {
                if (jsonWriter != null) {
                    if (var21 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var15) {
                            var21.addSuppressed(var15);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

    }

    public IMail readMail(int index, String path) throws java.text.ParseException {
        IMail mail = new IMail();
        JSONParser parser = new JSONParser();

        try {
            FileReader re = new FileReader(path + "/" + index + "/" + index + ".json");
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            mail.setSubject(jo.get("subject").toString());
            mail.setSender(jo.get("sender").toString());
            mail.setIndex(Integer.parseInt(jo.get("index").toString()));
            mail.setPriority(Integer.parseInt(jo.get("priority").toString()));
            mail.setPath(jo.get("path").toString());
            mail.setFolderName(jo.get("folder name").toString());
            mail.setStar(Boolean.parseBoolean(jo.get("star").toString()));
            mail.setRead(Boolean.parseBoolean(jo.get("read").toString()));
            mail.setText(jo.get("text").toString());
            mail.setPath(jo.get("path").toString());
            String date = jo.get("date").toString();
            Date date1 = (new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")).parse(date);
            mail.setDate(date1);
            new JSONArray();
            JSONArray arr = (JSONArray) jo.get("reciever");
            MyLinkedList rec = new MyLinkedList();

            int attachmentsNom;
            for (attachmentsNom = 0; attachmentsNom < arr.size(); ++attachmentsNom) {
                rec.add(arr.get(attachmentsNom));
            }

            mail.setReciever(rec);
            attachmentsNom = Integer.parseInt(String.valueOf(jo.get("attachments numbers")));
            if (attachmentsNom != 0) {
                MyLinkedList attach = new MyLinkedList();
                File att = new File(path + "/" + index + "/attachments");
                File[] allContents = att.listFiles();

                for (int i = 0; i < allContents.length; ++i) {
                    attach.add(allContents[i]);
                }

                mail.setAttach(attach);
            }

            re.close();
        } catch (FileNotFoundException var17) {
            var17.printStackTrace();
        } catch (IOException var18) {
            var18.printStackTrace();
        } catch (ParseException var19) {
            var19.printStackTrace();
        }

        return mail;
    }

    public void resetIndex(String path) {
        MyDLinkedList mails = new MyDLinkedList();
        this.writemails(mails, path);
    }

    public void StoreFilter() {
    }

    public void makeFromIndex(String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("from", arr);

        try {
            FileWriter jsonWriter = new FileWriter(path + "/index.json");
            Throwable var5 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var5 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public void makeSubjectIndex(String path) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("subjects", arr);

        try {
            FileWriter jsonWriter = new FileWriter(path + "/index.json");
            Throwable var5 = null;

            try {
                jsonWriter.write(obj.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (jsonWriter != null) {
                    if (var5 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

    }

    public void storeFrom(String from) {
        new JSONObject();
    }

    public void writeFrom(MyDLinkedList senders, String path) {
        JSONArray allsenders = new JSONArray();

        for (int i = 0; i < senders.size(); ++i) {
            new String();
            String ac = senders.get(i).toString();
            System.out.println(ac);
            allsenders.add(ac);
        }

        JSONObject accOb = new JSONObject();
        accOb.put("from", allsenders);

        try {
            FileWriter jsonWriter = new FileWriter(path);
            Throwable var6 = null;

            try {
                jsonWriter.write(accOb.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var16) {
                var6 = var16;
                throw var16;
            } finally {
                if (jsonWriter != null) {
                    if (var6 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var15) {
                            var6.addSuppressed(var15);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

    }

    public void writeSubject(MyDLinkedList subjects, String path) {
        JSONArray allsubjects = new JSONArray();

        for (int i = 0; i < subjects.size(); ++i) {
            new String();
            String ac = subjects.get(i).toString();
            allsubjects.add(ac);
        }

        JSONObject accOb = new JSONObject();
        accOb.put("subjects", allsubjects);

        try {
            FileWriter jsonWriter = new FileWriter(path);
            Throwable var6 = null;

            try {
                jsonWriter.write(accOb.toString());
                jsonWriter.flush();
                jsonWriter.close();
            } catch (Throwable var16) {
                var6 = var16;
                throw var16;
            } finally {
                if (jsonWriter != null) {
                    if (var6 != null) {
                        try {
                            jsonWriter.close();
                        } catch (Throwable var15) {
                            var6.addSuppressed(var15);
                        }
                    } else {
                        jsonWriter.close();
                    }
                }

            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

    }

    public MyDLinkedList readFrom(String pathToRead) {
        MyDLinkedList senders = new MyDLinkedList();
        JSONParser parser = new JSONParser();
        File f = new File(pathToRead);
        if (!f.exists()) {
            this.writeFrom(senders, pathToRead);
        }

        try {
            FileReader re = new FileReader(pathToRead);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("from");

            for (int i = 0; i < arr.size(); ++i) {
                new String();
                String sender = arr.get(i).toString();
                senders.add(sender);
                re.close();
            }
        } catch (FileNotFoundException var11) {
            var11.printStackTrace();
        } catch (IOException var12) {
            var12.printStackTrace();
        } catch (ParseException var13) {
            var13.printStackTrace();
        }

        return senders;
    }

    public MyDLinkedList readSubject(String pathToRead) {
        MyDLinkedList subjects = new MyDLinkedList();
        JSONParser parser = new JSONParser();
        File f = new File(pathToRead);
        if (!f.exists()) {
            this.writeSubject(subjects, pathToRead);
        }

        try {
            FileReader re = new FileReader(pathToRead);
            Object obj = parser.parse(re);
            JSONObject jo = (JSONObject) obj;
            JSONArray arr = (JSONArray) jo.get("subjects");

            for (int i = 0; i < arr.size(); ++i) {
                new String();
                String subject = arr.get(i).toString();
                subjects.add(subject);
                re.close();
            }
        } catch (FileNotFoundException var11) {
            var11.printStackTrace();
        } catch (IOException var12) {
            var12.printStackTrace();
        } catch (ParseException var13) {
            var13.printStackTrace();
        }

        return subjects;
    }
}
