package indices;

import java.io.FileNotFoundException;

import types.Skill;
import utils.Utils;

public class SkillIndex extends DataIndex {
    public SkillIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("levels", this::addLevels);
    }

    @Override
    public boolean contains(String skillName) {
        return this.find(skillName) != null;
    }

    @Override
    public boolean contains(int skillId) {
        return false;
    }
    
    /**
     * Find an Item in the index table given its name.
     * @param itemName name of the item to find
     * @return a copy of the item, null if not found or failed to copy the item
     */
    public Skill find(String skillName) {
        try {
            int id = super.nameToId.get(skillName);
            return (Skill) ((Skill) super.idToData.get(id)).clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Item could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    private void addBase(String[] info) {
        Skill skill = new Skill(info);
        super.idToName.put(skill.getId(), skill.getName());
        super.nameToId.put(skill.getName(), skill.getId());
        super.idToData.put(skill.getId(), skill);
    }

    private void addLevels(String[] info) {
        if (this.contains(info[0])) {
            Skill skill = (Skill) super.idToData.get(super.nameToId.get(info[0]));
            skill.addLevels(Utils.parseInt(info[1]), info[2]);
        }
    }
}