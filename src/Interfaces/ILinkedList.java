package Interfaces;

public interface ILinkedList {
    void add(int var1, Object var2);

    void add(Object var1);

    Object get(int var1);

    void set(int var1, Object var2);

    void clear();

    boolean isEmpty();

    void remove(int var1);

    int size();

    ILinkedList sublist(int var1, int var2);

    boolean contains(Object var1);
}
