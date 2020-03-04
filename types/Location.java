package types;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import utils.Utils;

public class Location implements Data, Cloneable {
    private int id;
    private String name;
    private Set<Camp> camps;
    private Set<LocationItem> locationItems;

    public Location(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.camps = new HashSet<>();
        this.locationItems = new HashSet<>();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void addCamp(String campName, int area) {
        Camp camp = new Camp(campName, area);
        this.camps.add(camp);
    }

    public void addLocationItem(String locationItemName, int area, int percentage) {
        LocationItem locationItem = new LocationItem(locationItemName);
        locationItem.addAreaAndPercentageEntry(area, percentage);
        this.locationItems.add(locationItem);
    }

    private class Camp implements Cloneable {
        private String name;
        private int area;

        public Camp(String name, int area) {
            this.name = name;
            this.area = area;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private class LocationItem implements Cloneable {
        private String name;
        private Map<Integer, Integer> areaAndPercentage;

        public LocationItem(String name) {
            this.name = name;
            this.areaAndPercentage = new HashMap<Integer, Integer>();
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public void addAreaAndPercentageEntry(int area, int percentage) {
            if (!this.areaAndPercentage.containsKey(area)) {
                this.areaAndPercentage.put(area, percentage);
            }
        }
    }
}