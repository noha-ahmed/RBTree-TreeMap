package eg.edu.alexu.csd.filestructure.redblacktree;

public class RedBlackTree<T extends Comparable<T>, V>  implements IRedBlackTree {

    private INode root = null;

    @Override
    public INode getRoot() {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    public INode searchForNode(INode node, Comparable key){
        if(node.isNull()){
            return null;
        }
        if(node.getKey().compareTo(key) == 0){
            return node;
        }else if(key.compareTo(node.getKey()) > 0){
            return searchForNode(node.getRightChild(), key);
        }else {
            return searchForNode(node.getLeftChild(), key);
        }
    }


    @Override
    public Object search(Comparable key) {
        INode node = searchForNode(this.root, key);
        if(node != null)
            return node.getValue();
        return null;
    }

    @Override
    public boolean contains(Comparable key) {
        Object value  = search(key);
        if(value != null)
            return true;
        return false;
    }

    private INode leftRotation(INode node){
        INode parent =  node.getParent();
        INode rightChild = node.getRightChild();
        INode newRightChild = rightChild.getLeftChild();
        if(parent != null) {
            if (parent.getLeftChild().equals(node)) {
                parent.setLeftChild(rightChild);
            } else if (parent.getRightChild().equals(node)) {
                parent.setRightChild(rightChild);
            }
        }else{
            this.root = rightChild;
        }
        rightChild.setParent(parent);
        node.setParent(rightChild);
        rightChild.setLeftChild(node);
        node.setRightChild(newRightChild);
        newRightChild.setParent(node);
        return rightChild;
    }
    private INode rightRotation(INode node){
        INode parent = node.getParent();
        INode leftChild = node.getLeftChild();
        INode newLeftChild = leftChild.getRightChild();
        if(parent==null){
            this.root=leftChild;
        }else if(parent.getRightChild() == node){
            parent.setRightChild(leftChild);
        }else{
            parent.setLeftChild(leftChild);
        }
        node.setLeftChild(newLeftChild);
        leftChild.setRightChild(node);
        leftChild.setParent(parent);
        node.setParent(leftChild);
        newLeftChild.setParent(node);
        return leftChild;
    }
    private void reColor(INode node){
        if(node.getColor()){
            node.setColor(false);
        }else{
            node.setColor(true);
        }
    }

    public void normalInsert(INode root, INode node){
        if(root.isNull()){
            INode parent = root.getParent();
            if(parent.getRightChild() == root){
                parent.setRightChild(node);
            }else{
                parent.setLeftChild(node);
            }
            node.setParent(parent);
            INode nilLeft = new Node();
            INode nilRight = new Node();
            nilLeft.setColor(false);
            nilRight.setColor(false);
            node.setLeftChild(nilLeft);
            node.setRightChild(nilRight);
            nilLeft.setParent(node);
            nilRight.setParent(node);
            return;
        }
        if(node.getKey().compareTo(root.getKey()) > 0){
            normalInsert(root.getRightChild(), node);
        }else if(node.getKey().compareTo(root.getKey()) < 0){
            normalInsert(root.getLeftChild(), node);
        }else {
            root.setValue(node.getValue());
        }
    }

    private void fixInsert(INode node){
        if(node == root){
            node.setColor(false);
            return;
        }
        if(node.getParent().getColor()){
            INode parent = node.getParent();
            INode grandParent = parent.getParent();
            INode uncle = null;
            if(grandParent.getRightChild() == parent){
                // if parent is right child, uncle is the left child
                uncle = grandParent.getLeftChild();
            }else{
                // if parent is left child, uncle is the right child
                uncle = grandParent.getRightChild();
            }
            if(uncle.getColor()){
                //
                reColor(parent);
                reColor(uncle);
                reColor(grandParent);
                fixInsert(grandParent);
            }else{
                // parent is red and uncle is black
                if((grandParent.getRightChild() == parent) && (parent.getRightChild() == node) ){
                    // right right case
                    leftRotation(grandParent);
                    reColor(grandParent);
                    reColor(parent);
                }else  if((grandParent.getRightChild() == parent) && (parent.getLeftChild() == node)){
                    // right left case
                    rightRotation(parent);
                    leftRotation(grandParent);
                    reColor(grandParent);
                    reColor(node);
                }else if((grandParent.getLeftChild() == parent) && (parent.getLeftChild() == node)){
                    // left left case
                    rightRotation(grandParent);
                    reColor(grandParent);
                    reColor(parent);
                }else{
                    // left right case
                    leftRotation(parent);
                    rightRotation(grandParent);
                    reColor(grandParent);
                    reColor(node);
                }
            }
        }
    }

    @Override
    public void insert(Comparable key, Object value) {
        // creating new node
        INode node = new Node();
        node.setKey(key);
        node.setValue(value);

        // root is null
        if(root == null){
            root = node;
            INode nilLeft = new Node();
            INode nilRight = new Node();
            nilLeft.setColor(false);
            nilRight.setColor(false);
            node.setRightChild(nilRight);
            node.setLeftChild(nilLeft);
            nilLeft.setParent(node);
            nilRight.setParent(node);
            reColor(node);
        }else {
            // else normal BST insert
            normalInsert(this.root, node);
        }
        /// checking for red parent
        fixInsert(node);
    }

    @Override
    public boolean delete(Comparable key) {
        return false;
    }
}
