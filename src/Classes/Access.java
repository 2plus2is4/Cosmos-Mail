package Classes;

import Interfaces.MyDLinkedList;

public class Access {
    public Access() {
    }

    public MyDLinkedList getAcessMailFolders(String email, String name, String folderName) {
        new MyDLinkedList();
        Store s = new Store();
        MyDLinkedList store = s.mailsRead("accounts/" + name + "/" + email + "/" + folderName + "/index.json");
        return store;
    }

    public MyDLinkedList getAcessFolders(String email, String name, String folderName) {
        new MyDLinkedList();
        Store s = new Store();
        MyDLinkedList store = s.foldersRead("accounts/" + name + "/" + email + "/" + folderName + "/index.json");
        return store;
    }

    public MyDLinkedList getAcessContacts(String email, String name, String folderName) {
        new MyDLinkedList();
        Store s = new Store();
        MyDLinkedList store = s.readContacts("accounts/" + name + "/" + email + "/" + folderName + "/contacts.json");
        return store;
    }
}
