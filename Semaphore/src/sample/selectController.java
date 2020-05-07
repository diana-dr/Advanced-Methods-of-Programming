package sample;

import Controller.Controller;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class selectController implements Initializable {

    private ArrayList<IStmt> programStatements;
    private runController mainController;

    @FXML
    ListView<String> programListView;

    @FXML
    Button execButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProgramsStatements();
        programListView.setItems(FXCollections.observableArrayList(getString()));
    }

    public void setMainController(runController mainWindowController){
        this.mainController = mainWindowController;
    }

    @FXML
    private void handleExecButton() {
        int index = programListView.getSelectionModel().getSelectedIndex();

        if(index < 0)
            return ;

        List<PrgState> prgStateList = new ArrayList<>();
        PrgState initialProgramState = new PrgState(programStatements.get(index));
        prgStateList.add(initialProgramState);
        IRepository repo = new Repository(prgStateList, "log" + index + ".txt");
        Controller ctrl = new Controller(repo);

        mainController.setController(ctrl);

    }

    private List<String> getString(){
        return programStatements.stream().map(IStmt::toString).collect(Collectors.toList());
    }

    private void addProgramsStatements() {
        //  int v; v=2;Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt( new AssignStmt("v", new ValueExp(new IntValue(2))), new CompStmt(new PrintStmt(new
                        VarExp("v")), new NopStmt())));

        //  int v; v=2;Print(v)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new
                                ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new CompStmt(new PrintStmt(new VarExp("b")), new NopStmt())))));

        // bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new NopStmt())))));

        //string varf; varf="test.in"; openRFile(varf);
        //int varc; readFile(varf,varc);print(varc); readFile(varf,varc);print(varc); closeRFile(varf)
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CompStmt(new closeRFile(new VarExp("varf")), new NopStmt())))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(new PrintStmt(new rH(new rH(new VarExp("a")))), new NopStmt()))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new CompStmt(new PrintStmt(new ArithExp(1, new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))), new NopStmt()))))));

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                        new CompStmt(
                                        new PrintStmt(new ArithExp(1, new rH(new VarExp("v")), new ValueExp(new IntValue(5)))), new NopStmt())))));

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new NopStmt()))));

        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a",
                new RefType(new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new CompStmt(new PrintStmt(new rH(new VarExp("a"))),new NopStmt()))))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("a"))), new NopStmt())))))));

        // v = 1; fork(v = 2); fork(v = 3)
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(1))),
                new CompStmt(new forkStmt(new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new NopStmt())), new forkStmt(new CompStmt(new AssignStmt("v",
                        new ValueExp(new IntValue(3))), new NopStmt())))));

        IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(3))),
                new CompStmt(new SemaphoreStmt("cnt", new rH(new VarExp("v1"))), new CompStmt(new forkStmt(
                        new CompStmt(new AcquireStmt("cnt"), new CompStmt(new wH("v1", new ArithExp(3, new rH(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                new CompStmt(new PrintStmt(new rH(new VarExp("v1"))), new CompStmt(new ReleaseStmt("cnt"), new NopStmt())
                )))), new CompStmt(new forkStmt(new CompStmt(new AcquireStmt("cnt"), new CompStmt(new wH("v1", new ArithExp(3, new rH(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                        new CompStmt(new wH("v1", new ArithExp(3, new rH(new VarExp("v1")), new ValueExp(new IntValue(2)))),
                                new CompStmt(new PrintStmt(new rH(new VarExp("v1"))), new CompStmt(new ReleaseStmt("cnt"), new NopStmt())))))),
                        new CompStmt(new AcquireStmt("cnt"), new CompStmt(new PrintStmt(new ArithExp(2, new rH(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                new CompStmt(new ReleaseStmt("cnt"), new NopStmt())))
                        )))));

        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11));
    }

}

