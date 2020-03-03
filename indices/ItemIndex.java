package indices;

import java.io.FileNotFoundException;

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

    public boolean contains(String itemName) {
        return this.find(itemName) != null;
    }

    public boolean contains(int itemId) {
        return this.find(super.idToName.get(itemId)) != null;
    }

    /**
     * Find an Item in the index table given its name.
     * @param itemName name of the item to find
     * @return a copy of the item, null if not found or failed to copy the item
     */
    public Item find(String itemName) {
        try {
            int id = super.nameToId.get(itemName);
            return (Item) ((Item) super.idToData.get(id)).clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Item could not be cloned.");
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