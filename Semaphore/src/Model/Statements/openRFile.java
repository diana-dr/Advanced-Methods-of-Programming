package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.FileTable.IFileTable;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Dictionary;

public class openRFile implements IStmt {
    private Exp exp;

    public openRFile(Exp expression) {

        this.exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> hp = state.getHeap();

        Value val = exp.eval(symTbl, hp);
        StringValue file = (StringValue) val;
        if (fileTable.isDefined(file.getVal()))
            throw new MyException("Value already exists!");
        else {
            BufferedReader b = new BufferedReader(new FileReader(file.getVal()));
            fileTable.update(file.getVal(), b);
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
