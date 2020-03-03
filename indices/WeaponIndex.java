package indices;

import java.io.FileNotFoundException;

import types.Weapon;

public class WeaponIndex extends DataIndex {

    public WeaponIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::readBase);
        super.readFile("craft", this::readCraft);
        super.readFile("sharpness", this::readSharpness);
    }

    public Weapon find(String weaponName) {
        try {
            int id = super.nameToId.get(weaponName);
            Weapon res = (Weapon) super.idToData.get(id);
            return res == null ? null : (Weapon) res.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Weapon could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    boolean contains(String weaponName) {
        return this.find(weaponName) != null;
    }

    @Override
    boolean contains(int weaponId) {
        return this.find(super.idToName.get(weaponId)) != null;
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
            weapon.addCrafts(info);
        }
    }

    private void readSharpness(String[] info) {
        if (this.contains(info[0])) {
            Weapon weapon = (Weapon) super.idToData.get(super.nameToId.get(info[0]));
            weapon.addSharpness(info);
        }
    }
}