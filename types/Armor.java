package types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import utils.Utils;

public class Armor extends Data implements Cloneable {
    private int rarity;
    private String type;
    private int[] slots;
    private Map<String, String> defenses;
    private Craft craft;
    private Map<String, String> skills;

    public Armor(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.rarity = Utils.parseInt(values[2]);
        this.type = values[3];
        this.slots = new int[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = Utils.parseInt(values[5 + i]);
        }
        this.defenses = new LinkedHashMap<String, String>();
        this.defenses.put("base", values[8]);
        this.defenses.put("max", values[9]);
        this.defenses.put("augment max", values[10]);
        this.defenses.put("fire", values[11]);
        this.defenses.put("water", values[12]);
        this.defenses.put("thunder", values[13]);
        this.defenses.put("ice", values[14]);
        this.defenses.put("dragon", values[15]);
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        res.put("Rarity", Integer.toString(this.rarity));
        res.put("Type", this.type);
        int i = 0;
        for (String skillName : this.skills.keySet()) {
            i++;
            res.put("Skill " + i, skillName + " (Lv " + this.skills.get(skillName) + ")");
        }
        String slotsStr = Arrays.toString(this.slots);
        res.put("Slots", slotsStr.substring(1, slotsStr.length() - 1));
        for (String defenseType: this.defenses.keySet()) {
            res.put(Utils.capitalize(defenseType) + " Defense", this.defenses.get(defenseType));
        }
        if (this.craft != null) {
            res.put("Craft (" + this.craft.getType() + ")", this.craft.toString());
        }
        return res;
    }

    public void addCraft(String[] info) {
        String type = "Create";
        String[] craftItems = Arrays.copyOfRange(info, 1, info.length);
        this.craft = new Craft(type, craftItems);
    }

    public void addSkills(String[] info) {
        this.skills = new HashMap<String, String>();
        if (!info[1].isEmpty() && !info[2].isEmpty()) {
            skills.put(info[1], info[2]);
        }
        if (!info[3].isEmpty() && !info[3].isEmpty()) {
            skills.put(info[3], info[4]);
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