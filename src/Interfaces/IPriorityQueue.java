package Interfaces;

public interface IPriorityQueue {
    void insert(Object var1, int var2);

    Object removeMin();

    Object min();

    boolean isEmpty();

    int size();
}

