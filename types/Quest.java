package types;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import utils.Utils;

public class Quest implements Data, Cloneable {
    private int id;
    private String name;
    private String category;
    private int stars;
    private String type;
    private String location;
    private int zenny;
    private String monster;
    private int monsterNum;
    private Set<QuestReward> rewards;

    public Quest(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.category = values[2];
        this.stars = Utils.parseInt(values[3]);
        this.type = values[4];
        this.location = values[5];
        this.zenny = Utils.parseInt(values[6]);
        this.rewards = new HashSet<>();
    }

    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        
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

    public Set<QuestReward> getRewards() { return Set.copyOf(rewards); }

    public void addMonster(String monster, int num) {
        this.monster = monster;
        this.monsterNum = num;
    } 

    public void addRewards(String reward, int quantity, int percentage) {
        this.rewards.add(new QuestReward(reward, quantity, percentage));
    }

    public class QuestReward implements Cloneable {
        private String reward;
        private int quantity;
        private int percentage;

        public QuestReward(String reward, int quantity, int percentage) {
            this.reward = reward;
            this.quantity = quantity;
            this.percentage = percentage;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}