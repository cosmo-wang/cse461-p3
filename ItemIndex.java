import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    public ItemIndex(String dirName, int baseFileCols) throws FileNotFoundException {
        // invoke super constructor to fill some basic fields
        super(dirName, baseFileCols);
        // read the base file
        String baseFile = super.findFile(dirName, "base");
        if (baseFile == null) {
            throw new FileNotFoundException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(baseFile))) {
            String itemEntry = br.readLine();
            while ((itemEntry = br.readLine()) != null) {
                Item item = new Item(itemEntry.split(Utils.COMMA_DELIMITER, baseFileCols));
                super.idToName.put(item.getId(), item.getName());
                super.nameToId.put(item.getName(), item.getId());
                super.idToData.put(item.getId(), item);
            }
        } catch (IOException e) {
            System.err.println("faield to open/read file: " + baseFile);
            e.printStackTrace();
        }
        // read the combination file
        String combFile = super.findFile(dirName, "combination");
        try (BufferedReader br = new BufferedReader(new FileReader(combFile))) {
            String combEntry = br.readLine();
            while ((combEntry = br.readLine()) != null) {
                String[] info = combEntry.split(Utils.COMMA_DELIMITER);
                String name = info[1];
                if (this.contains(name)) {
                    Item item = (Item) super.idToData.get(super.nameToId.get(name));
                    item.addCombInfo(info);
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
            System.err.println("faield to open/read file " + combFile);
            e.printStackTrace();
        }
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
}