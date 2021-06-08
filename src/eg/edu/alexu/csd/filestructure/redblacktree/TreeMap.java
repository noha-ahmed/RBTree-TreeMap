package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;
import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	private RedBlackTree<T, V> redBlackTree = new RedBlackTree<T, V>();

	@Override
	public Map.Entry ceilingEntry(Comparable key) {
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		if(node == null){
			node = this.redBlackTree.getSuccessor(this.redBlackTree.getRoot(),key,new Node());
			if(node.isNull()){
				return null;
			}
		}
		Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T)node.getKey(),(V)node.getValue());
		return entry;
	}

	@Override
	public Comparable ceilingKey(Comparable key) {
		//INode node = this.redBlackTree.getSuccessor(this.redBlackTree.getRoot(),key,new Node());
		Map.Entry<T, V> entry = ceilingEntry(key);
		if(entry  == null) return null;
		return entry.getKey();
	}
	@Override
	public void clear() {
		this.redBlackTree.clear();
	}


	@Override
	public boolean containsKey(Comparable key) {

		Object object = this.redBlackTree.search(key);
		if(object==null)
			return false;
		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		if(value==null){
			throw new RuntimeErrorException(new Error(),"key is null");
		}
		Collection<Object> col = values();
		return col.contains(value);
	}
	

	@Override
	public Set<Map.Entry> entrySet() {
		Set<Map.Entry> set = new LinkedHashSet<Map.Entry>();
		INode node = this.redBlackTree.getRoot();
		addEntryToSet(node,set);
		return set;
	}
	
	private void addEntryToSet (INode node,Set<Map.Entry> set) {
		if(node.getKey()==null)
			return;
		addEntryToSet(node.getLeftChild(),set);
		Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T)node.getKey(),(V)node.getValue());
		set.add(entry);
		addEntryToSet(node.getRightChild(),set);

	}

	private INode getLeastKey(INode root) {
		if (root.getLeftChild().isNull()) {
			return root;
		}
		return getLeastKey(root.getLeftChild());
	}

	@Override
	public Map.Entry firstEntry() {
		if (!this.redBlackTree.isEmpty()) {
			INode leastKeyNode = getLeastKey(this.redBlackTree.getRoot());
			Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T) leastKeyNode.getKey(), (V) leastKeyNode.getValue());
			return entry;
		}
		return null;
	}

	@Override
	public Comparable firstKey() {
		if (!this.redBlackTree.isEmpty()) {
			INode node = getLeastKey(this.redBlackTree.getRoot());
			return node.getKey();
		}
		return null;
	}

	@Override
	public Map.Entry floorEntry(Comparable key) {
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		if(node == null){
			node = this.redBlackTree.getPredecessor(this.redBlackTree.getRoot(),key,new Node());
			if(node.isNull()){
				return null;
			}
		}
		Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T)node.getKey(),(V)node.getValue());
		return entry;
	}

	@Override
	public Comparable floorKey(Comparable key) {
		//INode node = this.redBlackTree.getPredecessor(this.redBlackTree.getRoot(),key,new Node());
		Map.Entry<T, V> entry = floorEntry(key);
		if(entry == null)
			return null;
		return entry.getKey();
	}

	@Override
	public Object get(Comparable key) {
		return this.redBlackTree.search(key);
	}

	@Override
	public ArrayList<Map.Entry<T,V>> headMap(Comparable toKey) {
		ArrayList<Map.Entry<T,V>> headMapArray = new ArrayList<Map.Entry<T,V>>();
		INode node = this.redBlackTree.getRoot();
		if(node==null)
			return null;
		headMapAdd(headMapArray, node, toKey);
		return headMapArray;
	}

	private void headMapAdd(ArrayList<Map.Entry<T,V>> array, INode node,Comparable toKey) {
		if (node.getKey() == null)
			return;

		if (node.getLeftChild().getKey() != null)
			headMapAdd(array, node.getLeftChild(),toKey);
		Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T)node.getKey(),(V)node.getValue());
		if (node.getKey()!=null&& node.getKey().compareTo(toKey)<0){
			array.add(entry);
			if (node.getRightChild().getKey() != null)
				headMapAdd(array, node.getRightChild(),toKey);
	}



	}

	@Override
	public ArrayList<Map.Entry<T,V>> headMap(Comparable toKey, boolean inclusive) {
		ArrayList<Map.Entry<T,V>> headMapArray = new ArrayList<Map.Entry<T,V>>();
		INode node = this.redBlackTree.getRoot();
		if(node==null)
			return null;
		headMapAdd(headMapArray, node, toKey);
		node = redBlackTree.searchForNode(this.redBlackTree.getRoot(),toKey);
		Map.Entry<T, V> entry = new AbstractMap.SimpleEntry<>((T)node.getKey(),(V)node.getValue());
		headMapArray.add(entry);
		return headMapArray;
	}

	@Override
	public Set keySet() {
		Set<Comparable> set = new LinkedHashSet<Comparable>();
		INode node = this.redBlackTree.getRoot();
		addKeyToSet(node,set);
		return set;
		
		
	}
	private  void addKeyToSet(INode node,Set<Comparable> set) {
		if(node.getKey()==null)
			return;
		addKeyToSet(node.getLeftChild(),set);
		set.add(node.getKey());
		addKeyToSet(node.getRightChild(),set);

	}

	private INode lastEntryNode(INode node) {
		if (node.getRightChild().isNull()) {
			return node;
		}
		return lastEntryNode(node.getRightChild());
	}

	@Override
	public Map.Entry lastEntry() {
		if (this.redBlackTree.isEmpty()) {
			return null;
		}
		INode node = lastEntryNode(this.redBlackTree.getRoot());
		return new AbstractMap.SimpleEntry((T) node.getKey(), (V) node.getValue());
	}

	@Override
	public Comparable lastKey() {
		if (this.redBlackTree.isEmpty()) {
			return null;
		}
		INode node = lastEntryNode(this.redBlackTree.getRoot());
		return node.getKey();
	}

	@Override
	public Map.Entry pollFirstEntry() {
		Map.Entry<T, V> entry = firstEntry();
		if (entry != null) {
			this.redBlackTree.delete(entry.getKey());
		}
		return entry;
	}

	@Override
	public Map.Entry pollLastEntry() {
		Map.Entry<T, V> lastEntry = lastEntry();
		if (lastEntry != null) {
			this.redBlackTree.delete(lastEntry.getKey());
		}
		return lastEntry;
	}

	@Override
	public void put(Comparable key, Object value) {
		this.redBlackTree.insert(key, value);
	}

	@Override
	public void putAll(Map map) {
		if(map == null){
			throw new RuntimeErrorException(new Error(),"Map is null");
		}
		Set<Map.Entry<T, V>> enrtyset = map.entrySet();
		//Map.Entry<T, V>[] entries = (Map.Entry<T, V>[]) enrtyset.toArray();
		Object[] entries = enrtyset.toArray();
		for (int i = 0; i < entries.length; i++) {
			Map.Entry<T, V> entry = (Map.Entry<T, V>) entries[i];
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean remove(Comparable key) {
		return this.redBlackTree.delete(key);
	}

	@Override
	public int size() {
		return this.redBlackTree.getSize();
	}

	@Override
	public Collection values() {
		Collection<Object> col = new ArrayList<Object>();
		INode node = this.redBlackTree.getRoot();
		addValueToCol(node,col);
		return col;
	}
	
	
	private void addValueToCol (INode node,Collection<Object> col) {
		if(node.getKey()==null)
			return;
		addValueToCol(node.getLeftChild(),col);
		col.add(node.getValue());
		addValueToCol(node.getRightChild(),col);

	}
	
}
