package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Armor;

public class ArmorIndex extends DataIndex {

    public ArmorIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::readBase);
        super.readFile("craft", this::readCraft);
        super.readFile("skills", this::readSkills);
    }

    public List<Armor> find(String query) {
        try {
            List<Armor> res = new ArrayList<Armor>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Armor a = (Armor)super.idToData.get(super.nameToId.get(name));
                    res.add((Armor)a.clone());
                }
            }
            return res;
        } catch (CloneNotSupportedException e) {
            System.err.println("Armor could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    private void readBase(String[] info) {
        Armor armor = new Armor(info);
        super.idToName.put(armor.getId(), armor.getName());
        super.nameToId.put(armor.getName(), armor.getId());
        super.idToData.put(armor.getId(), armor);
    }

    private void readCraft(String[] info) {
        if (this.contains(info[0])) {
            Armor armor = (Armor) super.idToData.get(super.nameToId.get(info[0]));
            armor.addCraft(info);
        }
    }

    private void readSkills(String[] info) {
        if (this.contains(info[0])) {
            Armor armor = (Armor) super.idToData.get(super.nameToId.get(info[0]));
            armor.addSkills(info);
        }
    }
}