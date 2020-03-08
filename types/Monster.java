package types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.Utils;

public class Monster extends Data implements Cloneable {
    private String ecology;
    private String size;
    private Map<String, Boolean> traps;
    private List<Break> breaks;
    private List<String> habitats;
    private List<Hitzone> hitzones;
    private List<MonsterReward> rewards;
    private List<Weakness> weaknesses;

    public Monster(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.ecology = values[2];
        this.size = values[3];
        this.traps = new HashMap<>();
        this.breaks = new ArrayList<>();
        this.habitats = new ArrayList<>();
        this.hitzones = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        if (!values[4].isEmpty()) {
            traps.put("Pitfall Trap", values[4].equals("TRUE"));
        }
        
        if (!values[5].isEmpty()) {
            traps.put("Shock Trap", values[5].equals("TRUE"));
        }
        if (!values[6].isEmpty()) {
            traps.put("Vine Trap", values[6].equals("TRUE"));
        }
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        if (!this.ecology.isEmpty()) {
            res.put("Ecology", this.ecology);
        }
        if (!this.size.isEmpty()) {
            res.put("Size", this.size);
        } 
        for (Map.Entry<String, Boolean> trap: this.traps.entrySet()) {
            res.put(trap.getKey() , trap.getValue() ? "TRUE" : "FALSE");
        }
        for (Break b: this.breaks) {
            res.put(Utils.capitalize(b.getPart()) + " Break", b.toString());
        }
        String habitatsValue = "";
        for (String h: this.habitats) {
            habitatsValue += h + ", ";
        }
        if (!habitatsValue.isEmpty()) {
            res.put("Habitats", habitatsValue.substring(0, habitatsValue.length() - 2));
        }
        for (Hitzone h: this.hitzones) {
            res.put(Utils.capitalize(h.getPart() + " Hitzone"), h.toString());
        }
        for (Weakness w: this.weaknesses) {
            if (w.getForm().equals("normal")) {
                res.put(Utils.capitalize(w.getForm()) + " Weakness", w.toString());
            } else {
                res.put(Utils.capitalize(w.getForm()) + " Weakness (" 
                                        + w.getAltDescription() + ")", w.toString());
            }
        }
        return res;
    }

    public void addBreaks(String[] info) {
        String part = info[1];
        if (info[3].isEmpty() && info[4].isEmpty() && info[5].isEmpty()) {
            return;
        }
        int flinch = Utils.parseInt(info[3]);
        int wound = Utils.parseInt(info[4]);
        int sever = Utils.parseInt(info[5]);
        this.breaks.add(new Break(part, flinch, wound, sever));
    }

    public void addHabitats(String[] info) {
        this.habitats.add(info[1]);
    }

    public void addHitzones(String[] info) {
        String part = info[1];
        int cut = Utils.parseInt(info[2]);
        int impact = Utils.parseInt(info[3]);
        int shot = Utils.parseInt(info[4]);
        int fire = Utils.parseInt(info[5]);
        int water = Utils.parseInt(info[6]);
        int thunder = Utils.parseInt(info[7]);
        int ice = Utils.parseInt(info[8]);
        int dragon = Utils.parseInt(info[9]);
        int ko = Utils.parseInt(info[10]);
        this.hitzones.add(new Hitzone(part, cut, impact, shot, fire, water, thunder, ice, dragon, ko));
    }

    public void addRewards(String[] info) {
        String condition = info[1];
        String item = info[3];
        int quantity = Utils.parseInt(info[4]);
        int percentage = Utils.parseInt(info[5]);
        this.rewards.add(new MonsterReward(condition, item, quantity, percentage));
    }

    public void addWeaknesses(String[] info) {
        String form = info[1];
        String altDescription = info[2];
        int fire = Utils.parseInt(info[3]);
        int water = Utils.parseInt(info[4]);
        int thunder = Utils.parseInt(info[5]);
        int ice = Utils.parseInt(info[6]);
        int dragon = Utils.parseInt(info[7]);
        int poison = Utils.parseInt(info[8]);
        int sleep = Utils.parseInt(info[9]);
        int paralysis = Utils.parseInt(info[10]);
        int blast = Utils.parseInt(info[11]);
        int stun = Utils.parseInt(info[12]);
        this.weaknesses.add(new Weakness(form, altDescription, fire, water, thunder, ice, dragon, poison, sleep, paralysis, blast, stun));
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public class Break implements Cloneable {
        private String part;
        private Map<String, Integer> data;

        public Break (String part, int flinch, int wound, int sever) {
            this.part = part;
            this.data = new HashMap<String, Integer>();
            data.put("flinch", flinch);
            data.put("wound", wound);
            data.put("sever", sever);
        }

        public String getPart() {
            return part;
        }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                if (entry.getValue() != -1) {
                    res += Utils.capitalize(entry.getKey()) 
                            + " (" + entry.getValue() + "), ";
                }
            }
            return res.substring(0, res.length() - 2);
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class Habitat implements Cloneable {
        private Map<String, String> data;

        public Habitat(String map, String start, String move, String rest) {
            this.data = new HashMap<String, String>();
            data.put("map", map);
            data.put("start", start);
            data.put("move", move);
            data.put("rest", rest);
        }

        public String getMap() { return data.get("map"); }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class Hitzone implements Cloneable {
        private String part;
        private Map<String, Integer> data;

        public Hitzone(String part, int cut, int impact, int shot, int fire,
                        int water, int thunder, int ice, int dragon, int ko) {
            this.part = part;
            this.data = new HashMap<String, Integer>();
            data.put("cut", cut);
            data.put("impact", impact);
            data.put("shot", shot);
            data.put("fire", fire);
            data.put("water", water);
            data.put("thunder", thunder);
            data.put("ice", ice);
            data.put("dragon", dragon);
            data.put("ko", ko);
        }

        public String getPart() { return part; }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                if (entry.getValue() != -1) {
                    res += Utils.capitalize(entry.getKey()) 
                            + " (" + entry.getValue() + "), ";
                }
            }
            return res.substring(0, res.length() - 2);
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class MonsterReward implements Cloneable {
        private String item, condition;
        private int quantity, percentage;

        public MonsterReward(String item, String condition, int quantity, int percentage) {
            this.item = item;
            this.condition = condition;
            this.quantity = quantity;
            this.percentage = percentage;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class Weakness implements Cloneable {
        private String form, altDescription;
        private Map<String, Integer> data;

        public Weakness(String form, String altDescription, int fire, int water, int thunder,
                        int ice, int dragon, int poison, int sleep, int paralysis, int blast, int stun) {
            this.form = form;
            this.altDescription = altDescription;
            this.data = new HashMap<String, Integer>();
            data.put("fire", fire);
            data.put("water", water);
            data.put("thunder", thunder);
            data.put("ice", ice);
            data.put("dragon", dragon);
            data.put("poison", poison);
            data.put("sleep", sleep);
            data.put("paralysis", paralysis);
            data.put("blast", blast);
            data.put("stun", stun);
        }

        public String getForm() { return form; }

        public String getAltDescription() { return altDescription; }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                if (entry.getValue() != -1) {
                    res += Utils.capitalize(entry.getKey()) 
                            + " (" + entry.getValue() + "), ";
                }
            }
            return res.substring(0, res.length() - 2);
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}