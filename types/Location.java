package types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utils.Utils;

public class Location extends Data implements Cloneable {
    private List<Camp> camps;
    private List<LocationItem> locationItems;

    public Location(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.camps = new ArrayList<>();
        this.locationItems = new ArrayList<>();
    }

    @Override
    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        int i = 0;
        for (Camp camp : this.camps) {
            i++;
            res.put("Camp " + i, camp.toString());
        }
        for (LocationItem lm: this.locationItems) {
            res.put(lm.getName(), lm.toString());
        }
        return res;
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
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
        for (LocationItem item : this.locationItems) {
            if (item.getName().equals(locationItemName)) {
                item.addAreaAndPercentageEntry(area, percentage);
                return;
            }
        }
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

        @Override
        public String toString() {
            return this.name + " (" + area + ")";
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

        public String getName() { return name; }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<Integer, Integer> entry: this.areaAndPercentage.entrySet()){
                res += "Area " + entry.getKey() + " @ " + entry.getValue() + "%, ";
            }
            return res.substring(0, res.length() - 2);
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