package Model.DataStructures.Semaphore;

import Model.Exceptions.MyException;

import java.util.*;

public class SemaphoreTable<K,V> implements ISemaphoreTable<K,V> {

    private HashMap<K, V> semaphoreTable = new HashMap<K, V>();

    public V lookup(K id) throws MyException {
            return semaphoreTable.get(id);
    }

    @Override
    public boolean isDefined(K id) {
        return semaphoreTable.containsKey(id);
    }

    @Override
    public void add(K id, V val) {

        semaphoreTable.put(id, val);
    }


    @Override
    public Iterable<Map.Entry<K, V>> getAll() {

        return semaphoreTable.entrySet();
    }

    @Override
    public void remove(K val) {
        semaphoreTable.remove(val);
    }

    @Override
    public Map<K, V> getContent() {
        return semaphoreTable;
    }

    @Override
    public ISemaphoreTable<K, V> clone_dict() {
        ISemaphoreTable<K, V> dict = new SemaphoreTable<>();
        for(K key : semaphoreTable.keySet())
            dict.add(key, semaphoreTable.get(key));
        return dict;
    }

    @Override
    public Collection<V> values() {
        return semaphoreTable.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return semaphoreTable.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Semaphore Table: {");
        Iterator<Map.Entry<K, V>> it = semaphoreTable.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            sb.append(entry.getKey());
            sb.append("->").append('(');
            sb.append(entry.getValue());
            sb.append(')');
            if (it.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString() + "}" + "\n";
    }
}
