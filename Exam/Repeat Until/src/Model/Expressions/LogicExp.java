package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.*;


public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1-and, 2-or

    public LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        boolean result = true;

        v2 = e2.eval(tbl, hp);
        if (v2.getType().equals(new BoolType()))
            if (op == 1) {
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                result = b1.getVal() && b2.getVal();
            }
            else if (op == 2) {
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                result = b1.getVal() || b2.getVal();
            }
        return new BoolValue(result);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("Second operand is not a boolean!");
        } else
            throw new MyException("First operand is not a boolean!");
    }

    @Override
    public String toString() {

        return "";
    }
}
