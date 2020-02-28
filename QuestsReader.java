import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import types.Quest;

public class QuestsReader {
    public static final String BASE_FILE = "data/quests/quest_base.csv";
    public static final int BASE_FILE_COLS = 7;
    public static final String MONSTERS_FILE = "data/quests/quest_monsters.csv";
    public static final String REWARDS_FILE = "data/quests/quest_rewards.csv";

    private HashMap<Integer, Quest> table;

    public QuestsReader() {
        this.table = new HashMap<Integer, Quest>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_FILE))) {
            String itemEntry = br.readLine();
            while ((itemEntry = br.readLine()) != null) {
                Quest quest = new Quest(itemEntry.split(Utils.COMMA_DELIMITER, BASE_FILE_COLS));
                this.table.put(quest.getId(), quest);
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + BASE_FILE);
          e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(MONSTERS_FILE))) {
            String monsterEntry = br.readLine();
            while ((monsterEntry = br.readLine()) != null) {
                String[] info = monsterEntry.split(Utils.COMMA_DELIMITER);
                int id = Integer.parseInt(info[0]);
                if (this.table.containsKey(id)) {
                    this.table.get(id).addMonster(info[1], Integer.parseInt(info[2]));
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + MONSTERS_FILE);
          e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(REWARDS_FILE))) {
            String rewardEntry = br.readLine();
            while ((rewardEntry = br.readLine()) != null) {
                String[] info = rewardEntry.split(Utils.COMMA_DELIMITER);
                int id = Integer.parseInt(info[0]);
                if (this.table.containsKey(id)) {
                    this.table.get(id).addRewards(info[2],
                                Integer.parseInt(info[3]), Integer.parseInt(info[3]));
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + REWARDS_FILE);
          e.printStackTrace();
        }
    }
    
}