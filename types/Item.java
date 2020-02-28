package types;

public class Item {
    private String name;
    private String category;
    private int rarity;
    private int buyPrice;
    private int sellPrice;
    private int carryLimit;
    private int points;
    private String iconName;
    private Combination comb;

    public Item (String[] values) {
        this.name = values[1];
        this.category = values[2];
        this.rarity = parseInt(values[4]);
        this.buyPrice = parseInt(values[5]);
        this.sellPrice = parseInt(values[6]);
        this.carryLimit = parseInt(values[7]);
        this.points = parseInt(values[8]);
        this.iconName = values[9];
    }

    public void addCombInfo(String[] info) {
        this.comb = new Combination(info);
    }

    public String getName() { return this.name; }

    private int parseInt(String value) {
        return value.isEmpty() ? -1 : Integer.parseInt(value);
    }

    private class Combination {
        public String item1;
        public String item2;
        public int quantity;

        public Combination(String[] info) {
            this.item1 = info[2];
            this.item2 = (info[3]).isEmpty() ? null : info[3];
            this.quantity = Integer.parseInt(info[4]);
        }
    }
}