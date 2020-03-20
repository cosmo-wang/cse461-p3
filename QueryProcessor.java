import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import indices.*;
import types.Data;
import types.Item;
import utils.Utils;

public class QueryProcessor {
    public static final String DIRPATH = "img/";

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

    public String processQuery(String type, String query) {
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
        String res;
        if (query.isEmpty()) {
            res = "<div class=\"resultHeader\">Showing all " + Utils.capitalize(type) + "s: </div>\n";
        } else {
            res = "<div class=\"resultHeader\">Results for \"" + query + "\" of type " + Utils.capitalize(type)
                    + ":</div>\n";
        }
        for (Data resultEntry : queryResult) {
            // res += "<p>" + resultEntry.getName() + "</p>\n";
            // res += toHtml(resultEntry.assembleWithHeader());
            res += toHTml(type, resultEntry);
        }
        return res;
    }

    private String toHTml(String type, Data resultEntry) {
        String res = "";
        String name = resultEntry.getName();
        Map<String, String> data = resultEntry.assembleWithHeader();
        if (type.equals("armor")) {
            String part = data.get("Type");
            res += "<p><img src=\"" + DIRPATH + "armors/" + part + ".svg\" class=\"iconBig\"/>" + name + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Slots")) {
                    res += slotsToHtml(data.get(entry));
                } else if (entry.contains("Base") || entry.contains("Max")) {
                    res += "<li><img src=\"" + DIRPATH + "other/defense.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else if (entry.contains("Defense")) {
                    res += "<li><img src=\"" + DIRPATH + "other/" + entry.split(" ")[0].toLowerCase()
                            + ".svg\" class=\"iconSmall\" />" + entry + ": " + data.get(entry) + "</li>\n";
                } else if (entry.contains("Craft")) {
                    res += craftToHtml(entry, data.get(entry), true);
                } else {
                    res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
                }
            }
            return res + "</ul>\n";
        } else if (type.equals("charm")) {
            res += "<p><img src=\"" + DIRPATH + "charms/charm.svg\" class=\"iconBig\"/>" + name + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Craft")) {
                    res += craftToHtml(entry, data.get(entry), true);
                } else {
                    res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
                }
            }
            return res + "</ul>\n";
        } else if (type.equals("item")) {
            String icon = ((Item) resultEntry).getIcon();
            String iconColor = ((Item) resultEntry).getIconColor();
            if (icon.isEmpty() || iconColor.isEmpty()) {
                res += "<p><img src=\"" + DIRPATH + "items/Question.svg\" class=\"iconBig\"/>" + name + "</p>\n";
            } else {
                res += "<p><img src=\"" + DIRPATH + "items/" + icon + iconColor + ".svg\" class=\"iconBig\"/>" + name + "</p>\n";
            }
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Price")) {
                    res += "<li><img src=\"" + DIRPATH + "other/zenny.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                    ;
                } else if (entry.contains("Combination")) {
                    res += "<li><img src=\"" + DIRPATH + "other/crafting.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                    ;
                } else if (entry.contains("Points")) {
                    res += "<li><img src=\"" + DIRPATH + "other/points.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                    ;
                } else {
                    res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
                }
            }
            return res + "</ul>\n";
        } else if (type.equals("location")) {
            String nameNoSpace = name.replaceAll(" ", "");
            if (!nameNoSpace.equals("AncientForest") && !nameNoSpace.equals("WildspireWaste")
                    && !nameNoSpace.equals("CoralHighlands") && !nameNoSpace.equals("RottenVale")
                    && !nameNoSpace.equals("Elder'sRecess")) {
                res += "<p><img src=\"" + DIRPATH + "other/map.svg\" class=\"iconBig\"/>" + name + "</p>\n";
            } else {
                res += "<p><img src=\"" + DIRPATH + "locations/" + nameNoSpace + ".svg\" class=\"iconBig\"/>" + name
                        + "</p>\n";
            }
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Camp")) {
                    res += "<li><img src=\"" + DIRPATH + "other/camp.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else {
                    String icon;
                    try {
                        Item i = (Item) this.ii.get(entry);
                        if (i != null) {
                            if (!i.getIcon().isEmpty()) {
                                icon = i.getIcon() + i.getIconColor();
                            } else {
                                icon = "Question";
                            }
                        } else {
                            icon = "Question";
                        }
                    } catch (CloneNotSupportedException e) {
                        icon = "Question";
                    }
                    res += "<li><img src=\"" + DIRPATH + "items/" + icon + ".svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                }
            }
            return res + "</ul>\n";
        } else if (type.equals("monster")) {
            res += "<p><img src=\"" + DIRPATH + "monsters/" + resultEntry.getId() + ".png\" class=\"iconBig\"/>" + name + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            res += "<img src=\"" + DIRPATH + "monsters/" + resultEntry.getId() + ".png\" class=\"monsterImg\" />";
            for (String entry : data.keySet()) {
                res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
            }
            return res + "</ul>\n";
        } else if (type.equals("quest")) {
            res += "<p>" + resultEntry.getName() + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Rewards")) {
                    res += craftToHtml("Rewards", data.get(entry), false);
                } else {
                    res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
                }
            }
            return res + "</ul>\n";
        } else if (type.equals("weapon")) {
            res += "<p><img src=\"" + DIRPATH + "weapons/" + data.get("Type") + ".svg\" class=\"iconBig\"/>" + name.replaceAll("\"", "") + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                if (entry.contains("Slots")) {
                    res += slotsToHtml(data.get(entry));
                } else if (entry.contains("Element")) {
                    res += "<li><img src=\"" + DIRPATH + "other/element.svg\" class=\"iconSmall\" />" + entry + ": ";
                    String[] elements = data.get(entry).split(", ");
                    for (String element : elements) {
                        res += "<img src=\"" + DIRPATH + "other/" + element.split(" ")[0].toLowerCase()
                            + ".svg\" class=\"iconSmall\" />" + element + ", ";
                    }
                    res = res.substring(0, res.length() - 2);
                } else if (entry.contains("Defence")) {
                    res += "<li><img src=\"" + DIRPATH + "other/defense.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else if (entry.contains("Attack")) {
                    res += "<li><img src=\"" + DIRPATH + "other/attack.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else if (entry.contains("Affinity")) {
                    res += "<li><img src=\"" + DIRPATH + "other/affinity.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else if (entry.contains("Craft")) {
                    res += craftToHtml(entry, data.get(entry), true);
                } else if (entry.contains("Sharpness")) {
                    res += "<li><img src=\"" + DIRPATH + "other/sharpness.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else if (entry.contains("Elderseal")) {
                    res += "<li><img src=\"" + DIRPATH + "other/elderseal.svg\" class=\"iconSmall\" />" + entry + ": "
                            + data.get(entry) + "</li>\n";
                } else {
                    res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
                }
                
            }
            return res + "</ul>\n";
        } else {
            res += "<p>" + resultEntry.getName() + "</p>\n";
            res += "<ul style=\"list-style-type:none; display:none\">\n";
            for (String entry : data.keySet()) {
                res += "<li>" + entry + ": " + data.get(entry) + "</li>\n";
            }
            return res + "</ul>\n";
        }
    }

    private String slotsToHtml(String slotsInfo) {
        String res = "";
        String[] slots = slotsInfo.split(", ");
        res += "<li><img src=\"" + DIRPATH + "other/slots.svg\" class=\"iconSmall\" />" + "Slots: ";
        for (int i = 0; i < slots.length; i++) {
            res += "<img src=\"" + DIRPATH + "other/slot_" + slots[i] + ".svg\" class=\"iconSmall\" />";
        }
        res += "</li>\n";
        return res;
    }

    private String craftToHtml(String entry, String craftInfo, boolean craft) {
        String res = "";
        String[] craftItems = craftInfo.split(", ");
        if (craft) {
            res += "<li><img src=\"" + DIRPATH + "other/crafting.svg\" class=\"iconSmall\" />" + entry + ": ";
        } else {
            res += "<li>" + entry + ": ";
        }
        for (String item : craftItems) {
            String icon;
            try {
                Item i = (Item) this.ii.get(item.split(" \\(")[0]);
                if (i != null) {
                    if (!i.getIcon().isEmpty()) {
                        icon = i.getIcon() + i.getIconColor();
                    } else {
                        icon = "Question";
                    }
                } else {
                    icon = "Question";
                }
            } catch (CloneNotSupportedException e) {
                icon = "Question";
            }
            res += "<img src=\"" + DIRPATH + "items/" + icon
                            + ".svg\" class=\"iconSmall\" />" + item + ", ";
        }
        res = res.substring(0, res.length() - 2);
        res += "</li>\n";
        return res;
    }
}