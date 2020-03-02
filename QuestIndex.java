import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import types.Quest;
import utils.Utils;

public class QuestIndex extends DataIndex {
    public QuestIndex(String dirName, int baseFileCols) throws FileNotFoundException {
        // invoke super constructor to fill some basic fields
        super(dirName, baseFileCols);
        // reads the base file
        String baseFile = super.findFile(dirName, "base");
        if (baseFile == null) {
            throw new FileNotFoundException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(baseFile))) {
            String questEntry = br.readLine();
            while ((questEntry = br.readLine()) != null) {
                Quest quest = new Quest(questEntry.split(Utils.COMMA_DELIMITER, baseFileCols));
                super.idToName.put(quest.getId(), quest.getName());
                super.nameToId.put(quest.getName(), quest.getId());
                super.idToData.put(quest.getId(), quest);
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + baseFile);
          e.printStackTrace();
        }
        // reads the monsters file
        String monsterFile = super.findFile(dirName, "monsters");
        if (monsterFile == null) {
            throw new FileNotFoundException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(monsterFile))) {
            String monsterEntry = br.readLine();
            while ((monsterEntry = br.readLine()) != null) {
                String[] info = monsterEntry.split(Utils.COMMA_DELIMITER);
                int id = Integer.parseInt(info[0]);
                if (this.contains(id)) {
                    Quest quest = (Quest) super.idToData.get(id);
                    quest.addMonster(info[1], Integer.parseInt(info[2]));
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + monsterFile);
          e.printStackTrace();
        }
        // reads the rewards file
        String rewardFile = super.findFile(dirName, "rewards");
        if (rewardFile == null) {
            throw new FileNotFoundException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(rewardFile))) {
            String rewardEntry = br.readLine();
            while ((rewardEntry = br.readLine()) != null) {
                String[] info = rewardEntry.split(Utils.COMMA_DELIMITER);
                int id = Integer.parseInt(info[0]);
                if (this.contains(id)) {
                    Quest quest = (Quest) super.idToData.get(id);
                    quest.addRewards(info[2], Integer.parseInt(info[3]), Integer.parseInt(info[3]));
                } else {
                    System.err.println("error");
                }
            }
        } catch (IOException e) {
          System.err.println("faield to open/read file " + rewardFile);
          e.printStackTrace();
        }
    }

    public boolean contains(String questName) {
        return this.find(questName) != null;
    }

    public boolean contains(int questId) {
        return this.find(super.idToName.get(questId)) != null;
    }
    
    /**
     * Find an Item in the index table given its name.
     * @param itemName name of the item to find
     * @return a copy of the item, null if not found or failed to copy the item
     */
    public Quest find(String questName) {
        try {
            int id = super.nameToId.get(questName);
            return (Quest) ((Quest) super.idToData.get(id)).clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Item could not be cloned.");
            e.printStackTrace();
            return null;
        }
    }
}