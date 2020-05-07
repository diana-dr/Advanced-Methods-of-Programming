package Model.Statements;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.List.MyIList;
import Model.Exceptions.*;
import Model.Expressions.*;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp v) {

        this.exp = v;
    }

    public String toString(){ return "print("  + exp.toString() + ")"; }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Value> hp = state.getHeap();

        Value val = exp.eval(symTbl, hp);
        out.add(val);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}