package searching_supp;

import org.junit.Test;
import static org.junit.Assert.*;

public class RBTTest {
    
    @Test
    public void testSimple(){
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < 1000; i++) {
            tree.put(i, i);
            assertTrue(tree.isBalanced());
        }
        for (int i = 0; i < 500; i++) {
            tree.delete(i);
            assertTrue(tree.isBalanced());
            assertEquals(tree.get(i), null);
        }
    }
}
