package indices;

import java.io.FileNotFoundException;

import types.Item;
import types.Monster;

public class HttpServer {
    public static void main(String[] args) {
        try {
            ItemIndex ir = new ItemIndex("data/items");
            Item item = ir.find("Potion");
            MonsterIndex mr = new MonsterIndex("data/monsters");
            Monster m = mr.find("Rathalos");
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("failed to read items");
            e.printStackTrace();
        }
    }   
}