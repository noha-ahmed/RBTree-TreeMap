package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap{

    private RedBlackTree<T, V> redBlackTree = new RedBlackTree<T, V>();
    private int size = 0;

    @Override
    public Map.Entry ceilingEntry(Comparable key) {
        return null;
    }

    @Override
    public Comparable ceilingKey(Comparable key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(Comparable key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<Map.Entry> entrySet() {
        return null;
    }

    private INode getLeastKey(INode root){
        if(root.getLeftChild().isNull()){
            return root;
        }
        return getLeastKey(root.getLeftChild());
    }

    @Override
    public Map.Entry firstEntry() {
        if(!this.redBlackTree.isEmpty()){
            INode leastKeyNode = getLeastKey(this.redBlackTree.getRoot());
            Map.Entry<T, V> entry = new MapEntry<T, V>((T)leastKeyNode.getKey(),(V)leastKeyNode.getValue());
            return entry;
        }
        return null;
    }

    @Override
    public Comparable firstKey() {
        if(!this.redBlackTree.isEmpty()){
            INode node = getLeastKey(this.redBlackTree.getRoot());
            return node.getKey();
        }
        return null;
    }

    @Override
    public Map.Entry floorEntry(Comparable key) {
        return null;
    }

    @Override
    public Comparable floorKey(Comparable key) {
        return null;
    }

    @Override
    public Object get(Comparable key) {
        return null;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey) {
        return null;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
        return null;
    }

    @Override
    public Set keySet() {
        return null;
    }

    private INode lastEntryNode(INode node){
        if(node.getRightChild().isNull()){
            return node;
        }
        return lastEntryNode(node.getRightChild());
    }

    @Override
    public Map.Entry lastEntry() {
        if(redBlackTree.isEmpty()){
            return null;
        }
        INode node = lastEntryNode(redBlackTree.getRoot());
        return new MapEntry(node.getKey(),node.getValue());
    }

    @Override
    public Comparable lastKey() {
        if(redBlackTree.isEmpty()){
            return null;
        }
        INode node = lastEntryNode(redBlackTree.getRoot());
        return node.getKey();
    }

    @Override
    public Map.Entry pollFirstEntry() {
        Map.Entry<T, V> entry = firstEntry();
        this.redBlackTree.delete(entry.getKey());
        return entry;
    }

    @Override
    public Map.Entry pollLastEntry() {
        return null;
    }

    @Override
    public void put(Comparable key, Object value) {
        this.redBlackTree.insert(key, value);
        this.size++;
    }

    @Override
    public void putAll(Map map) {
        //RedBlackTree

    }

    @Override
    public boolean remove(Comparable key) {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection values() {
        return null;
    }

    public RedBlackTree<T, V> getRedBlackTree() {
        return redBlackTree;
    }

    public void setRedBlackTree(RedBlackTree<T, V> redBlackTree) {
        this.redBlackTree = redBlackTree;
    }
}
