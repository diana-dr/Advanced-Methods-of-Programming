package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.FileTable.IFileTable;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class closeRFile implements IStmt {
    private Exp exp;

    public closeRFile(Exp expression) {

        this.exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> hp = state.getHeap();

        Value val = exp.eval(symTbl, hp);
        StringValue file = (StringValue) val;

        if (!val.getType().equals(new StringType()))
            throw new MyException("Value not string!");
        else {
            BufferedReader b = new BufferedReader(new FileReader(file.getVal()));
            if (!fileTable.isDefined(file.getVal()))
                throw new MyException("");
            else {
                b.close();
                fileTable.remove(file.getVal());
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);

        if (!typexp.equals(new StringType()))
            throw new MyException("Type not string!");
        return typeEnv;
    }

    public String toString() { return exp.toString(); }

}
