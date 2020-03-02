import java.io.FileNotFoundException;

public class HttpServer {
    public static final int ITEM_BASE_FILE_COLS = 11;
    public static final int QUEST_BASE_FILE_COLS = 7;
    public static final int MONSTER_BASE_FILE_COLS = 7;
    public static void main(String[] args) {
        try {
            ItemIndex ir = new ItemIndex("data/items", ITEM_BASE_FILE_COLS);
            QuestIndex qr = new QuestIndex("data/quests", QUEST_BASE_FILE_COLS);
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("failed to read items");
            e.printStackTrace();
        }
    }   
}