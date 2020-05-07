package Model.Values;

import Model.Types.*;

public class IntValue implements Value {
    private int val;

    public IntValue(int v) {

        val = v;
    }

    public int getVal() {

        return val;
    }

    public String toString() {

        return String.valueOf(val);
    }

    public Type getType() {

        return new IntType();
    }

    public boolean equals(Object another) {

        return another instanceof IntValue;
    }

}
