package com.mlh.modules.algorithm.niuke;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2019/2/13
 * @description:
 *       1
 *    /     \
 *   2       3
 *  / \    /  \
 *  4  #  #    5
 *
 *  前序遍历 pre
 */
public class TreeSerialization {

    public String preorderTreeSerialize(TreeNode node) {
        String res = "";
        if(node == null){
            return  res+"#!";
        }else {
            res = res + node.getVal() + "!";
            if( !(node.left == null && node.right ==null)){
                res = res+ preorderTreeSerialize(node.left)+preorderTreeSerialize(node.right);
            }
        }
       return res;
    }

    public TreeNode preorderTreeDeserialize(String str) {
        if(str==null||str.length()<=0) return null;
        String[] strs = str.split("!");
        TreeNode root = this.deSerializeCore(strs);
        return root;
    }

    int index=0;

    private TreeNode deSerializeCore( String[] strs){
        TreeNode newNode = new TreeNode(0);
        if(index >= strs.length){
           return newNode;
        }
        if("#".equals(strs[index])){
            index++;
            return null;
        }else{
            //如果不为空结点，则先恢复这个结点

            newNode.val=Integer.parseInt(strs[index]+"");
            //千万注意在递归调用之前(使用了一个元素建立结点之后)，要将index向后移动1位
            index++;
            //恢复左子树
            newNode.left=this.deSerializeCore(strs);
            //恢复右子树
            newNode.right=this.deSerializeCore(strs);
            //建立二叉树完成，返回根结点
            return newNode;
        }
    }
}
