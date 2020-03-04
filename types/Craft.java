package types;

import java.util.HashMap;
import java.util.Map;

import utils.Utils;

public class Craft implements Cloneable {
    private String type;
    private Map<String, Integer> craftItems;

    public Craft(String type, String[] craftItems) {
        this.type = type;
        this.craftItems = new HashMap<String, Integer>();
        for (int i = 0; i < craftItems.length; i += 2) {
            String item = craftItems[i];
            String qty = craftItems[i + 1];
            if (!item.isEmpty() && !qty.isEmpty()) {
                this.craftItems.put(item, Utils.parseInt(qty));
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}