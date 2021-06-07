package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree {

    private INode root = null;

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
        INode parent = node.getParent();
        INode rightChild = node.getRightChild();
        INode newRightChild = rightChild.getLeftChild();
        if (parent != null) {
            if (parent.getLeftChild().equals(node)) {
                parent.setLeftChild(rightChild);
            } else if (parent.getRightChild().equals(node)) {
                parent.setRightChild(rightChild);
            }
        } else {
            this.root = rightChild;
        }
        rightChild.setParent(parent);
        node.setParent(rightChild);
        rightChild.setLeftChild(node);
        node.setRightChild(newRightChild);
        newRightChild.setParent(node);
        return rightChild;
    }

    private INode rightRotation(INode node) {
        if(node.isNull()){
            return null;
        }
        if(node.getLeftChild().isNull()){
            return null;
        }
        INode parent = node.getParent();
        INode leftChild = node.getLeftChild();
        INode newLeftChild = leftChild.getRightChild();
        if (parent == null) {
            this.root = leftChild;
        } else if (parent.getRightChild() == node) {
            parent.setRightChild(leftChild);
        } else {
            parent.setLeftChild(leftChild);
        }
        node.setLeftChild(newLeftChild);
        leftChild.setRightChild(node);
        leftChild.setParent(parent);
        node.setParent(leftChild);
        newLeftChild.setParent(node);
        return leftChild;
    }

    private void reColor(INode node) {
        if (node.getColor()) {
            node.setColor(Node.BLACK);
        } else {
            node.setColor(Node.RED);
        }
    }

    public void normalInsert(INode root, INode node) {
        if (root.isNull()) {
            INode parent = root.getParent();
            if (parent.getRightChild() == root) {
                parent.setRightChild(node);
            } else {
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
        if (node.getKey().compareTo(root.getKey()) > 0) {
            normalInsert(root.getRightChild(), node);
        } else if (node.getKey().compareTo(root.getKey()) < 0) {
            normalInsert(root.getLeftChild(), node);
        } else {
            root.setValue(node.getValue());
        }
    }

    private void fixInsert(INode node) {
        if (node == root) {
            node.setColor(false);
            return;
        }
        if (node.getParent().getColor()) {
            INode parent = node.getParent();
            INode grandParent = parent.getParent();
            INode uncle = null;
            if (grandParent.getRightChild() == parent) {
                // if parent is right child, uncle is the left child
                uncle = grandParent.getLeftChild();
            } else {
                // if parent is left child, uncle is the right child
                uncle = grandParent.getRightChild();
            }
            if (uncle.getColor()) {
                //
                reColor(parent);
                reColor(uncle);
                reColor(grandParent);
                fixInsert(grandParent);
            } else {
                // parent is red and uncle is black
                if ((grandParent.getRightChild() == parent) && (parent.getRightChild() == node)) {
                    // right right case
                    leftRotation(grandParent);
                    reColor(grandParent);
                    reColor(parent);
                } else if ((grandParent.getRightChild() == parent) && (parent.getLeftChild() == node)) {
                    // right left case
                    rightRotation(parent);
                    leftRotation(grandParent);
                    reColor(grandParent);
                    reColor(node);
                } else if ((grandParent.getLeftChild() == parent) && (parent.getLeftChild() == node)) {
                    // left left case
                    rightRotation(grandParent);
                    reColor(grandParent);
                    reColor(parent);
                } else {
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
    public void insert(Comparable key, Object value)  {
        if(key!=null){
        // creating new node
        INode node = new Node();
        node.setKey(key);
        node.setValue(value);
        // root is null
        if (root == null) {
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
        } else {
            // else normal BST insert
            normalInsert(this.root, node);
        }
        /// checking for red parent
        fixInsert(node);}
        else{
            throw new RuntimeErrorException(new Error(),"key is null");
        }
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