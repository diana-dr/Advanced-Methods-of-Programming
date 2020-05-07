package Repository;
import Model.Participant;

public interface Repository
{
    void add(Participant p) throws Exception;
    void delete(int index) throws Exception;
    Participant[] getAll();
}
