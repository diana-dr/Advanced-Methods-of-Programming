package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState prgState) throws MyException, IOException;

    PrgState getPrgStateWithId(int currentId);
}
