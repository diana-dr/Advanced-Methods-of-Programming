package Repository;
import Model.Participant;

public class ArrayRepository implements Repository {

    private int position;
    private Participant[] participants;

    public ArrayRepository(int capacity)
    {
        participants = new Participant[capacity];
        position = 0;
    }

    public Participant[] getAll() {
        return participants;
    }

    public void add(Participant newParticipant) throws Exception {
//        if (position >= participants.length)
//            throw new Exception("Maximum capacity!");
//        else
//        {
//            participants[position] = newParticipant;
//            position++;
//        }
        for(Participant p : participants)
            if(newParticipant.equals(p))
                throw new Exception("Participant not unique!");

        Participant[] new_elems = new Participant[position + 1];
        System.arraycopy(participants, 0, new_elems, 0, position);
        new_elems[position] = newParticipant;
        participants = new Participant[position + 1];
        participants = new_elems;
        position++;
    }

    public void delete(int index) throws Exception {
        if (index >= 0 && index < position)
        {
            participants[index] = participants[index - 1];
            --position;
        }
        else
            throw new Exception("Invalid index!");
    }

    int getPosition()
    {
        return this.position;
    }
}
