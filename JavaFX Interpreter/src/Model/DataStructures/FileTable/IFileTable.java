package Model.DataStructures.FileTable;

import Model.Exceptions.MyException;

import java.util.Set;

public interface IFileTable<K, V> {
    V lookup(K id) throws MyException;

    boolean isDefined(K id);

    void update(K id, V val);

    void remove(K val);

    Set<K> getAllKeys();
}
