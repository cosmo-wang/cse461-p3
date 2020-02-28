package types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Quest {
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
        this.id = Integer.parseInt(values[0]);
        this.name = values[1];
        this.category = values[2];
        this.stars = Integer.parseInt(values[3]);
        this.type = values[4];
        this.location = values[5];
        this.zenny = Integer.parseInt(values[6]);
        this.rewards = new HashSet<>();
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void addMonster(String monster, int num) {
        this.monster = monster;
        this.monsterNum = num;
    } 

    public void addRewards(String reward, int quantity, int percentage) {
        this.rewards.add(new QuestReward(reward, quantity, percentage));
    }

    public class QuestReward {
        private String reward;
        private int quantity;
        private int percentage;

        public QuestReward(String reward, int quantity, int percentage) {
            this.reward = reward;
            this.quantity = quantity;
            this.percentage = percentage;
        }
    }
}