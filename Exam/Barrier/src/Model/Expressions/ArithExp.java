package Model.Expressions;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Values.*;
import Model.Types.*;
import Model.Exceptions.*;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(int op, Exp e1, Exp e2){
         this.e1 = e1;
         this.e2 = e2;
         this.op = op;
     }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> hp) throws MyException {
        Value v1,v2;
        Value result = null;
        v1 = e1.eval(tbl, hp);
        v2 = e2.eval(tbl, hp);
        IntValue i1 = (IntValue)v1;
        IntValue i2 = (IntValue)v2;
        int n1,n2;
        n1= i1.getVal();
        n2 = i2.getVal();
        if (op == 1)
            result = new IntValue(n1+n2);
        if (op == 2)
            result = new IntValue(n1-n2);
        if (op == 3)
            result = new IntValue(n1*n2);
        if (op == 4)
            if(n2 == 0)
                throw new MyException("Division by zero!");
            else
                result = new IntValue(n1/n2);
        return result;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("Second operand is not an integer!");
        } else
        throw new MyException("First operand is not an integer!");
    }

    @Override
    public String toString() {
        String result = this.e1.toString();
        if (this.op == 1)
            result += " + ";
        else if (this.op == 2)
            result += " - ";
        else if (this.op == 3)
            result += " * ";
        else
            result += " / ";
        result += this.e2.toString();
        return result;
    }
}
