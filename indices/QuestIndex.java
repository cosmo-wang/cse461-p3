package indices;

import java.io.FileNotFoundException;

import types.Quest;
import utils.Utils;

public class QuestIndex extends DataIndex {
    public QuestIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("monster", this::addMonster);
        super.readFile("rewards", this::addRewards);
    }

    public boolean contains(String questName) {
        return this.find(questName) != null;
    }

    public boolean contains(int questId) {
        return this.find(super.idToName.get(questId)) != null;
    }
    
    /**
     * Find an Item in the index table given its name.
     * @param itemName name of the item to find
     * @return a copy of the item, null if not found or failed to copy the item
     */
    public Quest find(String questName) {
        try {
            int id = super.nameToId.get(questName);
            return (Quest) ((Quest) super.idToData.get(id)).clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Item could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    private void addBase(String[] info) {
        Quest quest = new Quest(info);
        super.idToName.put(quest.getId(), quest.getName());
        super.nameToId.put(quest.getName(), quest.getId());
        super.idToData.put(quest.getId(), quest);
    }

    private void addMonster(String[] info) {
        int id = Utils.parseInt(info[0]);
        if (this.contains(id)) {
            Quest quest = (Quest) super.idToData.get(super.nameToId.get(info[0]));
            quest.addMonster(info[1], Utils.parseInt(info[2]));
        }
    }

    private void addRewards(String[] info) {
        int id = Utils.parseInt(info[0]);
        if (this.contains(id)) {
            Quest quest = (Quest) super.idToData.get(super.nameToId.get(info[0]));
            quest.addRewards(info[2], Utils.parseInt(info[3]), Utils.parseInt(info[4]));
        }
    }
}