package types;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

    public String getType() { return type; }

    @Override
    public String toString() {
        String res = "";
        for (Entry<String, String> entry : craftItems.entrySet()) {
            res += entry.getKey() + " (" + entry.getValue() + "), ";
        }
        return res.substring(0, res.length() - 2);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}