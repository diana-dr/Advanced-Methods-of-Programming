package Model.DataStructures.Heap;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<V> {
    V lookup(Integer id) throws MyException;

    boolean isDefined(Integer id);

    void update(Integer id, V val);

    void remove(Integer val);
    int allocate(V value);

    public Set<Map.Entry<Integer, V>> entrySet();
    HashMap<Integer, V> getContent();
    void setContent(Set<Map.Entry<Integer, V>> set);
    public Iterable<Map.Entry<Integer, V>> getAll();
}
