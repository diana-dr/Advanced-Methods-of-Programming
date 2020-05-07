package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int i, Type inner) {
        this.address = i;
        this.locationType = inner;
    }

    public RefValue() {}
    public String toString() {
        return locationType + ", " + address;
    }
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType); }
}
