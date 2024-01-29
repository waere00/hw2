import java.util.ArrayList;
import java.util.List;

public class Tree {
    private TreeNode root;
    public Tree(TreeNode root) {
        this.root = root;
    }
    public TreeNode getRoot() {
        return root;
    }

    public List<TreeNode> getAllNodes() {
        List<TreeNode> allNodes = new ArrayList<>();
        findChildNodes(root, allNodes);
        return allNodes;
    }

    private void findChildNodes(TreeNode node, List<TreeNode> allNodes) {
        if (node != null) {
            allNodes.add(node);
            for (TreeNode child : node.getChildren()) {
                findChildNodes(child, allNodes);
            }
        }
    }

    public List<TreeNode> getAllLeaves() {
        List<TreeNode> leaves = new ArrayList<>();
        for (TreeNode node : getAllNodes()) {
            if (node.isLeaf() && !node.isRoot()) {
                leaves.add(node);
            }
        }
        return leaves;
    }
}
