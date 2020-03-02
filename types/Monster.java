package types;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import utils.Utils;

public class Monster implements Data {
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
    
    public class Break implements Cloneable {
        private String part;
        private int flinch, wound, sever;

        public Break (String part, int flinch, int wound, int sever) {
            this.part = part;
            this.flinch = flinch;
            this.wound = wound;
            this.sever = sever;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class Habitat implements Cloneable {
        private String map, start, move, rest;

        public Habitat(String map, String start, String move, String rest) {
            this.map = map;
            this.start = start;
            this.move = move;
            this.rest = rest;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public class Hitzone implements Cloneable {
        private String part;
        private int cut, impact, shot, fire, water, thunder, ice, dragon, ko;

        public Hitzone(String part, int cut, int impact, int shot, int fire,
                        int water, int thunder, int ice, int dragon, int ko) {
            this.part = part;
            this.cut = cut;
            this.impact = impact;
            this.shot = shot;
            this.fire = fire;
            this.water = water;
            this.thunder = thunder;
            this.ice = ice;
            this.dragon = dragon;
            this.ko = ko;
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
        private int fire, water, thunder, ice, dragon, poison, sleep, paralysis, blast, stun;

        public Weakness(String form, String altDescription, int fire, int water, int thunder,
                        int ice, int dragon, int poison, int sleep, int paralysis, int blast, int stun) {
            this.form = form;
            this.altDescription = altDescription;
            this.fire = fire;
            this.water = water;
            this.thunder = thunder;
            this.ice = ice;
            this.dragon = dragon;
            this.poison = poison;
            this.sleep = sleep;
            this.paralysis = paralysis;
            this.blast = blast;
            this.stun = stun;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}