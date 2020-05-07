package View;
import Controller.Controller;
import Model.Participant;
import Repository.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Repository repository = new ArrayRepository(4);
        Controller controller = new Controller(repository);

        try {
            controller.add("student", "name1", 19, "speciality1", true);
            controller.add("professor", "name2", 30, "cs", false);
            controller.add("specialist", "name3", 45, "math", false);
            controller.add("professor", "name4", 77, "math", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (Participant p : controller.getPresentedParticipants())
            System.out.println(p);
    }
    }
