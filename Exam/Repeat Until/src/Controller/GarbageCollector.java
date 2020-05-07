package Controller;

import Model.DataStructures.Heap.MyIHeap;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {
    private Map<Integer, Value> conservativeGarbageCollector(Collection<Value> symTblValues, MyIHeap<Value> heapTable) {

        return heapTable.entrySet().stream()
                .filter(e->symTblValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {

        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
}
