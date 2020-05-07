package Model.Statements;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.*;
import Model.Values.*;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type typ;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.typ = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(name))
            throw new MyException("Variable is already declared");
        else {
            if (typ.equals(new BoolType())) {
                BoolValue b = (BoolValue) typ.defaultValue();
                state.getSymTable().add(name, b);
            }
            else if (typ.equals(new IntType()))
            {
                IntValue i = (IntValue) typ.defaultValue();
                state.getSymTable().add(name, i);
            }
            else if (typ.equals(new StringType())) {
                StringValue s = (StringValue) typ.defaultValue();
                state.getSymTable().add(name, s);
            }
            else {
                RefValue r = (RefValue) typ.defaultValue();
                state.getSymTable().add(name, r);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, typ);
        return typeEnv;
    }

    public String toString() {

        return name + " " + typ.toString();
    }
}
