import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ItemsReader {
    public static final String BASE_FILE = "data/items/item_base.csv";
    public static final int BASE_FILE_COLS = 11;
    public static final String COMBINATION_FILE = "data/items/item_combination_list.csv";
    public static final String COMMA_DELIMITER = ",";

    private HashMap<String, Item> nameToItem;

    public ItemsReader() throws FileNotFoundException {
        this.nameToItem = new HashMap<String, Item>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_FILE))) {
            String itemEntry = br.readLine();
            while ((itemEntry = br.readLine()) != null) {
                Item item = new Item(itemEntry.split(COMMA_DELIMITER, BASE_FILE_COLS));
                this.nameToItem.put(item.name, item);
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + BASE_FILE);
          e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(COMBINATION_FILE))) {
            String combEntry = br.readLine();
            while ((combEntry = br.readLine()) != null) {
                String[] info = combEntry.split(COMMA_DELIMITER);
                if (this.nameToItem.containsKey(info[1])) {
                    this.nameToItem.get(info[1]).addCombInfo(info);
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + COMBINATION_FILE);
          e.printStackTrace();
        }
    }

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
}