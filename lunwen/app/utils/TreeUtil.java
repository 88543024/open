//package utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import models.tree.TreeNode;
//
//public class TreeUtil<T extends TreeNode> {
//
//    public TreeUtil() {
//
//    }
//
//    private void findParent(T node, List<T> roots) {
//        if ((roots == null) || (roots.size() == 0)) {
//            return;
//        }
//        for (T parent : roots) {
//            if (node.parent_id == parent.getId()) {
//                parent.addChild(node);
//                break;
//            } else {
//                findParent(node, (List<T>) parent.getChildren());
//            }
//        }
//    }
//
//    public List<T> buildTree(List<T> list, Long pid) {
//        List<T> tree = new ArrayList<T>();
//        for (T node : list) {
//            if (node.isRootNode(pid)) {
//                tree.add(node);
//            } else {
//                findParent(node, tree);
//            }
//        }
//        return tree;
//    }
//}
