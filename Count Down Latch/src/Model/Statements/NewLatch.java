package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStmt {

    private String var;
    private Exp exp;
    private static Lock lock = new ReentrantLock();
    private int newFree = 0;

    public NewLatch(String var, Exp exp) {

        this.exp = exp;
        this.var = var;

    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        lock.lock();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();

        IntValue val = (IntValue) exp.eval(symTbl, hp);
        Integer number = val.getVal();

        synchronized (state.getCdlTable()) {
            state.getCdlTable().add(newFree, number);
        }

        if (symTbl.isDefined(var))
            symTbl.update(var, new IntValue(newFree));
        else
            symTbl.add(var, new IntValue(newFree));

        newFree++;

        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
