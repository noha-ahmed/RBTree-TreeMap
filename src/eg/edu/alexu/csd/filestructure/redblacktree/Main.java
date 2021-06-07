package eg.edu.alexu.csd.filestructure.redblacktree;

public class Main {

    public static void main(String[] args){
        test3();

    }

    static void  test1(){
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<Integer, Integer>();
        tree.insert(33,33);
        tree.insert(13,13);
        tree.insert(53,53);
        tree.insert(11,11);
        tree.insert(21,21);
        tree.insert(41,41);
        tree.insert(61,61);
        tree.insert(15,15);
        tree.insert(31,31);
        tree.insert(20,20);
        tree.insert(100,100);
        tree.insert(67,67);
        tree.insert(-5,-5);
        tree.insert(70,70);
        //tree.printHelper(tree.getRoot(),"",false);
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(33) + " 33");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(13 ) + " 13");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(53) + " 53" );
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(11) + " 11");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(21) + " 21");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(41) + " 41");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(61) + " 61");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(15) + " 15");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(31) + " 31");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(20) + " 20");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(100) + " 100");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(67) + " 67");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(-5) + " -5");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.delete(70) + " 70");
        BTreePrinter.printNode(tree.getRoot());
        System.out.println(tree.getRoot() +" " + tree.isEmpty());
        //   tree.printHelper(tree.getRoot(),"",false);

        // System.out.println(tree.search(33));
        // System.out.println(tree.contains(41));

        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();


        /*RedBlackTree<Integer, Integer> tree = new RedBlackTree<Integer, Integer>();
        tree.insert(33,33);
        tree.insert(13,13);
        tree.insert(53,53);
        tree.leftRotation(tree.searchForNode(tree.getRoot(),33));
        System.out.println("done");

         */
    }

    static void test2(){
        RedBlackTree<Integer, String> rbTree = new RedBlackTree<>();
        rbTree.insert(30, null);
        rbTree.insert(15, null);
        rbTree.insert(24, null);
        rbTree.insert(13, null);
        rbTree.insert(55, null);
        rbTree.insert(12, null);
        rbTree.insert(23, null);
        rbTree.insert(9, null);
        rbTree.insert(10, null);
        rbTree.insert(11, null);
        rbTree.insert(8, null);
        rbTree.insert(7, null);
        rbTree.insert(3, null);
        rbTree.insert(16, null);
        rbTree.insert(18, null);
        rbTree.insert(22, null);
        rbTree.insert(25, null);
        rbTree.insert(32, null);
        rbTree.insert(31, null);
        rbTree.insert(66, null);
        rbTree.delete(30);
        rbTree.delete(15);
        rbTree.insert(77, null);
        rbTree.delete(66);
        rbTree.delete(7);
        rbTree.delete(8);
        rbTree.delete(16);
        String expectedInOrderTraversal = "3 Red 9 Black 10 Black 11 Red 12 Black 13 Black " +
                "18 Black 22 Black 23 Black 24 Red 25 Black 31 Black " +
                "32 Black 55 Red 77 Black ";
        BTreePrinter.printNode(rbTree.getRoot());
    }

    static void test3(){
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<Integer, Integer>();
        tree.insert(15,0);
        BTreePrinter.printNode(tree.getRoot());
        tree.insert(14,0);
        BTreePrinter.printNode(tree.getRoot());
        tree.insert(16,0);
        BTreePrinter.printNode(tree.getRoot());
        tree.insert(13,0);
        BTreePrinter.printNode(tree.getRoot());

    }
}

