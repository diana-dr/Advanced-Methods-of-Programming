package Model.DataStructures.FileTable;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileTable<K, V> implements IFileTable<K, V> {

    private HashMap<K, V> fileTable = new HashMap<K, V>();

    public V lookup(K id) throws MyException {
        if (fileTable.get(id) == null)
            throw new MyException("Variable is not defined!");
        else
            return fileTable.get(id);
    }

    @Override
    public boolean isDefined(K id) {

        return fileTable.containsKey(id);
    }

    @Override
    public void update(K id, V val) {

        fileTable.put(id, val);
    }

    @Override
    public void remove(K val) {
        fileTable.remove(val);
    }

    @Override
    public Set<K> getAllKeys() {
        return fileTable.keySet();
    }

    @Override
    public String toString() {
        return "FileTable: " + fileTable.keySet().toString() + "\n";
    }
}
