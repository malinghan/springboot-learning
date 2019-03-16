package  niuke;

import com.mlh.modules.algorithm.niuke.TreeNode;
import com.mlh.modules.algorithm.niuke.TreeSerialization;
import org.junit.Test;

/**
 * @author: linghan.ma
 * @DATE: 2019/2/13
 * @description:
 */
public class TreeSerializeTest {

    @Test
    public void testTreeSerialize(){
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node2.setRight(node4);

        TreeSerialization treeSerialization = new TreeSerialization();

        System.out.println(treeSerialization.preorderTreeSerialize(root));
    }

    @Test
    public void preorderTreeDeserialize(){
       String  r = "1!2!4!#!3!#!5!";
        TreeSerialization treeSerialization = new TreeSerialization();
        TreeNode treeNode =  treeSerialization.preorderTreeDeserialize(r);
        System.out.println(treeNode);
    }
}
