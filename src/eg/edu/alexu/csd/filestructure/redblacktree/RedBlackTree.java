package eg.edu.alexu.csd.filestructure.redblacktree;

public class RedBlackTree implements IRedBlackTree {

    INode root = null;

    @Override
    public INode getRoot() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object search(Comparable key) {
        return null;
    }

    @Override
    public boolean contains(Comparable key) {
        return false;
    }

    private INode leftRotation(Node node){
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
    private INode rightRotation(Node node){
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
    private void reColor(Node node){
        if(node.getColor()){
            node.setColor(false);
        }else{
            node.setColor(true);
        }
    }

    @Override
    public void insert(Comparable key, Object value) {
        Node node = new Node();

    }

    @Override
    public boolean delete(Comparable key) {
        return false;
    }
}
