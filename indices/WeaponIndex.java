package indices;

import java.io.FileNotFoundException;

public class WeaponIndex extends DataIndex {

    public WeaponIndex(String dirName) throws FileNotFoundException {
        super(dirName);
        
    }

    @Override
    boolean contains(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    boolean contains(int id) {
        // TODO Auto-generated method stub
        return false;
    }
  
}