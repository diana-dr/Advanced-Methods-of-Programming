package Model.DataStructures.CyclicBarrier;

import Model.Exceptions.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ICyclicBarrier<K, V> {
    V lookup(K id) throws MyException;

    boolean isDefined(K id);

    public ICyclicBarrier<K, V> clone_dict();

    void add(K id, V val);

    void remove(K val);

    public Collection<V> values();

    Map<K, V> getContent();

    public Set<Map.Entry<K, V>> entrySet();

    public Iterable<Map.Entry<K, V>> getAll() ;
}
