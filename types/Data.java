package types;

import java.util.Map;

public abstract class Data implements Comparable<Data> {
    protected int id;
    protected String name;

    abstract public int getId();
    abstract public String getName();
    abstract public Map<String, String> assembleWithHeader();
    abstract public Data copy() throws CloneNotSupportedException;

    @Override
    public int compareTo(Data other) {
        if(this.id > other.id) return 1; 
        if(this.id < other.id) return -1;
        else                   return 0;
    }
}