import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 需求条目相关操作的数据库接口
 */
public class RequirementDAO {
    /**
     * 增加需求
     * @param requirement 需求条目对象
     * @return 若成功增加需求, 返回true; 否则返回false
     */
    public boolean addRequirements(Requirements requirement) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "INSERT INTO Requirements (RequirementsID, DocID, RequirementsNote) VALUES " +
                    "('" + requirement.requirementID + "', '" + requirement.docID + "', '" + requirement.requirementNote + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 修改需求描述
     * @param reqID 要修改的需求条目编号
     * @param reqNote 新的需求描述
     * @return 若成功修改需求描述, 返回true; 否则返回false
     */
    public boolean modifyRequirements(String reqID, String reqNote) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "UPDATE Requirements SET RequirementsNote = '" + reqNote +
                    "' WHERE RequirementsID = '" + reqID + "'" ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除需求条目
     * @param reqID 要删除的需求条目编号
     * @return 若成功删除需求条目, 返回true; 否则返回false
     */
    public boolean delRequirements(String reqID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "DELETE FROM Requirements WHERE RequirementsID = '" + reqID + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取某一文档中的所有需求条目
     * @param docID 文档编号
     * @return 若成功获取所有需求条目, 返回true; 否则返回false
     */
    public boolean getRequirement(String docID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "SELECT * FROM Requirements WHERE DocID = '" + docID + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("RequirementsID\tDocID\tRequirementsNote\tLinkID");
            while(resultSet.next()) {
                // 通过字段搜索
                String reqID = resultSet.getString("RequirementsID");
                String reqNote = resultSet.getString("RequirementsNote");
                String linkID = resultSet.getString("linkID");
                // 输出数据
                System.out.print(reqID + "\t\t\t");
                System.out.print(docID + "\t\t");
                System.out.print(reqNote + "\t\t\t");
                System.out.println(linkID);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 当某一文档被删除时, 删除其中的所有需求条目
     * @param docID 被删除的文档编号
     * @return 若成功删除所有需求条目, 返回true; 否则返回false
     */
    public boolean delAll(String docID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "DELETE FROM Requirements WHERE docID = '" + docID + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 设置需求链接, 将编号为reqID的需求条目链接到编号为linkID的需求条目
     * @return 若成功创建链接, 返回true; 否则返回false
     */
    public boolean setLink(String reqID, String linkID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "UPDATE Requirements SET LinkID = '" + linkID + "' WHERE RequirementsID = '" + reqID + "'" ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 跳转到需求条目的链接条目
     * @param reqID 需求条目编号
     * @return 若成功跳转, 返回true; 否则返回false
     */
    public boolean getLink(String reqID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "SELECT * FROM Requirements WHERE RequirementsID = '" + reqID + "'" ;
            ResultSet resultSet = stmt.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("No Requirement with ID " + "reqID!");
                return false;
            }
            String linkID = resultSet.getString("LinkID");
            sql = "SELECT * FROM Requirements WHERE RequirementsID = '" + linkID + "'" ;
            resultSet = stmt.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Linked requirement invalid!");
                return false;
            }
            System.out.println("RequirementsID\tDocID\tRequirementsNote\tLinkID");
            System.out.print(linkID + "\t\t\t");
            System.out.print(resultSet.getString("DocID") + "\t\t");
            System.out.print(resultSet.getString("RequirementsNote") + "\t\t\t");
            System.out.println(resultSet.getString("LinkID"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}