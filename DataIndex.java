import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import types.Data;

/**
 * This class represents a generic reader of a type of data.
 */
public abstract class DataIndex {
    protected List<String> files; // names of files that contain information about this data
    protected int baseFileCols; // number of columns in the base file of this type
    protected HashMap<Integer, String> idToName; // a mapping from id of the data to its name
    protected HashMap<String, Integer> nameToId; // a mapping from name of the data to its id
    protected HashMap<Integer, Data> idToData; // a mapping from name of the data to the actual data.

    protected DataIndex(String dirName, int baseFileCols) {
        this.baseFileCols = baseFileCols;
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

    protected String findFile(String dirName, String target) {
        for (String fileName: files) {
            if (fileName.contains(target)) {
                return dirName + "/" + fileName;
            }
        }
        return null;
    }

    abstract boolean contains(String name);

    abstract boolean contains(int id);
}