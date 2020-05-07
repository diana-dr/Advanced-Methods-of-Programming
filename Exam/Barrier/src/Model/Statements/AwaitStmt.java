package Model.Statements;

import Model.DataStructures.CyclicBarrier.Pair;
import Model.DataStructures.Dictionary.MyDictionary;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.beans.Expression;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {

    private Exp var;
    private static Lock lock = new ReentrantLock();

    public AwaitStmt(Exp var) { this.var = var; }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        lock.lock();

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        String string = var.toString();

        if (!symTbl.isDefined(string))
            throw new MyException("Var not defined in sym table!");

        IntValue value = (IntValue) symTbl.lookup(string);
        int foundIdx = value.getVal();

        if (!state.getBarrier().isDefined(foundIdx))
            throw new MyException("Found Index is not defined in BarrierTable!");

        synchronized (state.getBarrier()) {

            Pair<Integer, List<Integer>> pair = state.getBarrier().lookup(foundIdx);
            Integer N1 = pair.getFirst();
            List<Integer> L1 = pair.getSecond();
            Integer NL = L1.size();

            if (N1 > NL)
                if (L1.contains(state.getThreadID()))
                    stk.push(this);
                else {
                    L1.add(state.getThreadID());
                    stk.push(this);
                }
        }

        lock.unlock();

        return null;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typ1;
        typ1 = var.typecheck(typeEnv);

        if (!typ1.equals(new IntType()))
            throw new MyException("Var does not have the type int!");

        return null;
    }
}
