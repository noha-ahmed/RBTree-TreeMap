package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree {

    private INode root = null;
    public int size=0;

    public int getSize() {
        return this.size;
    }

    @Override
    public INode getRoot() {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return (this.root == null);
    }

    @Override
    public void clear() {
        this.root = null;
    }

    public INode searchForNode(INode node, Comparable key) {
        if (node.isNull()) {
            return null;
        }
        if (node.getKey().compareTo(key) == 0) {
            return node;
        } else if (key.compareTo(node.getKey()) > 0) {
            return searchForNode(node.getRightChild(), key);
        } else {
            return searchForNode(node.getLeftChild(), key);
        }
    }

    @Override
    public Object search(Comparable key) {
        if(key==null){
            throw new RuntimeErrorException(new Error(),"key is null");
        }
        if (this.root == null) {
            return null;
        }
        INode node = searchForNode(this.root, key);
        if (node != null)
            return node.getValue();
        return null;
    }

    @Override
    public boolean contains(Comparable key) {

        Object value = search(key);
        if (value != null)
            return true;
        return false;
    }

    private INode leftRotation(INode node) {
        if(node.isNull()){
            return null;
        }
        if(node.getRightChild().isNull()){
            return null;
        }
        INode rightChild = node.getRightChild();
        node.setRightChild(rightChild.getLeftChild());
        if(!rightChild.getLeftChild().isNull()){
            rightChild.getLeftChild().setParent(node);
        }
        rightChild.setParent(node.getParent());
        if(node.getParent() == null){
            this.root = rightChild;
        }else if(node.getParent().getLeftChild().equals(node)){
            node.getParent().setLeftChild(rightChild);
        }else{
            node.getParent().setRightChild(rightChild);
        }
        rightChild.setLeftChild(node);
        node.setParent(rightChild);
        return rightChild;
    }


    private INode rightRotation(INode node) {
        if(node.isNull()){
            return null;
        }
        if(node.getLeftChild().isNull()){
            return null;
        }
        INode leftChild = node.getLeftChild();
        node.setLeftChild(leftChild.getRightChild());
        if(!leftChild.getRightChild().isNull()){
            leftChild.getRightChild().setParent(node);
        }
        leftChild.setParent(node.getParent());
        if(node.getParent() == null){
            this.root = leftChild;
        }else if(node.getParent().getRightChild().equals(node)){
            node.getParent().setRightChild(leftChild);
        }else{
            node.getParent().setLeftChild(leftChild);
        }
        leftChild.setRightChild(node);
        node.setParent(leftChild);
        return leftChild;
    }

    private void reColor(INode node) {
        if (node.getColor()) {
            node.setColor(Node.BLACK);
        } else {
            node.setColor(Node.RED);
        }
    }

    private void fixInsert(INode node){
        INode uncle = null;
        while(node.getParent().getColor() == Node.RED){
            if(isLeftChild(node.getParent())){
                uncle = node.getParent().getParent().getRightChild();
                if(uncle.getColor() == Node.RED){
                    uncle.setColor(Node.BLACK);
                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    node = node.getParent().getParent();
                }
                else{
                    if(!isLeftChild(node)){
                        node  = node.getParent();
                        leftRotation(node);
                    }
                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    rightRotation(node.getParent().getParent());
                }
            }else{
                uncle = node.getParent().getParent().getLeftChild();
                if(uncle.getColor() == Node.RED){
                    uncle.setColor(Node.BLACK);
                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    node = node.getParent().getParent();
                }else{
                    if(isLeftChild(node)){
                        node = node.getParent();
                        rightRotation(node);
                    }
                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    leftRotation(node.getParent().getParent());
                }
            }
            if(node == this.root){
                break;
            }
        }
        root.setColor(Node.BLACK);
    }

    @Override
    public void insert(Comparable key, Object value)  {
        if(key == null || value == null){
            throw new RuntimeErrorException(new Error(),"key is null");
        }
        // creating node
        INode newNode = new Node();
        newNode.setValue(value);
        newNode.setKey(key);
        INode nilRight = new Node();
        nilRight.setColor(Node.BLACK);
        nilRight.setParent(newNode);
        newNode.setRightChild(nilRight);
        INode nilLeft = new Node();
        nilLeft.setColor(Node.BLACK);
        nilLeft.setParent(newNode);
        newNode.setLeftChild(nilLeft);

        // normal BST insert
        INode parent = null;
        INode x = this.root;
        if(x != null) {
            while (!x.isNull()) {
                parent = x;
                if(newNode.getKey().compareTo(parent.getKey()) == 0){
                    parent.setValue(value);
                    return;
                } else if (newNode.getKey().compareTo(parent.getKey()) > 0) {
                    x = x.getRightChild();
                } else if(newNode.getKey().compareTo(parent.getKey()) < 0){
                    x = x.getLeftChild();
                }
            }
        }
        size++;
        newNode.setParent(parent);
        if(parent == null){
            this.root = newNode;
        }else if(newNode.getKey().compareTo(parent.getKey()) > 0){
            parent.setRightChild(newNode);
        }else {
            parent.setLeftChild(newNode);
        }

        if(newNode.getParent() == null){
            newNode.setColor(Node.BLACK);
            return;
        }

        if(newNode.getParent().getParent() == null){
            return;
        }

        // fix coloring of the tree
        fixInsert(newNode);

    }

    @Override
    public boolean delete(Comparable key) {
        if(key==null){
            throw new RuntimeErrorException(new Error(),"key is null");
        }
        boolean initialColorNode;
        boolean initialColorChild;


        //search function

        INode node = searchForNode(root, key);
        if (node == null) return false;
        if ( root.getKey().equals(key) && root.getRightChild().isNull() && root.getLeftChild().isNull()){
            root = null;
            return true;
        }

        //step1
        if (node.getLeftChild().isNull()) {
            initialColorNode = node.getColor();
            initialColorChild = node.getRightChild().getColor();

            transplant(node, node.getRightChild());
            node = node.getRightChild();
        }
        //step2
        else if (node.getRightChild().isNull()) {
            initialColorNode = node.getColor();
            initialColorChild = node.getLeftChild().getColor();
            transplant(node, node.getLeftChild());
            node = node.getLeftChild();
        }
        //step3
        else {
            INode newNode = getSuccessor(node, key, new Node());
            node.setValue(newNode.getValue());
            node.setKey(newNode.getKey());
            //  newNode.getParent().setLeftChild(newNode.getRightChild());
            transplant(newNode, newNode.getRightChild());
            initialColorNode = newNode.getColor();
            initialColorChild = newNode.getRightChild().getColor();
            node = newNode.getRightChild();
        }

        if(root.isNull()){
            root = null;
            return true;
        }

        if (initialColorNode == Node.RED || initialColorChild == Node.RED) {
            node.setColor(Node.BLACK);
        } else {
            fixTree(node);
        }

        return true;
    }

    /**
     * putting v in place of u and connecting v with u's parent
     *
     * @param u (parent)
     * @param v (child)
     */
    private void transplant(INode u, INode v) {
        if (u.getParent() == null) {
            this.root = v;
        } else if (isLeftChild(u)) {
            u.getParent().setLeftChild(v);
        } else {
            u.getParent().setRightChild(v);
        }
        v.setParent(u.getParent());
    }

    private void fixTree(INode node) {
        while ((node != this.root) && (isBlack(node))) {
            INode sibling = getSibling(node);
            //case1 sibling is red
            if (!isBlack(sibling)) {
                sibling.setColor(Node.BLACK);
                sibling.getParent().setColor(Node.RED);
                if (isLeftChild(sibling)) {
                    rightRotation(sibling.getParent());
                } else {
                    leftRotation(sibling.getParent());
                }
                sibling = getSibling(node);
            }

            //case sibling is black and both children are black
            if (isBlack(sibling) && isBlack(sibling.getRightChild()) && isBlack(sibling.getLeftChild())) {
                sibling.setColor(Node.RED);
                node = sibling.getParent();
            }
            //at least one of children is red
            else {
                //sibling is right child
                if (!isLeftChild(sibling)) {
                    if (isBlack(sibling.getRightChild())) {
                        sibling.getLeftChild().setColor(Node.BLACK);
                        sibling.setColor(Node.RED);
                        rightRotation(sibling);
                        sibling = node.getParent().getRightChild();
                    }
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(Node.BLACK);
                    sibling.getRightChild().setColor(Node.BLACK);
                    leftRotation(node.getParent());
                    node = root;

                }
                //sibling is left child
                else {
                    if (isBlack(sibling.getLeftChild())) {
                        sibling.getRightChild().setColor(Node.BLACK);
                        sibling.setColor(Node.RED);
                        leftRotation(sibling);
                        sibling = node.getParent().getLeftChild();
                    }
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(Node.BLACK);
                    sibling.getLeftChild().setColor(Node.BLACK);
                    rightRotation(node.getParent());
                    node = root;
                }

            }

        }
        node.setColor(Node.BLACK);
    }

    private boolean isBlack(INode node) {
        return (node.getColor() == Node.BLACK);
    }

    private INode getSibling(INode node) {
        if (isLeftChild(node)) {
            return node.getParent().getRightChild();
        }
        return node.getParent().getLeftChild();
    }

    private boolean isLeftChild(INode node) {
        return node.getParent().getLeftChild().equals(node);
    }

    public INode getPredecessor(INode node, Comparable key, INode pre) {
        if(key==null){
            throw new RuntimeErrorException(new Error(),"key is null");
        }
        if (node.isNull()) {
            return pre;
        }
        if (node.getKey().equals(key)) {
            if (!node.getLeftChild().isNull()) {
                INode currentNode = node.getLeftChild();
                while (!currentNode.getRightChild().isNull()) {
                    currentNode = currentNode.getRightChild();
                }
                pre = currentNode;
            }
            return pre;
        } else if (node.getKey().compareTo(key) == 1) {
            return getPredecessor(node.getLeftChild(), key, pre);
        } else {
            pre = node;
            return getPredecessor(node.getRightChild(), key, pre);
        }

    }

    public INode getSuccessor(INode node, Comparable key, INode suc) {
        if(key==null){
            throw new RuntimeErrorException(new Error(),"key is null");
        }
        if (node.isNull()) {
            return suc;
        }
        if (node.getKey().equals(key)) {
            if (!node.getRightChild().isNull()) {
                INode currentNode = node.getRightChild();
                while (!currentNode.getLeftChild().isNull()) {
                    currentNode = currentNode.getLeftChild();
                }
                suc = currentNode;
            }
            return suc;
        } else if (node.getKey().compareTo(key) == 1) {
            suc = node;
            return getSuccessor(node.getLeftChild(), key, suc);
        } else {
            return getSuccessor(node.getRightChild(), key, suc);
        }
    }

    public void printHelper(INode root, String indent, boolean last) {
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            String sColor = root.getColor() == true ? "RED " : "BLACK ";
            System.out.println(root.getKey() + " " + sColor);
            printHelper(root.getLeftChild(), indent, false);
            printHelper(root.getRightChild(), indent, true);
        }
    }
}