package Model.DataStructures.CyclicBarrier;

import java.util.*;

public class CyclicBarrier<K, V> implements ICyclicBarrier<K, V> {
    private HashMap<K, V> cyclicBarrier = new HashMap<K, V>();

    public synchronized V lookup(K id) {

        return cyclicBarrier.get(id);
    }

    @Override
    public boolean isDefined(K id) { return cyclicBarrier.containsKey(id); }

    @Override
    public synchronized void add(K id, V val) {

        cyclicBarrier.put(id, val);
    }

    @Override
    public Iterable<Map.Entry<K, V>> getAll() {

        return cyclicBarrier.entrySet();
    }

    @Override
    public void remove(K val) {
        cyclicBarrier.remove(val);
    }

    @Override
    public Map<K, V> getContent() {
        return cyclicBarrier;
    }

    @Override
    public ICyclicBarrier<K, V> clone_dict() {
        ICyclicBarrier<K, V> dict = new CyclicBarrier<>();
        for(K key : cyclicBarrier.keySet())
            dict.add(key, cyclicBarrier.get(key));
        return dict;
    }

    @Override
    public Collection<V> values() {
        return cyclicBarrier.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return cyclicBarrier.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BarrierTable: {");
        Iterator<Map.Entry<K, V>> it = cyclicBarrier.entrySet().iterator();
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
