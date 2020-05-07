package Model.Values;
import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {

    private String val;

    public StringValue(String v){

        val = v;
    }

    public String getVal() {

        return val;
    }

    public String toString() {

        return val;
    }

    public Type getType() {

        return new StringType();
    }

    public boolean equals(Object another) {

        return another instanceof StringValue;
    }

}
