package Model.Statements;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    public String toString() {
        return "( IF(" + exp.toString() + ") THEN(" + thenS.toString() + ") ELSE(" + elseS.toString() + "))";
    }

    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();

        Value val = exp.eval(symTbl, hp);

        if (val.getType().equals(new BoolType())) {
            BoolValue v = (BoolValue) val;
            if (v.getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        else
            throw new MyException("Not bool type!");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {

            thenS.typecheck(typeEnv.clone_dict());
            elseS.typecheck(typeEnv.clone_dict());

            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}