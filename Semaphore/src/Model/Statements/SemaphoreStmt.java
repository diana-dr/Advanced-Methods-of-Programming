package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Semaphore.ISemaphoreTable;
import Model.DataStructures.Semaphore.Pair;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreStmt implements IStmt {

    private String var;
    private Exp exp;
    private static Lock lock = new ReentrantLock();
    private int free = 0;

    public SemaphoreStmt(String var, Exp exp) {

        this.exp = exp;
        this.var = var;
    }

    @Override
    public String toString() {
        return "newSemaphore(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        lock.lock();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        IntValue value = (IntValue) exp.eval(symTbl, hp);
        Integer number = value.getVal();

        if (symTbl.lookup(var) != null)
            symTbl.update(var, new IntValue(free));
        else
            symTbl.add(var, new IntValue(free));

        semaphoreTable.add(free, new Pair<>(number, new ArrayList<>()));

        state.setSemaphoreTable(semaphoreTable);
        state.setSymTable(symTbl);

        free++;
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
