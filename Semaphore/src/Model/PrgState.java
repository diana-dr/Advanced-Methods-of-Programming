package Model;

import Model.DataStructures.Dictionary.MyDictionary;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.FileTable.FileTable;
import Model.DataStructures.FileTable.IFileTable;
import Model.DataStructures.Heap.MyHeap;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.List.MyIList;
import Model.DataStructures.List.MyList;
import Model.DataStructures.Semaphore.ISemaphoreTable;
import Model.DataStructures.Semaphore.Pair;
import Model.DataStructures.Semaphore.SemaphoreTable;
import Model.DataStructures.Stack.MyIStack;
import Model.DataStructures.Stack.MyStack;
import Model.Exceptions.MyException;
import Model.Statements.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {
    private int threadID = 1;
    private int lastID = 1;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IFileTable<String, BufferedReader> fileTable;
    private MyIHeap<Value> heap;
    private ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable;

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public int getLastID() {
        return lastID;
    }

    public PrgState(ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> st, MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IFileTable<String, BufferedReader> fTable, MyIHeap<Value> heapTable,
                    int id, IStmt prg)
    {
        semaphoreTable = st;
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

        semaphoreTable = new SemaphoreTable<>();
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

    public void setSemaphoreTable(ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return semaphoreTable;
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
