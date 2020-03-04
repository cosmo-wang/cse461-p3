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

    private void addBase(String[] info) {
        Quest quest = new Quest(info);
        super.idToName.put(quest.getId(), quest.getName());
        super.nameToId.put(quest.getName(), quest.getId());
        super.idToData.put(quest.getId(), quest);
    }

    private void addMonster(String[] info) {
        int id = Utils.parseInt(info[0]);
        if (this.contains(id)) {
            Quest quest = (Quest) super.idToData.get(id);
            quest.addMonster(info[1], Utils.parseInt(info[2]));
        }
    }

    private void addRewards(String[] info) {
        int id = Utils.parseInt(info[0]);
        if (this.contains(id)) {
            Quest quest = (Quest) super.idToData.get(id);
            quest.addRewards(info[2], Utils.parseInt(info[3]), Utils.parseInt(info[4]));
        }
    }
}