import java.io.FileNotFoundException;

public class HttpServer {
    public static void main(String[] args) {
        try {
            ItemsReader ir = new ItemsReader();
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("failed to read items");
            e.printStackTrace();
        }
    }   
}