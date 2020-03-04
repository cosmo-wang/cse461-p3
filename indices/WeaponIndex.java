package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Weapon;

public class WeaponIndex extends DataIndex {

    public WeaponIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::readBase);
        super.readFile("craft", this::readCraft);
        super.readFile("sharpness", this::readSharpness);
    }

    public List<Weapon> find(String query) {
        try {
            List<Weapon> res = new ArrayList<Weapon>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Weapon w = (Weapon)super.idToData.get(super.nameToId.get(name));
                    res.add((Weapon)w.clone());
                }
            }
            return res;
        } catch (CloneNotSupportedException e) {
            System.err.println("Monster could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    private void readBase(String[] info) {
        Weapon weapon = new Weapon(info);
        super.idToName.put(weapon.getId(), weapon.getName());
        super.nameToId.put(weapon.getName(), weapon.getId());
        super.idToData.put(weapon.getId(), weapon);
    }
    
    private void readCraft(String[] info) {
        if (this.contains(info[0])) {
            Weapon weapon = (Weapon) super.idToData.get(super.nameToId.get(info[0]));
            weapon.addCraft(info);
        }
    }

    private void readSharpness(String[] info) {
        if (this.contains(info[0])) {
            Weapon weapon = (Weapon) super.idToData.get(super.nameToId.get(info[0]));
            weapon.addSharpness(info);
        }
    }
}