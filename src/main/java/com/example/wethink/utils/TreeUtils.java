package com.example.wethink.utils;

import com.example.wethink.model.Comments;
import com.example.wethink.model.TreeNode;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class TreeUtils {

    public static Map<String, Comments> CommentsToId(List<Comments> commentsList){
        Map<String, Comments> commentsMap = new HashMap<>();
        for (Comments comments : commentsList) {
            commentsMap.put(comments.getId(), comments);
        }
        return commentsMap;
    }

    /**
     * 把list转成一对多的map,key是parentId
     * @return Map<String, List<TreeNode>>
     */
    public static Map<String, List<TreeNode>> CommentsListToParentId(List<Comments> commentsList){
        Map<String, List<TreeNode>> commentsMap = new HashMap<>();
        for (Comments comments : commentsList) {
            List<TreeNode> childComments = commentsMap.get(comments.getParentId());
            if(childComments == null || childComments.size() == 0){
                childComments = new ArrayList<>();
            }
            TreeNode child = new TreeNode();
            BeanUtils.copyProperties(comments, child);
            childComments.add(child);
            commentsMap.put(comments.getParentId(), childComments);
        }
        return commentsMap;
    }
    /**
     * 把CommentsList 转换成CommentsTree
     * @param commentsMap
     * @return TreeNode
     */
    public static TreeNode CommentsListToCommentsTree(Map<String, List<TreeNode>> commentsMap){
        TreeNode root = new TreeNode();
        // 初始化root, 将他的child存到children数组里面.
        List<TreeNode> comments = commentsMap.get("root");
        root.setChildren(comments);
        // 构建评论树.
        buildTree(root, commentsMap);
        return root;
    }

    /**
     * 多叉树的递归构建
     * @param root
     * @param commentsMap
     */
    public static void buildTree(TreeNode root, Map<String, List<TreeNode>> commentsMap){
        //如果没有孩子，就跳出递归.
        if(root.getChildren() == null || root.getChildren().size() == 0){
            return;
        }
        for (TreeNode child : root.getChildren()) {
            List<TreeNode> childComments = commentsMap.get(child.getId());
            child.setChildren(childComments);
            buildTree(child, commentsMap);
        }
    }
}
