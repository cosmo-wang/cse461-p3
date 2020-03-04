package indices;

import java.io.FileNotFoundException;

import types.Location;
import utils.Utils;

public class LocationIndex extends DataIndex {
    public LocationIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        super.readFile("base", this::addBase);
        super.readFile("camps", this::addCamps);
        super.readFile("items", this::addLocationItems);
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