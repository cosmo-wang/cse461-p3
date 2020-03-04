package types;

public interface Data {
    public int getId();
    public String getName();
    public Data copy() throws CloneNotSupportedException;
}