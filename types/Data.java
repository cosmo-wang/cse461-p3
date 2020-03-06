package types;

import java.util.Map;

public interface Data {
    public int getId();
    public String getName();
    public Map<String, String> assembleWithHeader();
    public Data copy() throws CloneNotSupportedException;
}