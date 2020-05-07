package Model.Statements;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.*;
import Model.PrgState;
import Model.Types.Type;

public class  CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt v1, IStmt v2) {
        this.first = v1;
        this.snd = v2;
    }

    public String toString() {

        return "(" + first.toString() + "; " + snd.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return snd.typecheck(first.typecheck(typeEnv));
    }

}
