package types;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import utils.Utils;

public class Quest extends Data implements Cloneable {
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
        if (!this.category.isEmpty()) {
            res.put("Category", this.category);
        }
        if (this.stars != -1) {
            String stars = "";
            for (int i = 0; i < this.stars; i++) {
                stars += "&#9733;";
            }
            res.put("Stars", stars);
        }
        if (!this.type.isEmpty()) {
            res.put("Type", this.type);
        }
        if (!this.location.isEmpty()) {
            res.put("Location", this.location);
        }
        if (this.zenny != -1) {
            res.put("Zenny", Integer.toString(this.zenny) + " z");
        }
        if (this.monster != null && !this.monster.isEmpty()) {
            res.put("Monster", this.monster + " &#215; " + this.monsterNum);
        }
        String rewardsValue = "";
        for (QuestReward qr: this.rewards) {
            rewardsValue += qr.toString() + ", ";
        }
        res.put("Rewards", rewardsValue.substring(0, rewardsValue.length() - 2));
        return res;
    }

    @Override
    public int compareTo(Data other) {
        if (other instanceof Quest) {
            if(this.stars > ((Quest)other).stars) return 1; 
            if(this.stars < ((Quest)other).stars) return -1;
            else                   return 0;
        } else {
            return super.compareTo(other);
        }
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

        @Override
        public String toString() {
            return this.reward + " (&#215; " + this.quantity + " @ " + this.percentage + "%)";
        }

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