package Model.Expressions;

import Model.DataStructures.Dictionary.MyDictionary;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private String op;

    public RelExp(Exp e1, Exp e2, String operator) {
        exp1 = e1;
        exp2 = e2;
        op = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException {
        BoolValue result = new BoolType().defaultValue();
        Value v1 = exp1.eval(tbl, hp);
        Value v2 = exp2.eval(tbl, hp);
        int n1 = ((IntValue) v1).getVal();
        int n2 = ((IntValue) v2).getVal();

        switch (op) {
            case "==":
                result = new BoolValue(n1 == n2);
                break;
            case ">":
                result = new BoolValue(n1 > n2);
                break;
            case "<":
                result = new BoolValue(n1 < n2);
                break;
            case "!=":
                result = new BoolValue(n1 != n2);
                break;
            case ">=":
                result = new BoolValue(n1 >= n2);
                break;
            case "<=":
                result = new BoolValue(n1 <= n2);
                break;
        }
        return result;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);

        if (!typ1.equals(new IntType()))
            throw new MyException("First argument not int!");
        else
            if (!typ2.equals(new IntType()))
                throw new MyException("Second argument not int!");
            else
                return new BoolType();
    }

    @Override
    public String toString() {
        return exp1.toString() + " " +  op + " " + exp2.toString();
    }
}
