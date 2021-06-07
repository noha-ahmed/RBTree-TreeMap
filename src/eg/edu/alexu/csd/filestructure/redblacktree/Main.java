package eg.edu.alexu.csd.filestructure.redblacktree;

public class Main {

    public static void main(String[] args){
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
        tree.printHelper(tree.getRoot(),"",false);
        System.out.println(tree.delete(33));
        tree.printHelper(tree.getRoot(),"",false);

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
}
