package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Quest;
import utils.Utils;

public class QuestIndex extends DataIndex {
    public QuestIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("monster", this::addMonster);
        super.readFile("rewards", this::addRewards);
    }

    public List<Quest> find(String query) {
        try {
            List<Quest> res = new ArrayList<Quest>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Quest q = (Quest)super.idToData.get(super.nameToId.get(name));
                    res.add((Quest)q.clone());
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