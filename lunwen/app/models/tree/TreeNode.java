//package models.tree;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import play.db.jpa.Model;
//import controllers.CRUD.Hidden;
//
//@MappedSuperclass
//public class TreeNode extends Model {
//
//    /** 上级id */
//    @Hidden
//    public Long parent_id;
//
//    public int level;
//
//    @Hidden
//    public int node_index;
//
//    @Hidden
//    public int child_count;
//
//    @Transient
//    public List<TreeNode> children = new ArrayList<TreeNode>();
//
//    protected TreeNode(Long parent_id, int level, int node_index, int child_count) {
//        super();
//        this.parent_id = parent_id;
//        this.level = level;
//        this.node_index = node_index;
//        this.child_count = child_count;
//        this.children = new ArrayList<TreeNode>();
//    }
//
//    public TreeNode() {
//        this(-1L, 0, 0, 0);
//    }
//
//    public TreeNode(TreeNode parentNode) {
//        this(parentNode.id, parentNode.level + 1, parentNode.maxChildIndex(), 0);
//    }
//
//    public int maxChildIndex() {
//        int maxIndex = -1;
//        for (TreeNode n : this.children) {
//            if (n.node_index > maxIndex)
//                maxIndex = n.node_index;
//        }
//        return maxIndex + 1;
//    }
//
//    public boolean addChild(TreeNode node) {
//        return children.add(node);
//    }
//
//    public boolean isRootNode(Long pid) {
//        return this.parent_id == pid;
//    }
//
//    public List<TreeNode> getChildren() {
//        return children;
//    }
//
//}
