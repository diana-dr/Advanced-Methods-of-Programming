package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.*;
import Model.Values.RefValue;
import Model.Values.Value;

import java.io.IOException;

public class NewStmt implements IStmt {
    private String var_name;
    private Exp exp;

    public NewStmt(String name, Exp e) {
        this.var_name = name;
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();

        if (symTable.isDefined(var_name)) {
            Value value = exp.eval(symTable, hp);

            int adr = state.getHeap().allocate(value);

            Value val = symTable.lookup(var_name);
            RefValue newRef = new RefValue(adr, val.getType());
            state.getSymTable().add(var_name, newRef);
        }
        else
            throw new MyException("Var name nor defined in dictionary!");


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString() { return "new(" + var_name + ", " + exp + ")"; }
}
