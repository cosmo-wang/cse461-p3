package types;

import java.util.LinkedHashMap;
import java.util.Map;

import utils.Utils;

public class Item extends Data implements Cloneable {
    private int rarity;
    private int buyPrice;
    private int sellPrice;
    private int carryLimit;
    private int points;
    private Combination comb;
    private String icon;
    private String iconColor;

    public Item(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.rarity = Utils.parseInt(values[4]);
        this.buyPrice = Utils.parseInt(values[5]);
        this.sellPrice = Utils.parseInt(values[6]);
        this.carryLimit = Utils.parseInt(values[7]);
        this.points = Utils.parseInt(values[8]);
        this.icon = values[9];
        this.iconColor = values[10].replaceAll("#", "").toUpperCase();
    }

    @Override
    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        if (this.rarity != -1) {
            res.put("Rarity", Integer.toString(this.rarity));
        }
        if (this.carryLimit != -1) {
            res.put("Carry Limit", Integer.toString(this.carryLimit));
        }
        if (this.buyPrice != -1) {
            res.put("Buy Price", Integer.toString(this.buyPrice) + " z");
        }
        if (this.sellPrice != -1) {
            res.put("Sell Price", Integer.toString(this.sellPrice) + " z");
        }
        if (this.points != -1) {
            res.put("Points", Integer.toString(this.points) + " pts");
        }
        if (this.comb != null) {
            res.put("Combination", this.comb.toString() + " " + this.name);
        }
        return res;
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addCombInfo(String[] info) {
        this.comb = new Combination(info);
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public Combination getComb() {
        try {
            return (Combination) this.comb.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Combination could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    public String getIcon() { return this.icon; }

    public String getIconColor() { return this.iconColor; }

    public class Combination implements Cloneable {
        public String item1;
        public String item2;
        public int quantity;

        public Combination(String[] info) {
            this.item1 = info[2];
            this.item2 = (info[3]).isEmpty() ? null : info[3];
            this.quantity = Utils.parseInt(info[4]);
        }

        @Override
        public String toString() {
            return Utils.capitalize(item1 + " + " 
                                    + item2 + " = " + quantity + " &#215;");
        }

        public Object clone() throws CloneNotSupportedException {  
            return super.clone();  
        }
    }
}