package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Skill;
import utils.Utils;

public class SkillIndex extends DataIndex {
    public SkillIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("levels", this::addLevels);
    }

    public List<Skill> find(String query) {
        try {
            List<Skill> res = new ArrayList<Skill>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Skill s = (Skill)super.idToData.get(super.nameToId.get(name));
                    res.add((Skill)s.clone());
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