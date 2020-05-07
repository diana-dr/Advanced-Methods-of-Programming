package Model.Statements;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.Heap.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;

    public AssignStmt(String v, Exp valueExp) {
        this.id = v;
        this.exp = valueExp;
    }

    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> hp = state.getHeap();

        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, hp);
            symTbl.add(id, val);
        }
        else throw new MyException("The used variable " + id + " was not declared before!");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    public String toString() {
        return id + " = " + exp.toString(); }

}
