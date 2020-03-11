import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import indices.*;
import types.Data;

public class QueryProcessor {
    private ArmorIndex ai;
    private CharmIndex ci;
    private ItemIndex ii;
    private LocationIndex li;
    private MonsterIndex mi;
    private QuestIndex qi;
    private SkillIndex si;
    private WeaponIndex wi;

    public QueryProcessor() {
        try {
            this.ai = new ArmorIndex("data/armors");
            this.ci = new CharmIndex("data/charms");
            this.ii = new ItemIndex("data/items");
            this.li = new LocationIndex("data/locations");
            this.mi = new MonsterIndex("data/monsters");
            this.qi = new QuestIndex("data/quests");
            this.si = new SkillIndex("data/skills");
            this.wi = new WeaponIndex("data/weapons");
        } catch (FileNotFoundException e) {
            System.err.println("Failed to create QueryProcessor");
            e.printStackTrace();
        }
    }

    public String processQuery(String input) {
        String[] components = input.trim().split(" ");
        String type = components[0];
        String query = null;
        if (components.length == 1) {
            query = "";
        } else if (components.length == 2) {
            query = input.trim().split(" ")[1];
        } else {
            return null;
        }
        List<Data> queryResult = null;
        switch (type) {
            case "armor":
                queryResult = ai.find(query);
                break;
            case "charm":
                queryResult = ci.find(query);
                break;
            case "item":
                queryResult = ii.find(query);
                break;
            case "location":
                queryResult = li.find(query);
                break;
            case "monster":
                queryResult = mi.find(query);
                break;
            case "quest":
                queryResult = qi.find(query);
                break;
            case "skill":
                queryResult = si.find(query);
                break;
            case "weapon":
                queryResult = wi.find(query);
                break;
            default:
                return null;
        }
        Collections.sort(queryResult);
        String res = "";
        for (Data resultEntry : queryResult) {
            res += "<p>" + resultEntry.getName() + "</p>\n";
            res += toHtml(resultEntry.assembleWithHeader());
        }
        return res;
    }

    private String toHtml(Map<String, String> data) {
        String res = "<ul style=\"list-style-type:none; display:none\">\n";
        for (String entry : data.keySet()) {
            res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
        }
        return res + "</ul>\n";
    }
}