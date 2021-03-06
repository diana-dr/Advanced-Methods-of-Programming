package Model;

public class Professor implements Participant {

    private boolean presented;
    private String name;
    private String speciality;
    private int age;

    public Professor(String name, int age, String speciality, boolean presented) {
        this.name = name;
        this.age = age;
        this.speciality = speciality;
        this.presented = presented;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSpeciality()
    {
        return this.speciality;
    }

    public int getAge()
    {
        return this.age;
    }

    public boolean getPresented()
    {
        return this.presented;
    }

    @Override
    public String toString() {
        return "Professor: " + this.name;
    }
}
