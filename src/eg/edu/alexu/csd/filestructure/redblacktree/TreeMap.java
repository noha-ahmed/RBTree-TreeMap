package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	private RedBlackTree<T, V> redBlackTree = new RedBlackTree<T, V>();

	@Override
	public Map.Entry ceilingEntry(Comparable key) {
		INode node = this.redBlackTree.getSuccessor(this.redBlackTree.getRoot(),key,new Node());
		if(node.isNull()){
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			if(node == null){
				return null;
			}
		}
		Map.Entry<T, V> entry = new MapEntry<T, V>((T)node.getKey(),(V)node.getValue());
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
		Collection<Object> col = values();
		return col.contains(value);
	}
	

	@Override
	public Set<Map.Entry> entrySet() {
		Set<Map.Entry> set = new HashSet<Map.Entry>();
		INode node = this.redBlackTree.getRoot();
		addEntryToSet(node,set);
		return set;
	}
	
	private void addEntryToSet (INode node,Set<Map.Entry> set) {
		if(node==null)
			return;
		addEntryToSet(node.getLeftChild(),set);
		addEntryToSet(node.getRightChild(),set);
		set.add(new MapEntry<T, V>((T) node.getKey(), (V) node.getValue()));
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
			Map.Entry<T, V> entry = new MapEntry<T, V>((T) leastKeyNode.getKey(), (V) leastKeyNode.getValue());
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
		INode node = this.redBlackTree.getPredecessor(this.redBlackTree.getRoot(),key,new Node());
		if(node.isNull()){
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			if(node == null){
				return null;
			}
		}
		Map.Entry<T, V> entry = new MapEntry<T, V>((T)node.getKey(),(V)node.getValue());
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
	public ArrayList<Map.Entry> headMap(Comparable toKey) {
		ArrayList<Map.Entry> headMapArray = new ArrayList<Map.Entry>();
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
		boolean flag= false;
		if (node == null) {
			put(toKey,null);
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
			flag=true;
		}
		
		headMapAdd(headMapArray, node.getLeftChild());
		if(flag==true)
			remove(toKey);
		return headMapArray;
	}

	private void headMapAdd(ArrayList<Map.Entry> array, INode node) {
		if (node == null)
			return;
		if (node.getLeftChild() != null)
			headMapAdd(array, node.getLeftChild());
		Map.Entry<T, V> entry = new MapEntry<T, V>((T) node.getKey(), (V) node.getValue());
		array.add(entry);
		if (node.getLeftChild() != null)
			headMapAdd(array, node.getRightChild());

	}

	@Override
	public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
		ArrayList<Map.Entry> headMapArray = new ArrayList<Map.Entry>();
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
		boolean flag= false;
		if (node == null) {
			put(toKey,null);
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
			flag=true;
		}
		if(flag==true) {
			headMapAdd(headMapArray, node.getLeftChild());
			remove(toKey);
		}
		else
			headMapAdd(headMapArray, node);
		return headMapArray;
	}

	@Override
	public Set keySet() {
		Set<Comparable> set = new HashSet<Comparable>();
		INode node = this.redBlackTree.getRoot();
		addKeyToSet(node,set);
		return set;
		
		
	}
	private  void addKeyToSet(INode node,Set<Comparable> set) {
		if(node==null)
			return;
		addKeyToSet(node.getLeftChild(),set);
		addKeyToSet(node.getRightChild(),set);
		set.add(node.getKey());
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
		return new MapEntry<T, V>((T) node.getKey(), (V) node.getValue());
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
			boolean removed = this.redBlackTree.delete(entry.getKey());
			if(removed){
				this.redBlackTree.size--;
			}
		}
		return entry;
	}

	@Override
	public Map.Entry pollLastEntry() {
		Map.Entry<T, V> lastEntry = lastEntry();
		if (lastEntry != null) {
			boolean removed = this.redBlackTree.delete(lastEntry.getKey());
			if(removed){
				this.redBlackTree.size--;
			}
		}
		return lastEntry;
	}

	@Override
	public void put(Comparable key, Object value) {
		this.redBlackTree.insert(key, value);
	}

	@Override
	public void putAll(Map map) {
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
		boolean removed = this.redBlackTree.delete(key);
		if( removed ) this.redBlackTree.size--;
		return removed;
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
		if(node==null)
			return;
		addValueToCol(node.getLeftChild(),col);
		addValueToCol(node.getRightChild(),col);
		col.add(node.getValue());
	}
	
}
