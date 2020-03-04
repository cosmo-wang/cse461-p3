package indices;

import java.io.FileNotFoundException;

import types.Monster;

public class MonsterIndex extends DataIndex {

    public MonsterIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("breaks", this::addBreaks);
        super.readFile("habitats", this::addHabitats);
        super.readFile("hitzones", this::addHitzones);
        super.readFile("rewards", this::addRewards);
        super.readFile("weaknesses", this::addWeaknesses);
    }

    private void addBase(String[] info) {
        Monster monster = new Monster(info);
        super.idToName.put(monster.getId(), monster.getName());
        super.nameToId.put(monster.getName(), monster.getId());
        super.idToData.put(monster.getId(), monster);
    }

    private void addBreaks(String[] info) {
        if (this.contains(info[0])) {
            Monster monster = (Monster) super.idToData.get(super.nameToId.get(info[0]));
            monster.addBreaks(info);
        }
    }

    private void addHabitats(String[] info) {
        if (this.contains(info[0])) {
            Monster monster = (Monster) super.idToData.get(super.nameToId.get(info[0]));
            monster.addHabitats(info);
        }
    }

    private void addHitzones(String[] info) {
        if (this.contains(info[0])) {
            Monster monster = (Monster) super.idToData.get(super.nameToId.get(info[0]));
            monster.addHitzones(info);
        }
    }

    private void addRewards(String[] info) {
        if (this.contains(info[0])) {
            Monster monster = (Monster) super.idToData.get(super.nameToId.get(info[0]));
            monster.addRewards(info);
        }
    }

    private void addWeaknesses(String[] info) {
        if (this.contains(info[0])) {
            Monster monster = (Monster) super.idToData.get(super.nameToId.get(info[0]));
            monster.addWeaknesses(info);
        }
    }
  
}