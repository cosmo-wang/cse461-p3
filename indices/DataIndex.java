package indices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import types.Data;
import utils.Utils;

/**
 * This class represents a generic reader of a type of data. 
 */
public class DataIndex {
    protected String dirName;
    protected List<String> files; // names of files that contain information about this data
    protected HashMap<Integer, String> idToName; // a mapping from id of the data to its name
    protected HashMap<String, Integer> nameToId; // a mapping from name of the data to its id
    protected HashMap<Integer, Data> idToData; // a mapping from name of the data to the actual data.

    protected DataIndex(String dirName) {
        this.dirName = dirName;
        this.files = new ArrayList<String>();
        this.idToName = new HashMap<Integer, String>();
        this.nameToId = new HashMap<String, Integer>();
        this.idToData = new HashMap<Integer, Data>();
        File[] files = new File(dirName).listFiles();
        for (File file : files) {
            String name = file.getName();
            if (!file.isDirectory() && name.endsWith("csv")) {
                this.files.add(name);
            }
        }
    }

    protected void readFile(String target, Consumer<String[]> f) throws FileNotFoundException {
        String filename = null;
        for (String file: this.files) {
            if (file.contains(target)) {
                filename =  dirName + "/" + file;
            }
        }
        if (filename == null) {
            throw new FileNotFoundException();
        }
        int maxCols = Utils.maxCols(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String entry = br.readLine();
            while ((entry = br.readLine()) != null) {
                f.accept(entry.split(Utils.COMMA_DELIMITER, maxCols));
            }
        } catch (IOException e) {
            System.err.println("faield to open/read file: " + filename);
            e.printStackTrace();
        }
    }

    public boolean contains(String name) {
        return this.nameToId.containsKey(name);
    }

    public boolean contains(int id) {
        return this.idToData.containsKey(id);
    }
}