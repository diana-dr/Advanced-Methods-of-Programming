package Model.DataStructures.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    String toString();
    T peek();
    Iterable<T> getAll();
}
