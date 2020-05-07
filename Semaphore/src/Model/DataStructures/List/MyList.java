package Model.DataStructures.List;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {

    private ArrayList<T> list;

    public MyList() { list = new ArrayList<T>(); }

    @Override
    public int size() {

        return list.size();
    }

    @Override
    public void add(T e) {

        list.add(e);
    }

    @Override
    public boolean isEmpty() {

        return list.isEmpty();
    }

    @Override
    public void clear() {

        list.clear();
    }

    @Override
    public T get(int index) {

        return list.get(index);
    }

    @Override
    public String toString() { return  "Output List: " + list.toString() + "\n"; }

    @Override
    public Iterable<T> getAll() {
        return list;
    }
}
