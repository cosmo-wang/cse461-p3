package indices;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import types.Charm;

public class CharmIndex extends DataIndex {
    public CharmIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::readBase);
        // read json file for skills and craft to add to charms
        try (BufferedReader br = new BufferedReader(new FileReader("data/charms/charm_ext.json"))) {
            String line = null;
            String entry = "";
            while ((line = br.readLine()) != null) {
                if (!line.equals("},")) {
                    entry += line;
                } else {
                    this.readExt(entry);
                    entry = "";
                }
            }
        } catch (IOException e) {
            System.err.println("faield to open/read file: charm_ext.json");
            e.printStackTrace();
        }
    }

    private void readBase(String[] info) {
        Charm charm = new Charm(info);
        super.idToName.put(charm.getId(), charm.getName());
        super.nameToId.put(charm.getName(), charm.getId());
        super.idToData.put(charm.getId(), charm);
    }

    private void readExt(String jsonInfo) {
        String name = (jsonInfo.split(":")[0]).replaceAll("^\"|\"$", "");
        if (this.contains(name)) {
            Charm charm = (Charm) super.idToData.get(super.nameToId.get(name));
            charm.addSkillsAndCraft(jsonInfo);
        }
    }
}