package Model.Statements;

import Model.DataStructures.CDL.ICountDown;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;

public class AwaitStmt implements IStmt {

    private String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        ICountDown<Integer, Integer> cdl = state.getCdlTable();

        if (!symTbl.isDefined(var))
            throw new MyException("Var not declared in table!");

        IntValue foundValue = (IntValue) symTbl.lookup(var);
        Integer foundIndex = foundValue.getVal();

        if (!cdl.isDefined(foundIndex))
            throw new MyException("Found Index not in latch table!");

        if (cdl.lookup(foundIndex) != 0)
            stk.push(this);

        return null;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
