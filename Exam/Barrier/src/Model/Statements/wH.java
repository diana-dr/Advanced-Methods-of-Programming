package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.*;
import Model.Values.RefValue;
import Model.Values.Value;

public class wH implements IStmt {
    private String var_name;
    private Exp exp;

    public wH(String name, Exp expression) {
        this.var_name = name;
        this.exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)) {
            Value val = symTable.lookup(var_name);
            Type type = val.getType();

            RefValue value = (RefValue) val;

            while (type instanceof RefType)
                type = ((RefType) type).getInner();

            if (heap.isDefined(value.getAddr())) {

                    Value evaluation = exp.eval(symTable, heap);
                    Type type1 = evaluation.getType();

                    if (type1.equals(type))
                        heap.update(value.getAddr(), evaluation);

                    else
                        throw new MyException("Type is not the same!");
            } else
                throw new MyException("Key is not defined in the heap!");
        } else
            throw new MyException("Var name not defined!");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);

        if (typexp instanceof RefValue)
            return typeEnv;
        else throw new MyException("Type not string!");
    }

    @Override
    public String toString() {
        return "wH(" + var_name + ", " + exp + ")";
    }
}
