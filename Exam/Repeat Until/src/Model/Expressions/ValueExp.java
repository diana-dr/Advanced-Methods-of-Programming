package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Types.Type;
import Model.Values.*;
import Model.Exceptions.*;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {

        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException { return e; }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() { return e.toString(); }

}
