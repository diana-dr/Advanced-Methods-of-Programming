package Model.Values;

import Model.Types.*;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean b) {
        val = b;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {

        return String.valueOf(val);
    }

    public Type getType() {
        return new BoolType();
    }

    public boolean equals(Object another) {

        return another instanceof BoolValue;
    }

}
