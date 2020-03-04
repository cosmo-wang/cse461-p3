package types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import utils.Utils;

public class Monster implements Data, Cloneable {
    private int id;
    private String name;
    private String ecology;
    private String size;
    private Map<String, Boolean> traps;
    private Set<Break> breaks;
    private Set<Habitat> habitats;
    private Set<Hitzone> hitzones;
    private Set<MonsterReward> rewards;
    private Set<Weakness> weaknesses;

    public Monster(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.ecology = values[2];
        this.size = values[3];
        this.traps = new HashMap<>();
        this.breaks = new HashSet<>();
        this.habitats = new HashSet<>();
        this.hitzones = new HashSet<>();
        this.rewards = new HashSet<>();
        this.weaknesses = new HashSet<>();
        traps.put("Pitfall Trap", values[4] == "TRUE");
        traps.put("Shock Trap", values[5] == "TRUE");
        traps.put("Vine Trap", values[6] == "TRUE");
    }

    public void addBreaks(String[] info) {
        String part = info[1];
        int flinch = Utils.parseInt(info[3]);
        int wound = Utils.parseInt(info[4]);
        int sever = Utils.parseInt(info[5]);
        this.breaks.add(new Break(part, flinch, wound, sever));
    }

    public void addHabitats(String[] info) {
        String map = info[1];
        String start = info[2];
        String move = info[3];
        String rest = info[4];
        this.habitats.add(new Habitat(map, start, move, rest));
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

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}