import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import types.Item;

public class ItemsReader {
    public static final String BASE_FILE = "data/items/item_base.csv";
    public static final int BASE_FILE_COLS = 11;
    public static final String COMBINATION_FILE = "data/items/item_combination_list.csv";

    private HashMap<String, Item> table;

    public ItemsReader() throws FileNotFoundException {
        this.table = new HashMap<String, Item>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_FILE))) {
            String itemEntry = br.readLine();
            while ((itemEntry = br.readLine()) != null) {
                Item item = new Item(itemEntry.split(Utils.COMMA_DELIMITER, BASE_FILE_COLS));
                this.table.put(item.getName(), item);
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + BASE_FILE);
          e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(COMBINATION_FILE))) {
            String combEntry = br.readLine();
            while ((combEntry = br.readLine()) != null) {
                String[] info = combEntry.split(Utils.COMMA_DELIMITER);
                if (this.table.containsKey(info[1])) {
                    this.table.get(info[1]).addCombInfo(info);
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + COMBINATION_FILE);
          e.printStackTrace();
        }
    }
}