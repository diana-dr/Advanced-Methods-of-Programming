package Model.DataStructures.Heap;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyHeap<V> implements MyIHeap<V> {
    private HashMap<Integer, V> heap = new HashMap<Integer, V>();
    private int memory;

    @Override
    public int allocate(V value) {
        ++ this.memory;
        heap.put(memory, value);
        return memory;
    }

    @Override
    public HashMap<Integer, V> getContent() {

        return heap;
    }

    @Override
    public Iterable<Map.Entry<Integer, V>> getAll() {

        return  heap.entrySet();
    }

    @Override
    public void setContent(Set<Map.Entry<Integer, V>> set) {

        heap.clear();
        for (Map.Entry<Integer, V> entry : set) {
            heap.put(entry.getKey(), entry.getValue());
        }
    }

    public V lookup(Integer id) throws MyException {
        if (heap.get(id) == null)
            throw new MyException("Variable is not defined!");
        else
            return heap.get(id);
    }

    @Override
    public boolean isDefined(Integer id) {

        return heap.containsKey(id);
    }

    @Override
    public void update(Integer id, V val) {

        heap.put(id, val);
    }

    @Override
    public void remove(Integer val) {

        heap.remove(val);
    }

    public Set<Map.Entry<Integer, V>> entrySet() {
        return heap.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Heap: {");
        Iterator<Map.Entry<Integer, V>> it = heap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, V> entry = it.next();
            sb.append(entry.getKey());
            sb.append("->");
            sb.append(entry.getValue());
            if (it.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString() + "}" + "\n";
    }
}
