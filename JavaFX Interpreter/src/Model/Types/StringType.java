package Model.Types;

import Model.Values.StringValue;

public class StringType implements Type {

    @Override
    public StringValue defaultValue() {
        return new StringValue("");
    }

    public boolean equals(Object another) {

        return another instanceof StringType;
    }

    public String toString() { return "string"; }
}
