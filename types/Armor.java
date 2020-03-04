package types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.Utils;

public class Armor implements Data, Cloneable {
    private int id;
    private String name;
    private int rarity;
    private String type;
    private int[] slots;
    private Map<String, Integer> defenses;
    private Craft craft;
    private Map<String, Integer> skills;

    public Armor(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.rarity = Utils.parseInt(values[2]);
        this.type = values[3];
        this.slots = new int[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = Utils.parseInt(values[5 + i]);
        }
        this.defenses = new HashMap<String, Integer>();
        this.defenses.put("base", Utils.parseInt(values[8]));
        this.defenses.put("max", Utils.parseInt(values[9]));
        this.defenses.put("augment max", Utils.parseInt(values[10]));
        this.defenses.put("fire", Utils.parseInt(values[11]));
        this.defenses.put("water", Utils.parseInt(values[12]));
        this.defenses.put("thunder", Utils.parseInt(values[13]));
        this.defenses.put("ice", Utils.parseInt(values[14]));
        this.defenses.put("dragon", Utils.parseInt(values[15]));
    }

    public void addCraft(String[] info) {
        String type = "Create";
        String[] craftItems = Arrays.copyOfRange(info, 1, info.length);
        this.craft = new Craft(type, craftItems);
    }

    public void addSkills(String[] info) {
        this.skills = new HashMap<String, Integer>();
        if (!info[1].isEmpty() && !info[2].isEmpty()) {
            skills.put(info[1], Utils.parseInt(info[2]));
        }
        if (!info[3].isEmpty() && !info[3].isEmpty()) {
            skills.put(info[3], Utils.parseInt(info[4]));
        }
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}