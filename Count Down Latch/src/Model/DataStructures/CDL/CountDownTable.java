package Model.DataStructures.CDL;

import Model.DataStructures.CDL.ICountDown;
import Model.Exceptions.MyException;

import java.util.*;

public class CountDownTable<K,V> implements ICountDown<K,V> {

    private HashMap<K, V> dictionary = new HashMap<K, V>();

    public V lookup(K id) throws MyException {
        if (dictionary.get(id) == null)
            throw new MyException("Variable is not defined!");
        else
            return dictionary.get(id);
    }

    @Override
    public boolean isDefined(K id) {
        return dictionary.containsKey(id);
    }


    @Override
    public void add(K id, V val) {

        dictionary.put(id, val);
    }

    @Override
    public void update(K id, V val) {

        dictionary.replace(id, val);
    }

    @Override
    public Iterable<Map.Entry<K, V>> getAll() {

        return dictionary.entrySet();
    }

    @Override
    public void remove(K val) {
        dictionary.remove(val);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public ICountDown<K, V> clone_dict() {
        ICountDown<K, V> dict = new CountDownTable<>();
        for(K key : dictionary.keySet())
            dict.add(key, dictionary.get(key));
        return dict;
    }

    @Override
    public Collection<V> values() {
        return dictionary.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return dictionary.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CountDownTable: {");
        Iterator<Map.Entry<K, V>> it = dictionary.entrySet().iterator();
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
