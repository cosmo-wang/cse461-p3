package types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.Utils;

public class Skill implements Data, Cloneable {
    private int id;
    private String name;
    private Map<Integer, String> description;

    public Skill(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.description = new HashMap<>();
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        
        return res;
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void addLevels(int levelNum, String description) {
        this.description.put(levelNum, description);
    }
}