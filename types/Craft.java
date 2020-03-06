package types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Craft implements Cloneable {
    private String type;
    private Map<String, String> craftItems;

    public Craft(String type, String[] craftItems) {
        this.type = type;
        this.craftItems = new HashMap<String, String>();
        for (int i = 0; i < craftItems.length; i += 2) {
            String item = craftItems[i];
            String qty = craftItems[i + 1];
            if (!item.isEmpty() && !qty.isEmpty()) {
                this.craftItems.put(item, qty);
            }
        }
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        res.put("Craft Type", this.type);
        int i = 0;
        for (String craftItem: this.craftItems.keySet()) {
            i++;
            res.put("Craft Item " + i, craftItem);
            res.put("Item " + i + " Qty", this.craftItems.get(craftItem));
        }
        return res;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}