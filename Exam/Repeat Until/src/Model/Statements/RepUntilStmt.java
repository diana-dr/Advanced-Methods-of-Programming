package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Expressions.NotExpression;
import Model.PrgState;
import Model.Types.Type;

import java.io.IOException;

public class RepUntilStmt implements IStmt {

    private IStmt stmt;
    private Exp exp;

    public RepUntilStmt(IStmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "repeat " + stmt.toString() + " until(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        state.getStk().push(stmt);

        IStmt statement = new CompStmt(stmt, new WhileStmt(new NotExpression(exp), stmt));

        state.getStk().push(statement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
