package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Weapon extends Data implements Cloneable {
    private String type;
    private String previous;
    private String category;
    private int rarity;
    private int attack;
    private int affinity;
    private int defense;
    private boolean elementHidden;
    private List<Element> elements;
    private String elderseal;
    private int[] slots;
    private List<Craft> crafts;
    private Sharpness sharpness;

    public Weapon(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.type = values[2];
        this.previous = values[3];
        this.category = values[4].equals("") ? "normal" : values[4];
        this.rarity = Utils.parseInt(values[5]);
        this.attack = Utils.parseInt(values[6]);
        this.affinity = Utils.parseInt(values[7]);
        this.defense = values[8].equals("") ? 0 : Utils.parseInt(values[8]);
        this.elementHidden = values[9].equals("TRUE");
        this.elements = new ArrayList<Element>();
        this.addElement(values[10], values[11]);
        this.addElement(values[12], values[13]);
        this.elderseal = values[14];
        this.slots = new int[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = Utils.parseInt(values[15 + i]);
        }
        this.crafts = new ArrayList<Craft>();
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        if (!this.type.isEmpty()) {
            res.put("Type", this.type);
        }
        if (!this.previous.isEmpty()) {
            res.put("Previous", this.previous);
        }
        if (!this.category.isEmpty()) {
            res.put("Category", this.category);
        }
        if (this.rarity != -1) {
            res.put("Rarity", Integer.toString(this.rarity));
        }
        if (this.attack != -1) {
            res.put("Attack", Integer.toString(this.attack));
        }
        if (this.affinity != -1) {
            res.put("Affinity", Integer.toString(this.affinity) + "%");
        }
        if (this.defense != -1 && this.defense != 0) {
            res.put("Defence", Integer.toString(this.defense));
        }
        if (this.elementHidden) {
            res.put("Element Hidden", Boolean.toString(this.elementHidden));
        }
        String elementsValue = "";
        for (Element e : this.elements) {
            elementsValue += e.toString() + ", ";
        }
        if (!elementsValue.isEmpty()) {
            res.put("Element Attack", elementsValue.substring(0, elementsValue.length() - 2));
        }
        if (!this.elderseal.isEmpty()) {
            res.put("Elderseal", this.elderseal);
        }
        String slotsStr = Arrays.toString(this.slots);
        res.put("Slots", slotsStr.substring(1, slotsStr.length() - 1));
        if (this.crafts != null) {
            for (Craft craft: this.crafts) {
                res.put("Craft (" + craft.getType() + ")", craft.toString());
            }
        }
        if (this.sharpness != null) {
            res.put("Sharpness", this.sharpness.toString());
        }
        return res;
    }

    public void addCraft(String[] info) {
        String type = info[1];
        String[] craftItems = Arrays.copyOfRange(info, 2, info.length);
        this.crafts.add(new Craft(type, craftItems));
    }

    public void addSharpness(String[] info) {
        this.sharpness = new Sharpness(info);
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    private void addElement(String element, String attack) {
        if (!element.isEmpty() && !attack.isEmpty()) {
            this.elements.add(new Element(element, Utils.parseInt(attack)));
        }
    }

    private class Element implements Cloneable {
        private String element;
        private int attack;

        public Element(String element, int attack) {
            this.element = element;
            this.attack = attack;
        }

        @Override
        public String toString() {
            return Utils.capitalize(this.element + " (" + Integer.toString(attack) + ")");
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private class Sharpness implements Cloneable {
        private boolean maxed;
        private Map<String, Integer> data;

        public Sharpness(String[] values) {
            this.maxed = values[1].equals("TRUE");
            this.data = new LinkedHashMap<String, Integer>();
            this.data.put("red", Utils.parseInt(values[2]));
            this.data.put("orange", Utils.parseInt(values[3]));
            this.data.put("yellow", Utils.parseInt(values[4]));
            this.data.put("green", Utils.parseInt(values[5]));
            this.data.put("blue", Utils.parseInt(values[6]));
            this.data.put("white", Utils.parseInt(values[7]));
            this.data.put("purple", Utils.parseInt(values[8]));
        }

        @Override
        public String toString() {
            String res = "";
            for (Entry<String, Integer> entry : data.entrySet()) {
                if (entry.getValue() == 0) {
                    break;
                }
                res += entry.getKey() + " (" + entry.getValue() + "), ";
            }
            return res.substring(0, res.length() - 2);
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}