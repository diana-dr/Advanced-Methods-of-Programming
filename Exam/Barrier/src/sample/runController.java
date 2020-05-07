package sample;

import Controller.Controller;
import Model.DataStructures.CyclicBarrier.ICyclicBarrier;
import Model.DataStructures.CyclicBarrier.Pair;
import Model.DataStructures.Dictionary.MyIDictionary;
import Model.DataStructures.FileTable.IFileTable;
import Model.DataStructures.Heap.MyIHeap;
import Model.DataStructures.List.MyIList;
import Model.DataStructures.Stack.MyIStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class runController implements Initializable {

    Controller controller;


    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symTableView;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTableView;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> indexTableColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> valueBarrTableColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>> listTableColumn;

    @FXML
    TableColumn<Map.Entry<String, Value>, String> varNameTableColumn;

    @FXML
    TableColumn<Map.Entry<String, Value>, Value> varValueTableColumn;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Value> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private TextField noOfPrgStatesTextField;

    @FXML
    TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    TableColumn<Map.Entry<Integer, Value>, Integer> addressTableColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Value>, Value> valueTableColumn;

    public void setController(Controller ctrl) {
        this.controller = ctrl;
        populatePrgStateIdentifiers();
    }

    private List<Integer> getPrgStateIds(List<PrgState> programStateList) {
        return programStateList.stream().map(PrgState::getThreadID).collect(Collectors.toList());
    }

    private void populatePrgStateIdentifiers() {
        List<PrgState> programStates = controller.getRepo().getPrgList();
        programStateListView.setItems(FXCollections.observableList(getPrgStateIds(programStates)));

        noOfPrgStatesTextField.setText("" + programStates.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addressTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<Value>(p.getValue().getValue()));

        varNameTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        varValueTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<Value>(p.getValue().getValue()));

        indexTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueBarrTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<Integer>(p.getValue().getValue().getFirst()));
        listTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<List<Integer>>(p.getValue().getValue().getSecond()));

        programStateListView.setOnMouseClicked(mouseEvent -> changeProgramState(getCurrentProgramState()));
    }

    private PrgState getCurrentProgramState() {
        if(programStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int currentId = programStateListView.getSelectionModel().getSelectedItem();

        return controller.getRepo().getPrgStateWithId(currentId);
    }

    private void changeProgramState(PrgState currentProgramState) {
        if(currentProgramState == null)
            return ;

        populateSymbolTable(currentProgramState);
        populateExecutionStack(currentProgramState);
    }

    @FXML
    private void oneStepButtonHandler() throws InterruptedException {
        controller.setExecutor(Executors.newFixedThreadPool(2));
        List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());

        controller.oneStepForAllPrg(prgList);
        prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());

        controller.getExecutor().shutdownNow();
        controller.getRepo().setPrgList(prgList);

        if (prgList.size() > 0) {

            populatePrgStateIdentifiers();
            populateHeapTable(prgList.get(prgList.size() - 1));
            populateOutputList(prgList.get(prgList.size() - 1));
            populateFileTable(prgList.get(prgList.size() - 1));
            populateSymbolTable(prgList.get(prgList.size() - 1));
            populateExecutionStack(prgList.get(prgList.size() - 1));
            populateBarrier(prgList.get(prgList.size() - 1));

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("ERROR!");
            alert.setHeaderText("Empty Program State List");
            alert.setContentText("There are no more steps to run!");

            alert.showAndWait();
        }
    }

    private void populateBarrier(PrgState currentProgramState) {

        ICyclicBarrier<Integer, Pair<Integer, List<Integer>>> barrier = currentProgramState.getBarrier();
        List<Map.Entry<Integer, Pair<Integer, List<Integer>>>> st = new ArrayList<>();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> s : barrier.getAll())
            st.add(s);

        barrierTableView.setItems(FXCollections.observableList(st));
        barrierTableView.refresh();
    }

    private void populateExecutionStack(PrgState currentProgramState) {
        MyIStack<IStmt> exeStack = currentProgramState.getStk();
        List<String> es = new ArrayList<>();

        for (IStmt stmt : exeStack.getAll())
            es.add(stmt.toString());

        exeStackListView.setItems(FXCollections.observableList(es));
        exeStackListView.refresh();
    }

    private void populateSymbolTable(PrgState currentProgramState) {

        MyIDictionary<String, Value> symTable = currentProgramState.getSymTable();
        List<Map.Entry<String, Value>> st = new ArrayList<>();

        for (Map.Entry<String, Value> s : symTable.getAll())
            st.add(s);

        symTableView.setItems(FXCollections.observableList(st));
        symTableView.refresh();
    }

    private void populateFileTable(PrgState currentProgramState) {

        IFileTable<String, BufferedReader> fileTable = currentProgramState.getFileTable();

        List<String> ft = new ArrayList<>(fileTable.getAllKeys());

        fileTableListView.setItems(FXCollections.observableList(ft));
        symTableView.refresh();
    }

    private void populateOutputList(PrgState currentProgramState) {
        MyIList<Value> outputList = currentProgramState.getOut();
        List<Value> out = new ArrayList<>();

        for (Value v : outputList.getAll())
            out.add(v);

        outputListView.setItems(FXCollections.observableList(out));
        symTableView.refresh();
    }

    private void populateHeapTable(PrgState currentProgramState) {

        MyIHeap<Value> heapTable = currentProgramState.getHeap();
        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>();

        for(Map.Entry<Integer, Value> entry : heapTable.getAll())
            heapTableList.add(entry);

        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

}
