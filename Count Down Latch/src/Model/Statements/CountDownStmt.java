package Model.Statements;

import Model.DataStructures.CDL.ICountDown;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.List.MyIList;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt{

    private String var;
    private static Lock lock = new ReentrantLock();

    public CountDownStmt(String var) {

        this.var = var;
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        lock.lock();

        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        ICountDown<Integer, Integer> cdl = state.getCdlTable();

        if (!symTbl.isDefined(var))
            throw new MyException("Var not declared in table");

        IntValue foundValue = (IntValue) symTbl.lookup(var);
        Integer foundIndex = foundValue.getVal();
        int count = state.getCdlTable().lookup(foundIndex);

        if (count > 0) {
            cdl.update(foundIndex, count - 1);
            out.add(new IntValue(state.getThreadID()));
        }

        lock.unlock();

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
