package Model.DataStructures.Stack;
import Model.DataStructures.Dictionary.MyDictionary;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stack;

    public MyStack() { stack = new Stack<T>(); }

    @Override
    public T pop() {
         return stack.pop();
    }

    @Override
    public void push(T v) {

        stack.push(v);
    }

    @Override
    public boolean isEmpty() { return stack.empty(); }

    @Override
    public String toString() { return "Execution Stack: " + stack.toString() + "\n"; }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public Iterable<T> getAll() {
        return stack;
    }
}
