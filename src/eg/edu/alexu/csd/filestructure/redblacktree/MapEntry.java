package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.Map;

public class MapEntry<k,v> implements Map.Entry<k, v>{
    private k key;
    private v value;

    public MapEntry(k key, v value){
        this.key = key;
        this.value = value;
    }

    @Override
    public k getKey() {
        return this.key;
    }

    @Override
    public v getValue() {
        return this.value;
    }

    @Override
    public v setValue(v value) {
        v oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
