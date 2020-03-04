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