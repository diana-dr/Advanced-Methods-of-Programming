package Model.Statements;

import Model.DataStructures.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt{
    PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException, CloneNotSupportedException, Exception;
}