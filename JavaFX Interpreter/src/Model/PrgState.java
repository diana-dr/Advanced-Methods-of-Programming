package Model;

import Model.DataStructures.Dictionary.MyDictionary;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.FileTable.FileTable;
import Model.DataStructures.FileTable.IFileTable;
import Model.DataStructures.Heap.MyHeap;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.List.MyIList;
import Model.DataStructures.List.MyList;
import Model.DataStructures.Stack.MyIStack;
import Model.DataStructures.Stack.MyStack;
import Model.Exceptions.MyException;
import Model.Statements.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    private int threadID = 1;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IFileTable<String, BufferedReader> fileTable;
    private MyIHeap<Value> heap;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IFileTable<String, BufferedReader> fTable, MyIHeap<Value> heapTable,
             int id, IStmt prg)
    {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fTable;
        heap = heapTable;
        threadID = id;

        stk.push(prg);
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public int getThreadID() {
        return threadID;
    }


    public PrgState(IStmt prg){

        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        out = new MyList<>();
        fileTable = new FileTable<>();
        heap = new MyHeap<>();

        exeStack.push(prg);
    }

    public MyIStack<IStmt> getStk() {

        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {

        return symTable;
    }

    public MyIList<Value> getOut() {

        return out;
    }

    public IFileTable<String, BufferedReader> getFileTable() {

        return fileTable;
    }

    public MyIHeap<Value> getHeap() {

        return heap;
    }


    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, IOException {
        if(exeStack.isEmpty())
            throw new MyException("Program state stack is empty!");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public String toString() {
        return "Program state:\n" + "ID: " + threadID + " \n" + exeStack.toString() + symTable.toString()
                + out.toString() + fileTable.toString()  + heap.toString() + "\n";
    }
}
