package eg.edu.alexu.csd.filestructure.redblacktree;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args){
      /*  RedBlackTree<Integer, Integer> tree = new RedBlackTree<Integer, Integer>();
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
        IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

        try {
            Random r = new Random();
            ArrayList<Integer> keysToSearch = new ArrayList<>();
            for(int i = 0; i < 1000; i++) {
                int key = r.nextInt(10000);
                if (i % 50 == 0)
                    keysToSearch.add(key);
                redBlackTree.insert(key, "toto" + key);
            }
            for (int i = 0; i < keysToSearch.size(); i++) {
                String ans = redBlackTree.search(keysToSearch.get(i));
                Assert.assertEquals("toto" + keysToSearch.get(i), ans);
            }
        }catch (Throwable e) {
            TestRunner.fail("Fail to search for a key in the tree", e);
        }
    }
}
