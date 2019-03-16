package  niuke;

import com.mlh.modules.algorithm.niuke.TreeNode;
import com.mlh.modules.algorithm.niuke.TreePrinter;
import org.junit.Test;

/**
 * @author: linghan.ma
 * @DATE: 2019/2/13
 * @description:
 */
public class TreePrinterTest {

    @Test
    public void testTreePrinter(){
        TreeNode root = new TreeNode(0);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        TreePrinter treePrinter = new TreePrinter();
        int[][] result = treePrinter.printTree(root);
        int l = 0;
        for(int i=0;i<result.length;i++){
            String a = "";
            for(int j=0;j<result[i].length;j++){
                a = a+result[i][j]+" ";
            }
            System.out.println(l+":"+a);
            l++;
        }
    }
}
