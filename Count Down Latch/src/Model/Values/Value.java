package Model.Values;
import Model.Types.Type;

public interface Value {
    String toString();
    Type getType();
    boolean equals(Object another);
}
