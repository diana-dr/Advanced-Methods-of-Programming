package Model.Statements;

import Model.DataStructures.CyclicBarrier.CyclicBarrier;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Stack.MyStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class forkStmt implements IStmt {

    private IStmt stmt;
    private static Lock lock = new ReentrantLock();

    public forkStmt(IStmt stmt1) {

        this.stmt = stmt1;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        MyIDictionary<String, Value> newTbl = symTbl.clone_dict();
        PrgState newPrgState = new PrgState(state.getBarrier(), new MyStack<>(), newTbl, state.getOut(), state.getFileTable(),
                state.getHeap(), state.getLastID() * 10, stmt);

        state.setLastID(state.getLastID() * 10);

        return newPrgState;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
