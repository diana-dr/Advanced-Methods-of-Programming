package Repository;
import Model.Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private List<PrgState> prgList;
    private String logFilePath;

    public Repository(List<PrgState> states, String filePath) {
        this.prgList = states;
        this.logFilePath = filePath;
    }

    @Override
    public List<PrgState> getPrgList() {

        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {

        this.prgList = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        PrintWriter file =  new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        file.write(prgState.toString());
        file.close();
    }

    @Override
    public PrgState getPrgStateWithId(int currentId) {
        for (PrgState prgState : prgList)
            if (prgState.getThreadID() == currentId)
                return prgState;
        return null ;
    }

}
