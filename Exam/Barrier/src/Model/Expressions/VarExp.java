package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp {
    private String id;

    public VarExp(String id) {

        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException { return tbl.lookup(id); }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {

        return id;
    }
}


