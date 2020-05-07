package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class rH implements Exp {
    private Exp exp;

    public rH(Exp expression) {

        this.exp = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException {
        Value value = exp.eval(tbl, hp);
        int address = ((RefValue) value).getAddr();

       return hp.lookup(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType ref = (RefType) typ;
            return ref.getInner();
        } else
            throw new MyException("The rH argument is not a Ref Type");
    }

    @Override
    public String toString() {

        return "rH(" + exp.toString() + ")";
    }
}
