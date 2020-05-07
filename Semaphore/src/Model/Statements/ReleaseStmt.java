package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Semaphore.ISemaphoreTable;
import Model.DataStructures.Semaphore.Pair;
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

public class ReleaseStmt implements IStmt {

    private String var;
    private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static Lock lock = new ReentrantLock();

    public ReleaseStmt(String string) {

        this.var = string;
    }

    @Override
    public String toString() {
        return "release(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if (!symTbl.isDefined(var))
            throw new MyException("Var nor defined in syn table!");

        IntValue value = (IntValue) symTbl.lookup(var);
        int foundIdx = value.getVal();

        if (!semaphoreTable.isDefined(foundIdx))
            throw new MyException("Index not in semaphore table!");

        List<Integer> List1 = semaphoreTable.lookup(foundIdx).getSecond();

        if (List1.contains(state.getThreadID()))
        {
            for (int index = 0; index < List1.size(); index++)
                if (List1.get(index) == state.getThreadID())
                    List1.remove(index);
        }

        lock.unlock();

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
