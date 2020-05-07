package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.*;
import Model.Types.Type;
import Model.Values.*;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException;

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    String toString();
}
