package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static final String COMMA_DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public static int parseInt(String value) {
        return value.isEmpty() ? -1 : Integer.parseInt(value);
    }

    public static int maxCols(String filename) {
        int max = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String entry = br.readLine();
            while ((entry = br.readLine()) != null) {
                String[] entries = entry.split(COMMA_DELIMITER);
                max = Math.max(max, entries.length);
            }
            return max;
        } catch (IOException e) {
            System.err.println("faield to open/read file: " + filename);
            e.printStackTrace();
            return -1;
        }
    }

    public static String capitalize(String str){  
        String words[] = str.split("\\s");  
        String capitalizeWord = "";  
        for(String w: words) {  
            String first = w.substring(0,1);  
            String afterfirst = w.substring(1);  
            capitalizeWord += first.toUpperCase() + afterfirst + " ";  
        }  
        return capitalizeWord.trim();  
    }  
}