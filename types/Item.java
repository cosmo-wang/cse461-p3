package types;

import utils.Utils;

public class Item implements Data, Cloneable {
    private int id;
    private String name;
    private String category;
    private int rarity;
    private int buyPrice;
    private int sellPrice;
    private int carryLimit;
    private int points;
    private String iconName;
    private Combination comb;

    public Item(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.category = values[2];
        this.rarity = Utils.parseInt(values[4]);
        this.buyPrice = Utils.parseInt(values[5]);
        this.sellPrice = Utils.parseInt(values[6]);
        this.carryLimit = Utils.parseInt(values[7]);
        this.points = Utils.parseInt(values[8]);
        this.iconName = values[9];
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

    public class Combination implements Cloneable {
        public String item1;
        public String item2;
        public int quantity;

        public Combination(String[] info) {
            this.item1 = info[2];
            this.item2 = (info[3]).isEmpty() ? null : info[3];
            this.quantity = Utils.parseInt(info[4]);
        }

        public Object clone() throws CloneNotSupportedException {  
            return super.clone();  
        }
    }
}