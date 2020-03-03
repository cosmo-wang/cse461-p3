import java.io.FileNotFoundException;

import indices.ItemIndex;
import indices.MonsterIndex;
import indices.QuestIndex;
import indices.SkillIndex;
import indices.WeaponIndex;
import types.Item;
import types.Monster;
import types.Quest;
import types.Skill;
import types.Weapon;

public class HttpServer {
    public static void main(String[] args) {
        try {
            // ItemIndex ir = new ItemIndex("data/items");
            // Item item = ir.find("Potion");
            // MonsterIndex mr = new MonsterIndex("data/monsters");
            // Monster m = mr.find("Rathalos");
            // QuestIndex qi = new QuestIndex("data/quests");
            // Quest q = qi.find("Tickled Pink");
            SkillIndex si = new SkillIndex("data/skills");
            Skill s = si.find("Iron Skin");
            WeaponIndex wi = new WeaponIndex("data/weapons");
            Weapon w = wi.find("Giant Jawblade I");
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("failed to read items");
            e.printStackTrace();
        }
    }   
}