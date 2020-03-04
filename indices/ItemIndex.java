package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Item;
import utils.Utils;

/**
 * This class is a index table that stores information about Items.
 */
public class ItemIndex extends DataIndex {
    /**
     * Constructs a new instance of ItemIndex.
     * @param dirName name of the directory to read from
     * @param baseFileCols number of columns in the base file
     * @throws FileNotFoundException when the file is not found
     */
    public ItemIndex(String dirName) throws FileNotFoundException {
        // invoke super constructor to fill some basic fields
        super(dirName);
        // read the base file
        super.readFile("base", this::addBase);
        super.readFile("combination", this::addComb);
    }

    public List<Item> find(String query) {
        try {
            List<Item> res = new ArrayList<Item>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Item i = (Item)super.idToData.get(super.nameToId.get(name));
                    res.add((Item)i.clone());
                }
            }
            return res;
        } catch (CloneNotSupportedException e) {
            System.err.println("Monster could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    private void addBase(String[] info) {
        Item item = new Item(info);
        super.idToName.put(item.getId(), item.getName());
        super.nameToId.put(item.getName(), item.getId());
        super.idToData.put(item.getId(), item);
    }

    private void addComb(String[] info) {
        int id = Utils.parseInt(info[0]);
        if (this.contains(id)) {
            Item item = (Item) super.idToData.get(id);
            item.addCombInfo(info);
        }
    }
}