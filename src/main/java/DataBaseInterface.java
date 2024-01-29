import java.sql.SQLException;
import java.util.List;

public interface DataBaseInterface {
    List<Tree> readTrees() throws SQLException;
}
