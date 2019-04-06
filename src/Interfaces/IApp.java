package Interfaces;

public interface IApp {
    boolean signin(String var1, String var2);

    boolean signup(IContact var1);

    void setViewingOptions(IFolder var1, IFilter var2, ISort var3);

    IMail[] listEmails(int var1);

    void deleteEmails(ILinkedList var1);

    void moveEmails(ILinkedList var1, IFolder var2);

    boolean compose(IMail var1);
}

