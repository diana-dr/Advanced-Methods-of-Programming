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
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {
    private Exp exp;
    private String var_name;

    public readFile(Exp expression, String variable_name) {
        exp = expression;
        var_name = variable_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> hp = state.getHeap();
        int value;

        if (!symTbl.isDefined(var_name))
            throw new MyException("Variable name is not defined!");
        else {
            Value val = exp.eval(symTbl, hp);

            StringValue file = (StringValue) val;
            BufferedReader b = fileTable.lookup(file.getVal());
            String line = b.readLine();

            if (line == null)
                value = 0;

            else value = Integer.parseInt(line);

        }
        symTbl.add(var_name, new IntValue(value));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);

        if (!typexp.equals(new StringType()))
            throw new MyException("Type not string!");

        return typeEnv;
    }

    public String toString() { return exp.toString() + " " + var_name; }
}
