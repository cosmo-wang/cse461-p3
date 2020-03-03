package types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Weapon implements Data, Cloneable {
    private int id;
    private String name;
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
    private List<Integer> slots;
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
        this.slots = new ArrayList<Integer>();
        this.slots.add(Utils.parseInt(values[15]));
        this.slots.add(Utils.parseInt(values[16]));
        this.slots.add(Utils.parseInt(values[17]));
        this.crafts = new ArrayList<Craft>();
    }

    public void addCrafts(String[] info) {
        this.crafts.add(new Craft(info));
    }

    public void addSharpness(String[] info) {
        this.sharpness = new Sharpness(info);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    private void addElement(String element, String attack) {
        if (element.isEmpty() || attack.isEmpty()) {
            this.elements.add(null);
        } else {
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

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private class Craft implements Cloneable {
        private String type;
        private CraftItem[] craftItems;

        public Craft(String[] values) {
            this.type = values[1];
            this.craftItems = new CraftItem[4];
            for (int i = 0; i < craftItems.length; i++) {
                String item = values[2 * i + 2];
                String qty = values[(2 * i + 2) + 1];
                if (item.isEmpty() || qty.isEmpty()) {
                    craftItems[i] = null;
                } else {
                    craftItems[i] = new CraftItem(item, Utils.parseInt(qty));
                }
            }
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
        private class CraftItem implements Cloneable {
            private String item;
            private int qty;

            public CraftItem(String item, int qty) {
                this.item = item;
                this.qty = qty;
            }

            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
    }

    private class Sharpness implements Cloneable {
        private boolean maxed;
        private Map<String, Integer> data;

        public Sharpness(String[] values) {
            this.maxed = values[1].equals("TRUE");
            this.data = new HashMap<String, Integer>();
            this.data.put("red", Utils.parseInt(values[2]));
            this.data.put("orange", Utils.parseInt(values[3]));
            this.data.put("yellow", Utils.parseInt(values[4]));
            this.data.put("green", Utils.parseInt(values[5]));
            this.data.put("blue", Utils.parseInt(values[6]));
            this.data.put("white", Utils.parseInt(values[7]));
            this.data.put("purple", Utils.parseInt(values[8]));
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}