import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseService implements DataBaseInterface {
    private String url;
    private String user;
    private String password;

    public DatabaseService(String dbType) throws IOException {
        Properties props = new Properties();
        try (InputStream input = DatabaseService.class.getClassLoader().getResourceAsStream("database.properties")) {
            props.load(input);
            this.url = props.getProperty(dbType + ".url");
            this.user = props.getProperty(dbType + ".user");
            this.password = props.getProperty(dbType + ".password");
        }
    }

    @Override
    public List<Tree> readTrees() throws SQLException {
        Map<Integer, TreeNode> nodeMap = new HashMap<>();
        Map<Integer, Tree> treeMap = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, parent_id FROM TREES")) {

            while (rs.next()) {
                int nodeId = rs.getInt("id");
                int parentId = rs.getInt("parent_id");

                TreeNode node = nodeMap.computeIfAbsent(nodeId, TreeNode::new);
                TreeNode parent = nodeMap.computeIfAbsent(parentId, TreeNode::new);

                if (nodeId != parentId) {
                    parent.addChild(node);
                } else {
                    treeMap.putIfAbsent(nodeId, new Tree(node));
                }
            }
        }
        return new ArrayList<>(treeMap.values());
    }
}
