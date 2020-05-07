package Model.DataStructures.List;

import java.util.List;

public interface MyIList<T> {
    int size();
    void add(T e);
    boolean isEmpty();
    void clear();
    T get(int index);
    String toString();
    Iterable<T> getAll();
}
