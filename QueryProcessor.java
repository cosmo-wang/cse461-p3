import java.io.FileNotFoundException;

import indices.*;

public class QueryProcessor {
    private ArmorIndex ai;
    private CharmIndex ci;
    private ItemIndex ii;
    private LocationIndex li;
    private MonsterIndex mi;
    private QuestIndex qi;
    private SkillIndex si;
    private WeaponIndex wi;

    public QueryProcessor() throws FileNotFoundException {
        this.ai = new ArmorIndex("data/armors");
        this.ci = new CharmIndex("data/charms");
        this.ii = new ItemIndex("data/items");
        this.li = new LocationIndex("data/locations");
        this.mi = new MonsterIndex("data/monsters");
        this.qi = new QuestIndex("data/quests");
        this.si = new SkillIndex("data/skills");
        this.wi = new WeaponIndex("data/weapons");
    }
}