package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node implements INode{
    static final boolean RED   = true;
    static final boolean BLACK = false;
    private INode parentNode;
    private INode leftChild;
    private INode rightChild;
    private Comparable key;
    private Object value;
    private boolean color = RED;

    @Override
    public void setParent(INode parent) {
        this.parentNode=parent;
    }

    @Override
    public INode getParent() {
        return this.parentNode;
    }

    @Override
    public void setLeftChild(INode leftChild) {
        this.leftChild=leftChild;
    }

    @Override
    public INode getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(INode rightChild) {
        this.rightChild=rightChild;
    }

    @Override
    public INode getRightChild() {
        return this.rightChild;
    }

    @Override
    public Comparable getKey() {
        return this.key;
    }

    @Override
    public void setKey(Comparable key) {
        this.key=key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value=value;
    }

    @Override
    public boolean getColor() {
        return this.color;
    }

    @Override
    public void setColor(boolean color) {
        this.color=color;
    }

    @Override
    public boolean isNull() {
        if(this.key == null){
            return true;
        }
        return false;
    }
}
