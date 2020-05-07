package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Semaphore.ISemaphoreTable;
import Model.DataStructures.Semaphore.Pair;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AcquireStmt implements IStmt {

    private String var;
    private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {

        this.var = var;
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIStack<IStmt> stk = state.getStk();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if (!symTbl.isDefined(var))
            throw new MyException("Var nor defined in sym table!");

        IntValue value = (IntValue) symTbl.lookup(var);
        int foundIdx = value.getVal();

        if (!semaphoreTable.isDefined(foundIdx))
            throw new MyException("Index not in semaphore table!");

        Integer N1 = semaphoreTable.lookup(foundIdx).getFirst();
        List<Integer> List1 = semaphoreTable.lookup(foundIdx).getSecond();
        Integer NL = List1.size();

        if (!N1.equals(NL)) {
            if (!List1.contains(state.getThreadID())) {
                List1.add(state.getThreadID());
                semaphoreTable.add(foundIdx, new Pair<>(N1, List1));
            }
        } else {
            stk.push(this);
            state.setExeStack(stk);
        }

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
