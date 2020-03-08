package types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import utils.Utils;

public class Charm extends Data implements Cloneable {
    private String previous;
    private int rarity;
    private Map<String, String> skills;
    private Craft craft;

    public Charm(String[] values) {
        this.id = Utils.parseInt(values[0]);
        this.name = values[1];
        this.previous = values[2];
        this.rarity = Utils.parseInt(values[3]);
    }

    @Override
    public Map<String, String> assembleWithHeader() {
        Map<String, String> res = new LinkedHashMap<String, String>();
        if (!this.previous.isEmpty()) {
            res.put("Previous", this.previous);
        }
        res.put("Rarity", Integer.toString(this.rarity));
        int i = 0;
        for (String skillName : this.skills.keySet()) {
            i++;
            res.put("Skill " + i, skillName + " (Lv " + this.skills.get(skillName) + ")");
        }
        if (this.craft != null) {
            res.put("Craft (" + craft.getType() + ")", craft.toString());
        }
        return res;
    }

    public void addSkillsAndCraft(String jsonInfo) {
        this.skills = new HashMap<String, String>();
        String[] otherInfo = jsonInfo.split(":", 2)[1].split("},");
        String[] skillsInfo = otherInfo[0].split(": ", 2)[1]
                    .replaceAll("\\{", "").replaceAll("\\}", "").split(",");
        for (int i = 0; i < skillsInfo.length; i++) {
            String[] skillInfo = skillsInfo[i].split(":");
            String skillName = skillInfo[0].replaceAll("^\"|\"$", "").trim().substring(1);
            String skillLevel = skillInfo[1].trim();
            this.skills.put(skillName, skillLevel);
        }
        String[] craftsInfo = otherInfo[1].split(": ", 2)[1]
                    .replaceAll("\\{", "").replaceAll("\\}", "").split(",");
        String[] craftItems = new String[craftsInfo.length * 2];
        for (int i = 0; i < craftsInfo.length; i++) {
            String[] craftInfo = craftsInfo[i].split(":");
            String craftName = craftInfo[0].replaceAll("^\"|\"$", "").trim().substring(1);
            String craftQty = craftInfo[1].trim();
            craftItems[2 * i] = craftName;
            craftItems[2 * i + 1] = craftQty;
        }
        this.craft = new Craft("Create", craftItems);
    }

    @Override
    public Data copy() throws CloneNotSupportedException {
        return (Data) this.clone();
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}