import java.io.FileNotFoundException;
import java.util.List;

import indices.ArmorIndex;
import indices.CharmIndex;
import indices.ItemIndex;
import indices.LocationIndex;
import indices.MonsterIndex;
import indices.QuestIndex;
import indices.SkillIndex;
import indices.WeaponIndex;
import types.Armor;
import types.Charm;
import types.Data;
import types.Item;
import types.Location;
import types.Monster;
import types.Quest;
import types.Skill;
import types.Weapon;

public class HttpServer {
    public static void main(String[] args) {
        try {
            ItemIndex ir = new ItemIndex("data/items");
            List<Data> item = ir.find("poti");
            MonsterIndex mr = new MonsterIndex("data/monsters");
            List<Data> m = mr.find("rath");
            QuestIndex qi = new QuestIndex("data/quests");
            List<Data> q = qi.find("pink");
            SkillIndex si = new SkillIndex("data/skills");
            List<Data> s = si.find("skin");
            WeaponIndex wi = new WeaponIndex("data/weapons");
            List<Data> w = wi.find("blade");
            ArmorIndex ai = new ArmorIndex("data/armors");
            List<Data> a = ai.find("kulu");
            LocationIndex li = new LocationIndex("data/locations");
            List<Data> l = li.find("rotten");
            CharmIndex ci = new CharmIndex("data/charms");
            List<Data> c = ci.find("attack");
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("failed to read items");
            e.printStackTrace();
        }
    }   
}