package indices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import types.Location;
import utils.Utils;

public class LocationIndex extends DataIndex {
    public LocationIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("camps", this::addCamps);
        super.readFile("items", this::addLocationItems);
    }

    public List<Location> find(String query) {
        try {
            List<Location> res = new ArrayList<Location>();
            for (String name: super.nameToId.keySet()) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    Location l = (Location)super.idToData.get(super.nameToId.get(name));
                    res.add((Location)l.clone());
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
        Location location = new Location(info);
        super.idToName.put(location.getId(), location.getName());
        super.nameToId.put(location.getName(), location.getId());
        super.idToData.put(location.getId(), location);
    }

    private void addCamps(String[] info) {
        if (this.contains(info[0])) {
            Location location = (Location) super.idToData.get(super.nameToId.get(info[0]));
            location.addCamp(info[1], Utils.parseInt(info[3]));
        }
    }

    private void addLocationItems(String[] info) {
        if (this.contains(info[0])) {
            Location location = (Location) super.idToData.get(super.nameToId.get(info[0]));
            location.addLocationItem(info[4], Utils.parseInt(info[1]), Utils.parseInt(info[6]));
        }
    }
}