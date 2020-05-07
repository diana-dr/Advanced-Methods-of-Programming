package Model.Statements;

import Model.DataStructures.CyclicBarrier.Pair;
import Model.DataStructures.Dictionary.MyDictionary;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BarrierStmt implements IStmt {

    private Exp var;
    private Exp exp;
    private Integer newFree = 0;
    private static Lock lock = new ReentrantLock();

    public BarrierStmt(Exp var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {

        return "newBarrier(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        lock.lock();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();

        IntValue val = (IntValue) exp.eval(symTbl, hp);

        Integer number = val.getVal();

        String string = var.toString();

        synchronized (state.getBarrier()) {
            state.getBarrier().add(newFree, new Pair<>(number, new ArrayList<>()));
        }

        if (symTbl.isDefined(string))
            symTbl.update(string, new IntValue(newFree));
        else
            symTbl.add(string, new IntValue(newFree));

        newFree++;

        lock.unlock();

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typ1, typ2;
        typ1 = var.typecheck(typeEnv);
        typ2 = exp.typecheck(typeEnv);
        if (!typ1.equals(new IntType()) || !typ2.equals(new IntType()))
                throw new MyException("Var or Exp do not have the type int!");

        return null;
    }
}
