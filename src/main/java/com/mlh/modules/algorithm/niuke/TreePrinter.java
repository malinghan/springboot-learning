package com.mlh.modules.algorithm.niuke;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: linghan.ma
 * @DATE: 2019/2/13
 * @description:
 */
public class TreePrinter {
    public int[][] printTree(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> temp = new ArrayList<Integer>();//存上一层的数据
        Queue<TreeNode> queue = new LinkedList<TreeNode>();//队列
        queue.offer(root);
        TreeNode node=null;//当前遍历的节点
        TreeNode last = root;//最后节点
        TreeNode nlast = last;//所在层的最后节点

        if(root == null){
            return null;
        }

        while(!queue.isEmpty()){
            node = queue.poll();//弹出去
            temp.add(node.val);
            if(node.left!=null){
                queue.offer(node.left);//弹进来
                nlast = node.left;
            }
            if(node.right!=null){
                queue.offer(node.right);
                nlast = node.right;
            }
            if(node == last){
                res.add(temp);
                temp = new ArrayList<Integer>();
                last = nlast;
            }
        }

        int[][] result = new int[res.size()][];
        for(int i=0;i<res.size();i++){
            result[i] = new int[res.get(i).size()];
            for(int j=0;j<result[i].length;j++){
                result[i][j] = res.get(i).get(j);
            }
        }
        return result;
    }
}
