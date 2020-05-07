package Controller;
import Model.*;
import Repository.*;

public class Controller {

    private Repository repository;

    public Controller(Repository repo) {
        repository = repo;
    }

    public void add(String type, String name, int age, String speciality, boolean presented) throws Exception {
        Participant p = null;
        switch (type) {
            case "student":
            {
                p = new Student(name, age, speciality, presented);
                break;
            }
            case "professor":
            {
                p = new Professor(name, age, speciality, presented);
                break;
            }
            case "specialist":
            {
                p = new Specialist(name, age, speciality, presented);
                break;
            }
        }
        try {
            repository.add(p);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(int index) throws Exception {
        repository.delete(index);
    }

    public Participant[] getPresentedParticipants() throws Exception {
        ArrayRepository presented = new ArrayRepository(repository.getAll().length);
        for (Participant p : repository.getAll())
            if (p.getPresented())
                presented.add(p);
        return presented.getAll();
    }

    public Participant[] getAll() {
        return repository.getAll();
    }
}
