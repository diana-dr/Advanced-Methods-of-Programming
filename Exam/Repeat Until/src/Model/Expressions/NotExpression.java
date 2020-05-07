package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class NotExpression implements Exp {

    private Exp exp;

    public NotExpression(Exp expression) {
        this.exp = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException {

        Value init = exp.eval(tbl, hp);
        BoolValue cond = (BoolValue) init;
        boolean finalCond = cond.getVal();

        return finalCond ? new BoolValue(false) : new BoolValue(true);
    }

    @Override
    public String toString() {
        return "not(" + exp.toString() +
                ')';
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
