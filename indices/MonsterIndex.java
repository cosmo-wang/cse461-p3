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

    public Monster find(String monsterName) {
        try {
            int id = super.nameToId.get(monsterName);
            Monster res = (Monster) super.idToData.get(id);
            return res == null ? null : (Monster) res.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Monster could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean contains(String monsterName) {
        return this.find(monsterName) != null;
    }

    @Override
    public boolean contains(int monsterId) {
        return this.find(super.idToName.get(monsterId)) != null;
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