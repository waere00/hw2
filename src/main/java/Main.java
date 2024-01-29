import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseService db = new DatabaseService("h2"); // "postgres" для PostgreSQL
            List<Tree> trees = db.readTrees();
            int totalLeaves = 0;
            for (Tree tree : trees) {
                List<TreeNode> leaves = tree.getAllLeaves();
                totalLeaves += leaves.size();
            }

            try (PrintWriter writer = new PrintWriter("output.csv")) {
                writer.println(totalLeaves);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try (PrintWriter writer = new PrintWriter("output.csv")) {
                writer.println("0,0");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
